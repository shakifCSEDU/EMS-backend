package com.example.todo.service.impl;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String activeUser(Long user_id) {
        User user =  userRepository.findById(user_id).
                orElseThrow(()->new ResourceNotFoundException("User not found with id : "+user_id));
        user.setStatus(Boolean.TRUE);
        System.out.println(user_id);
        userRepository.save(user);
        return "Active user done!";
    }

    @Override
    public String deActiveUser(Long user_id) {
        User user =  userRepository.findById(user_id).
                orElseThrow(()->new ResourceNotFoundException("User not found with id : "+user_id));
        user.setStatus(Boolean.FALSE);
        userRepository.save(user);
        return "De Active user done!";
    }
}
