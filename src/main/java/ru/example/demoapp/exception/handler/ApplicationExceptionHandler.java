package ru.example.demoapp.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.example.demoapp.dto.FriendshipErrorResponse;
import ru.example.demoapp.dto.UserErrorResponse;
import ru.example.demoapp.exception.FriendshipException;
import ru.example.demoapp.exception.UserNotFoundException;

@ControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<FriendshipErrorResponse> handleException(FriendshipException e){
        FriendshipErrorResponse response = new FriendshipErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(UserNotFoundException e){
        UserErrorResponse response = new UserErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
