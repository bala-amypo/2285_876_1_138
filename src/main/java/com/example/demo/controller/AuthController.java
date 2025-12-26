// package com.example.demo.controller;

// import com.example.demo.model.Guest;
// import com.example.demo.repository.GuestRepository;
// import com.example.demo.security.JwtTokenProvider;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.web.bind.annotation.*;

// import java.util.HashMap;
// import java.util.Map;

// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {

//     private final AuthenticationManager authenticationManager;
//     private final JwtTokenProvider jwtTokenProvider;
//     private final GuestRepository guestRepository;

//     public AuthController(AuthenticationManager authenticationManager,
//                           JwtTokenProvider jwtTokenProvider,
//                           GuestRepository guestRepository) {
//         this.authenticationManager = authenticationManager;
//         this.jwtTokenProvider = jwtTokenProvider;
//         this.guestRepository = guestRepository;
//     }

//     @PostMapping("/login")
//     public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//         try {
//             Authentication authentication =
//                     authenticationManager.authenticate(
//                             new UsernamePasswordAuthenticationToken(
//                                     request.getEmail(),
//                                     request.getPassword()
//                             )
//                     );

//             String token = jwtTokenProvider.generateToken(authentication);
//             Guest guest = guestRepository.findByEmail(request.getEmail()).orElseThrow();

//             Map<String, Object> response = new HashMap<>();
//             response.put("token", token);
//             response.put("email", guest.getEmail());
//             response.put("role", guest.getRole());
//             response.put("guestId", guest.getId());

//             return ResponseEntity.ok(response);

//         } catch (AuthenticationException ex) {
//             return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                     .body("Invalid email or password");
//         }
//     }

//     // DTO
//     public static class LoginRequest {
//         private String email;
//         private String password;

//         public String getEmail() { return email; }
//         public void setEmail(String email) { this.email = email; }

//         public String getPassword() { return password; }
//         public void setPassword(String password) { this.password = password; }
//     }
// }
