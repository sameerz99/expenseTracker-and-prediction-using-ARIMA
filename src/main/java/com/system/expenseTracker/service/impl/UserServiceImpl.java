package com.system.expenseTracker.service.impl;

import com.system.expenseTracker.dto.requestDto.UserRequestDto;
import com.system.expenseTracker.dto.responseDto.UserResponseDto;
import com.system.expenseTracker.model.User;
import com.system.expenseTracker.repo.RoleRepo;
import com.system.expenseTracker.repo.UserRepo;
import com.system.expenseTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    @Override
    public User saveUserToTable(UserRequestDto dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User newUser = new User();

        newUser.setName(dto.getName());
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(encoder.encode(dto.getPassword()));
        newUser.setEmail(dto.getEmail());
        newUser.setRoles(roleRepo.getUserRole("USER"));

        User savedUser = userRepo.save(newUser);

        return savedUser;
    }

    @Override
    public UserResponseDto findUserByUserName(String username) {
        return new UserResponseDto(userRepo.getUserByUsername(username));
    }
}
