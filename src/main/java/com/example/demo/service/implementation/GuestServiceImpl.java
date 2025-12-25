package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import com.example.demo.service.GuestService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository repository;
    private final PasswordEncoder passwordEncoder;

    public GuestServiceImpl(GuestRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Guest createGuest(Guest guest) {
        if (repository.existsByEmail(guest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        guest.setPassword(passwordEncoder.encode(guest.getPassword()));
        return repository.save(guest);
    }

    @Override
    public Guest getGuestById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest " + id));
    }

    @Override
    public Guest updateGuest(Long id, Guest update) {
        Guest g = getGuestById(id);
        g.setFullName(update.getFullName());
        g.setPhoneNumber(update.getPhoneNumber());
        g.setVerified(update.getVerified());
        g.setActive(update.getActive());
        g.setRole(update.getRole());
        return repository.save(g);
    }

    @Override
    public void deactivateGuest(Long id) {
        Guest g = getGuestById(id);
        g.setActive(false);
        repository.save(g);
    }

    @Override
    public List<Guest> getAllGuests() {
        return repository.findAll();
    }
}
