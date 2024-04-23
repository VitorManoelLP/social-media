package com.socialmedia.main.application.exception;

public class AlreadyExistsFriendRequestException extends IllegalStateException {

    public AlreadyExistsFriendRequestException(String message) {
        super(message);
    }

}
