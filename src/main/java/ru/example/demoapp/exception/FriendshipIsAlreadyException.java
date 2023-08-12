package ru.example.demoapp.exception;

public class FriendshipIsAlreadyException extends FriendshipException{
    private static final String ERROR_MESSAGE_TEMPLATE = "Friendship between user_id=%s and user_id=%s is already";
    public FriendshipIsAlreadyException(Long userId1, Long userId2){
        super(String.format(ERROR_MESSAGE_TEMPLATE, userId1, userId2));
    }
}
