package com.example.todo.service;

import com.example.todo.dto.*;

import java.util.List;

public interface AuthService {
    StudentDto registerStudent(StudentDto studentDto);
    TeacherDto registerTeacher(TeacherDto teacherDto);
    RegisterDto registerUser(RegisterDto registerDto);
    List<UserDto>getAllUnAllotedUser();

    JwtAuthResponse login(LoginDto loginDto);
    //public void createAdminAccount();

}
