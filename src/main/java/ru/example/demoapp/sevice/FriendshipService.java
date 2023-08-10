package ru.example.demoapp.sevice;

import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;

import java.util.List;


public interface FriendshipService {

    public List<UserInfoDto> getFriends(User principalUser);

    public void addFriend(User senderUser, User receiverUser);

    public void deleteFriend(User senderUser, User receiverUser);
}
