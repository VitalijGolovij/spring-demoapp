package ru.example.demoapp.sevice;

import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;

import java.util.List;

public interface UserService {
    public User getUser(Long id);
    List<UserInfoDto> getUsers(String username, String firstName, String lastName, String city);
}
