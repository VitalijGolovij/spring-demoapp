package ru.example.demoapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.example.demoapp.dto.SuccessUserAction;
import ru.example.demoapp.sevice.FriendshipService;
import ru.example.demoapp.convertor.DtoConvertor;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;
import ru.example.demoapp.sevice.PrincipalService;
import ru.example.demoapp.sevice.UserService;

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
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Get friends")
    public List<UserInfoDto> getFriends(){
        User principalUser = principalService.getPrincipal();
        return friendshipService.getFriends(principalUser);
    }


    @PostMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Add friend by id")
    public SuccessUserAction addFriend(@PathVariable Long id){
        User principalUser = principalService.getPrincipal();
        User receiverUser = userService.getUser(id);
        friendshipService.addFriend(principalUser, receiverUser);

        return new SuccessUserAction(dtoConvertor.fromUserToUserInfoDto(receiverUser));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Delete friend by id")
    public SuccessUserAction deleteFriend(@PathVariable Long id){
        User principalUser = principalService.getPrincipal();
        User receiverUser = userService.getUser(id);
        friendshipService.deleteFriend(principalUser, receiverUser);

        return new SuccessUserAction(dtoConvertor.fromUserToUserInfoDto(receiverUser));
    }
}
