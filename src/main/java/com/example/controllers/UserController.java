package com.example.controllers;


import com.example.dtos.ResponseStatus;
import com.example.dtos.SignupRequestDto;
import com.example.dtos.SignupResponseDto;
import com.example.models.User;
import com.example.services.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    public SignupResponseDto signup(SignupRequestDto requestDto){
        User user = userService.signUp(requestDto.getEmail(), requestDto.getPassword());
        return new SignupResponseDto(user.getId(), ResponseStatus.SUCCESS);
    }

    public boolean login(String email, String password){
        return userService.login(email, password);
    }
}
