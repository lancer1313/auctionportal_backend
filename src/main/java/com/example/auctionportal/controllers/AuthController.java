package com.example.auctionportal.controllers;

import com.example.auctionportal.dto.LoginRequest;
import com.example.auctionportal.dto.MessageResponse;
import com.example.auctionportal.dto.SignupRequest;
import com.example.auctionportal.exceptions.UserRegistrationException;
import com.example.auctionportal.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new MessageResponse(authService.loginUser(loginRequest)));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupRequest signupRequest) throws UserRegistrationException {
        return ResponseEntity.ok(authService.registerUser(signupRequest));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("loged out successfully"));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.status(HttpStatus.OK).body(authService.getProfile());
    }

    @GetMapping("/time")
    public ResponseEntity<?> getTime() {
        return ResponseEntity.status(HttpStatus.OK).body(authService.getTime());
    }
}
