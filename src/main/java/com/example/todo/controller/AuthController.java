package com.example.todo.controller;

import com.example.todo.dto.LoginDto;
import com.example.todo.dto.RegisterDto;
import com.example.todo.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    // build Register REST API
    @PostMapping("/register")
    public ResponseEntity<String>register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // build Login Rest api
    @PostMapping("/login")
    public ResponseEntity<String>login(@RequestBody LoginDto loginDto){
       String response =  authService.login(loginDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
