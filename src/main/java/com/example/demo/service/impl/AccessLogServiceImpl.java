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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;

@Service
public class AccessLogServiceImpl implements AccessLogService {

    private final AccessLogRepository accessLogRepository;
    private final DigitalKeyRepository digitalKeyRepository;
    private final GuestRepository guestRepository;
    private final KeyShareRequestRepository keyShareRequestRepository;

    @Autowired
    public AccessLogServiceImpl(AccessLogRepository accessLogRepository, DigitalKeyRepository digitalKeyRepository, GuestRepository guestRepository, KeyShareRequestRepository keyShareRequestRepository) {
        this.accessLogRepository = accessLogRepository;
        this.digitalKeyRepository = digitalKeyRepository;
        this.guestRepository = guestRepository;
        this.keyShareRequestRepository = keyShareRequestRepository;
    }

    @Override
    public AccessLog createLog(AccessLog log) {
        if (log.getAccessTime().isAfter(Instant.now())) {
            throw new IllegalArgumentException("Access time cannot be in the future");
        }
        DigitalKey key = digitalKeyRepository.findById(log.getDigitalKey().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Digital key not found"));
        Guest guest = guestRepository.findById(log.getGuest().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));
        boolean hasAccess = false;
        if (key.getBooking().getGuest().getId().equals(guest.getId())) {
            hasAccess = true;
        } else {
            List<KeyShareRequest> shares = keyShareRequestRepository.findBySharedWithId(guest.getId());
            for (KeyShareRequest share : shares) {
                if (share.getDigitalKey().getId().equals(key.getId()) &&
                    log.getAccessTime().isAfter(share.getShareStart()) &&
                    log.getAccessTime().isBefore(share.getShareEnd())) {
                    hasAccess = true;
                    break;
                }
            }
        }
        if (key.getActive() && hasAccess && log.getAccessTime().isAfter(key.getIssuedAt()) && log.getAccessTime().isBefore(key.getExpiresAt())) {
            log.setResult("SUCCESS");
        } else {
            log.setResult("DENIED");
        }
        log.setDigitalKey(key);
        log.setGuest(guest);
        return accessLogRepository.save(log);
    }

    @Override
    public List<AccessLog> getLogsForGuest(Long guestId) {
        return accessLogRepository.findByGuestId(guestId);
    }

    @Override
    public List<AccessLog> getLogsForKey(Long keyId) {
        return accessLogRepository.findByDigitalKeyId(keyId);
    }

}