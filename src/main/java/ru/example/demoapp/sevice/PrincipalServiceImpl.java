package ru.example.demoapp.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.example.demoapp.dto.UserDetailsDto;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PrincipalServiceImpl implements PrincipalService {
    private final UserRepository userRepository;
    @Override
    public User getPrincipalUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDto userDetailsDto = (UserDetailsDto) authentication.getPrincipal();
        return userRepository.findByUsername(userDetailsDto.getUsername()).get();
    }
}
