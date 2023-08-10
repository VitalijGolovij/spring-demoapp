package ru.example.demoapp.exception;

public class FriendshipIsAlreadyException extends FriendshipException{
    public FriendshipIsAlreadyException(){
        super("Friendship is already");
    }
}
