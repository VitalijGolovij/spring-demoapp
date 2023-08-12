package ru.example.demoapp.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.UserRepository;
import ru.example.demoapp.sevice.RegisterServiceImpl;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private RegisterServiceImpl registerService;

    @AfterEach
    public void verifyMocks() {
        verifyNoMoreInteractions(
                userRepository,
                passwordEncoder
        );
    }

    @Test
    public void testRegister(){
        User user = new User();
        user.setUsername("Test");
        user.setPassword("password");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        registerService.register(user);

        Assertions.assertEquals(user.getRole(), "ROLE_USER");
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(user);
    }
}
