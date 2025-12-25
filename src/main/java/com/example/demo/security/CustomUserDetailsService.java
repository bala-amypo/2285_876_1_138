package com.example.demo.security;

import com.example.demo.repository.GuestRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final GuestRepository repo;

    public CustomUserDetailsService(GuestRepository r) {
        this.repo = r;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        var g = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return User.withUsername(g.getEmail())
                .password(g.getPassword())
                .roles(g.getRole().replace("ROLE_", ""))
                .build();
    }
}
