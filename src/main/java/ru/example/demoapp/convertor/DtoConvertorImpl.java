package ru.example.demoapp.convertor;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.demoapp.dto.RegisterUserDto;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;

@Component
@RequiredArgsConstructor
public class DtoConvertorImpl implements DtoConvertor{
    private final ModelMapper modelMapper;

    @Override
    public UserInfoDto fromUserToUserInfoDto(User user){
        return modelMapper.map(user, UserInfoDto.class);
    }

    @Override
    public User fromRegisterUserDtoToUser(RegisterUserDto registerUserDto) {
        return modelMapper.map(registerUserDto, User.class);
    }
}
