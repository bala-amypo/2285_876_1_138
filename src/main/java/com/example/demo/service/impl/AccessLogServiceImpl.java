package com.example.demo.service.impl;

import com.example.demo.model.AccessLog;
import com.example.demo.repository.AccessLogRepository;
import com.example.demo.service.AccessLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessLogServiceImpl implements AccessLogService {

    private final AccessLogRepository repo;

    public AccessLogServiceImpl(AccessLogRepository repo) {
        this.repo = repo;
    }

    @Override
    public AccessLog createLog(AccessLog log) {
        return repo.save(log);
    }

    @Override
    public List<AccessLog> getLogsForGuest(Long guestId) {
        return repo.findByGuestId(guestId);
    }
}
