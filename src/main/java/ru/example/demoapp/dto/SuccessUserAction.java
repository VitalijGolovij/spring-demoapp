package ru.example.demoapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SuccessUserAction {
    private LocalDateTime timestamp = LocalDateTime.now();
    private final String message = "success";
    private UserInfoDto userInfo;
    public SuccessUserAction(UserInfoDto userInfo){
        this.userInfo = userInfo;
    }
}
