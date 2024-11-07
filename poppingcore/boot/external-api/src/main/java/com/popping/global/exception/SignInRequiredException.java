package com.popping.global.exception;

public class SignInRequiredException extends RuntimeException {
    public SignInRequiredException() {
        super("Please sign in again");
    }
}
