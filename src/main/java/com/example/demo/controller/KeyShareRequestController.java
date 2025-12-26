package com.example.demo.controller;

import com.example.demo.model.KeyShareRequest;
import com.example.demo.service.KeyShareService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/key-share-requests")
public class KeyShareRequestController {

    private final KeyShareService service;

    public KeyShareRequestController(KeyShareService service) {
        this.service = service;
    }

    // Share a digital key
    @PostMapping
    public KeyShareRequest shareKey(@RequestBody KeyShareRequest request) {
        return service.shareKey(request);
    }

    // Get received key share requests
    @GetMapping("/received/{guestId}")
    public List<KeyShareRequest> getReceivedRequests(@PathVariable Long guestId) {
        return service.receivedRequests(guestId);
    }
}
