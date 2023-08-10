package ru.example.demoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.demoapp.model.Friendship;
import ru.example.demoapp.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Friendship.FriendshipId> {
    List<Friendship> findAllBySender(User senderUser);

    Optional<Friendship> findFriendshipBySenderAndReceiver(User senderUser, User receiverUser);
}
