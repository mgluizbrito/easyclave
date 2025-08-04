package io.github.mgluiz.algorithms.interfaces;

import io.github.mgluiz.algorithms.Algorithms;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

class SymmetricAlgorithmTest {

    @Test
    void generateKey() throws NoSuchAlgorithmException {
        SecretKey key = SymmetricAlgorithm.generateKey(Algorithms.AES, 128);
        Base64.getEncoder().encodeToString(key.getEncoded());
    }

    @Test
    void base64KeyDecoder() {
        String keyTest = "[-119, 64, -67, -23, 107, -100, -109, 93, -91, 43, -31, 49, 51, -77, 90, 59]";
        String base64Decoded = Arrays.toString(SymmetricAlgorithm.base64KeyDecoder("iUC96Wuck12lK+ExM7NaOw==", Algorithms.AES).getEncoded());

        if (!keyTest.equals(base64Decoded)) throw new RuntimeException("Algo deu errado");
        System.out.println(true);
    }
}