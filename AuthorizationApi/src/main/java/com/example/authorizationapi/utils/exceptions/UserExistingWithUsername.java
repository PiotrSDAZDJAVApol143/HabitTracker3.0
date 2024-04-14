package com.example.authorizationapi.utils.exceptions;

public class UserExistingWithUsername extends RuntimeException{
    public UserExistingWithUsername(String message) {
        super(message);
    }

    public UserExistingWithUsername(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExistingWithUsername(Throwable cause) {
        super(cause);
    }

}
