package io.github.mgluiz.algorithms.interfaces;

import io.github.mgluiz.algorithms.Algorithms;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Interface for generating cryptographic hash values (digests)
 * for both strings (messages) and files, using various algorithms.
 */
public interface HashAlgorithm {

    /**
     * Generates the cryptographic hash (digest) for a given input message string.
     * <p>
     * The specific hashing algorithm used must be determined by the
     * concrete implementation of this interface.
     *
     * @param msg The input string message to be hashed.
     * @return The hash value as a hexadecimal string.
     */
    String msgHash(String msg);


    /**
     * Generates the cryptographic hash (digest) for the contents of a file.
     * <p>
     * This default implementation reads the file in chunks (8KB buffer)
     * to efficiently handle large files without excessive memory usage.
     *
     * @param file The {@code File} object representing the file to be hashed.
     * @param algo The specific hashing algorithm to be used (e.g., MD5, SHA-256).
     * @return The file's hash value as a hexadecimal string.
     * @throws IOException If an I/O error occurs while reading the file.
     * @throws NoSuchAlgorithmException If the specified algorithm is not
     * available in the environment.
     */
    default String fileHash(File file, Algorithms algo) throws IOException, NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance(algo.NAME);

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] byteArray = new byte[8192]; // 8KB buffer
            int bytesCount;

            while ((bytesCount = fis.read(byteArray)) != -1) {
                // Atualiza o digest com cada pedaço
                digest.update(byteArray, 0, bytesCount);
            }
        }
        // Obter o hash final como um array de bytes
        byte[] bytes = digest.digest();

        // 5. Converter o array de bytes para uma string hexadecimal
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    };
}
