package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Guest;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final GuestService guestService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthController(
            GuestService guestService,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.guestService = guestService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    @Operation(summary = "Register new guest")
    public ApiResponse<Guest> register(@RequestBody RegisterRequest request) {

        Guest guest = new Guest();
        guest.setEmail(request.getEmail());
        guest.setPassword(request.getPassword());
        guest.setFullName(request.getFullName());
        guest.setPhoneNumber(request.getPhoneNumber());
        guest.setRole(request.getRole());

        return new ApiResponse<>(
                true,
                "Guest registered",
                guestService.createGuest(guest)
        );
    }

    @PostMapping("/login")
    @Operation(summary = "Login and generate JWT")
    public ApiResponse<Map<String, Object>> login(
            @RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtTokenProvider.generateToken(authentication);
        Guest guest = guestService.getGuestByEmail(request.getEmail());

        return new ApiResponse<>(
                true,
                "Login successful",
                Map.of(
                        "token", token,
                        "guest", guest
                )
        );
    }
}
