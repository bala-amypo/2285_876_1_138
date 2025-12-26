package com.example.demo.service.impl;

import com.example.demo.model.DigitalKey;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.service.DigitalKeyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DigitalKeyServiceImpl implements DigitalKeyService {

    private final DigitalKeyRepository repo;
    private final RoomBookingRepository bookingRepo;

    public DigitalKeyServiceImpl(DigitalKeyRepository repo, RoomBookingRepository bookingRepo) {
        this.repo = repo;
        this.bookingRepo = bookingRepo;
    }

    public DigitalKey generateKey(Long bookingId) {
        RoomBooking booking = bookingRepo.findById(bookingId).orElseThrow();
        if (!booking.getActive()) throw new IllegalStateException("Booking inactive");

        DigitalKey key = new DigitalKey();
        key.setBooking(booking);
        return repo.save(key);
    }

    public DigitalKey getActiveKeyForBooking(Long bookingId) {
        return repo.findByBookingIdAndActiveTrue(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("No active key " + bookingId));
    }

    public List<DigitalKey> getKeysForGuest(Long guestId) {
        return repo.findByBookingGuestId(guestId);
    }
}
