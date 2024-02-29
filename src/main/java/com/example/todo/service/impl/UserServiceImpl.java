package com.example.todo.service.impl;

import com.example.todo.dto.TodoDto;
import com.example.todo.dto.UserDto;
import com.example.todo.entity.Role;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.repository.RoleRepository;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
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

    @Override
    public UserDto grantUserToRole(UserDto userDto, Long id) {
         User user =  userRepository.
                 findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id : "+id));

        Role role = new Role();
        role.setName(userDto.getRole().getName());
        role.setDescription(userDto.getRole().getDescription());
        Role savedRole = roleRepository.save(role);
        user.setRole(savedRole);

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser,UserDto.class);

    }
}
