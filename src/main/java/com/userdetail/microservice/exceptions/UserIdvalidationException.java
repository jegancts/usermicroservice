package com.userdetail.microservice.exceptions;

public class UserIdvalidationException extends RuntimeException {
    private String msg;

    public UserIdvalidationException (String msg) {
        super(msg);
        this.msg = msg;
    }
}
