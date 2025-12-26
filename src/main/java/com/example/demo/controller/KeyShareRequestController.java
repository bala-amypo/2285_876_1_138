// package com.example.demo.controller;

// import com.example.demo.model.KeyShareRequest;
// import com.example.demo.service.KeyShareRequestService;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/share-requests")
// public class KeyShareRequestController {

//     private final KeyShareRequestService service;

//     public KeyShareRequestController(KeyShareRequestService service) {
//         this.service = service;
//     }

//     @PostMapping
//     public KeyShareRequest create(@RequestBody KeyShareRequest request) {
//         return service.createShareRequest(request);
//     }

//     @GetMapping("/shared-by/{id}")
//     public List<KeyShareRequest> sharedBy(@PathVariable Long id) {
//         return service.getRequestsSharedBy(id);
//     }

//     @GetMapping("/shared-with/{id}")
//     public List<KeyShareRequest> sharedWith(@PathVariable Long id) {
//         return service.getRequestsSharedWith(id);
//     }
// }
