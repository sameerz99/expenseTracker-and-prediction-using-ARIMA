package com.system.expenseTracker.service;

import com.system.expenseTracker.dto.requestDto.UserRequestDto;
import com.system.expenseTracker.dto.responseDto.UserResponseDto;
import com.system.expenseTracker.model.User;

public interface UserService {
    User saveUserToTable(UserRequestDto userRequestDto);
    UserResponseDto findUserByUserName(String username);
}
