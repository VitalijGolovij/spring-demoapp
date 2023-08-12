package ru.example.demoapp.sevice;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.example.demoapp.dto.UserDetailsDto;
import ru.example.demoapp.model.User;

@Service
public class PrincipalServiceImpl implements PrincipalService {
    @Override
    public User getPrincipal(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDto userDetailsDto = (UserDetailsDto) authentication.getPrincipal();
        return userDetailsDto.getUser();
    }
}
