package ru.example.demoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.sevice.SearchServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchServiceImpl searchService;

    @Autowired
    public SearchController(SearchServiceImpl searchService) {
        this.searchService = searchService;
    }

    @GetMapping("")
    public List<UserInfoDto> getAllUsers(){
        return searchService.getAllUsers();
    }

}
