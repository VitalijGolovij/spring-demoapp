package ru.example.demoapp.dto;

import lombok.Data;

@Data
public class UserInfoDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String city;
    private String email;
}
