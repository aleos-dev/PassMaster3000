package org.application.exception.encryption;

/**
 * Custom exception for encryption errors.
 */
public class EncryptionException extends Exception {
    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
