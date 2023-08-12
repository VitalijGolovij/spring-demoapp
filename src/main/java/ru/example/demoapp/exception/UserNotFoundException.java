package ru.example.demoapp.exception;

public class UserNotFoundException extends RuntimeException{
    private static final String ERROR_MESSAGE_TEMPLATE = "User with id=%s not found";
    public UserNotFoundException(Long id){
        super(String.format(ERROR_MESSAGE_TEMPLATE, id));
    }
}
