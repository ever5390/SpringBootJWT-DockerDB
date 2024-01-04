package com.rpej.security.Exceptions.domain;

public class UserNameExistsException extends  Exception {
    public UserNameExistsException(String message) {
        super(message);
    }
}
