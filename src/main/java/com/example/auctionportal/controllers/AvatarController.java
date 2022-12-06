package com.example.auctionportal.controllers;

import com.example.auctionportal.dao.UserDao;
import com.example.auctionportal.dto.MessageResponse;
import com.example.auctionportal.models.User;
import com.example.auctionportal.service.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/avatar")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AvatarController {

    private final UserDao userDao;
    private final String FOLDER_PATH = "D:\\Works Web\\webstorm\\auction-frontendv0.1\\images\\";
    private final String DEFAULT_IMAGE = "DEFAULT_AVATAR.jpg";

    public AvatarController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PutMapping("/send")
    public ResponseEntity<?> postAvatar(@ModelAttribute MultipartFile image) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userDao.findById(userDetails.getId()).get();
        String fileName = image.getOriginalFilename();
        if (image.getContentType().equalsIgnoreCase("image/jpg") ||
                image.getContentType().equalsIgnoreCase("image/png") ||
                image.getContentType().equalsIgnoreCase("image/jpeg")) {
            String filePath = FOLDER_PATH + UUID.randomUUID() + fileName;
            try {
                image.transferTo(new File(filePath));
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse("Произошла ошибка сохранения файла"));
            }
            user.setAvatarFilePath(filePath);
            userDao.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Фото добавлено успешно"));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Недопустимый формат файла"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> retrieveAvatar() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String filePath = userDetails.getAvatarFilePath();
        if (filePath == null) {
            filePath = FOLDER_PATH + DEFAULT_IMAGE;
        }
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(filePath));
    }

}
