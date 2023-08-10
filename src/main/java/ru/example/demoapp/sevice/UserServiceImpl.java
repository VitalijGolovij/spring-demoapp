package ru.example.demoapp.sevice;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.exception.UserNotFoundException;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserInfoDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToUserInfoDto).collect(Collectors.toList());
    }

    public User getUser(Long id){
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty())
            throw new UserNotFoundException();

        return user.get();
    }

    private UserInfoDto convertToUserInfoDto(User user){
        return modelMapper.map(user, UserInfoDto.class);
    }
}
