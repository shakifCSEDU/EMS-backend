package com.example.todo.controller;

import com.example.todo.dto.AuthStudentResponse;
import com.example.todo.dto.AuthTeacherResponse;
import com.example.todo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    // get All info after successfull login authentication
    @PreAuthorize("hasAnyRole('TEACHER')")
    @GetMapping("/getAllInfo")
    public ResponseEntity<AuthTeacherResponse> getAllTeacherInfo(@RequestParam("token") String token){

        AuthTeacherResponse authTeacherResponse =
                teacherService.getAllTeacherInfo(token);
        return ResponseEntity.ok(authTeacherResponse);
    }
}
