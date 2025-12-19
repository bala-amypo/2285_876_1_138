package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class DigitalKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;
    private Boolean active = true;

    // getters and setters
}
