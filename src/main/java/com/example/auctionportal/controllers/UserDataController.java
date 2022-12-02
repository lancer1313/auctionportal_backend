package com.example.auctionportal.controllers;

import com.example.auctionportal.dao.UserDao;
import com.example.auctionportal.dto.ProfileInfoResponse;
import com.example.auctionportal.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserDataController {

    private final UserDao userDao;

    public UserDataController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long id) {
        User user = userDao.findById(id).get();
        ProfileInfoResponse profileInfoResponse = new ProfileInfoResponse(user.getFirstName(),
                user.getLastName(), user.getLoginsCount());
        return ResponseEntity.status(HttpStatus.OK).body(profileInfoResponse);
    }
}
