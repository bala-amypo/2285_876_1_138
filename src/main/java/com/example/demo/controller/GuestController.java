package com.example.demo.controller;

import com.example.demo.model.Guest;
import com.example.demo.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/guests")
@Tag(name = "Guest Management", description = "Guest management endpoints")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new guest")
    public ResponseEntity<Guest> createGuest(@Valid @RequestBody Guest guest) {
        return ResponseEntity.ok(guestService.createGuest(guest));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get guest by ID")
    public ResponseEntity<Guest> getGuest(@PathVariable Long id) {
        return ResponseEntity.ok(guestService.getGuestById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all guests")
    public ResponseEntity<List<Guest>> getAllGuests() {
        return ResponseEntity.ok(guestService.getAllGuests());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update guest")
    public ResponseEntity<Guest> updateGuest(@PathVariable Long id, @Valid @RequestBody Guest guest) {
        return ResponseEntity.ok(guestService.updateGuest(id, guest));
    }

    @PutMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deactivate guest")
    public ResponseEntity<Void> deactivateGuest(@PathVariable Long id) {
        guestService.deactivateGuest(id);
        return ResponseEntity.ok().build();
    }
}