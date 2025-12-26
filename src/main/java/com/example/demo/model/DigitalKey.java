package com.example.demo.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class DigitalKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RoomBooking booking;

    private String keyValue;
    private Instant issuedAt;
    private Instant expiresAt;
    private Boolean active = true;

    @PrePersist
    public void generateKey() {
        issuedAt = Instant.now();
        expiresAt = issuedAt.plusSeconds(3600);
        keyValue = UUID.randomUUID().toString();
    }

    // getters & setters
}
