package ru.example.demoapp.exception;

public class FriendshipNotFoundException extends RuntimeException{
    public FriendshipNotFoundException(){
        super("Friendship not found already");
    }
}
