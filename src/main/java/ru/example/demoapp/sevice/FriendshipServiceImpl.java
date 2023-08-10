package ru.example.demoapp.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.demoapp.convertor.DtoConvertor;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.Friendship;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.FriendshipRepository;

import java.util.List;
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
    public List<UserInfoDto> getFriends(User user) {
        List<Friendship> friendships = friendshipRepository.findAllBySender(user);
        return friendships.stream().map(this::getUserInfoReceiver).collect(Collectors.toList());
    }

    private UserInfoDto getUserInfoReceiver(Friendship friendship){
        User userReceiver = friendship.getReceiver();
        return dtoConvertor.fromUserToUserInfoDto(userReceiver);
    }
}
