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
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para SHA256Service")
class SHA256ServiceTest {

    private SHA256Service sha256Service;
    private String testMessage;

    @BeforeEach
    void setUp() {
        sha256Service = new SHA256Service();
        testMessage = "Esta é uma mensagem de teste para hash SHA256";
    }

    @Test
    @DisplayName("Deve gerar hash SHA256 para mensagem")
    void shouldGenerateSHA256HashForMessage() {
        String hash = sha256Service.msgHash(testMessage);
        
        assertNotNull(hash);
        assertEquals(64, hash.length()); // SHA256 produz hash de 64 caracteres hexadecimais
        assertTrue(hash.matches("[0-9a-f]+")); // Deve conter apenas caracteres hexadecimais
    }

    @Test
    @DisplayName("Deve gerar hash consistente para a mesma mensagem")
    void shouldGenerateConsistentHashForSameMessage() {
        String hash1 = sha256Service.msgHash(testMessage);
        String hash2 = sha256Service.msgHash(testMessage);
        
        assertEquals(hash1, hash2);
    }

    @Test
    @DisplayName("Deve gerar hashes diferentes para mensagens diferentes")
    void shouldGenerateDifferentHashesForDifferentMessages() {
        String message1 = "Mensagem 1";
        String message2 = "Mensagem 2";
        
        String hash1 = sha256Service.msgHash(message1);
        String hash2 = sha256Service.msgHash(message2);
        
        assertNotEquals(hash1, hash2);
    }

    @Test
    @DisplayName("Deve gerar hash SHA256 para arquivo")
    void shouldGenerateSHA256HashForFile(@TempDir Path tempDir) throws IOException, NoSuchAlgorithmException {
        File testFile = tempDir.resolve("test.txt").toFile();
        
        // Criar arquivo de teste
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write(testMessage);
        }
        
        String hash = sha256Service.fileHash(testFile, Algorithms.SHA256);
        
        assertNotNull(hash);
        assertEquals(64, hash.length()); // SHA256 produz hash de 64 caracteres hexadecimais
        assertTrue(hash.matches("[0-9a-f]+")); // Deve conter apenas caracteres hexadecimais
    }

    @Test
    @DisplayName("Deve gerar hash consistente para o mesmo arquivo")
    void shouldGenerateConsistentHashForSameFile(@TempDir Path tempDir) throws IOException, NoSuchAlgorithmException {
        File testFile = tempDir.resolve("test.txt").toFile();
        
        // Criar arquivo de teste
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write(testMessage);
        }
        
        String hash1 = sha256Service.fileHash(testFile, Algorithms.SHA256);
        String hash2 = sha256Service.fileHash(testFile, Algorithms.SHA256);
        
        assertEquals(hash1, hash2);
    }

    @Test
    @DisplayName("Deve gerar hashes diferentes para arquivos diferentes")
    void shouldGenerateDifferentHashesForDifferentFiles(@TempDir Path tempDir) throws IOException, NoSuchAlgorithmException {
        File file1 = tempDir.resolve("test1.txt").toFile();
        File file2 = tempDir.resolve("test2.txt").toFile();
        
        // Criar arquivos de teste com conteúdo diferente
        try (FileWriter writer1 = new FileWriter(file1)) {
            writer1.write("Conteúdo do arquivo 1");
        }
        
        try (FileWriter writer2 = new FileWriter(file2)) {
            writer2.write("Conteúdo do arquivo 2");
        }
        
        String hash1 = sha256Service.fileHash(file1, Algorithms.SHA256);
        String hash2 = sha256Service.fileHash(file2, Algorithms.SHA256);
        
        assertNotEquals(hash1, hash2);
    }

    @Test
    @DisplayName("Deve falhar ao gerar hash para arquivo inexistente")
    void shouldFailGenerateHashForNonExistentFile(@TempDir Path tempDir) {
        File nonExistentFile = tempDir.resolve("nonexistent.txt").toFile();
        
        assertThrows(IOException.class, () -> {
            sha256Service.fileHash(nonExistentFile, Algorithms.SHA256);
        });
    }

    @Test
    @DisplayName("Deve gerar hash vazio para mensagem vazia")
    void shouldGenerateHashForEmptyMessage() {
        String emptyMessage = "";
        String hash = sha256Service.msgHash(emptyMessage);
        
        assertNotNull(hash);
        assertEquals(64, hash.length());
        assertTrue(hash.matches("[0-9a-f]+"));
    }

    @Test
    @DisplayName("Deve gerar hash para mensagem com caracteres especiais")
    void shouldGenerateHashForMessageWithSpecialCharacters() {
        String specialMessage = "Mensagem com acentos: ção, ñ, ü, é, à";
        String hash = sha256Service.msgHash(specialMessage);
        
        assertNotNull(hash);
        assertEquals(64, hash.length());
        assertTrue(hash.matches("[0-9a-f]+"));
    }
}
