package com.example.demo.repository;

import com.example.demo.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface KeyShareRequestRepository extends JpaRepository<KeyShareRequest, Long> {
    List<KeyShareRequest> findBySharedById(Long id);
    List<KeyShareRequest> findBySharedWithId(Long id);
}
