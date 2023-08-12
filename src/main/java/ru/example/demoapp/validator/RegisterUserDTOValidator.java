package ru.example.demoapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.example.demoapp.dto.RegisterUserDto;
import ru.example.demoapp.sevice.UserDetailServiceImpl;

import java.util.Objects;

@Component
public class RegisterUserDTOValidator implements Validator {
    private final UserDetailServiceImpl userDetailService;

    @Autowired
    public RegisterUserDTOValidator(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterUserDto registerUserDTO = (RegisterUserDto) target;

        if (!userDetailService.isUsernameAvailable(registerUserDTO.getUsername())){
            errors.rejectValue("username","1",
                    String.format("name '%s' is already taken", registerUserDTO.getUsername())
            );
        }
        if (!Objects.equals(registerUserDTO.getPassword(), registerUserDTO.getConfirmPassword())){
            errors.rejectValue("passwordConfirm","2",
                    "incorrect password confirmation"
            );
        }
    }
}
