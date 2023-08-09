package ru.example.demoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.demoapp.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);
}
