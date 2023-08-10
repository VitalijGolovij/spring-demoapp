package ru.example.demoapp.sevice;

import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;

import java.util.List;
import java.util.Optional;

public interface SearchService {
    public List<UserInfoDto> getAllUsers();
    public User getUser(Long id);
}
