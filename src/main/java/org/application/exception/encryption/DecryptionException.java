package org.application.exception.encryption;

public class DecryptionException extends Exception {

    /**
     * Custom exception for decryption errors.
     */
    public DecryptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
