package com.security.service;

import com.security.model.request.LoginRequest;
import com.security.model.request.UserRequest;
import com.security.model.response.JwtResponse;

public interface UserService {


    void registerUser(UserRequest userRequest);

    JwtResponse generateToken(LoginRequest loginRequest);

}
