package io.github.mgluizbrito.algorithms.services;

import io.github.mgluizbrito.algorithms.Algorithms;
import io.github.mgluizbrito.algorithms.interfaces.SymmetricAlgorithm;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

public class AESService implements SymmetricAlgorithm {

    private static final String ALGORITHM_MODE = "AES/ECB/PKCS5Padding";
    private static final int DEFAULT_KEY_BITS = 128;

    @Override
    public String encryptMessage(String plainText) throws GeneralSecurityException {
        return this.encryptMessage(plainText, DEFAULT_KEY_BITS);
    }

    @Override
    public String encryptMessage(String plainText, Integer keyBites) throws GeneralSecurityException {
        SecretKey key = SymmetricAlgorithm.generateKey(Algorithms.AES, keyBites);

        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] cipherTextBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(cipherTextBytes);
    }

    @Override
    public String decryptMessage(String cipherTextBase64, String keyBase64) throws GeneralSecurityException{
        byte[] cipherTextBytes = Base64.getDecoder().decode(cipherTextBase64);
        SecretKey key = SymmetricAlgorithm.base64KeyDecode(keyBase64, Algorithms.AES);

        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE);
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] plainTextBytes = cipher.doFinal(cipherTextBytes);

        return new String(plainTextBytes, StandardCharsets.UTF_8);
    }

    @Override
    public void encryptFile(File inputFile, File outputFile) throws GeneralSecurityException, IOException {
        this.encryptFile(inputFile, outputFile, DEFAULT_KEY_BITS);
    }

    @Override
    public void encryptFile(File inputFile, File outputFile, int keyBits) throws GeneralSecurityException, IOException {
        SecretKey key = SymmetricAlgorithm.generateKey(Algorithms.AES, keyBits);
        processFile(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    @Override
    public void decryptFile(String base64Key, File inputFile, File outputFile) throws GeneralSecurityException, IOException {
        SecretKey key = SymmetricAlgorithm.base64KeyDecode(base64Key, Algorithms.AES);
        processFile(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    private static void processFile(int mode, SecretKey key, File inputFile, File outputFile) throws GeneralSecurityException, IOException {
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE);
        cipher.init(mode, key);

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile))
        {
            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {

                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) outputStream.write(output);
            }

            byte[] output = cipher.doFinal();
            if (output != null) outputStream.write(output);
        }
    }
}
