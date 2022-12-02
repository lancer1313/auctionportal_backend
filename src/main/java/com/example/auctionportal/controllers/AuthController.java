package com.example.auctionportal.controllers;

import com.example.auctionportal.dto.LoginRequest;
import com.example.auctionportal.dto.SignupRequest;
import com.example.auctionportal.exceptions.UserRegistrationException;
import com.example.auctionportal.service.AuthService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginRequest loginRequest) throws UserRegistrationException {

        return ResponseEntity.ok(authService.loginUser(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupRequest signupRequest) throws UserRegistrationException {

        return ResponseEntity.ok(authService.registerUser(signupRequest));
    }
}
