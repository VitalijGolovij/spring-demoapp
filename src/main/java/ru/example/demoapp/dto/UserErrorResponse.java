package ru.example.demoapp.dto;

import lombok.Data;

@Data
public class UserErrorResponse {
    private String message;

    public UserErrorResponse(String message){
        this.message = message;
    }
}
