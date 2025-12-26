package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DigitalKey;
import com.example.demo.model.RoomBooking;
import com.example.demo.repository.DigitalKeyRepository;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.service.DigitalKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class DigitalKeyServiceImpl implements DigitalKeyService {

    private final DigitalKeyRepository digitalKeyRepository;
    private final RoomBookingRepository roomBookingRepository;

    @Autowired
    public DigitalKeyServiceImpl(DigitalKeyRepository digitalKeyRepository, RoomBookingRepository roomBookingRepository) {
        this.digitalKeyRepository = digitalKeyRepository;
        this.roomBookingRepository = roomBookingRepository;
    }

    @Override
    public DigitalKey generateKey(Long bookingId) {
        RoomBooking booking = roomBookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
        if (!booking.getActive()) {
            throw new IllegalStateException("Cannot generate key for inactive booking");
        }
        DigitalKey key = new DigitalKey();
        key.setKeyValue(UUID.randomUUID().toString());
        key.setIssuedAt(Instant.now());
        key.setExpiresAt(booking.getCheckOutDate().atStartOfDay().toInstant(java.time.ZoneOffset.UTC));
        key.setActive(true);
        key.setBooking(booking);
        return digitalKeyRepository.save(key);
    }

    @Override
    public DigitalKey getActiveKeyForBooking(Long bookingId) {
        return digitalKeyRepository.findByBookingIdAndActiveTrue(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Active key not found for booking: " + bookingId));
    }

    @Override
    public List<DigitalKey> getKeysForGuest(Long guestId) {
        return digitalKeyRepository.findByBookingGuestId(guestId);
    }

}