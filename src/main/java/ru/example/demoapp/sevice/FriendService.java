package ru.example.demoapp.sevice;

import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;

import java.util.List;


public interface FriendService {

    public List<UserInfoDto> getFriends(User principalUser);
    public void deleteFriend(User userFrom, User userTo);
    public void inviteToFriend(User userFrom, User userTo);
    public void acceptToFriend(User userFrom, User userTo);
    public void cancelInviteToFriend(User userFrom, User userTo);
    public List<UserInfoDto> getFriendRequests(User principalUser);
}
