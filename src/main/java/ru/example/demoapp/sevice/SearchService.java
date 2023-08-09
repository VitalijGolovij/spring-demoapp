package ru.example.demoapp.sevice;

import ru.example.demoapp.dto.UserInfoDto;

import java.util.List;

public interface SearchService {
    public List<UserInfoDto> getAllUsers();
}
