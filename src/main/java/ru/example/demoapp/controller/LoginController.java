package ru.example.demoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.demoapp.dto.LoginUserDto;
import ru.example.demoapp.util.JWTUtil;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("")
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
}