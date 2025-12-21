package com.example.demo.controller;

import com.example.demo.model.AccessLog;
import com.example.demo.service.AccessLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/access-logs")
@Tag(name = "Access Logs", description = "Access log management endpoints")
public class AccessLogController {

    private final AccessLogService accessLogService;

    public AccessLogController(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    @PostMapping
    @Operation(summary = "Create an access log")
    public ResponseEntity<AccessLog> createLog(@Valid @RequestBody AccessLog log) {
        return ResponseEntity.ok(accessLogService.createLog(log));
    }

    @GetMapping("/key/{keyId}")
    @Operation(summary = "Get logs for key")
    public ResponseEntity<List<AccessLog>> getLogsForKey(@PathVariable Long keyId) {
        return ResponseEntity.ok(accessLogService.getLogsForKey(keyId));
    }

    @GetMapping("/guest/{guestId}")
    @Operation(summary = "Get logs for guest")
    public ResponseEntity<List<AccessLog>> getLogsForGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(accessLogService.getLogsForGuest(guestId));
    }
}
