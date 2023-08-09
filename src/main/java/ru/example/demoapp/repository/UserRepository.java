package ru.example.demoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.demoapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
