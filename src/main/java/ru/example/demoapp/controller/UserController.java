package ru.example.demoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.demoapp.convertor.DtoConvertor;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.exception.UserNotFoundException;
import ru.example.demoapp.model.User;
import ru.example.demoapp.sevice.UserServiceImpl;
import ru.example.demoapp.dto.UserErrorResponse;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;
    private final DtoConvertor dtoConvertor;

    @Autowired
    public UserController(UserServiceImpl userService, DtoConvertor dtoConvertor) {
        this.userService = userService;
        this.dtoConvertor = dtoConvertor;
    }

    @GetMapping("")
    public List<UserInfoDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserInfoDto getUser(@PathVariable Long id){
        User user = userService.getUser(id);
        return dtoConvertor.fromUserToUserInfoDto(user);
    }
}
