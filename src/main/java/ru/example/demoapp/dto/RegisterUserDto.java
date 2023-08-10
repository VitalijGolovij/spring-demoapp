package ru.example.demoapp.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RegisterUserDto {
    @NotEmpty
    @Size(min = 1, max = 30, message = "Username size should be greater than 1 and less than 30 characters")
    private String username;

    private String firstName;

    private String lastName;

    private String city;

    @Email
    private String email;

    private String password;

    private String confirmPassword;
}
