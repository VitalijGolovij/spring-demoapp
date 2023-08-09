package ru.example.demoapp.sevice;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SearchServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserInfoDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToUserInfoDto).collect(Collectors.toList());
    }

    private UserInfoDto convertToUserInfoDto(User user){
        return modelMapper.map(user, UserInfoDto.class);
    }
}
