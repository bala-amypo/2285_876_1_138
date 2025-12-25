package com.example.demo.config;

import com.example.demo.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation") // needed for Spring Boot 2.x
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // 🔐 PASSWORD ENCODER (USED IN TESTS)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 🔑 AUTHENTICATION MANAGER (REQUIRED FOR LOGIN TESTS)
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 👤 USER DETAILS SERVICE CONFIG
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    // 🌐 HTTP SECURITY (SWAGGER + AUTH CONTROLLER)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()

            .authorizeRequests()
                .antMatchers(
                        "/api/auth/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/swagger-resources/**",
                        "/webjars/**"
                ).permitAll()
                .anyRequest().authenticated()

            .and()
            .httpBasic(); // simple & test-friendly
    }
}
