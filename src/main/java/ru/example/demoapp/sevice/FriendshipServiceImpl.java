package ru.example.demoapp.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.demoapp.util.convertor.DtoConvertor;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.util.exception.FriendshipIsAlreadyException;
import ru.example.demoapp.util.exception.FriendshipNotFoundException;
import ru.example.demoapp.util.exception.ImpossibleFriendshipException;
import ru.example.demoapp.model.Friendship;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.FriendshipRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final DtoConvertor dtoConvertor;

    @Autowired
    public FriendshipServiceImpl(FriendshipRepository friendshipRepository, DtoConvertor dtoConvertor) {
        this.friendshipRepository = friendshipRepository;
        this.dtoConvertor = dtoConvertor;
    }

    @Override
    public List<UserInfoDto> getFriends(User principalUser) {
        List<Friendship> friendships = friendshipRepository.findAllBySender(principalUser);
        return friendships.stream().map(this::getUserInfoReceiver).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addFriend(final User senderUser, final User receiverUser) {
        Optional<Friendship> friendship = friendshipRepository.findFriendshipBySenderAndReceiver(
                senderUser,
                receiverUser
        );
        if(senderUser.equals(receiverUser))
            throw new ImpossibleFriendshipException();
        if (friendship.isPresent())
            throw new FriendshipIsAlreadyException();

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
            throw new FriendshipNotFoundException();

        friendshipRepository.delete(friendship.get());
    }


    private UserInfoDto getUserInfoReceiver(Friendship friendship){
        User userReceiver = friendship.getReceiver();
        return dtoConvertor.fromUserToUserInfoDto(userReceiver);
    }

}
