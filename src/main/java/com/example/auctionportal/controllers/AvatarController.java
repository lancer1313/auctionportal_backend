package com.example.auctionportal.controllers;

import com.example.auctionportal.exceptions.FileSavingException;
import com.example.auctionportal.exceptions.InvalidFileFormatException;
import com.example.auctionportal.service.AvatarManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/avatar")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AvatarController {

    private final AvatarManagerService avatarManagerService;

    public AvatarController(AvatarManagerService avatarManagerService) {
        this.avatarManagerService = avatarManagerService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> postAvatar(@ModelAttribute MultipartFile image) throws InvalidFileFormatException, FileSavingException {
        return ResponseEntity.status(HttpStatus.OK).body(avatarManagerService.postAvatar(image));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAvatar() {
        return ResponseEntity.status(HttpStatus.OK).body(avatarManagerService.getAvatar());
    }

}
