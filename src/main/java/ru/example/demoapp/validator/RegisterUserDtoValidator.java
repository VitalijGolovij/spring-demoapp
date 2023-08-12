package ru.example.demoapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.example.demoapp.dto.RegisterUserDto;
import ru.example.demoapp.sevice.UserDetailService;

import java.util.Objects;

@Component
public class RegisterUserDtoValidator implements Validator {
    private final UserDetailService userDetailService;

    @Autowired
    public RegisterUserDtoValidator(UserDetailService userDetailService) {
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
