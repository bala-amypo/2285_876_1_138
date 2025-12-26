package com.example.demo.controller;

import com.example.demo.model.AccessLog;
import com.example.demo.service.AccessLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/access-logs")
public class AccessLogController {

    private final AccessLogService service;

    public AccessLogController(AccessLogService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AccessLog> createLog(@RequestBody AccessLog log) {
        return new ResponseEntity<>(service.createLog(log), HttpStatus.CREATED);
    }

    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<AccessLog>> getLogsForGuest(
            @PathVariable Long guestId) {
        return ResponseEntity.ok(service.getLogsForGuest(guestId));
    }

    @GetMapping("/key/{keyId}")
    public ResponseEntity<List<AccessLog>> getLogsForKey(
            @PathVariable Long keyId) {
        return ResponseEntity.ok(service.getLogsForKey(keyId));
    }
}
