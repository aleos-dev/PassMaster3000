package org.application.exception.encryption;

public class InvalidEncryptionKeyException extends Exception {

    /**
     * Custom exception for invalid encryption keys.
     */
    public InvalidEncryptionKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
