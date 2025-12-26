package com.example.demo.repository;

import com.example.demo.model.DigitalKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface DigitalKeyRepository extends JpaRepository<DigitalKey, Long> {
    Optional<DigitalKey> findByBookingIdAndActiveTrue(Long bookingId);
    java.util.List<DigitalKey> findByBookingGuestId(Long guestId);
}
