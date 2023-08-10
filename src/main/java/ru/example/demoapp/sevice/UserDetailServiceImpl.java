package ru.example.demoapp.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.UserRepository;
import ru.example.demoapp.dto.UserDetailsDto;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException(
                    String.format("Username '%s' not found", username)
            );
        return new UserDetailsDto(user.get());
    }

    public boolean isUsernameAvailable(String username){
        return userRepository.findByUsername(username).isEmpty();
    }
}
