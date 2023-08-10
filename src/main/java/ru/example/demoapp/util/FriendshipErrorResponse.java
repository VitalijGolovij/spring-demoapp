package ru.example.demoapp.util;

import lombok.Data;

@Data
public class FriendshipErrorResponse {
    private String message;

    public FriendshipErrorResponse(String message){
        this.message = message;
    }
}
