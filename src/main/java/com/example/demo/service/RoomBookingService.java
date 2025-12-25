package com.example.demo.service;

import com.example.demo.model.RoomBooking;

public interface RoomBookingService {

    RoomBooking createBooking(RoomBooking booking);

    RoomBooking getBookingById(Long id);

    void deactivateBooking(Long id);
}
