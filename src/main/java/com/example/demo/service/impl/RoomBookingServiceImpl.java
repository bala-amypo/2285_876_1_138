package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.RoomBooking;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.service.RoomBookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomBookingServiceImpl implements RoomBookingService {

    private final RoomBookingRepository repository;

    public RoomBookingServiceImpl(RoomBookingRepository repository) {
        this.repository = repository;
    }

    @Override
    public RoomBooking createBooking(RoomBooking booking) {
        return repository.save(booking);
    }

    @Override
    public RoomBooking getBookingById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }

    @Override
    public List<RoomBooking> getBookingsForGuest(Long guestId) {
        return repository.findByGuestId(guestId);
    }

    @Override
    public RoomBooking updateBooking(Long id, RoomBooking booking) {
        RoomBooking existing = getBookingById(id);
        existing.setCheckIn(booking.getCheckIn());
        existing.setCheckOut(booking.getCheckOut());
        return repository.save(existing);
    }

    @Override
    public void deactivateBooking(Long id) {
        RoomBooking booking = getBookingById(id);
        booking.setActive(false);
        repository.save(booking);
    }
}
