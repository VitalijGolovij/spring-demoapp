package ru.example.demoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.example.demoapp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);

    public Optional<User> findById(Long id);
    public List<User> findByFriendsRequestContaining(User user);
    @Query("SELECT u FROM User u " +
            "WHERE (:firstName IS NULL OR u.firstName = :firstName) " +
            "AND (:lastName IS NULL OR u.lastName = :lastName) " +
            "AND (:username IS NULL OR u.username = :username)" +
            "AND (:city IS NULL OR u.city = :city)")
    public List<User> findUsers(
            String username,
            String firstName,
            String lastName,
            String city
    );
}
