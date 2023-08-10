package ru.example.demoapp.exception;

public class ImpossibleFriendshipException extends FriendshipException{
    public ImpossibleFriendshipException(){
        super("Impossible friendship");
    }
}
