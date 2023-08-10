package ru.example.demoapp.exception;

public class ImpossibleFriendshipException extends RuntimeException{
    public ImpossibleFriendshipException(){
        super("Impossible friendship");
    }
}
