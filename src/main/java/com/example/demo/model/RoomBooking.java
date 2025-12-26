package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class RoomBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Boolean active = true;

    @ManyToOne
    private Guest guest;

    // getters & setters
}
