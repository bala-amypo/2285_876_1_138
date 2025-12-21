package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.AccessLog;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.Guest;
import com.example.demo.model.KeyShareRequest;
import com.example.demo.repository.AccessLogRepository;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.KeyShareRequestRepository;
import com.example.demo.service.AccessLogService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class AccessLogServiceImpl implements AccessLogService {

    private final AccessLogRepository accessLogRepository;
    private final DigitalKeyRepository digitalKeyRepository;
    private final GuestRepository guestRepository;
    private final KeyShareRequestRepository keyShareRequestRepository;

    public AccessLogServiceImpl(AccessLogRepository accessLogRepository,
                               DigitalKeyRepository digitalKeyRepository,
                               GuestRepository guestRepository,
                               KeyShareRequestRepository keyShareRequestRepository) {
        this.accessLogRepository = accessLogRepository;
        this.digitalKeyRepository = digitalKeyRepository;
        this.guestRepository = guestRepository;
        this.keyShareRequestRepository = keyShareRequestRepository;
    }

    @Override
    public AccessLog createLog(AccessLog log) {
        Timestamp now = Timestamp.from(Instant.now());
        if (log.getAccessTime().after(now)) {
            throw new IllegalArgumentException("Access time cannot be in the future");
        }

        DigitalKey key = digitalKeyRepository.findById(log.getDigitalKey().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Key not found"));
        
        Guest guest = guestRepository.findById(log.getGuest().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));

        boolean hasAccess = false;
        String reason = "";

        if (!key.getActive()) {
            reason = "Key is not active";
        } else if (!guest.getActive()) {
            reason = "Guest is not active";
        } else if (log.getAccessTime().before(key.getIssuedAt()) || log.getAccessTime().after(key.getExpiresAt())) {
            reason = "Access time outside key validity";
        } else if (key.getBooking().getGuest().getId().equals(guest.getId())) {
            hasAccess = true;
            reason = "Booking owner access";
        } else {
            List<KeyShareRequest> approvedShares = keyShareRequestRepository.findBySharedWithId(guest.getId());
            for (KeyShareRequest share : approvedShares) {
                if ("APPROVED".equals(share.getStatus()) && 
                    share.getDigitalKey().getId().equals(key.getId()) &&
                    !log.getAccessTime().before(share.getShareStart()) &&
                    !log.getAccessTime().after(share.getShareEnd())) {
                    hasAccess = true;
                    reason = "Approved share access";
                    break;
                }
            }
            if (!hasAccess) {
                reason = "No valid share found";
            }
        }

        log.setResult(hasAccess ? "SUCCESS" : "DENIED");
        log.setReason(reason);

        return accessLogRepository.save(log);
    }

    @Override
    public List<AccessLog> getLogsForKey(Long keyId) {
        return accessLogRepository.findByDigitalKeyId(keyId);
    }

    @Override
    public List<AccessLog> getLogsForGuest(Long guestId) {
        return accessLogRepository.findByGuestId(guestId);
    }
}