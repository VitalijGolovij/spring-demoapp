package ru.example.demoapp.exception;

public class ImpossibleFriendException extends FriendException {
    public ImpossibleFriendException(){
        super("Impossible friendship");
    }
}
