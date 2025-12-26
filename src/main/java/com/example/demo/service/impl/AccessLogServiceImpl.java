package com.example.demo.service.impl;

import com.example.demo.model.AccessLog;
import com.example.demo.repository.AccessLogRepository;
import com.example.demo.service.AccessLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessLogServiceImpl implements AccessLogService {

    private final AccessLogRepository repo;
    private final DigitalKeyRepository keyRepo;
    private final GuestRepository guestRepo;
    private final KeyShareRequestRepository shareRepo;

    public AccessLogServiceImpl(
            AccessLogRepository repo,
            DigitalKeyRepository keyRepo,
            GuestRepository guestRepo,
            KeyShareRequestRepository shareRepo) {
        this.repo = repo;
        this.keyRepo = keyRepo;
        this.guestRepo = guestRepo;
        this.shareRepo = shareRepo;
    }

    public AccessLog createLog(AccessLog log) {
        if (log.getAccessTime().isAfter(Instant.now())) {
            throw new IllegalArgumentException("Access time in future");
        }

        DigitalKey key = keyRepo.findById(log.getDigitalKey().getId()).orElseThrow();
        log.setResult(key.getActive() ? "SUCCESS" : "DENIED");
        return repo.save(log);
    }

    public List<AccessLog> getLogsForGuest(Long guestId) {
        return repo.findByGuestId(guestId);
    }

    public List<AccessLog> getLogsForKey(Long keyId) {
        return repo.findByDigitalKeyId(keyId);
    }
}
