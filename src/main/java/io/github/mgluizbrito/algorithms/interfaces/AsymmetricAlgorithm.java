package io.github.mgluizbrito.algorithms.interfaces;

import java.io.File;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Interface para algoritmos de criptografia assimétrica (chave pública/privada)
 */
public interface AsymmetricAlgorithm {

    /**
     * Gera um par de chaves (pública e privada) para o algoritmo assimétrico
     * @param keySize Tamanho da chave em bits
     * @return Par de chaves gerado
     * @throws GeneralSecurityException Se houver erro na geração das chaves
     */
    KeyPair generateKeyPair(int keySize) throws GeneralSecurityException;

    /**
     * Criptografa uma mensagem usando a chave pública
     * @param plainText Texto a ser criptografado
     * @param publicKey Chave pública para criptografia
     * @return Texto criptografado em Base64
     * @throws GeneralSecurityException Se houver erro na criptografia
     */
    String encryptMessage(String plainText, PublicKey publicKey) throws GeneralSecurityException;

    /**
     * Descriptografa uma mensagem usando a chave privada
     * @param cipherTextBase64 Texto criptografado em Base64
     * @param privateKeyBase64 Chave privada em Base64
     * @return Texto descriptografado
     * @throws GeneralSecurityException Se houver erro na descriptografia
     */
    String decryptMessage(String cipherTextBase64, String privateKeyBase64) throws GeneralSecurityException;

    /**
     * Criptografa um arquivo usando a chave pública
     * @param inputFile Arquivo a ser criptografado
     * @param outputFile Arquivo de saída criptografado
     * @param publicKey Chave pública para criptografia
     * @throws GeneralSecurityException Se houver erro na criptografia
     * @throws java.io.IOException Se houver erro de I/O
     */
    void encryptFile(File inputFile, File outputFile, PublicKey publicKey) throws GeneralSecurityException, java.io.IOException;

    /**
     * Descriptografa um arquivo usando a chave privada
     * @param privateKeyBase64 Chave privada em Base64
     * @param inputFile Arquivo criptografado
     * @param outputFile Arquivo de saída descriptografado
     * @throws GeneralSecurityException Se houver erro na descriptografia
     * @throws java.io.IOException Se houver erro de I/O
     */
    void decryptFile(String privateKeyBase64, File inputFile, File outputFile) throws GeneralSecurityException, java.io.IOException;
}
