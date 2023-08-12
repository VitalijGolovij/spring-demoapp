package ru.example.demoapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.demoapp.sevice.FriendshipService;
import ru.example.demoapp.convertor.DtoConvertor;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;
import ru.example.demoapp.sevice.PrincipalService;
import ru.example.demoapp.sevice.UserService;
import ru.example.demoapp.dto.FriendActionResponse;

import java.util.List;

@RestController
@RequestMapping("/user/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendshipService friendshipService;
    private final UserService userService;
    private final DtoConvertor dtoConvertor;
    private final PrincipalService principalService;

    @GetMapping
    public List<UserInfoDto> getFriends(){
        User principalUser = principalService.getPrincipal();
        return friendshipService.getFriends(principalUser);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> addFriend(@PathVariable Long id){
        User principalUser = principalService.getPrincipal();
        User receiverUser = userService.getUser(id);
        friendshipService.addFriend(principalUser, receiverUser);

        return ResponseEntity.ok(new FriendActionResponse(
                "success",
                dtoConvertor.fromUserToUserInfoDto(receiverUser)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFriend(@PathVariable Long id){
        User principalUser = principalService.getPrincipal();
        User receiverUser = userService.getUser(id);
        friendshipService.deleteFriend(principalUser, receiverUser);

        return ResponseEntity.ok(new FriendActionResponse(
                "success",
                dtoConvertor.fromUserToUserInfoDto(receiverUser)
        ));
    }
}
