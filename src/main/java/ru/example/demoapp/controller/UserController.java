package ru.example.demoapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.example.demoapp.convertor.DtoConvertor;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;
import ru.example.demoapp.sevice.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final DtoConvertor dtoConvertor;

    @GetMapping("")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Get users")
    public List<UserInfoDto> getUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String city
    ){
        return userService.getUsers(
                username,
                firstName,
                lastName,
                city
        );
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Get user by id")
    public UserInfoDto getUser(@PathVariable Long id){
        User user = userService.getUser(id);
        return dtoConvertor.fromUserToUserInfoDto(user);
    }

}
