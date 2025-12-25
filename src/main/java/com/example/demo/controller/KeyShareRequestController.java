package com.example.demo.controller;

import com.example.demo.model.KeyShareRequest;
import com.example.demo.service.KeyShareRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/key-shares")
public class KeyShareRequestController {

    private final KeyShareRequestService service;

    public KeyShareRequestController(KeyShareRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<KeyShareRequest> createShare(
            @RequestBody KeyShareRequest request) {
        return new ResponseEntity<>(service.createShareRequest(request), HttpStatus.CREATED);
    }

    @GetMapping("/shared-by/{guestId}")
    public ResponseEntity<List<KeyShareRequest>> getSharedBy(
