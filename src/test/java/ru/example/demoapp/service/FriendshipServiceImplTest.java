package ru.example.demoapp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.Friendship;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.FriendshipRepository;
import ru.example.demoapp.sevice.FriendshipServiceImpl;
import ru.example.demoapp.util.convertor.DtoConvertor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendshipServiceImplTest {
    @InjectMocks
    private FriendshipServiceImpl friendshipService;
    @Mock
    private FriendshipRepository friendshipRepository;
    @Mock
    private DtoConvertor dtoConvertor;

    @Test
    void getFriend(){
        List<Friendship> friendships = new ArrayList<>();

        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setId(1L);
        user2.setId(2L);
        user3.setId(3L);

        Friendship friendship1 = new Friendship(user1, user2);
        Friendship friendship2 = new Friendship(user1, user3);

        friendships.add(friendship1);
        friendships.add(friendship2);

        when(friendshipRepository.findAllBySender(any(User.class))).thenReturn(friendships);
        when(dtoConvertor.fromUserToUserInfoDto(any(User.class))).thenReturn(new UserInfoDto());

        List<UserInfoDto> result = friendshipService.getFriends(user1);

        Assertions.assertEquals(2, result.size());
        verify(friendshipRepository).findAllBySender(user1);
        verify(dtoConvertor, times(2)).fromUserToUserInfoDto(any(User.class));
    }

    @Test
    void addFriend(){
        User senderUser = new User();
        User receiverUser = new User();
        senderUser.setId(1L);
        receiverUser.setId(2L);

        when(friendshipRepository.findFriendshipBySenderAndReceiver(senderUser, receiverUser)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> friendshipService.addFriend(senderUser, receiverUser));
        verify(friendshipRepository).findFriendshipBySenderAndReceiver(senderUser, receiverUser);
        verify(friendshipRepository).save(any(Friendship.class));
    }

    @Test
    void deleteFriend(){
        User senderUser = new User();
        User receiverUser = new User();
        senderUser.setId(1L);
        receiverUser.setId(2L);

        when(friendshipRepository.findFriendshipBySenderAndReceiver(senderUser, receiverUser)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> friendshipService.addFriend(senderUser, receiverUser));
        verify(friendshipRepository).findFriendshipBySenderAndReceiver(senderUser, receiverUser);
        verify(friendshipRepository).save(any(Friendship.class));
    }
}
