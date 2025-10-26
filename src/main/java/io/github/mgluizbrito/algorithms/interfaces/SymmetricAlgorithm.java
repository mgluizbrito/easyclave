package io.github.mgluizbrito.algorithms.interfaces;

import io.github.mgluizbrito.algorithms.Algorithms;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public interface SymmetricAlgorithm {

    /**
     * Generates a secret key for the specified algorithm and key size.
     * * @param algo The symmetric algorithm.
     * @param bits The key size in bits (e.g., 128, 192, 256 for AES).
     * @return The generated SecretKey.
     * @throws NoSuchAlgorithmException If the algorithm is not supported.
     */
    static SecretKey generateKey(Algorithms algo, int bits) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(algo.toString());
        keyGen.init(bits);
        SecretKey key = keyGen.generateKey();

        System.out.printf("CHAVE GERADA COM %d BITS. NÃO COMPARTILHE, NEM PERCA! Ou não será possível descriptografar.\n" +
                "KEY: %s\n", bits, base64KeyEncode(key));
        return key;
    }

    /**
     * Generates a secret key with a default size (128 bits for AES).
     *
     * @param algo The symmetric algorithm.
     * @return The generated SecretKey.
     * @throws NoSuchAlgorithmException If the algorithm is not supported.
     */
    static SecretKey generateKey(Algorithms algo) throws NoSuchAlgorithmException {
        return generateKey(algo, 128);
    }

    /**
     * Encodes a SecretKey to a Base64 String for storage or transmission.
     *
     * @param key The SecretKey to encode.
     * @return The Base64 encoded key string.
     */
    static String base64KeyEncode(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * Decodes a Base64 String back into a SecretKey.
     *
     * @param keyBase64 The Base64 key string.
     * @param algo The algorithm associated with the key.
     * @return The decoded SecretKey.
     */
    static SecretKey base64KeyDecode(String keyBase64, Algorithms algo) {
        byte[] key = Base64.getDecoder().decode(keyBase64);
        return new SecretKeySpec(key, algo.toString());
    }

    /**
     * Encrypts a plain text string using a default 128 bits key.
     * @param plainText The text to be encrypted.
     * @return The encrypted text as a Base64 encoded String.
     * @throws GeneralSecurityException - If an exception occurs during the decryption process
     *      * (e.g., invalid key, bad padding, or I/O error).
     */
    String encryptMessage(String plainText) throws GeneralSecurityException;

    /**
     * Encrypts a plain text string using a custom key bites.
     * @param plainText The text to be encrypted.
     * @param keyBites The number of bites to generate a key
     * @return The encrypted text as a Base64 encoded String.
     * @throws GeneralSecurityException - If an exception occurs during the decryption process
     *      * (e.g., invalid key, bad padding, or I/O error).
     */
    String encryptMessage(String plainText, Integer keyBites) throws GeneralSecurityException;

    /**
     * Decrypts a Base64 encoded cipher text string using the provided SecretKey.
     *
     * @param cipherTextBase64 The Base64 encoded cipher text to be decrypted.
     * @param keyBase64 The Base64 key used to encrypt
     * @return The decrypted plain text String.
     * @throws GeneralSecurityException - If an exception occurs during the decryption process
     *      * (e.g., invalid key, bad padding, or I/O error).
     */
    String decryptMessage(String cipherTextBase64, String keyBase64) throws GeneralSecurityException;

    /**
     * Encrypts the content of an input file and writes the encrypted result to an output file,
     * using a default key size of 128 bits.
     * The generated key is printed to the console and must be saved for future decryption.
     *
     * @param inputFile The source file to be encrypted.
     * @param outputFile The destination file where the encrypted content will be written.
     * @throws GeneralSecurityException If an exception occurs during the encryption process
     * (e.g., unsupported algorithm, invalid key).
     * @throws IOException - If an exception occurs during the files manipulation
     */
    void encryptFile(File inputFile, File outputFile) throws GeneralSecurityException, IOException;

    /**
     * Encrypts the content of an input file and writes the encrypted result to an output file,
     * using a key size specified by the user.
     * The generated key is printed to the console and must be saved for future decryption.
     *
     * @param inputFile The source file to be encrypted.
     * @param outputFile The destination file where the encrypted content will be written.
     * @param keyBits The number of bits for the key generation (e.g., 128, 192, or 256 for AES).
     * @throws GeneralSecurityException If an exception occurs during the decryption process
     *      * (e.g., invalid key, bad padding, or I/O error).
     * @throws IOException - If an exception occurs during the files manipulation
     */
    void encryptFile(File inputFile, File outputFile, int keyBits) throws GeneralSecurityException, IOException;

    /**
     * Decrypts the content of an input file and writes the decrypted result
     * to an output file, using a key provided in Base64 format.
     *
     * @param base64Key The Base64 encoded key used to perform the original encryption.
     * @param inputFile The source file (cipher text) to be decrypted.
     * @param outputFile The destination file where the decrypted content (plain text) will be written.
     * @throws GeneralSecurityException If an exception occurs during the decryption process
     * (e.g., invalid key, bad padding, or I/O error).
     * @throws IOException - If an exception occurs during the files manipulation
     */
    void decryptFile(String base64Key, File inputFile, File outputFile) throws GeneralSecurityException, IOException;
}