package ru.example.demoapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.example.demoapp.dto.LoginUserDTO;
import ru.example.demoapp.repository.UserRepository;
import ru.example.demoapp.sevice.UserDetailServiceImpl;

@Component
public class LoginUserDTOValidator implements Validator {
    private final UserDetailServiceImpl userDetailService;

    @Autowired
    public LoginUserDTOValidator(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginUserDTO userDTO = (LoginUserDTO) target;

        if (userDetailService.isUsernameAvailable(userDTO.getUsername())){
            errors.rejectValue("username","3",
                    String.format("username '%s' not found", userDTO.toString()));
        }
    }
}
