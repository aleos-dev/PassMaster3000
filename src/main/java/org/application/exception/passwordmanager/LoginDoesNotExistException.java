package org.application.exception.passwordmanager;

public class LoginDoesNotExistException extends Exception {

    public LoginDoesNotExistException(String message) {
        super(message);
    }
}
