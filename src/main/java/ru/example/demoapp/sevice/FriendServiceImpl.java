package ru.example.demoapp.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.demoapp.convertor.DtoConvertor;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.exception.FriendException;
import ru.example.demoapp.exception.FriendAlreadyException;
import ru.example.demoapp.exception.FriendNotFoundException;
import ru.example.demoapp.exception.ImpossibleFriendException;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final DtoConvertor dtoConvertor;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void inviteToFriend(User userFrom, User userTo){
        validateFriendRequest(userFrom, userTo);

        if (isAlreadyInvite(userTo, userFrom)){
            acceptToFriend(userFrom, userTo);
            return;
        }
        userFrom.getFriendsRequest().add(userTo);

        userRepository.save(userFrom);
    }

    @Override
    @Transactional
    public void cancelInviteToFriend(User userFrom, User userTo){
        validateFriendRequest(userFrom, userTo);

        if (isAlreadyInvite(userFrom, userTo)){
            userFrom.getFriendsRequest().remove(userTo);
            userRepository.save(userFrom);
        } else {
            throw new FriendException("The friend invitation was not found");
        }
    }

    @Override
    @Transactional
    public void acceptToFriend(User userFrom, User userTo) {
        validateFriendRequest(userFrom, userTo);

        if (!isAlreadyInvite(userTo, userFrom)){
            throw new FriendException("The friend invitation was not found");
        }
        userTo.getFriendsRequest().remove(userFrom);

        userFrom.getFriends().add(userTo);
        userTo.getFriends().add(userFrom);

        userRepository.saveAll(Arrays.asList(userFrom, userTo));
    }

    @Override
    public List<UserInfoDto> getFriends(User principalUser) {
        Set<User> friends = principalUser.getFriends();
        return friends.stream()
                .map(dtoConvertor::fromUserToUserInfoDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteFriend(User userFrom, User userTo) {
        if (isAlreadyFriend(userFrom, userTo)){
            userFrom.getFriends().remove(userTo);
            userTo.getFriends().remove(userFrom);

            userRepository.saveAll(Arrays.asList(userFrom, userTo));
        } else {
            throw new FriendNotFoundException(userFrom.getId(), userTo.getId());
        }
    }

    @Override
    public List<UserInfoDto> getFriendRequests(User principalUser){
        List<User> friendRequests = userRepository.findByFriendsRequestContaining(principalUser);
        return friendRequests.stream()
                .map(dtoConvertor::fromUserToUserInfoDto)
                .collect(Collectors.toList());
    }

    private boolean isAlreadyFriend(User userFrom, User userTo){
        return userFrom.getFriends().contains(userTo);
    }

    private boolean isAlreadyInvite(User userFrom, User userTo) {
        return userFrom.getFriendsRequest().contains(userTo);
    }

    private void validateFriendRequest(User userFrom, User userTo){
        if (userFrom.equals(userTo)) {
            throw new ImpossibleFriendException();
        }
        if (isAlreadyFriend(userFrom, userTo)){
            throw new FriendAlreadyException(userFrom.getId(), userTo.getId());
        }
    }
}
