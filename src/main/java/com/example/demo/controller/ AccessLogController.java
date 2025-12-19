package com.example.demo.controller;

import com.example.demo.model.AccessLog;
import com.example.demo.service.AccessLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/access-logs")
@Tag(name = "Access Logs")
public class AccessLogController {

    private final AccessLogService accessLogService;

    public AccessLogController(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    @PostMapping
    public AccessLog create(@RequestBody AccessLog log) {
        return accessLogService.createLog(log);
    }

    @GetMapping("/key/{keyId}")
    public List<AccessLog> getByKey(@PathVariable Long keyId) {
        return accessLogService.getLogsForKey(keyId);
    }

    @GetMapping("/guest/{guestId}")
    public List<AccessLog> getByGuest(@PathVariable Long guestId) {
        return accessLogService.getLogsForGuest(guestId);
    }
}
