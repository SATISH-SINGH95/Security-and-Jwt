package com.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.model.request.LoginRequest;
import com.security.model.request.UserRequest;
import com.security.model.response.JwtResponse;
import com.security.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest){
        userService.registerUser(userRequest);
        return new ResponseEntity<>("User Registered Successfully", HttpStatus.CREATED);

    }

    @PostMapping("/jwt-token")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){
        JwtResponse token = userService.generateToken(loginRequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
