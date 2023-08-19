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
        User user1 = new User();
        UserInfoDto userInfo = new UserInfoDto();
        List<User> users = List.of(user1);

        when(userRepository.findUsers(null, null, null, null)).thenReturn(users);
        when(dtoConvertor.fromUserToUserInfoDto(user1)).thenReturn(userInfo);

        List<UserInfoDto> result = userService.getUsers(null, null, null, null);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(result.get(0), userInfo);
        Mockito.verify(userRepository).findUsers(null, null, null, null);
        Mockito.verify(dtoConvertor, times(1)).fromUserToUserInfoDto(any(User.class));
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
