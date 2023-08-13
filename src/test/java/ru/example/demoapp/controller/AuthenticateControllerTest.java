package ru.example.demoapp.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.example.demoapp.convertor.DtoConvertor;
import ru.example.demoapp.dto.ErrorResponse;
import ru.example.demoapp.dto.JwtResponse;
import ru.example.demoapp.dto.LoginUserDto;
import ru.example.demoapp.dto.RegisterUserDto;
import ru.example.demoapp.model.User;
import ru.example.demoapp.sevice.RegisterService;
import ru.example.demoapp.util.JWTUtil;
import ru.example.demoapp.validator.RegisterUserDtoValidator;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AuthenticateControllerTest {
    @Mock
    private JWTUtil jwtUtil;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private RegisterUserDtoValidator registerUserDTOValidator;
    @Mock
    private RegisterService registerService;
    @Mock
    private DtoConvertor dtoConvertor;
    @Mock
    private BindingResult bindingResult;
    @InjectMocks
    private AuthenticateController authenticateController;

    @AfterEach
    public void verifyMocks() {
        verifyNoMoreInteractions(
                authenticationManager,
                jwtUtil,
                registerUserDTOValidator,
                registerService,
                dtoConvertor,
                bindingResult
        );
    }

    @Test
    public void testLoginFailure() {
        String username = "testUser";
        String password = "incorrectPassword";
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setUsername(username);
        loginUserDto.setPassword(password);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username, password);

        when(authenticationManager.authenticate(token))
                .thenThrow(new BadCredentialsException("Wrong login or password"));

        assertThrows(BadCredentialsException.class, () -> authenticateController.login(loginUserDto, null));
        verify(authenticationManager).authenticate(token);
    }

    @Test
    public void testLoginSuccess(){
        String username = "testUser";
        String password = "testPassword";
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setUsername(username);
        loginUserDto.setPassword(password);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username, password);

        when(authenticationManager.authenticate(token)).thenReturn(null);

        JwtResponse jwt = new JwtResponse("jwtToken", null);
        when(jwtUtil.generateToken(loginUserDto.getUsername())).thenReturn(jwt);

        ResponseEntity<?> response = authenticateController.login(loginUserDto, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jwt, response.getBody());
        verify(authenticationManager).authenticate(token);
        verify(jwtUtil).generateToken(username);
    }

    @Test
    public void testRegisterSuccess(){
        RegisterUserDto registerUserDto = new RegisterUserDto();
        User user = new User();
        JwtResponse jwt = new JwtResponse("jwtToken", null);

        doNothing().when(registerUserDTOValidator).validate(registerUserDto, bindingResult);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(dtoConvertor.fromRegisterUserDtoToUser(registerUserDto)).thenReturn(user);
        doNothing().when(registerService).register(user);
        when(jwtUtil.generateToken(user.getUsername())).thenReturn(jwt);

        ResponseEntity<?> response = authenticateController.registerUser(registerUserDto, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jwt, response.getBody());
        verify(dtoConvertor).fromRegisterUserDtoToUser(registerUserDto);
        verify(registerService).register(user);
        verify(jwtUtil).generateToken(user.getUsername());
    }

    @Test
    public void testRegisterFailure(){
        RegisterUserDto registerUserDto = new RegisterUserDto();
        List<FieldError> fieldErrors = Collections.emptyList();

        doNothing().when(registerUserDTOValidator).validate(registerUserDto, bindingResult);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        ResponseEntity<?> response = authenticateController.registerUser(registerUserDto, bindingResult);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(registerUserDTOValidator).validate(registerUserDto, bindingResult);

    }
}
