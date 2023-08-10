package ru.example.demoapp.util;

import lombok.Data;

import java.util.Date;

@Data
public class JwtResponse {
    private String jwtToken;
    private Date expirationDate;

    public JwtResponse(String jwtToken, Date expirationDate){
        this.jwtToken = jwtToken;
        this.expirationDate = expirationDate;
    }
}
