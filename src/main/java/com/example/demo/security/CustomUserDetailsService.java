package com.example.demo.security;

import com.example.demo.repository.GuestRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final GuestRepository repo;

    public CustomUserDetailsService(GuestRepository repo) {
        this.repo = repo;
    }

    public UserDetails loadUserByUsername(String email) {
        var g = repo.findByEmail(email).orElseThrow();
        return User.withUsername(g.getEmail())
                .password(g.getPassword())
                .roles(g.getRole())
                .build();
    }
}
