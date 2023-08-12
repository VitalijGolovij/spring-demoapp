package ru.example.demoapp.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;
import ru.example.demoapp.sevice.UserServiceImpl;
import ru.example.demoapp.convertor.DtoConvertorImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserServiceImpl userService;
    @Mock
    private DtoConvertorImpl dtoConvertor;
    @InjectMocks
    private UserController userController;

    @AfterEach
    public void verifyMocks() {
        verifyNoMoreInteractions(
                userService,
                dtoConvertor
        );
    }

    @Test
    public void testGetAllUser(){
        List<UserInfoDto> userInfoDtoList = new ArrayList<>();
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(1L);
        userInfoDtoList.add(userInfoDto);

        when(userService.getAllUsers()).thenReturn(userInfoDtoList);

        List<UserInfoDto> result = userController.getAllUsers();

        Assertions.assertEquals(userInfoDtoList, result);
        verify(userService).getAllUsers();
    }

    @Test
    public void testGetUser(){
        User user = new User();
        Long userId = 1L;
        user.setId(userId);

        UserInfoDto userInfo = new UserInfoDto();
        userInfo.setId(1L);

        when(userService.getUser(userId)).thenReturn(user);
        when(dtoConvertor.fromUserToUserInfoDto(user)).thenReturn(userInfo);

        UserInfoDto result = userController.getUser(userId);

        Assertions.assertEquals(result, userInfo);
        verify(userService).getUser(userId);
        verify(dtoConvertor).fromUserToUserInfoDto(user);
    }
}
