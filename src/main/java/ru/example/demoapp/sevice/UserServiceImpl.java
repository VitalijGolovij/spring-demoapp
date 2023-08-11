package ru.example.demoapp.sevice;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.util.convertor.DtoConvertor;
import ru.example.demoapp.util.convertor.DtoConvertorImpl;
import ru.example.demoapp.util.exception.UserNotFoundException;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DtoConvertorImpl dtoConvertor;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, DtoConvertorImpl dtoConvertor) {
        this.userRepository = userRepository;
        this.dtoConvertor = dtoConvertor;
    }

    @Override
    public List<UserInfoDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(dtoConvertor::fromUserToUserInfoDto).toList();
    }

    public User getUser(Long id){
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty())
            throw new UserNotFoundException();

        return user.get();
    }
}
