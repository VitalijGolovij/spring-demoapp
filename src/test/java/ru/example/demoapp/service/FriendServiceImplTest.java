package ru.example.demoapp.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.exception.FriendAlreadyException;
import ru.example.demoapp.exception.FriendException;
import ru.example.demoapp.exception.FriendNotFoundException;
import ru.example.demoapp.exception.ImpossibleFriendException;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.UserRepository;
import ru.example.demoapp.sevice.FriendServiceImpl;
import ru.example.demoapp.convertor.DtoConvertor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendServiceImplTest {
    @Mock
    private DtoConvertor dtoConvertor;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private FriendServiceImpl friendService;

    @AfterEach
    public void verifyMocks() {
        verifyNoMoreInteractions(
                dtoConvertor,
                userRepository
        );
    }

    @Test
    public void testInviteToFriend_Success(){
        User user1 = getUserWithId(1L);
        User user2 = getUserWithId(2L);
        user1.setFriendsRequest(new HashSet<>());
        user2.setFriendsRequest(new HashSet<>());
        user1.setFriends(new HashSet<>());
        user2.setFriends(new HashSet<>());

        when(userRepository.save(user1)).thenReturn(user1);

        friendService.inviteToFriend(user1, user2);

        Assertions.assertTrue(user1.getFriendsRequest().contains(user2));
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    public void testInviteToFriendImpossible_FriendshipException(){
        User user1 = getUserWithId(1L);
        Assertions.assertThrows(ImpossibleFriendException.class, ()-> friendService.inviteToFriend(user1, user1));
    }

    @Test
    public void testInviteToFriend_FriendAlreadyException(){
        User user1 = getUserWithId(1L);
        User user2 = getUserWithId(2L);
        user1.setFriendsRequest(new HashSet<>());
        user2.setFriendsRequest(new HashSet<>());
        user1.setFriends(new HashSet<>(List.of(user2)));
        user2.setFriends(new HashSet<>(List.of(user1)));

        Assertions.assertThrows(FriendAlreadyException.class, ()-> friendService.inviteToFriend(user1, user2));
    }

    @Test
    public void testCancelInviteToFriend_Success(){
        User user1 = getUserWithId(1L);
        User user2 = getUserWithId(2L);
        user1.setFriendsRequest(new HashSet<>(List.of(user2)));
        user2.setFriendsRequest(new HashSet<>());
        user1.setFriends(new HashSet<>());
        user2.setFriends(new HashSet<>());

        when(userRepository.save(user1)).thenReturn(user1);

        friendService.cancelInviteToFriend(user1, user2);

        Assertions.assertFalse(user1.getFriendsRequest().contains(user2));
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    public void testCancelInviteToFriend_FriendAlreadyException(){
        User user1 = getUserWithId(1L);
        User user2 = getUserWithId(2L);
        user1.setFriendsRequest(new HashSet<>());
        user2.setFriendsRequest(new HashSet<>());
        user1.setFriends(new HashSet<>(List.of(user2)));
        user2.setFriends(new HashSet<>(List.of(user1)));

        Assertions.assertThrows(FriendAlreadyException.class, ()-> friendService.cancelInviteToFriend(user1, user2));
    }

    @Test
    public void testCancelInviteToFriend_FriendException(){
        User user1 = getUserWithId(1L);
        User user2 = getUserWithId(2L);
        user1.setFriendsRequest(new HashSet<>());
        user2.setFriendsRequest(new HashSet<>());
        user1.setFriends(new HashSet<>(List.of(user2)));
        user2.setFriends(new HashSet<>(List.of(user1)));

        Assertions.assertThrows(FriendException.class, ()-> friendService.cancelInviteToFriend(user1, user2));
    }

    @Test
    public void testAcceptInviteToFriend_Success(){
        User user1 = getUserWithId(1L);
        User user2 = getUserWithId(2L);
        user1.setFriendsRequest(new HashSet<>());
        user2.setFriendsRequest(new HashSet<>(List.of(user1)));
        user1.setFriends(new HashSet<>());
        user2.setFriends(new HashSet<>());

        List<User> userList = Arrays.asList(user1, user2);
        when(userRepository.saveAll(userList)).thenReturn(userList);

        friendService.acceptToFriend(user1, user2);

        Assertions.assertTrue(user1.getFriends().contains(user2));
        Assertions.assertTrue(user2.getFriends().contains(user1));
        Assertions.assertEquals(user2.getFriendsRequest().size(), 0);
    }

    @Test
    public void testAcceptInviteToFriend_FriendException(){
        User user1 = getUserWithId(1L);
        User user2 = getUserWithId(2L);
        user1.setFriendsRequest(new HashSet<>(List.of(user2)));
        user2.setFriendsRequest(new HashSet<>());
        user1.setFriends(new HashSet<>());
        user2.setFriends(new HashSet<>());

        Assertions.assertThrows(FriendException.class, ()-> friendService.acceptToFriend(user1, user2));
    }

    @Test
    public void testGetFriends(){
        User user1 = getUserWithId(1L);
        Set<User> friends = new HashSet<>(List.of(new User()));
        user1.setFriends(friends);

        UserInfoDto infoDto = getUserInfoWithId(1L);

        when(dtoConvertor.fromUserToUserInfoDto(any(User.class))).thenReturn(infoDto);

        List<UserInfoDto> users = friendService.getFriends(user1);

        Assertions.assertEquals(users.get(0), infoDto);
    }

    @Test
    public void testDeleteFriend_Success(){
        User user1 = getUserWithId(1L);
        User user2 = getUserWithId(2L);
        user1.setFriendsRequest(new HashSet<>());
        user2.setFriendsRequest(new HashSet<>());
        user1.setFriends(new HashSet<>(List.of(user2)));
        user2.setFriends(new HashSet<>(List.of(user1)));

        when(userRepository.saveAll(List.of(user1, user2))).thenReturn(List.of(user1, user2));

        friendService.deleteFriend(user1, user2);

        Assertions.assertEquals(user1.getFriends().size(), 0 );
        Assertions.assertEquals(user2.getFriends().size(), 0 );
    }

    @Test
    public void testDeleteFriend_FriendNotFroundException(){
        User user1 = getUserWithId(1L);
        User user2 = getUserWithId(2L);
        user1.setFriendsRequest(new HashSet<>());
        user2.setFriendsRequest(new HashSet<>());
        user1.setFriends(new HashSet<>());
        user2.setFriends(new HashSet<>());

        Assertions.assertThrows(FriendNotFoundException.class, ()-> friendService.deleteFriend(user1, user2));

    }

    @Test
    public void testGetFriendRequests(){
        User user1 = getUserWithId(1L);
        User user2 = getUserWithId(2L);
        UserInfoDto userInfoDto = getUserInfoWithId(2L);
        when(userRepository.findByFriendsRequestContaining(user1)).thenReturn(List.of(user2));
        when(dtoConvertor.fromUserToUserInfoDto(user2)).thenReturn(userInfoDto);

        List<UserInfoDto> result = friendService.getFriendRequests(user1);

        Assertions.assertEquals(result.get(0), userInfoDto);
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
