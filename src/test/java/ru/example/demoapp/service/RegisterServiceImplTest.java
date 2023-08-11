package ru.example.demoapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.example.demoapp.model.User;
import ru.example.demoapp.repository.UserRepository;
import ru.example.demoapp.sevice.RegisterService;
import ru.example.demoapp.sevice.RegisterServiceImpl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class RegisterServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private RegisterService registerService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        registerService = new RegisterServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    public void testRegister(){
        User user = new User();
        user.setUsername("Test");
        user.setFirstName("Testkk");
        user.setLastName("Lokok");
        user.setEmail("jiaiu@mail.com");
        user.setRole("ROLE_USER");
        user.setCity("Ddwad");
        user.setPassword("password");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        registerService.register(user);

        verify(passwordEncoder).encode("password");
        verify(userRepository).save(user);
    }
}
