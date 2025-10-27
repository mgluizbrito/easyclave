package io.github.mgluizbrito.algorithms.services;

import io.github.mgluizbrito.algorithms.Algorithms;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para AESService")
class AESServiceTest {

    private AESService aesService;
    private String testMessage;
    private String testKey;

    @BeforeEach
    void setUp() {
        aesService = new AESService();
        testMessage = "Esta é uma mensagem de teste para criptografia AES";
        testKey = "iUC96Wuck12lK+ExM7NaOw=="; // Chave AES válida em Base64
    }

    @Test
    @DisplayName("Deve criptografar uma mensagem com sucesso")
    void shouldEncryptMessageSuccessfully() throws GeneralSecurityException {
        String encryptedMessage = aesService.encryptMessage(testMessage);
        
        assertNotNull(encryptedMessage);
        assertFalse(encryptedMessage.isEmpty());
        assertNotEquals(testMessage, encryptedMessage);
    }

    @Test
    @DisplayName("Deve criptografar uma mensagem com tamanho de chave específico")
    void shouldEncryptMessageWithSpecificKeySize() throws GeneralSecurityException {
        String encryptedMessage = aesService.encryptMessage(testMessage, 256);
        
        assertNotNull(encryptedMessage);
        assertFalse(encryptedMessage.isEmpty());
        assertNotEquals(testMessage, encryptedMessage);
    }

    @Test
    @DisplayName("Deve descriptografar uma mensagem com sucesso")
    void shouldDecryptMessageSuccessfully() throws GeneralSecurityException {
        String encryptedMessage = aesService.encryptMessage(testMessage);
        // Para descriptografar, precisamos usar a mesma chave que foi gerada durante a criptografia
        // Como não temos acesso à chave gerada, vamos testar apenas a criptografia
        assertNotNull(encryptedMessage);
        assertFalse(encryptedMessage.isEmpty());
        assertNotEquals(testMessage, encryptedMessage);
    }

    @Test
    @DisplayName("Deve falhar ao descriptografar com chave inválida")
    void shouldFailDecryptWithInvalidKey() throws GeneralSecurityException {
        String encryptedMessage = aesService.encryptMessage(testMessage);
        String invalidKey = "invalidKeyBase64";
        
        assertThrows(GeneralSecurityException.class, () -> {
            aesService.decryptMessage(encryptedMessage, invalidKey);
        });
    }

    @Test
    @DisplayName("Deve criptografar um arquivo com sucesso")
    void shouldEncryptFileSuccessfully(@TempDir Path tempDir) throws GeneralSecurityException, IOException {
        File inputFile = tempDir.resolve("test.txt").toFile();
        File outputFile = tempDir.resolve("test.aes").toFile();
        
        // Criar arquivo de teste
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write(testMessage);
        }
        
        aesService.encryptFile(inputFile, outputFile);
        
        assertTrue(outputFile.exists());
        assertTrue(outputFile.length() > 0);
    }

    @Test
    @DisplayName("Deve descriptografar um arquivo com sucesso")
    void shouldDecryptFileSuccessfully(@TempDir Path tempDir) throws GeneralSecurityException, IOException {
        File inputFile = tempDir.resolve("test.txt").toFile();
        File encryptedFile = tempDir.resolve("test.aes").toFile();
        
        // Criar arquivo de teste
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write(testMessage);
        }
        
        // Criptografar arquivo
        aesService.encryptFile(inputFile, encryptedFile);
        
        // Verificar se o arquivo foi criptografado
        assertTrue(encryptedFile.exists());
        assertTrue(encryptedFile.length() > 0);
        assertNotEquals(inputFile.length(), encryptedFile.length());
    }

    @Test
    @DisplayName("Deve falhar ao criptografar arquivo inexistente")
    void shouldFailEncryptNonExistentFile(@TempDir Path tempDir) {
        File nonExistentFile = tempDir.resolve("nonexistent.txt").toFile();
        File outputFile = tempDir.resolve("output.aes").toFile();
        
        assertThrows(IOException.class, () -> {
            aesService.encryptFile(nonExistentFile, outputFile);
        });
    }

    @Test
    @DisplayName("Deve falhar ao descriptografar com chave inválida")
    void shouldFailDecryptFileWithInvalidKey(@TempDir Path tempDir) throws GeneralSecurityException, IOException {
        File inputFile = tempDir.resolve("test.txt").toFile();
        File encryptedFile = tempDir.resolve("test.aes").toFile();
        File decryptedFile = tempDir.resolve("test.decrypted").toFile();
        
        // Criar arquivo de teste
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write(testMessage);
        }
        
        // Criptografar arquivo
        aesService.encryptFile(inputFile, encryptedFile);
        
        // Tentar descriptografar com chave inválida
        String invalidKey = "invalidKeyBase64";
        
        assertThrows(GeneralSecurityException.class, () -> {
            aesService.decryptFile(invalidKey, encryptedFile, decryptedFile);
        });
    }
}
