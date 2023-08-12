package ru.example.demoapp.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.UserRepository;
import ru.example.demoapp.sevice.UserServiceImpl;
import ru.example.demoapp.convertor.DtoConvertorImpl;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DtoConvertorImpl dtoConvertor;

    @AfterEach
    public void verifyMocks() {
        verifyNoMoreInteractions(
                userRepository,
                dtoConvertor
        );
    }
    @Test
    void testGetAllUser(){
        List<User> userList = userList();

        UserInfoDto userInfoDto1 = new UserInfoDto();
        userInfoDto1.setUsername("abc");

        UserInfoDto userInfoDto2 = new UserInfoDto();
        userInfoDto2.setUsername("1abc");

        when(userRepository.findAll()).thenReturn(userList);

        when(dtoConvertor.fromUserToUserInfoDto(userList.get(0))).thenReturn(userInfoDto1);
        when(dtoConvertor.fromUserToUserInfoDto(userList.get(1))).thenReturn(userInfoDto2);

        List<UserInfoDto> result = userService.getAllUsers();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(result.get(0), userInfoDto1);
        Assertions.assertEquals(result.get(1), userInfoDto2);
        Mockito.verify(userRepository).findAll();
        Mockito.verify(dtoConvertor, times(2)).fromUserToUserInfoDto(any(User.class));
    }

    @Test
    void testGetUser(){
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUser(userId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(userId, user.getId());
        verify(userRepository).findById(userId);
    }

    private List<User> userList(){
        List<User> userList = new ArrayList<>();

        User user1 = new User();
        user1.setUsername("abc");
        user1.setPassword("dada");
        user1.setCity("daddad");
        user1.setEmail("dadd@mail.ru");
        user1.setFirstName("Iuya");
        user1.setLastName("uuuuq");

        User user2 = new User();
        user2.setUsername("1abc");
        user2.setPassword("d1ada");
        user2.setCity("daddad1");
        user2.setEmail("dad1d@mail.ru");
        user2.setFirstName("1Iuya");
        user2.setLastName("uu1uuq");

        userList.add(user1);
        userList.add(user2);

        return userList;
    }
}
