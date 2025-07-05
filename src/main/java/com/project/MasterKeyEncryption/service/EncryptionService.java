package com.project.MasterKeyEncryption.service;

// src/main/java/com/yourapp/service/EncryptionService.java

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionService {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private final SecretKey secretKey;
    private final IvParameterSpec iv;

    public EncryptionService(String masterKey) {
        // Convert the master key string to a SecretKey
        this.secretKey = new SecretKeySpec(masterKey.getBytes(StandardCharsets.UTF_8), "AES");
        // Generate a fixed IV for simplicity (in production, you'd want to generate a new IV for each encryption)
        byte[] ivBytes = new byte[16];
        new SecureRandom().nextBytes(ivBytes);
        this.iv = new IvParameterSpec(ivBytes);
    }

    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }
}
