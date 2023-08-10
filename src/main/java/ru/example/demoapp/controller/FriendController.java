package ru.example.demoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.FriendshipRepository;
import ru.example.demoapp.security.UserDetailsDto;
import ru.example.demoapp.sevice.FriendshipServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {
    private final FriendshipServiceImpl friendshipService;

    @Autowired
    public FriendController(FriendshipServiceImpl friendshipService) {
        this.friendshipService = friendshipService;
    }

    @GetMapping("")
    public List<UserInfoDto> getFriends(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsDto userDetailsDto = (UserDetailsDto) authentication.getPrincipal();
        User user = userDetailsDto.getUser();

        return friendshipService.getFriends(user);
    }
}
