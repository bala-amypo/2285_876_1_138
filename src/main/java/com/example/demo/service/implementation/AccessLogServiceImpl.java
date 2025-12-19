package com.example.demo.service.impl;

import com.example.demo.model.AccessLog;
import com.example.demo.model.DigitalKey;
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

    public AccessLogServiceImpl(
            AccessLogRepository accessLogRepository,
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

        if (log.getAccessTime().after(Timestamp.from(Instant.now()))) {
            throw new IllegalArgumentException("future");
        }

        DigitalKey key = digitalKeyRepository
                .findById(log.getDigitalKey().getId())
                .orElse(null);

        boolean allowed = false;

        if (key != null && key.getActive()) {
            Long guestId = log.getGuest().getId();

            allowed = key.getBooking().getGuest().getId().equals(guestId)
                    || keyShareRequestRepository
                    .findBySharedWithId(guestId)
                    .stream()
                    .anyMatch(r ->
                            r.getDigitalKey().getId().equals(key.getId())
                                    && "APPROVED".equals(r.getStatus())
                                    && log.getAccessTime().after(r.getShareStart())
                                    && log.getAccessTime().before(r.getShareEnd())
                    );
        }

        log.setResult(allowed ? "SUCCESS" : "DENIED");
        log.setReason(allowed ? "Access granted" : "Access denied");

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
