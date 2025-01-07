package com.brito.autentication.exceptions;

public class UsernameUniqueViolationException extends RuntimeException {
    public UsernameUniqueViolationException(String str) {
        super(str);
    }
}
