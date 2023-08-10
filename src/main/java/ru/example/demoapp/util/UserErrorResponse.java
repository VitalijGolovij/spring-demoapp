package ru.example.demoapp.util;

import lombok.Data;

@Data
public class UserErrorResponse {
    private String message;

    public UserErrorResponse(String message){
        this.message = message;
    }
}
