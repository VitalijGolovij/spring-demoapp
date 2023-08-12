package ru.example.demoapp.dto;

import lombok.Data;
import ru.example.demoapp.dto.UserInfoDto;

@Data
public class FriendActionResponse {
    private String message;
    private UserInfoDto userInfoDto;
    public FriendActionResponse(String message, UserInfoDto userInfoDto){
        this.message = message;
        this.userInfoDto = userInfoDto;
    }
}
