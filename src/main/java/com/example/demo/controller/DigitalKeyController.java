package com.example.demo.controller;

import com.example.demo.model.DigitalKey;
import com.example.demo.service.DigitalKeyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keys")
public class DigitalKeyController {

    private final DigitalKeyService service;

    public DigitalKeyController(DigitalKeyService service) {
        this.service = service;
    }

    @PostMapping("/generate/{bookingId}")
    public ResponseEntity<DigitalKey> generateKey(@PathVariable Long bookingId) {
        return new ResponseEntity<>(service.generateKey(bookingId), HttpStatus.CREATED);
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<DigitalKey> getActiveKey(@PathVariable Long bookingId) {
        return ResponseEntity.ok(service.getActiveKeyForBooking(bookingId));
    }

    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<DigitalKey>> getKeysForGuest(@P
