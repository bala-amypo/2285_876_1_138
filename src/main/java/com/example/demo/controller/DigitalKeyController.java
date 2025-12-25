package com.example.demo.controller;

import com.example.demo.model.DigitalKey;
import com.example.demo.service.DigitalKeyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
A
@RestController
@RequestMapping("/api/digital-keys")
@Tag(name = "Digital Keys", description = "Digital key management endpoints")
public class DigitalKeyController {

    private final DigitalKeyService digitalKeyService;

    public DigitalKeyController(DigitalKeyService digitalKeyService) {
        this.digitalKeyService = digitalKeyService;
    }

    @PostMapping("/generate/{bookingId}")
    @Operation(summary = "Generate a digital key for booking")
    public ResponseEntity<DigitalKey> generateKey(@PathVariable Long bookingId) {
        return ResponseEntity.ok(digitalKeyService.generateKey(bookingId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get digital key by ID")
    public ResponseEntity<DigitalKey> getKey(@PathVariable Long id) {
        return ResponseEntity.ok(digitalKeyService.getKeyById(id));
    }

    @GetMapping("/booking/{bookingId}")
    @Operation(summary = "Get active key for booking")
    public ResponseEntity<DigitalKey> getActiveKeyForBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(digitalKeyService.getActiveKeyForBooking(bookingId));
    }

    @GetMapping("/guest/{guestId}")
    @Operation(summary = "Get keys for guest")
    public ResponseEntity<List<DigitalKey>> getKeysForGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(digitalKeyService.getKeysForGuest(guestId));
    }
}