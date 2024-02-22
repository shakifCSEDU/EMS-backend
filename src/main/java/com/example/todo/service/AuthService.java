package com.example.todo.service;

import com.example.todo.dto.JwtAuthResponse;
import com.example.todo.dto.LoginDto;
import com.example.todo.dto.StudentDto;
import com.example.todo.dto.TeacherDto;

public interface AuthService {
    String registerStudent(StudentDto studentDto);
    String registerTeacher(TeacherDto teacherDto);

    JwtAuthResponse login(LoginDto loginDto);
    //public void createAdminAccount();
}
