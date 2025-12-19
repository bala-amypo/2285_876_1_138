package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String role;
    private Boolean active = true;

    // getters and setters
}
