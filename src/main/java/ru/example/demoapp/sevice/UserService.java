package ru.example.demoapp.sevice;

import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;

import java.util.List;

public interface UserService {
    public List<UserInfoDto> getAllUsers();
    public User getUser(Long id);
}
