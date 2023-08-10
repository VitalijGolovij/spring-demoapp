package ru.example.demoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.example.demoapp.convertor.DtoConvertor;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;
import ru.example.demoapp.sevice.SearchServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final SearchServiceImpl searchService;
    private final DtoConvertor dtoConvertor;

    @Autowired
    public UserController(SearchServiceImpl searchService, DtoConvertor dtoConvertor) {
        this.searchService = searchService;
        this.dtoConvertor = dtoConvertor;
    }

    @GetMapping("")
    public List<UserInfoDto> getAllUsers(){
        return searchService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserInfoDto getUser(@PathVariable Long id){
        User user = searchService.getUser(id);
        return dtoConvertor.fromUserToUserInfoDto(user);
    }
}
