package com.example.auctionportal.service;

import com.example.auctionportal.dao.UserDao;
import com.example.auctionportal.dto.MessageResponse;
import com.example.auctionportal.dto.UserInfoResponse;
import com.example.auctionportal.models.ERole;
import com.example.auctionportal.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AdminService {
    private final UserDao userDao;

    public AdminService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<UserInfoResponse> getAllUsers() {
        List<User> users = userDao.findAll();
        List<UserInfoResponse> usersInfo = new ArrayList<>();
        for (User person : users) {
            usersInfo.add(new UserInfoResponse(person.getId(), person.getFirstName(), person.getLastName(), person.getEmail(),
                    person.getRole()));
        }
        return usersInfo;
    }

    // TO DO удаляем аватар в файловой системе
    public MessageResponse deleteUser(Long id) {
        userDao.deleteById(id);
        return new MessageResponse(id.toString());
    }

    public MessageResponse raiseUser(Long id, String role) {
        if (Arrays.stream(ERole.values()).anyMatch(eRole -> eRole.name().equals(role))) {
            User user = userDao.findById(id).get();
            user.setRole(role);
            userDao.save(user);
            return new MessageResponse(id.toString());
        }
        throw new IllegalArgumentException("Такой роли не существует");
    }

}
