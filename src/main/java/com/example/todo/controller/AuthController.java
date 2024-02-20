package com.example.todo.controller;

import com.example.todo.dto.*;
import com.example.todo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    // build Register REST API

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register-student")
    public ResponseEntity<String>registerStudent(@RequestBody StudentDto studentDto){
        String response = authService.registerStudent(studentDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register-teacher")
    public ResponseEntity<String>registerTeacher(@RequestBody TeacherDto teacherDto){
        String response = authService.registerTeacher(teacherDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // build Login Rest api
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse>login(@RequestBody LoginDto loginDto){
       JwtAuthResponse jwtAuthResponse =  authService.login(loginDto);
        return new ResponseEntity<>(jwtAuthResponse,HttpStatus.OK);
    }

}
