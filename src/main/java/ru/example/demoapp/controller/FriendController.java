package ru.example.demoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.example.demoapp.util.convertor.DtoConvertor;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.util.exception.FriendshipException;
import ru.example.demoapp.util.exception.UserNotFoundException;
import ru.example.demoapp.model.User;
import ru.example.demoapp.dto.UserDetailsDto;
import ru.example.demoapp.sevice.FriendshipServiceImpl;
import ru.example.demoapp.sevice.UserService;
import ru.example.demoapp.util.FriendshipErrorResponse;
import ru.example.demoapp.util.FriendActionResponse;
import ru.example.demoapp.util.UserErrorResponse;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {
    private final FriendshipServiceImpl friendshipService;
    private final UserService userService;
    private final DtoConvertor dtoConvertor;

    @Autowired
    public FriendController(FriendshipServiceImpl friendshipService, UserService userService, DtoConvertor dtoConvertor) {
        this.friendshipService = friendshipService;
        this.userService = userService;
        this.dtoConvertor = dtoConvertor;
    }

    @GetMapping("")
    public List<UserInfoDto> getFriends(){
        User currentUser = getCurrentUser();
        return friendshipService.getFriends(currentUser);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> addFriend(@PathVariable Long id){
        User currentUser = getCurrentUser();
        User receiverUser = userService.getUser(id);
        friendshipService.addFriend(currentUser, receiverUser);

        return ResponseEntity.ok(new FriendActionResponse(
                "success",
                dtoConvertor.fromUserToUserInfoDto(receiverUser)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFriend(@PathVariable Long id){
        User currentUser = getCurrentUser();
        User receiverUser = userService.getUser(id);
        friendshipService.deleteFriend(currentUser, receiverUser);

        return ResponseEntity.ok(new FriendActionResponse(
                "success",
                dtoConvertor.fromUserToUserInfoDto(receiverUser)
        ));
    }

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDto userDetailsDto = (UserDetailsDto) authentication.getPrincipal();
        return userDetailsDto.getUser();
    }

    @ExceptionHandler
    private ResponseEntity<FriendshipErrorResponse> handleException(FriendshipException e){
        FriendshipErrorResponse response = new FriendshipErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException e){
        UserErrorResponse response = new UserErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
