package com.example.auctionportal.controllers;

import com.example.auctionportal.dto.LotRequest;
import com.example.auctionportal.exceptions.FileManagerException;
import com.example.auctionportal.exceptions.NoFileFoundException;
import com.example.auctionportal.service.LotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/lots")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LotController {

    private final LotService lotService;

    public LotController(LotService lotService) {
        this.lotService = lotService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createLot(@ModelAttribute @Valid LotRequest lotRequest) throws FileManagerException, NoFileFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(lotService.createLot(lotRequest));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteLot(@PathVariable Long id) throws FileManagerException {
        return ResponseEntity.status(HttpStatus.OK).body(lotService.deleteLot(id));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllLots() {
        return ResponseEntity.status(HttpStatus.OK).body(lotService.getAllLots());
    }

    @PutMapping("/redact/{id}")
    public ResponseEntity<?> redactLot(@ModelAttribute @Valid LotRequest lotRequest, @PathVariable Long id) throws FileManagerException, NoFileFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(lotService.redactLot(lotRequest, id));
    }

}
