package com.example.todo.controller;

import com.example.todo.dto.*;
import com.example.todo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;

    }

    // register an user account
    @PostMapping("/register-user")
    public ResponseEntity<RegisterDto>registerUser(@RequestBody RegisterDto registerDto){
        RegisterDto responseUserDto = authService.registerUser(registerDto);
        return new ResponseEntity<>(responseUserDto, HttpStatus.CREATED);
    }

    // get all unalloted role user
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/get-unalloted-users")
    public ResponseEntity<List<UserDto>>getAllUnallotedUser(){
        List<UserDto> responseUserDto = authService.getAllUnAllotedUser();
        return ResponseEntity.ok(responseUserDto);
    }

    // build Register REST API
    @PostMapping("/register-student")
    public ResponseEntity<StudentDto>registerStudent(@RequestBody StudentDto studentDto){
        StudentDto responseStudentDto = authService.registerStudent(studentDto);
        return new ResponseEntity<>(responseStudentDto, HttpStatus.CREATED);
    }



    @PostMapping("/register-teacher")
    public ResponseEntity<TeacherDto>registerTeacher(@RequestBody TeacherDto teacherDto){
        TeacherDto responseTeacherDto = authService.registerTeacher(teacherDto);
        return new ResponseEntity<>(responseTeacherDto, HttpStatus.CREATED);
    }

    // build Login Rest api
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse>login(@RequestBody LoginDto loginDto){
       JwtAuthResponse jwtAuthResponse =  authService.login(loginDto);
        return new ResponseEntity<>(jwtAuthResponse,HttpStatus.OK);
    }

}
