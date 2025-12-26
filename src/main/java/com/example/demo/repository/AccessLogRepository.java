package com.example.demo.repository;

import com.example.demo.model.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
    java.util.List<AccessLog> findByGuestId(Long guestId);
    java.util.List<AccessLog> findByDigitalKeyId(Long keyId);
}
