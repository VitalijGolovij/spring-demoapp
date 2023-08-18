package ru.example.demoapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SuccessFriendActionResponse {
    private LocalDateTime timestamp = LocalDateTime.now();
    private final String message = "success";
    private UserInfoDto userInfo;
    public SuccessFriendActionResponse(UserInfoDto userInfo){
        this.userInfo = userInfo;
    }
}
