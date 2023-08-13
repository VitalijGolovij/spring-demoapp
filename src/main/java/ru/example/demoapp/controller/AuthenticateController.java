package ru.example.demoapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.demoapp.convertor.DtoConvertor;
import ru.example.demoapp.dto.JwtResponse;
import ru.example.demoapp.dto.LoginUserDto;
import ru.example.demoapp.dto.RegisterUserDto;
import ru.example.demoapp.exception.InvalidDataException;
import ru.example.demoapp.model.User;
import ru.example.demoapp.sevice.RegisterService;
import ru.example.demoapp.util.*;
import ru.example.demoapp.validator.RegisterUserDtoValidator;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Tag(name = "AuthenticateController",description = "Controller for authentication")
public class AuthenticateController {
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RegisterUserDtoValidator registerUserDTOValidator;
    private final RegisterService registerService;
    private final DtoConvertor dtoConvertor;

    @Operation(summary = "Login")
    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginUserDto loginUserDto,
                                              BindingResult bindingResult){
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(),
                        loginUserDto.getPassword());

        authenticationManager.authenticate(token);

        return jwtUtil.generateToken(loginUserDto.getUsername());
    }

    @PostMapping("/register")
    @Operation(summary = "Register")
    public JwtResponse registerUser(@RequestBody @Valid RegisterUserDto registerUserDTO,
                            BindingResult bindingResult) {
        registerUserDTOValidator.validate(registerUserDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();

            throw new InvalidDataException(errors.toString());
        }

        User user = dtoConvertor.fromRegisterUserDtoToUser(registerUserDTO);
        registerService.register(user);

        return jwtUtil.generateToken(user.getUsername());
    }

}
