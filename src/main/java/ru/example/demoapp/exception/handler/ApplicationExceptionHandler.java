package ru.example.demoapp.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.example.demoapp.dto.ErrorResponse;
import ru.example.demoapp.exception.FriendException;
import ru.example.demoapp.exception.InvalidDataException;
import ru.example.demoapp.exception.UserNotFoundException;

@ControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(FriendException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BadCredentialsException e){
        ErrorResponse response = new ErrorResponse("Wrong login or password");

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(InvalidDataException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
