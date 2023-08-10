package ru.example.demoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.demoapp.convertor.DtoConvertor;
import ru.example.demoapp.dto.LoginUserDto;
import ru.example.demoapp.dto.RegisterUserDto;
import ru.example.demoapp.model.User;
import ru.example.demoapp.sevice.RegisterServiceImpl;
import ru.example.demoapp.util.JWTUtil;
import ru.example.demoapp.validator.RegisterUserDTOValidator;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("")
public class AuthentificateController {
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RegisterUserDTOValidator registerUserDTOValidator;
    private final RegisterServiceImpl registerService;
    private final DtoConvertor dtoConvertor;

    @Autowired
    public AuthentificateController(JWTUtil jwtUtil, AuthenticationManager authenticationManager, RegisterUserDTOValidator registerUserDTOValidator, RegisterServiceImpl registerService, DtoConvertor dtoConvertor) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.registerUserDTOValidator = registerUserDTOValidator;
        this.registerService = registerService;
        this.dtoConvertor = dtoConvertor;
    }


    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginUserDto loginUserDto,
                                     BindingResult bindingResult){
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(),
                        loginUserDto.getPassword());
        try {
            authenticationManager.authenticate(token);
        } catch (BadCredentialsException e){
            return Map.of("message","wrong pass or login");
        }

        String jwtToken = jwtUtil.generateToken(loginUserDto.getUsername());
        return Map.of("jwtToken", jwtToken);
    }

    @PostMapping("/register")
    public Map<String, String> registerUser(@RequestBody @Valid RegisterUserDto registerUserDTO,
                                            BindingResult bindingResult) {
        registerUserDTOValidator.validate(registerUserDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            //TODO обработать ошибки
            return Map.of("oshibka", "hasErrors");
        }

        User user = dtoConvertor.fromRegisterUserDtoToUser(registerUserDTO);
        registerService.register(user);

        String jwtToken = jwtUtil.generateToken(user.getUsername());
        return Map.of("token", jwtToken);
    }
}
