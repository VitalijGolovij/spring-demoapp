package ru.example.demoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.demoapp.model.Friendship;
import ru.example.demoapp.model.User;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Friendship.FriendshipId> {
    //TODO
    List<Friendship> findAllBySender(User user);

}
