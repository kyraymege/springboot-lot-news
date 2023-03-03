package com.leadoftoken.news.api.controllers;

import com.leadoftoken.news.business.concretes.AuthManager;
import com.leadoftoken.news.entities.dtos.JwtAuthResponse;
import com.leadoftoken.news.entities.dtos.LoginDto;
import com.leadoftoken.news.entities.dtos.RegisterDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthManager authManager;

    public AuthController(AuthManager authManager) {
        this.authManager = authManager;
    }

    @PostMapping(value = {"/login","/signIn"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authManager.login(loginDto);
        JwtAuthResponse authResponse = new JwtAuthResponse();
        authResponse.setAccess_token(token);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(value = {"/register","/signUp"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authManager.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
