package ru.example.demoapp.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.exception.FriendshipIsAlreadyException;
import ru.example.demoapp.exception.FriendshipNotFoundException;
import ru.example.demoapp.exception.ImpossibleFriendshipException;
import ru.example.demoapp.model.Friendship;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.FriendshipRepository;
import ru.example.demoapp.sevice.FriendshipServiceImpl;
import ru.example.demoapp.convertor.DtoConvertor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendshipServiceImplTest {
    @Mock
    private FriendshipRepository friendshipRepository;
    @Mock
    private DtoConvertor dtoConvertor;
    @InjectMocks
    private FriendshipServiceImpl friendshipService;

    @AfterEach
    public void verifyMocks() {
        verifyNoMoreInteractions(
                friendshipRepository,
                dtoConvertor
        );
    }
    @Test
    void testGetFriend(){
        List<Friendship> friendships = new ArrayList<>();

        User user1 = getUserWithId(1L);
        User user2 = getUserWithId(2L);
        User user3 = getUserWithId(3L);

        Friendship friendship1 = new Friendship(user1, user2);
        Friendship friendship2 = new Friendship(user1, user3);

        friendships.add(friendship1);
        friendships.add(friendship2);

        when(friendshipRepository.findAllBySender(user1)).thenReturn(friendships);
        when(dtoConvertor.fromUserToUserInfoDto(any(User.class))).thenReturn(new UserInfoDto());

        List<UserInfoDto> result = friendshipService.getFriends(user1);

        Assertions.assertEquals(2, result.size());
        verify(friendshipRepository).findAllBySender(user1);
        verify(dtoConvertor, times(2)).fromUserToUserInfoDto(any(User.class));
    }

    @Test
    void testAddFriendSuccess(){
        User senderUser = getUserWithId(1L);
        User receiverUser = getUserWithId(2L);

        when(friendshipRepository.findFriendshipBySenderAndReceiver(senderUser, receiverUser)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> friendshipService.addFriend(senderUser, receiverUser));
        verify(friendshipRepository).findFriendshipBySenderAndReceiver(senderUser, receiverUser);
        verify(friendshipRepository).save(any(Friendship.class));
    }

    @Test
    void testAddFriendAlreadyFriendship(){
        User senderUser = getUserWithId(1L);
        User receiverUser = getUserWithId(2L);

        when(friendshipRepository.findFriendshipBySenderAndReceiver(senderUser, receiverUser))
                .thenReturn(Optional.of(new Friendship(senderUser, receiverUser)));

        assertThrows(FriendshipIsAlreadyException.class,
                () -> friendshipService.addFriend(senderUser, receiverUser));
        verify(friendshipRepository).findFriendshipBySenderAndReceiver(senderUser, receiverUser);
    }

    @Test
    void testAddFriendImpossibleFriendship(){
        User senderUser = getUserWithId(1L);
        User receiverUser = senderUser;

        when(friendshipRepository.findFriendshipBySenderAndReceiver(senderUser, receiverUser))
                .thenReturn(Optional.empty());

        assertThrows(ImpossibleFriendshipException.class,
                () -> friendshipService.addFriend(senderUser, receiverUser));
        verify(friendshipRepository).findFriendshipBySenderAndReceiver(senderUser, receiverUser);
    }

    @Test
    void testDeleteFriendSuccess(){
        User userSender = getUserWithId(1L);
        User userReceiver = getUserWithId(2L);
        Friendship friendship = new Friendship(userSender, userReceiver);
        Optional<Friendship> friendshipOptional = Optional.of(friendship);

        when(friendshipRepository.findFriendshipBySenderAndReceiver(userSender,userReceiver))
                .thenReturn(friendshipOptional);
        doNothing().when(friendshipRepository).delete(friendship);

        friendshipService.deleteFriend(userSender, userReceiver);

        verify(friendshipRepository).findFriendshipBySenderAndReceiver(userSender, userReceiver);
        verify(friendshipRepository).delete(friendship);
    }

    @Test
    void testDeleteFriendFailure(){
        User userSender = getUserWithId(1L);
        User userReceiver = getUserWithId(2L);
        Optional<Friendship> friendshipOptional = Optional.empty();

        when(friendshipRepository.findFriendshipBySenderAndReceiver(userSender,userReceiver))
                .thenReturn(friendshipOptional);

        assertThrows(FriendshipNotFoundException.class,
                () -> friendshipService.deleteFriend(userSender, userReceiver));
        verify(friendshipRepository).findFriendshipBySenderAndReceiver(userSender, userReceiver);
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
