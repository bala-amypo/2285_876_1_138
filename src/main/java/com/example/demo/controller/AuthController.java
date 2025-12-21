package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.TokenResponse;
import com.example.demo.model.Guest;
import com.example.demo.security.GuestPrincipal;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {

    private final GuestService guestService;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthController(GuestService guestService, JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.guestService = guestService;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new guest")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest request) {
        Guest guest = new Guest();
        guest.setFullName(request.getFullName());
        guest.setEmail(request.getEmail());
        guest.setPhoneNumber(request.getPhoneNumber());
        guest.setPassword(request.getPassword());
        guest.setRole(request.getRole() != null ? request.getRole() : "ROLE_USER");

        Guest savedGuest = guestService.createGuest(guest);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new TokenResponse(token, savedGuest.getId(), savedGuest.getEmail(), savedGuest.getRole()));
    }

    @PostMapping("/login")
    @Operation(summary = "Login with email and password")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = tokenProvider.generateToken(authentication);
        GuestPrincipal principal = (GuestPrincipal) authentication.getPrincipal();

        return ResponseEntity.ok(new TokenResponse(token, principal.getId(), principal.getEmail(), principal.getRole()));
    }
}