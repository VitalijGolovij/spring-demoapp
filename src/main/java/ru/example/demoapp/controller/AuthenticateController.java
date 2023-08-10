package ru.example.demoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import ru.example.demoapp.util.convertor.DtoConvertor;
import ru.example.demoapp.dto.LoginUserDto;
import ru.example.demoapp.dto.RegisterUserDto;
import ru.example.demoapp.model.User;
import ru.example.demoapp.sevice.RegisterServiceImpl;
import ru.example.demoapp.util.*;
import ru.example.demoapp.util.validator.RegisterUserDTOValidator;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("")
public class AuthenticateController {
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RegisterUserDTOValidator registerUserDTOValidator;
    private final RegisterServiceImpl registerService;
    private final DtoConvertor dtoConvertor;

    @Autowired
    public AuthenticateController(JWTUtil jwtUtil, AuthenticationManager authenticationManager, RegisterUserDTOValidator registerUserDTOValidator, RegisterServiceImpl registerService, DtoConvertor dtoConvertor) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.registerUserDTOValidator = registerUserDTOValidator;
        this.registerService = registerService;
        this.dtoConvertor = dtoConvertor;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto loginUserDto,
                                              BindingResult bindingResult){
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(),
                        loginUserDto.getPassword());
        try {
            authenticationManager.authenticate(token);
        } catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong login or password");
        }

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
                    .body(errors);
        }

        User user = dtoConvertor.fromRegisterUserDtoToUser(registerUserDTO);
        registerService.register(user);

        JwtResponse jwt = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok(jwt);
    }

}
