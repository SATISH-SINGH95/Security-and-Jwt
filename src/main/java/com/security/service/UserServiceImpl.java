package com.security.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.exception.AuthenticationFailedException;
import com.security.model.entity.User;
import com.security.model.request.LoginRequest;
import com.security.model.request.UserRequest;
import com.security.model.response.JwtResponse;
import com.security.repository.UserRepository;
import com.security.utils.JwtUtil;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void registerUser(UserRequest userRequest) {
        User user = User.builder().username(userRequest.getUsername())
            .password(passwordEncoder.encode(userRequest.getPassword()))
            .roles("USER")
            .build();

        userRepository.save(user);
    }

    @Override
    public JwtResponse generateToken(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if(authentication.isAuthenticated()){
            String token = jwtUtil.generateToken(loginRequest.getUsername());
            return JwtResponse.builder().token(token).build();
        }
        throw new AuthenticationFailedException("Authentication failed", HttpStatus.FORBIDDEN);

    }

}
