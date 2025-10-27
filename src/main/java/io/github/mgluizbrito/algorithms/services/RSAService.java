package io.github.mgluizbrito.algorithms.services;

import io.github.mgluizbrito.algorithms.interfaces.AsymmetricAlgorithm;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAService implements AsymmetricAlgorithm {

    private static final String ALGORITHM = "RSA";
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    private static final int DEFAULT_KEY_SIZE = 2048;

    @Override
    public KeyPair generateKeyPair(int keySize) throws GeneralSecurityException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        
        System.out.printf("PAR DE CHAVES RSA GERADO COM %d BITS.%n", keySize);
        System.out.println("CHAVE PÚBLICA (para criptografia):");
        System.out.println(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        System.out.println("CHAVE PRIVADA (para descriptografia - NÃO COMPARTILHE!):");
        System.out.println(Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
        
        return keyPair;
    }

    @Override
    public String encryptMessage(String plainText, PublicKey publicKey) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        
        byte[] cipherTextBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(cipherTextBytes);
    }

    @Override
    public String decryptMessage(String cipherTextBase64, String privateKeyBase64) throws GeneralSecurityException {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        byte[] cipherTextBytes = Base64.getDecoder().decode(cipherTextBase64);
        byte[] plainTextBytes = cipher.doFinal(cipherTextBytes);
        
        return new String(plainTextBytes, StandardCharsets.UTF_8);
    }

    @Override
    public void encryptFile(File inputFile, File outputFile, PublicKey publicKey) throws GeneralSecurityException, IOException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        
        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            
            byte[] buffer = new byte[245]; // RSA com chave de 2048 bits pode criptografar até 245 bytes por vez
            int bytesRead;
            
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] encrypted = cipher.doFinal(buffer, 0, bytesRead);
                outputStream.write(encrypted);
            }
        }
    }

    @Override
    public void decryptFile(String privateKeyBase64, File inputFile, File outputFile) throws GeneralSecurityException, IOException {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            
            byte[] buffer = new byte[256]; // RSA com chave de 2048 bits produz blocos de 256 bytes
            int bytesRead;
            
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] decrypted = cipher.doFinal(buffer, 0, bytesRead);
                outputStream.write(decrypted);
            }
        }
    }
}
