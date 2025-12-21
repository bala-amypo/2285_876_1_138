package com.example.demo.controller;

import com.example.demo.model.KeyShareRequest;
import com.example.demo.service.KeyShareRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/key-share")
@Tag(name = "Key Sharing", description = "Key sharing management endpoints")
public class KeyShareRequestController {

    private final KeyShareRequestService keyShareRequestService;

    public KeyShareRequestController(KeyShareRequestService keyShareRequestService) {
        this.keyShareRequestService = keyShareRequestService;
    }

    @PostMapping
    @Operation(summary = "Create a key share request")
    public ResponseEntity<KeyShareRequest> createShareRequest(@Valid @RequestBody KeyShareRequest request) {
        return ResponseEntity.ok(keyShareRequestService.createShareRequest(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get share request by ID")
    public ResponseEntity<KeyShareRequest> getShareRequest(@PathVariable Long id) {
        return ResponseEntity.ok(keyShareRequestService.getShareRequestById(id));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update share request status")
    public ResponseEntity<KeyShareRequest> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(keyShareRequestService.updateStatus(id, status));
    }

    @GetMapping("/shared-by/{guestId}")
    @Operation(summary = "Get requests shared by guest")
    public ResponseEntity<List<KeyShareRequest>> getRequestsSharedBy(@PathVariable Long guestId) {
        return ResponseEntity.ok(keyShareRequestService.getRequestsSharedBy(guestId));
    }

    @GetMapping("/shared-with/{guestId}")
    @Operation(summary = "Get requests shared with guest")
    public ResponseEntity<List<KeyShareRequest>> getRequestsSharedWith(@PathVariable Long guestId) {
        return ResponseEntity.ok(keyShareRequestService.getRequestsSharedWith(guestId));
    }
}