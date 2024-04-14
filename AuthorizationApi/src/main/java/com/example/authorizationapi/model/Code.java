package com.example.authorizationapi.model;

public enum Code {
    SUCCESS("Operaion end success :D"),

    PERMIT("Access granted :D"),
    A1("Login failed :("),
    A2("Incorrect Data"),
    A3("Specified token is empty or invalid :/"),
    A4("User with that username already exists"),
    A5("Account with that Email already exists"),
    A6("User dont exist");
    public final String label;
    private Code(String label){
        this.label = label;
    }
}

