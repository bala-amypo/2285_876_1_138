package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class KeyShareRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sharedById;
    private Long sharedWithId;
    private String status;

    // getters and setters
}
