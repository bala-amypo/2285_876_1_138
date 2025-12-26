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

    public DigitalKeyServiceImpl(DigitalKeyRepository repo,
                                 RoomBookingRepository bookingRepo) {
        this.repo = repo;
        this.bookingRepo = bookingRepo;
    }

    @Override
    public DigitalKey generateKey(Long bookingId) {
        DigitalKey key = new DigitalKey();
        key.setBooking(bookingRepo.findById(bookingId).orElseThrow());
        return repo.save(key);
    }

    @Override
    public List<DigitalKey> getKeysForGuest(Long guestId) {
        return repo.findByBookingGuestId(guestId);
    }
}
