// package com.example.demo.dto;

// import javax.validation.constraints.Email;
// import javax.validation.constraints.NotBlank;

// public class RegisterRequest {
//     @NotBlank
//     @Email
//     private String email;

//     @NotBlank
//     private String password;

//     @NotBlank
//     private String fullName;

//     private String phoneNumber;
//     private String role;

//     public RegisterRequest() {}

//     public RegisterRequest(String email, String password, String fullName, String phoneNumber, String role) {
//         this.email = email;
//         this.password = password;
//         this.fullName = fullName;
//         this.phoneNumber = phoneNumber;
//         this.role = role;
//     }

//     public String getEmail() { return email; }
//     public void setEmail(String email) { this.email = email; }

//     public String getPassword() { return password; }
//     public void setPassword(String password) { this.password = password; }

//     public String getFullName() { return fullName; }
//     public void setFullName(String fullName) { this.fullName = fullName; }

//     public String getPhoneNumber() { return phoneNumber; }
//     public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

//     public String getRole() { return role; }
//     public void setRole(String role) { this.role = role; }
// }