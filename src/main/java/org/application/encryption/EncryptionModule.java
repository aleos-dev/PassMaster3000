package org.application.encryption;

import org.application.exception.encryption.DecryptionException;
import org.application.exception.encryption.EncryptionException;
import org.application.exception.encryption.InvalidEncryptionKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;


/**
 * A module for encrypting and decrypting data using the AES algorithm.
 */
public class EncryptionModule {

    private static final String ALGORITHM = "AES";
    private final Key key;

    /**
     * Constructs an EncryptionModule with the provided secret key.
     *
     * @param secret the secret key used for encryption and decryption
     * @throws InvalidEncryptionKeyException if the secret key is invalid
     */
    public EncryptionModule(String secret) throws InvalidEncryptionKeyException {

        try {
            byte[] key = secret.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // use only the first 128 bits
            this.key = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new InvalidEncryptionKeyException("Error initializing EncryptionModule", e);
        }
    }

    /**
     * Encrypts the provided data using the AES algorithm and the secret key.
     *
     * @param data the data to be encrypted
     * @return the Base64-encoded encrypted data
     * @throws EncryptionException if an error occurs during encryption
     */
    public String encrypt(String data) throws EncryptionException {

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {

            throw new EncryptionException("Error encrypting data", e);
        }
    }

    /**
     * Decrypts the provided encrypted data using the AES algorithm and the secret key.
     *
     * @param encryptedData the Base64-encoded encrypted data
     * @return the decrypted data
     * @throws DecryptionException if an error occurs during decryption
     */
    public String decrypt(String encryptedData) throws DecryptionException {

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] originalBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));

            return new String(originalBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {

            throw new DecryptionException("Error decrypting data", e);
        }
    }


}
