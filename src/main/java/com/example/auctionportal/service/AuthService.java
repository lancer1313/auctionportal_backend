package com.example.auctionportal.service;

import com.example.auctionportal.config.jwt.JwtUtil;
import com.example.auctionportal.dao.UserDao;
import com.example.auctionportal.dto.LoginRequest;
import com.example.auctionportal.dto.MessageResponse;
import com.example.auctionportal.dto.ProfileInfoResponse;
import com.example.auctionportal.dto.SignupRequest;
import com.example.auctionportal.exceptions.UserRegistrationException;
import com.example.auctionportal.models.ERole;
import com.example.auctionportal.models.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(PasswordEncoder passwordEncoder, UserDao userDao,
                       AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String loginUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));

        User user = userDao.findByEmail(loginRequest.getEmail()).get();
        user.setLoginsCount(user.getLoginsCount() + 1);
        userDao.save(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtUtil.generateToken(loginRequest.getEmail());
    }

    public MessageResponse registerUser(SignupRequest signupRequest) throws UserRegistrationException {
        if (userDao.existsByEmail(signupRequest.getEmail())) {
            throw new UserRegistrationException("Такая почта уже занята");
        }

        User user = new User(signupRequest.getFirstName(), signupRequest.getLastName(),
                signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()), ERole.ROLE_USER.name());
        user.setLoginsCount(0);
        user.setAvatar(null);
        userDao.save(user);
        return new MessageResponse("User CREATED");
    }

    public ProfileInfoResponse getProfile() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        return new ProfileInfoResponse(userDetails.getFirstName(), userDetails.getLastName(),
                    userDetails.getLoginsCount(), userDetails.getAuthorities().toString());
    }

    public MessageResponse getTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd в HH:mm:ss");
        String dateAndTime = simpleDateFormat.format(date);
        return new MessageResponse(dateAndTime);
    }
}
