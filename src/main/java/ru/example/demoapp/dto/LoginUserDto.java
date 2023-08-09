package ru.example.demoapp.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginUserDto {
    @NotEmpty
    @Size(min = 1, max = 30, message = "Username size should be greater than 1 and less than 30 characters")
    private String username;

    private String password;
}
