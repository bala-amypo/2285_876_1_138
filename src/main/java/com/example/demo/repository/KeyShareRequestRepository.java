package com.example.demo.repository;

import com.example.demo.model.KeyShareRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface KeyShareRequestRepository extends JpaRepository<KeyShareRequest, Long> {
    java.util.List<KeyShareRequest> findBySharedById(Long guestId);
    java.util.List<KeyShareRequest> findBySharedWithId(Long guestId);
}
