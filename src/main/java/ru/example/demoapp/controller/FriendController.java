package ru.example.demoapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.example.demoapp.dto.SuccessFriendActionResponse;
import ru.example.demoapp.sevice.FriendService;
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
    private final FriendService friendService;
    private final UserService userService;
    private final DtoConvertor dtoConvertor;
    private final PrincipalService principalService;

    @GetMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Get friends")
    public List<UserInfoDto> getFriends(){
        User principalUser = principalService.getPrincipalUser();
        return friendService.getFriends(principalUser);
    }

    @PostMapping("/{id}/invite")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Invite to friends")
    public SuccessFriendActionResponse inviteToFriend(@PathVariable Long id){
        User principalUser = principalService.getPrincipalUser();
        User receiverUser = userService.getUser(id);

        friendService.inviteToFriend(principalUser, receiverUser);

        return new SuccessFriendActionResponse(dtoConvertor.fromUserToUserInfoDto(receiverUser));
    }

    @DeleteMapping("/{id}/invite")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Cancel an invitation to friends")
    public SuccessFriendActionResponse cancelInviteFriend(@PathVariable Long id){
        User principalUser = principalService.getPrincipalUser();
        User receiverUser = userService.getUser(id);

        friendService.cancelInviteToFriend(principalUser, receiverUser);

        return new SuccessFriendActionResponse(dtoConvertor.fromUserToUserInfoDto(receiverUser));
    }

    @PostMapping("/{id}/accept")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Accept an incoming friend invitation")
    public SuccessFriendActionResponse acceptToFriend(@PathVariable Long id){
        User principalUser = principalService.getPrincipalUser();
        User receiverUser = userService.getUser(id);

        friendService.acceptToFriend(principalUser, receiverUser);

        return new SuccessFriendActionResponse(dtoConvertor.fromUserToUserInfoDto(receiverUser));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Delete friend by id")
    public SuccessFriendActionResponse deleteFriend(@PathVariable Long id){
        User principalUser = principalService.getPrincipalUser();
        User receiverUser = userService.getUser(id);

        friendService.deleteFriend(principalUser, receiverUser);

        return new SuccessFriendActionResponse(dtoConvertor.fromUserToUserInfoDto(receiverUser));
    }

    @GetMapping("/requests")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Get friend requests")
    public List<UserInfoDto> getFriendRequests(){
        User principalUser = principalService.getPrincipalUser();
        return friendService.getFriendRequests(principalUser);
    }
}
