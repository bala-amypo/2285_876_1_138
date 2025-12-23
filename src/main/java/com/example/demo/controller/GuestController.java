package com.example.demo.controller;

import com.example.demo.model.Guest;
import com.example.demo.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
@Tag(name = "Guests", description = "Guest management endpoints")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    @Operation(summary = "Create guest")
    public ResponseEntity<Guest> createGuest(@Valid @RequestBody Guest guest) {
        return ResponseEntity.ok(guestService.createGuest(guest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Guest> getGuest(@PathVariable Long id) {
        return ResponseEntity.ok(guestService.getGuestById(id));
    }

    @GetMapping
    public ResponseEntity<List<Guest>> getAllGuests() {
        return ResponseEntity.ok(guestService.getAllGuests());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Guest> updateGuest(
            @PathVariable Long id,
            @Valid @RequestBody Guest guest) {
        return ResponseEntity.ok(guestService.updateGuest(id, guest));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateGuest(@PathVariable Long id) {
        guestService.deactivateGuest(id);
        return ResponseEntity.ok().build();
    }
}
