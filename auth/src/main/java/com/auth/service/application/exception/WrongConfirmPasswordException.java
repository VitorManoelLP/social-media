package com.auth.service.application.exception;

public class WrongConfirmPasswordException extends IllegalArgumentException {

    public WrongConfirmPasswordException(String message) {
        super(message);
    }

}
