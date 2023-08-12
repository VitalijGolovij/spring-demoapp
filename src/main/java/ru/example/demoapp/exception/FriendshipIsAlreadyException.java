package ru.example.demoapp.util.exception;

public class FriendshipIsAlreadyException extends FriendshipException{
    public FriendshipIsAlreadyException(){
        super("Friendship is already");
    }
}
