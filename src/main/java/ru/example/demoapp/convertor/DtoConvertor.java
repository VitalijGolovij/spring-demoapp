package ru.example.demoapp.convertor;

import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;

public interface DtoConvertor {
    public UserInfoDto fromUserToUserInfoDto(User user);
}
