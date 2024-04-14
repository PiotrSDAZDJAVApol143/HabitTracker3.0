package com.example.authorizationapi.utils.exceptions;

public class UserExistingWithEmail extends RuntimeException{
    public UserExistingWithEmail(String message) {
        super(message);
    }
    public UserExistingWithEmail(String message,Throwable cause) {
        super(message, cause);

    }
    public UserExistingWithEmail(Throwable cause) {
        super( cause);

    }


}
