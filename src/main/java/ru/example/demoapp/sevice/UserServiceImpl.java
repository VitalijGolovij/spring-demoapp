package ru.example.demoapp.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.demoapp.convertor.DtoConvertor;
import ru.example.demoapp.dto.UserInfoDto;
import ru.example.demoapp.convertor.DtoConvertorImpl;
import ru.example.demoapp.exception.UserNotFoundException;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DtoConvertor dtoConvertor;

    @Override
    public List<UserInfoDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(dtoConvertor::fromUserToUserInfoDto).toList();
    }

    @Override
    public User getUser(Long id){
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty())
            throw new UserNotFoundException(id);

        return user.get();
    }
}
