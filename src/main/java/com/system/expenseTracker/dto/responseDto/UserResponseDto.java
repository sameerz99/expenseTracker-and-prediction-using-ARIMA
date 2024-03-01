package com.system.expenseTracker.dto.responseDto;

import com.system.expenseTracker.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Integer id;
    private String name;
    private String username;
    private String email;
    private String password;

    public UserResponseDto(User user){
        this.id=user.getId();
        this.name=user.getName();
        this.username=user.getUsername();
        this.password=user.getPassword();
        this.email=user.getEmail();
    }
}
