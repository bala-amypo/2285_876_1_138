package com.example.demo.service;

import com.example.demo.model.AccessLog;
import java.util.List;

public interface AccessLogService {
    AccessLog logAccess(AccessLog log);
    List<AccessLog> logsByGuest(Long guestId);
}
