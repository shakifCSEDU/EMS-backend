package com.example.todo.service;

import com.example.todo.dto.LoginDto;
import com.example.todo.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
    String login(LoginDto loginDto);

}
