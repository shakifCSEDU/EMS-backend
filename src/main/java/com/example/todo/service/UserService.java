package com.example.todo.service;

import com.example.todo.dto.StudentDto;
import com.example.todo.dto.UserDto;

public interface UserService {
    String activeUser(Long user_id);
    String deActiveUser(Long user_id);
    UserDto grantUserToRole(UserDto userDto,Long id);
}
