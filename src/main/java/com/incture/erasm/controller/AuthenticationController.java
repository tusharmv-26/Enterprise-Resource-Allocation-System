package com.incture.erasm.controller;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.incture.erasm.dto.LoginRequest;
import com.incture.erasm.dto.LoginResponse;
import com.incture.erasm.security.CustomUserDetails;
import com.incture.erasm.security.CustomUserDetailsService;
import com.incture.erasm.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        logger.info("Login attempt for email: {}", request.getEmail());
        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(request.getEmail());
        CustomUserDetails customUser =
                (CustomUserDetails) userDetails;

        if (!passwordEncoder.matches(request.getPassword(), customUser.getPassword())) {
            logger.warn("Failed login attempt for email: {}", request.getEmail());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
        String token = jwtUtil.generateToken(customUser.getUsername());
        logger.info("User '{}' logged in successfully", request.getEmail());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}