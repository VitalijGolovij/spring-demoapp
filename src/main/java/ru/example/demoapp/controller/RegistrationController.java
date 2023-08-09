package ru.example.demoapp.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.demoapp.dto.UserDTO;
import ru.example.demoapp.model.User;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private final ModelMapper modelMapper;

    @Autowired
    public RegistrationController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostMapping("")
    public Map<String, UserDTO> registerUser(@RequestBody @Valid UserDTO userDTO){
        //TODO
        System.out.println(userDTO);
        return Map.of("inputUser", userDTO);
    }

    private User convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }
}
