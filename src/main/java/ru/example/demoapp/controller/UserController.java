package ru.example.demoapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.demoapp.convertor.DtoConvertor;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.exception.UserNotFoundException;
import ru.example.demoapp.model.User;
import ru.example.demoapp.sevice.UserService;
import ru.example.demoapp.sevice.UserServiceImpl;
import ru.example.demoapp.dto.UserErrorResponse;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final DtoConvertor dtoConvertor;

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
