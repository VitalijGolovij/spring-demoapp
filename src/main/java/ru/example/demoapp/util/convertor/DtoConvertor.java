package ru.example.demoapp.util.convertor;

import ru.example.demoapp.dto.RegisterUserDto;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;

public interface DtoConvertor {
    public UserInfoDto fromUserToUserInfoDto(User user);
    public User fromRegisterUserDtoToUser(RegisterUserDto registerUserDto);
}
