package com.example.demo.controller;

import com.example.demo.model.Guest;
import com.example.demo.service.GuestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final GuestService service;

    public GuestController(GuestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Guest> createGuest(@RequestBody Guest guest) {
        return new ResponseEntity<>(service.createGuest(guest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Guest> getGuest(@PathVariable Long id) {
        return ResponseEntity.ok(service.getGuestById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Guest> updateGuest(@PathVariable Long id,
                                             @RequestBody Guest guest) {
        return ResponseEntity.ok(service.updateGuest(id, guest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateGuest(@PathVariable Long id) {
        service.deactivateGuest(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Guest>> getAllGuests() {
        return ResponseEntity.ok(service.getAllGuests());
    }
}
