package com.example.auctionportal.service;

import com.example.auctionportal.config.jwt.JwtUtils;
import com.example.auctionportal.dao.RoleDao;
import com.example.auctionportal.dao.UserDao;
import com.example.auctionportal.dto.JwtResponse;
import com.example.auctionportal.dto.LoginRequest;
import com.example.auctionportal.dto.MessageResponse;
import com.example.auctionportal.dto.SignupRequest;
import com.example.auctionportal.exceptions.UserRegistrationException;
import com.example.auctionportal.models.ERole;
import com.example.auctionportal.models.Role;
import com.example.auctionportal.models.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final RoleDao roleDao;
    private final UserDao userDao;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthService(PasswordEncoder passwordEncoder, RoleDao roleDao, UserDao userDao,
                       AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.roleDao = roleDao;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public JwtResponse loginUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));

        User user = userDao.findByEmail(loginRequest.getEmail()).get();
        user.setLoginsCount(user.getLoginsCount() + 1);
        userDao.save(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles);
    }

    public MessageResponse registerUser(SignupRequest signupRequest) throws UserRegistrationException {
        if (userDao.existsByEmail(signupRequest.getEmail())) {
            throw new UserRegistrationException("Такая почта уже занята");
        }

        User user = new User(signupRequest.getFirstName(), signupRequest.getLastName(),
                signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()));


        Set<String> reqRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (reqRoles == null) {
            Role userRole = roleDao
                    .findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            roles.add(userRole);
        } else {
            reqRoles.forEach(r -> {
                switch (r) {
                    case "admin" -> {
                        Role adminRole = roleDao
                                .findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        Role modRole = roleDao
                                .findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error, Role MODERATOR is not found"));
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = roleDao
                                .findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
                        roles.add(userRole);
                    }
                }
            });
        }
        user.setRoles(roles);
        user.setLoginsCount(0);
        user.setAvatarFilePath(null);
        userDao.save(user);
        return new MessageResponse("User CREATED");
    }
}
