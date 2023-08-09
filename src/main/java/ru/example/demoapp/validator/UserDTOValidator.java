package ru.example.demoapp.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.example.demoapp.dto.UserDTO;
import ru.example.demoapp.model.User;
import ru.example.demoapp.sevice.UserDetailServiceImpl;

import java.util.Objects;

@Component
public class UserDTOValidator implements Validator {
    private final UserDetailServiceImpl userDetailService;

    public UserDTOValidator(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;

        if (!userDetailService.isUsernameAvailable(userDTO.getUsername())){
            errors.rejectValue("username","1",
                    String.format("name '%s' is already taken", userDTO.getUsername())
            );
        }
        if (!Objects.equals(userDTO.getPassword(), userDTO.getConfirmPassword())){
            errors.rejectValue("passwordConfirm","2",
                    "incorrect password confirmation"
            );
        }
    }
}
