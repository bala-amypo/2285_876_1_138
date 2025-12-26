package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final GuestRepository repo;
    private final JwtTokenProvider jwt;

    public AuthController(GuestRepository repo, JwtTokenProvider jwt) {
        this.repo = repo;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public Guest register(@RequestBody Guest guest) {
        return repo.save(guest);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest req) {
        return new JwtResponse(jwt.generateToken(req.email));
    }
}
