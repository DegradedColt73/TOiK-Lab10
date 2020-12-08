package com.example.demo.rest;

import com.example.demo.dto.LoginDto;
import com.example.demo.lockedAccountException;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserApiController.class);

    private final UserService userService;

    public UserApiController() {
        userService = new UserService();
    }

    @PostMapping("/api/users/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto) {
        LOGGER.info("--- check login data: {}", loginDto);

        try{
            if(userService.checkLogin(loginDto.getLogin(), loginDto.getPassword())) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch (lockedAccountException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
