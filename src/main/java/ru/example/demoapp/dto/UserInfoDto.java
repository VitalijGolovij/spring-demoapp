package ru.example.demoapp.dto;

import lombok.Data;

@Data
public class UserInfoDto {
    private Long id;
    private String username;
    private String email;
}
