package ru.example.demoapp.util.exception;

public class ImpossibleFriendshipException extends FriendshipException{
    public ImpossibleFriendshipException(){
        super("Impossible friendship");
    }
}
