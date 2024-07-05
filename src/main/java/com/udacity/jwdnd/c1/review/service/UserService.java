package com.udacity.jwdnd.c1.review.service;

import com.udacity.jwdnd.c1.review.mapper.UserMapper;
import com.udacity.jwdnd.c1.review.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserMapper userMapper;
    private HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            logger.info("principal instanceof User: " + (principal instanceof User) );
            if (principal instanceof User) {
                return ((User) principal).getUserName();
            } else {
                return principal.toString();
            }
        }

        return null;
    }

    public int createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

        int userId = userMapper.insert(new User(null, user.getUserName(), encodedSalt, hashedPassword,
                user.getFirstName(), user.getLastName()));
        logger.info("[[[[[[[[[[[[[[[[ user " + user.getUserName() + " existes: " + !(isUsernameAvailable(user.getUserName())) );
        return userId;

//        return userMapper.insert(new User(null, user.getUserName(), encodedSalt, hashedPassword,
//                user.getFirstName(), user.getLastName()));
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }
}
