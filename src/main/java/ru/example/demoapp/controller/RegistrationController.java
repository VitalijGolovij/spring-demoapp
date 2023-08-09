package ru.example.demoapp.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.demoapp.dto.UserDTO;
import ru.example.demoapp.model.User;
import ru.example.demoapp.sevice.RegisterServiceImpl;
import ru.example.demoapp.util.JWTUtil;
import ru.example.demoapp.validator.UserDTOValidator;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private final ModelMapper modelMapper;
    private final UserDTOValidator userDTOValidator;
    private final RegisterServiceImpl registerService;
    private final JWTUtil jwtUtil;

    @Autowired
    public RegistrationController(ModelMapper modelMapper, UserDTOValidator userDTOValidator,
                                  RegisterServiceImpl registerService, JWTUtil jwtUtil) {
        this.modelMapper = modelMapper;
        this.userDTOValidator = userDTOValidator;
        this.registerService = registerService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("")
    public Map<String, String> registerUser(@RequestBody @Valid UserDTO userDTO,
                                             BindingResult bindingResult){
        userDTOValidator.validate(userDTO, bindingResult);

        if (bindingResult.hasErrors()){
            //TODO обработать ошибки
            return Map.of("oshibka", "hasErrors");
        }

        User user = convertToUser(userDTO);
        registerService.register(user);

        String jwtToken = jwtUtil.generateToken(user.getUsername());
        return Map.of("token", jwtToken);
    }

    private User convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }
}
