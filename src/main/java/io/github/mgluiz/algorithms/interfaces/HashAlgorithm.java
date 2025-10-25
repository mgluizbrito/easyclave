package io.github.mgluiz.algorithms.interfaces;

import io.github.mgluiz.algorithms.Algorithms;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface HashAlgorithm {

    String msgHash(String msg);
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

    File fileEncrypt(File fileInput, File fileOutput);

}
