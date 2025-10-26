package io.github.mgluizbrito.algorithms.services;

import io.github.mgluizbrito.algorithms.Algorithms;
import io.github.mgluizbrito.algorithms.interfaces.SymmetricAlgorithm;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.File;

import static io.github.mgluizbrito.algorithms.interfaces.SymmetricAlgorithm.processFile;

public class AESService implements SymmetricAlgorithm {
    
    @Override
    public String msgEncrypt(String msg) {
        return "";
    }

    @Override
    public String msgEncrypt(String msg, int keyBites) {
        return "";
    }

    @Override
    public String msgDecrypt(String msg, SecretKey key) {
        return "";
    }

    @Override
    public void fileEncrypt(File inputFile, File outputFile) {
        try {

            SecretKey key = SymmetricAlgorithm.generateKey(Algorithms.AES);
            processFile(Cipher.ENCRYPT_MODE, inputFile, outputFile, key, "AES/CBC/PKCS5Padding");
        } catch (Exception e) {}
    }

    @Override
    public void fileEncrypt(File inputFile, File outputFile, int keyBits) {
        try {

            SecretKey key = SymmetricAlgorithm.generateKey(Algorithms.AES, keyBits);
            processFile(Cipher.ENCRYPT_MODE, inputFile, outputFile, key, "AES/CBC/PKCS5Padding");
        } catch (Exception e) {}
    }

    @Override
    public void fileDecrypt(String base64Key, File inputFile, File outputFile) {
        try {

            SecretKey key = SymmetricAlgorithm.base64KeyDecode(base64Key, Algorithms.AES);
            processFile(Cipher.DECRYPT_MODE, inputFile, outputFile, key, "AES/CBC/PKCS5Padding");
        }catch (Exception e){}
    }
}
