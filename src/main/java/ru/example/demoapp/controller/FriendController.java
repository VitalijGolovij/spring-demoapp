package ru.example.demoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;
import ru.example.demoapp.security.UserDetailsDto;
import ru.example.demoapp.sevice.FriendshipServiceImpl;
import ru.example.demoapp.sevice.SearchService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friends")
public class FriendController {
    private final FriendshipServiceImpl friendshipService;
    private final SearchService searchService;

    @Autowired
    public FriendController(FriendshipServiceImpl friendshipService, SearchService searchService) {
        this.friendshipService = friendshipService;
        this.searchService = searchService;
    }

    @GetMapping("")
    public List<UserInfoDto> getFriends(){
        User currentUser = getCurrentUser();
        return friendshipService.getFriends(currentUser);
    }

    //TODO изменить ответ и отработать исключения
    @PostMapping("/{id}")
    public Map<String, String> addFriend(@PathVariable Long id){
        User currentUser = getCurrentUser();
        User receiverUser = searchService.getUser(id);
        friendshipService.addFriend(currentUser, receiverUser);

        return Map.of("dobavleno","vrode");
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteFriend(@PathVariable Long id){
        User currentUser = getCurrentUser();
        User receiverUser = searchService.getUser(id);
        friendshipService.deleteFriend(currentUser, receiverUser);

        return Map.of("udalil","vrode");
    }

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDto userDetailsDto = (UserDetailsDto) authentication.getPrincipal();
        return userDetailsDto.getUser();
    }
}
