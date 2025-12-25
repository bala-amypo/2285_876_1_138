package com.example.demo.security;

import com.example.demo.repository.GuestRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final GuestRepository repository;

    public CustomUserDetailsService(GuestRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        var guest = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return User.withUsername(guest.getEmail())
                .password(guest.getPassword())
                .authorities(guest.getRole())
                .build();
    }
}
