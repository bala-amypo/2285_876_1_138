package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class RoomBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long guestId;
    private String roomNumber;
    private Boolean active = true;

    // getters and setters
}
