package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long guestId;
    private Long digitalKeyId;
    private String accessTime;

    // getters and setters
}
