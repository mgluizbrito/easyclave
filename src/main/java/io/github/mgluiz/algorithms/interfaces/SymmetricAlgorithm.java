package io.github.mgluiz.algorithms.interfaces;

import io.github.mgluiz.algorithms.Algorithms;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public interface SymmetricAlgorithm {

    String msgEncrypt(String msg);
    String msgEncrypt(String msg, int keyBites);

    String msgDecrypt(String msg, SecretKey key);

    void fileEncrypt(File inputFile, File outputFile);
    void fileEncrypt(File inputFile, File outputFile, int keyBits);

    void fileDecrypt(String base64Key, File inputFile, File outputFile);

    static void processFile(int cipherMode, File inputFile, File outputFile, SecretKey key, String tranformation) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(tranformation);
        cipher.init(cipherMode, key);

        try (InputStream inputStream = new FileInputStream(inputFile);
             OutputStream outputStream = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {

                byte[] outputBytes = cipher.update(buffer, 0, bytesRead);
                if (outputBytes != null) outputStream.write(outputBytes);
            }

            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) outputStream.write(outputBytes);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param algo Symmetric Algorithm to be used (AES, DES...)
     * @param bits The number of bits in the key (128, 192 ou 256 bits)
     * @return Key generated
     */
    static SecretKey generateKey(Algorithms algo, int bits) throws NoSuchAlgorithmException{
        KeyGenerator keyGen = KeyGenerator.getInstance(algo.toString());
        keyGen.init(bits); // 128, 192 ou 256 bits
        SecretKey key = keyGen.generateKey();

        System.out.println(Arrays.toString(key.getEncoded()));
        System.out.printf("CHAVE GERADA COM "+bits+" BITS, NÃO COMPARTILHE, NEM PERCA! Ou não será possível descriptografar\n" +
                "KEY: "+ base64KeyEncode(key));
        return key;
    }
    static SecretKey generateKey(Algorithms algo) throws NoSuchAlgorithmException{
        KeyGenerator keyGen = KeyGenerator.getInstance(algo.toString());
        keyGen.init(128); // 128, 192 ou 256 bits
        SecretKey key = keyGen.generateKey();

        System.out.println(Arrays.toString(key.getEncoded()));
        System.out.printf("CHAVE GERADA COM "+128+" BITS, NÃO COMPARTILHE, NEM PERCA! Ou não será possível descriptografar\n" +
                "KEY: "+ base64KeyEncode(key));
        return key;
    }

    static String base64KeyEncode(SecretKey key){
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    static SecretKey base64KeyDecode(String keyBase64, Algorithms algo){
        byte[] key = Base64.getDecoder().decode(keyBase64);
        return new SecretKeySpec(key, algo.toString());
    }
}
