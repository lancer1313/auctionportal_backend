package com.example.auctionportal.controllers;

import com.example.auctionportal.dto.MessageResponse;
import com.example.auctionportal.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllUsers());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.deleteUser(id));
    }

    @PutMapping("/raise/{id}/{role}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> raiseUser(@PathVariable Long id, @PathVariable String role) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.raiseUser(id, role));
    }

}
