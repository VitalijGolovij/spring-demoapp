package ru.example.demoapp.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.demoapp.dto.SuccessFriendActionResponse;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;
import ru.example.demoapp.sevice.FriendService;
import ru.example.demoapp.sevice.PrincipalService;
import ru.example.demoapp.sevice.UserService;
import ru.example.demoapp.convertor.DtoConvertor;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendControllerTest {
    @Mock
    private FriendService friendService;
    @Mock
    private UserService userService;
    @Mock
    private DtoConvertor dtoConvertor;
    @Mock
    private PrincipalService principalService;
    @InjectMocks
    private FriendController friendController;

    @AfterEach
    public void verifyMocks() {
        verifyNoMoreInteractions(
                friendService,
                userService,
                dtoConvertor,
                principalService
        );
    }

    @Test
    public void testGetFiends(){
        User user1 = getUserWithId(1L);

        UserInfoDto infoDto = new UserInfoDto();
        infoDto.setId(1L);

        List<UserInfoDto> userInfoDtoList = Collections.singletonList(infoDto);

        when(principalService.getPrincipalUser()).thenReturn(user1);
        when(friendService.getFriends(user1)).thenReturn(userInfoDtoList);

        List<UserInfoDto> result = friendController.getFriends();

        Assertions.assertEquals(result, userInfoDtoList);
        verify(principalService).getPrincipalUser();
        verify(friendService).getFriends(user1);
    }

    @Test
    public void testInviteToFriend(){
        User user1 = getUserWithId(1L);
        User user2 = getUserWithId(2L);
        UserInfoDto userInfoDto = getUserInfoWithId(2L);

        when(principalService.getPrincipalUser()).thenReturn(user1);
        when(userService.getUser(2L)).thenReturn(user2);
        doNothing().when(friendService).inviteToFriend(user1, user2);
        when(dtoConvertor.fromUserToUserInfoDto(user2)).thenReturn(userInfoDto);

        SuccessFriendActionResponse expected = new SuccessFriendActionResponse(userInfoDto);
        SuccessFriendActionResponse response = friendController.inviteToFriend(2L);

        Assertions.assertEquals(response.getUserInfo(), expected.getUserInfo());
        verify(friendService).inviteToFriend(user1, user2);
        verify(dtoConvertor).fromUserToUserInfoDto(user2);
    }

    @Test
    public void testAcceptToFriend(){
        User user1 = getUserWithId(1L);
        User user2 = getUserWithId(2L);
        UserInfoDto userInfoDto = getUserInfoWithId(2L);

        when(principalService.getPrincipalUser()).thenReturn(user1);
        when(userService.getUser(2L)).thenReturn(user2);
        doNothing().when(friendService).acceptToFriend(user1, user2);
        when(dtoConvertor.fromUserToUserInfoDto(user2)).thenReturn(userInfoDto);

        SuccessFriendActionResponse expected = new SuccessFriendActionResponse(userInfoDto);
        SuccessFriendActionResponse response = friendController.acceptToFriend(2L);

        Assertions.assertEquals(response.getUserInfo(), expected.getUserInfo());
        verify(friendService).acceptToFriend(user1, user2);
        verify(dtoConvertor).fromUserToUserInfoDto(user2);
    }

    @Test
    public void testDeleteToFriend(){
        User user1 = getUserWithId(1L);
        User user2 = getUserWithId(2L);
        UserInfoDto userInfoDto = getUserInfoWithId(2L);

        when(principalService.getPrincipalUser()).thenReturn(user1);
        when(userService.getUser(2L)).thenReturn(user2);
        doNothing().when(friendService).deleteFriend(user1, user2);
        when(dtoConvertor.fromUserToUserInfoDto(user2)).thenReturn(userInfoDto);

        SuccessFriendActionResponse expected = new SuccessFriendActionResponse(userInfoDto);
        SuccessFriendActionResponse response = friendController.deleteFriend(2L);

        Assertions.assertEquals(response.getUserInfo(), expected.getUserInfo());
        verify(friendService).deleteFriend(user1, user2);
        verify(dtoConvertor).fromUserToUserInfoDto(user2);
    }

    @Test
    public void testCancelInviteToFriend(){
        User user1 = getUserWithId(1L);
        User user2 = getUserWithId(2L);
        UserInfoDto userInfoDto = getUserInfoWithId(2L);

        when(principalService.getPrincipalUser()).thenReturn(user1);
        when(userService.getUser(2L)).thenReturn(user2);
        doNothing().when(friendService).cancelInviteToFriend(user1, user2);
        when(dtoConvertor.fromUserToUserInfoDto(user2)).thenReturn(userInfoDto);

        SuccessFriendActionResponse expected = new SuccessFriendActionResponse(userInfoDto);
        SuccessFriendActionResponse response = friendController.cancelInviteFriend(2L);

        Assertions.assertEquals(response.getUserInfo(), expected.getUserInfo());
        verify(friendService).cancelInviteToFriend(user1, user2);
        verify(dtoConvertor).fromUserToUserInfoDto(user2);
    }

    private User getUserWithId(Long id){
        User user1 = new User();
        user1.setId(id);
        return user1;
    }

    private UserInfoDto getUserInfoWithId(Long id){
        UserInfoDto user1 = new UserInfoDto();
        user1.setId(id);
        return user1;
    }
}
