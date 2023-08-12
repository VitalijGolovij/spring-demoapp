package ru.example.demoapp.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.demoapp.convertor.DtoConvertor;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.exception.FriendshipIsAlreadyException;
import ru.example.demoapp.exception.FriendshipNotFoundException;
import ru.example.demoapp.exception.ImpossibleFriendshipException;
import ru.example.demoapp.model.Friendship;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.FriendshipRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final DtoConvertor dtoConvertor;

    @Override
    public List<UserInfoDto> getFriends(User principalUser) {
        List<Friendship> friendships = friendshipRepository.findAllBySender(principalUser);
        return friendships.stream().map(this::getUserInfoReceiver).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addFriend(User senderUser, User receiverUser) {
        Optional<Friendship> friendship = friendshipRepository.findFriendshipBySenderAndReceiver(
                senderUser,
                receiverUser
        );
        if(senderUser.equals(receiverUser))
            throw new ImpossibleFriendshipException();
        if (friendship.isPresent())
            throw new FriendshipIsAlreadyException(senderUser.getId(), receiverUser.getId());

        friendshipRepository.save(new Friendship(senderUser, receiverUser));
    }

    @Override
    @Transactional
    public void deleteFriend(User senderUser, User receiverUser) {
        Optional<Friendship> friendship = friendshipRepository.findFriendshipBySenderAndReceiver(
                senderUser,
                receiverUser
        );
        if (friendship.isEmpty())
            throw new FriendshipNotFoundException(senderUser.getId(), receiverUser.getId());

        friendshipRepository.delete(friendship.get());
    }

    private UserInfoDto getUserInfoReceiver(Friendship friendship){
        User userReceiver = friendship.getReceiver();
        return dtoConvertor.fromUserToUserInfoDto(userReceiver);
    }

}
