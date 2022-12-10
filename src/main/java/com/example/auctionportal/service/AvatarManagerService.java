package com.example.auctionportal.service;

import com.example.auctionportal.dao.UserDao;
import com.example.auctionportal.dao.UserFileDao;
import com.example.auctionportal.dto.MessageResponse;
import com.example.auctionportal.exceptions.FileSavingException;
import com.example.auctionportal.exceptions.InvalidFileFormatException;
import com.example.auctionportal.models.User;
import com.example.auctionportal.models.UserFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class AvatarManagerService {
    private final UserFileDao userFileDao;
    private final UserDao userDao;
    private final String FOLDER_PATH = "D:\\Works Web\\webstorm\\auction-frontendv0.1\\avatar_images\\";
    private final String DEFAULT_IMAGE = "DEFAULT_AVATAR.jpg";

    public AvatarManagerService(UserFileDao userFileDao, UserDao userDao) {
        this.userDao = userDao;
        this.userFileDao = userFileDao;
    }

    public MessageResponse postAvatar(MultipartFile image) throws FileSavingException, InvalidFileFormatException {
        if (image.getContentType().equalsIgnoreCase("image/png") ||
                image.getContentType().equalsIgnoreCase("image/jpeg")) {
            Object principal = SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            UserDetailsImpl userDetails = (UserDetailsImpl) principal;
            User user = userDao.findById(userDetails.getId()).get();
            String fileName = image.getOriginalFilename();
            String filePath = FOLDER_PATH + UUID.randomUUID() + fileName;
            UserFile avatar = new UserFile(image.getOriginalFilename(), image.getContentType(), filePath);
            try {
                image.transferTo(new File(filePath));
            } catch (IOException e) {
                throw new FileSavingException("Произошла ошибка сохранения файла");
            }
            user.setAvatar(avatar);
            userFileDao.save(avatar);
            return new MessageResponse("Файл сохранен");
        }
        throw new InvalidFileFormatException("Недопустимый формат файла");
    }

    public MessageResponse getAvatar() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        String filePath;
        if (userDetails.getAvatar() != null) {
            UserFile userFile = userFileDao.findById(userDetails.getId()).get();
            filePath = userFile.getFilePath();
        } else {
            filePath = FOLDER_PATH + DEFAULT_IMAGE;
        }
        return new MessageResponse(filePath);
    }
}
