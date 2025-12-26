package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public GuestServiceImpl(GuestRepository guestRepository, PasswordEncoder passwordEncoder) {
        this.guestRepository = guestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Guest createGuest(Guest guest) {
        if (guestRepository.existsByEmail(guest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        guest.setPassword(passwordEncoder.encode(guest.getPassword()));
        return guestRepository.save(guest);
    }

    @Override
    public Guest getGuestById(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id: " + id));
    }

    @Override
    public Guest updateGuest(Long id, Guest guest) {
        Guest existing = getGuestById(id);
        if (guest.getFullName() != null) existing.setFullName(guest.getFullName());
        if (guest.getPhoneNumber() != null) existing.setPhoneNumber(guest.getPhoneNumber());
        if (guest.getVerified() != null) existing.setVerified(guest.getVerified());
        if (guest.getActive() != null) existing.setActive(guest.getActive());
        if (guest.getRole() != null) existing.setRole(guest.getRole());
        return guestRepository.save(existing);
    }

    @Override
    public void deactivateGuest(Long id) {
        Guest guest = getGuestById(id);
        guest.setActive(false);
        guestRepository.save(guest);
    }

    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

}