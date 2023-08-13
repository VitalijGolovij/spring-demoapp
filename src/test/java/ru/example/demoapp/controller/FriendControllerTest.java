package ru.example.demoapp.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.example.demoapp.dto.FriendActionResponse;
import ru.example.demoapp.dto.SuccessUserAction;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.exception.UserNotFoundException;
import ru.example.demoapp.model.User;
import ru.example.demoapp.sevice.FriendshipService;
import ru.example.demoapp.sevice.PrincipalService;
import ru.example.demoapp.sevice.UserService;
import ru.example.demoapp.convertor.DtoConvertor;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendControllerTest {
    @Mock
    private FriendshipService friendshipService;
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
                friendshipService,
                userService,
                dtoConvertor,
                principalService
        );
    }

    @Test
    public void testGetFiend(){
        User user1 = getUserWithId(1L);

        UserInfoDto infoDto = new UserInfoDto();
        infoDto.setId(1L);

        List<UserInfoDto> userInfoDtoList = Collections.singletonList(infoDto);

        when(principalService.getPrincipal()).thenReturn(user1);
        when(friendshipService.getFriends(user1)).thenReturn(userInfoDtoList);

        List<UserInfoDto> result = friendController.getFriends();

        Assertions.assertEquals(result, userInfoDtoList);
        verify(principalService).getPrincipal();
        verify(friendshipService).getFriends(user1);
    }

    @Test
    public void testAddFriend(){
        Long argId = 1L;

        User user1 = getUserWithId(2L);
        User user2 = getUserWithId(argId);
        UserInfoDto infoDto = getUserInfoWithId(argId);

        when(principalService.getPrincipal()).thenReturn(user1);
        when(userService.getUser(argId)).thenReturn(user2);
        when(dtoConvertor.fromUserToUserInfoDto(user2)).thenReturn(infoDto);

        Assertions.assertDoesNotThrow(()-> friendController.addFriend(argId));
        verify(principalService).getPrincipal();
        verify(userService).getUser(argId);
        verify(dtoConvertor).fromUserToUserInfoDto(user2);
        verify(friendshipService).addFriend(user1, user2);
    }

    @Test
    public void testDeleteFriend(){
        Long argId = 2L;

        User user1 = getUserWithId(1L);
        User user2 = getUserWithId(argId);
        UserInfoDto infoDto = getUserInfoWithId(argId);

        when(principalService.getPrincipal()).thenReturn(user1);
        when(userService.getUser(argId)).thenReturn(user2);
        when(dtoConvertor.fromUserToUserInfoDto(user2)).thenReturn(infoDto);

        ResponseEntity<?> expected = ResponseEntity.ok(new FriendActionResponse(
                "success",
                infoDto
        ));

        Assertions.assertDoesNotThrow(()-> friendController.deleteFriend(argId));
        verify(principalService).getPrincipal();
        verify(userService).getUser(argId);
        verify(dtoConvertor).fromUserToUserInfoDto(user2);
        verify(friendshipService).deleteFriend(user1, user2);
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
