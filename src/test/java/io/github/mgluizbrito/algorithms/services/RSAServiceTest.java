package io.github.mgluizbrito.algorithms.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para RSAService")
class RSAServiceTest {

    private RSAService rsaService;
    private String testMessage;
    private KeyPair keyPair;
    private String privateKeyBase64;
    private String publicKeyBase64;

    @BeforeEach
    void setUp() throws GeneralSecurityException {
        rsaService = new RSAService();
        testMessage = "Esta é uma mensagem de teste para criptografia RSA";
        
        // Gerar par de chaves para os testes
        keyPair = rsaService.generateKeyPair(2048);
        privateKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        publicKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
    }

    @Test
    @DisplayName("Deve gerar par de chaves com sucesso")
    void shouldGenerateKeyPairSuccessfully() throws GeneralSecurityException {
        KeyPair newKeyPair = rsaService.generateKeyPair(2048);
        
        assertNotNull(newKeyPair);
        assertNotNull(newKeyPair.getPublic());
        assertNotNull(newKeyPair.getPrivate());
    }

    @Test
    @DisplayName("Deve criptografar uma mensagem com chave pública")
    void shouldEncryptMessageWithPublicKey() throws GeneralSecurityException {
        String encryptedMessage = rsaService.encryptMessage(testMessage, keyPair.getPublic());
        
        assertNotNull(encryptedMessage);
        assertFalse(encryptedMessage.isEmpty());
        assertNotEquals(testMessage, encryptedMessage);
    }

    @Test
    @DisplayName("Deve descriptografar uma mensagem com chave privada")
    void shouldDecryptMessageWithPrivateKey() throws GeneralSecurityException {
        String encryptedMessage = rsaService.encryptMessage(testMessage, keyPair.getPublic());
        String decryptedMessage = rsaService.decryptMessage(encryptedMessage, privateKeyBase64);
        
        assertEquals(testMessage, decryptedMessage);
    }

    @Test
    @DisplayName("Deve falhar ao descriptografar com chave privada inválida")
    void shouldFailDecryptWithInvalidPrivateKey() throws GeneralSecurityException {
        String encryptedMessage = rsaService.encryptMessage(testMessage, keyPair.getPublic());
        String invalidPrivateKey = "invalidPrivateKeyBase64";
        
        assertThrows(GeneralSecurityException.class, () -> {
            rsaService.decryptMessage(encryptedMessage, invalidPrivateKey);
        });
    }

    @Test
    @DisplayName("Deve criptografar um arquivo com chave pública")
    void shouldEncryptFileWithPublicKey(@TempDir Path tempDir) throws GeneralSecurityException, IOException {
        File inputFile = tempDir.resolve("test.txt").toFile();
        File outputFile = tempDir.resolve("test.rsa").toFile();
        
        // Criar arquivo de teste
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write(testMessage);
        }
        
        rsaService.encryptFile(inputFile, outputFile, keyPair.getPublic());
        
        assertTrue(outputFile.exists());
        assertTrue(outputFile.length() > 0);
    }

    @Test
    @DisplayName("Deve descriptografar um arquivo com chave privada")
    void shouldDecryptFileWithPrivateKey(@TempDir Path tempDir) throws GeneralSecurityException, IOException {
        File inputFile = tempDir.resolve("test.txt").toFile();
        File encryptedFile = tempDir.resolve("test.rsa").toFile();
        File decryptedFile = tempDir.resolve("test.decrypted").toFile();
        
        // Criar arquivo de teste
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write(testMessage);
        }
        
        // Criptografar arquivo
        rsaService.encryptFile(inputFile, encryptedFile, keyPair.getPublic());
        
        // Descriptografar arquivo
        rsaService.decryptFile(privateKeyBase64, encryptedFile, decryptedFile);
        
        assertTrue(decryptedFile.exists());
        assertTrue(decryptedFile.length() > 0);
    }

    @Test
    @DisplayName("Deve falhar ao criptografar arquivo inexistente")
    void shouldFailEncryptNonExistentFile(@TempDir Path tempDir) {
        File nonExistentFile = tempDir.resolve("nonexistent.txt").toFile();
        File outputFile = tempDir.resolve("output.rsa").toFile();
        
        assertThrows(IOException.class, () -> {
            rsaService.encryptFile(nonExistentFile, outputFile, keyPair.getPublic());
        });
    }

    @Test
    @DisplayName("Deve falhar ao descriptografar com chave privada inválida")
    void shouldFailDecryptFileWithInvalidPrivateKey(@TempDir Path tempDir) throws GeneralSecurityException, IOException {
        File inputFile = tempDir.resolve("test.txt").toFile();
        File encryptedFile = tempDir.resolve("test.rsa").toFile();
        File decryptedFile = tempDir.resolve("test.decrypted").toFile();
        
        // Criar arquivo de teste
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write(testMessage);
        }
        
        // Criptografar arquivo
        rsaService.encryptFile(inputFile, encryptedFile, keyPair.getPublic());
        
        // Tentar descriptografar com chave privada inválida
        String invalidPrivateKey = "invalidPrivateKeyBase64";
        
        assertThrows(GeneralSecurityException.class, () -> {
            rsaService.decryptFile(invalidPrivateKey, encryptedFile, decryptedFile);
        });
    }

    @Test
    @DisplayName("Deve falhar com mensagem muito longa para RSA")
    void shouldFailWithMessageTooLongForRSA() throws GeneralSecurityException {
        // RSA com chave de 2048 bits pode criptografar apenas até 245 bytes
        String longMessage = "A".repeat(300); // Mensagem muito longa
        
        assertThrows(GeneralSecurityException.class, () -> {
            rsaService.encryptMessage(longMessage, keyPair.getPublic());
        });
    }
}
