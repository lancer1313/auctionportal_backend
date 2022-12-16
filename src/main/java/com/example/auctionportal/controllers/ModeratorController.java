package com.example.auctionportal.controllers;

import com.example.auctionportal.dto.NewsRequest;
import com.example.auctionportal.exceptions.FileManagerException;
import com.example.auctionportal.exceptions.InvalidFileFormatException;
import com.example.auctionportal.service.ModeratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModeratorController {

    private final ModeratorService moderatorService;

    public ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }

    @GetMapping("/all_table")
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllNewsTable() {
        return ResponseEntity.status(HttpStatus.OK).body(moderatorService.getAllNewsTable());
    }

    @GetMapping("/all_timeline")
    public ResponseEntity<?> getAllNewsTimeline() {
        return ResponseEntity.status(HttpStatus.OK).body(moderatorService.getAllNewsTimeLine());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteNews(@PathVariable Long id) throws FileManagerException {
        return ResponseEntity.status(HttpStatus.OK).body(moderatorService.deleteNews(id));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createNews(@ModelAttribute @Valid NewsRequest newsRequest) throws InvalidFileFormatException, FileManagerException {
        return ResponseEntity.status(HttpStatus.OK).body(moderatorService.createNews(newsRequest));
    }

    @PutMapping("/redact/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> redactNews(@ModelAttribute @Valid NewsRequest newsRequest, @PathVariable Long id) throws InvalidFileFormatException, FileManagerException {
        return ResponseEntity.status(HttpStatus.OK).body(moderatorService.redactNews(newsRequest, id));
    }

    @PutMapping("/redact_file/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> redactImage(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(moderatorService.redactImage(id));
    }

}
