package com.example.auctionportal.controllers;

import com.example.auctionportal.dao.UserDao;
import com.example.auctionportal.dto.AvatarRequest;
import com.example.auctionportal.dto.MessageResponse;
import com.example.auctionportal.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> postAvatar(@ModelAttribute AvatarRequest avatarRequest) {
        User user = userDao.findById(avatarRequest.getId()).get();
        String fileName = avatarRequest.getImage().getOriginalFilename();
        if (avatarRequest.getImage().getContentType().equalsIgnoreCase("image/jpg") ||
                avatarRequest.getImage().getContentType().equalsIgnoreCase("image/png")) {
            String filePath = FOLDER_PATH + UUID.randomUUID() + fileName;
            try {
                avatarRequest.getImage().transferTo(new File(filePath));
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse("Произошла ошибка сохранения файла"));
            }
            user.setAvatarFilePath(filePath);
            userDao.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Фото добавлено успешно"));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Недопустимый формат файла"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveAvatar(@PathVariable Long id) {
        User user = userDao.findById(id).get();
        String filePath = user.getAvatarFilePath();
        if (filePath == null) {
            filePath = FOLDER_PATH + DEFAULT_IMAGE;
        }
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(filePath));
    }

}
