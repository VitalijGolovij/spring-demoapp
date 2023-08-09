package ru.example.demoapp.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.demoapp.dto.RegisterUserDto;
import ru.example.demoapp.model.User;
import ru.example.demoapp.sevice.RegisterServiceImpl;
import ru.example.demoapp.util.JWTUtil;
import ru.example.demoapp.validator.RegisterUserDTOValidator;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private final ModelMapper modelMapper;
    private final RegisterUserDTOValidator registerUserDTOValidator;
    private final RegisterServiceImpl registerService;
    private final JWTUtil jwtUtil;

    @Autowired
    public RegistrationController(ModelMapper modelMapper, RegisterUserDTOValidator registerUserDTOValidator,
                                  RegisterServiceImpl registerService, JWTUtil jwtUtil) {
        this.modelMapper = modelMapper;
        this.registerUserDTOValidator = registerUserDTOValidator;
        this.registerService = registerService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("")
    public Map<String, String> registerUser(@RequestBody @Valid RegisterUserDto registerUserDTO,
                                             BindingResult bindingResult){
        registerUserDTOValidator.validate(registerUserDTO, bindingResult);

        if (bindingResult.hasErrors()){
            //TODO обработать ошибки
            return Map.of("oshibka", "hasErrors");
        }

        User user = convertToUser(registerUserDTO);
        registerService.register(user);

        String jwtToken = jwtUtil.generateToken(user.getUsername());
        return Map.of("token", jwtToken);
    }

    private User convertToUser(RegisterUserDto registerUserDTO){
        return modelMapper.map(registerUserDTO, User.class);
    }
}
