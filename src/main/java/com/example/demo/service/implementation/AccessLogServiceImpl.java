package com.example.demo.service.impl;

import com.example.demo.model.AccessLog;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.Guest;
import com.example.demo.repository.AccessLogRepository;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.KeyShareRequestRepository;
import com.example.demo.service.AccessLogService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AccessLogServiceImpl implements AccessLogService {

    private final AccessLogRepository repository;
    private final DigitalKeyRepository keyRepository;
    private final GuestRepository guestRepository;
    private final KeyShareRequestRepository shareRepository;

    public AccessLogServiceImpl(AccessLogRepository repository,
                                DigitalKeyRepository keyRepository,
                                GuestRepository guestRepository,
                                KeyShareRequestRepository shareRepository) {
        this.repository = repository;
        this.keyRepository = keyRepository;
        this.guestRepository = guestRepository;
        this.shareRepository = shareRepository;
    }

    @Override
    public AccessLog createLog(AccessLog log) {

        if (log.getAccessTime().isAfter(Instant.now())) {
            throw new IllegalArgumentException("future");
        }

        DigitalKey key = keyRepository.findById(log.getDigitalKey().getId()).orElseThrow();
        Guest guest = guestRepository.findById(log.getGuest().getId()).orElseThrow();

        boolean allowed =
                key.getActive()
                        && Instant.now().isAfter(key.getIssuedAt())
                        && Instant.now().isBefore(key.getExpiresAt());

        log.setResult(allowed ? "SUCCESS" : "DENIED");
        return repository.save(log);
    }

    @Override
    public List<AccessLog> getLogsForGuest(Long guestId) {
        return repository.findByGuestId(guestId);
    }

    @Override
    public List<AccessLog> getLogsForKey(Long keyId) {
        return repository.findByDigitalKeyId(keyId);
    }
}
