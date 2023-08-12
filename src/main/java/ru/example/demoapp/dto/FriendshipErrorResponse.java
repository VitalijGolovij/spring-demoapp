package ru.example.demoapp.dto;

import lombok.Data;

@Data
public class FriendshipErrorResponse {
    private String message;

    public FriendshipErrorResponse(String message){
        this.message = message;
    }
}
