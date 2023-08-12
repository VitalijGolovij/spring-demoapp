package ru.example.demoapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.demoapp.convertor.DtoConvertor;
import ru.example.demoapp.dto.ErrorResponse;
import ru.example.demoapp.dto.JwtResponse;
import ru.example.demoapp.dto.LoginUserDto;
import ru.example.demoapp.dto.RegisterUserDto;
import ru.example.demoapp.model.User;
import ru.example.demoapp.sevice.RegisterService;
import ru.example.demoapp.util.*;
import ru.example.demoapp.validator.RegisterUserDtoValidator;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class AuthenticateController {
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RegisterUserDtoValidator registerUserDTOValidator;
    private final RegisterService registerService;
    private final DtoConvertor dtoConvertor;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto loginUserDto,
                                              BindingResult bindingResult){
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(),
                        loginUserDto.getPassword());

        authenticationManager.authenticate(token);

        JwtResponse jwt = jwtUtil.generateToken(loginUserDto.getUsername());

        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserDto registerUserDTO,
                            BindingResult bindingResult) {
        registerUserDTOValidator.validate(registerUserDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(errors.toString()));
        }

        User user = dtoConvertor.fromRegisterUserDtoToUser(registerUserDTO);
        registerService.register(user);

        JwtResponse jwt = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok(jwt);
    }

}
