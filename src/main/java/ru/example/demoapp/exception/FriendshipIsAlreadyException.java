package ru.example.demoapp.exception;

public class FriendshipIsAlreadyException extends RuntimeException{
    public FriendshipIsAlreadyException(){
        super("Friendship is already");
    }
}
