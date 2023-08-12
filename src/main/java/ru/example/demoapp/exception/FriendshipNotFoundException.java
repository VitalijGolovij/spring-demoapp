package ru.example.demoapp.util.exception;

public class FriendshipNotFoundException extends FriendshipException{
    public FriendshipNotFoundException(){
        super("Friendship not found already");
    }
}
