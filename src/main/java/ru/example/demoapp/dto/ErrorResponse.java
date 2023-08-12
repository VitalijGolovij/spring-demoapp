package ru.example.demoapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;

    public ErrorResponse(String message){
        this.message = message;
    }
}
