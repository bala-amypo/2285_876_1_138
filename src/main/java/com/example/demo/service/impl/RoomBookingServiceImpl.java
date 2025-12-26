package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.RoomBooking;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.service.RoomBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomBookingServiceImpl implements RoomBookingService {

    private final RoomBookingRepository roomBookingRepository;

    @Autowired
    public RoomBookingServiceImpl(RoomBookingRepository roomBookingRepository) {
        this.roomBookingRepository = roomBookingRepository;
    }

    @Override
    public RoomBooking createBooking(RoomBooking booking) {
        if (booking.getCheckInDate().isAfter(booking.getCheckOutDate()) || booking.getCheckInDate().isEqual(booking.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        return roomBookingRepository.save(booking);
    }

    @Override
    public List<RoomBooking> getBookingsForGuest(Long guestId) {
        return roomBookingRepository.findByGuestId(guestId);
    }

    @Override
    public RoomBooking updateBooking(Long id, RoomBooking booking) {
        RoomBooking existing = roomBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        if (booking.getRoomNumber() != null) existing.setRoomNumber(booking.getRoomNumber());
        if (booking.getCheckInDate() != null) existing.setCheckInDate(booking.getCheckInDate());
        if (booking.getCheckOutDate() != null) existing.setCheckOutDate(booking.getCheckOutDate());
        if (booking.getActive() != null) existing.setActive(booking.getActive());
        return roomBookingRepository.save(existing);
    }

}