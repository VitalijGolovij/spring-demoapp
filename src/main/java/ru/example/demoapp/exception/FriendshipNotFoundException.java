package ru.example.demoapp.exception;

public class FriendshipNotFoundException extends FriendshipException{
    public FriendshipNotFoundException(){
        super("Friendship not found already");
    }
}
