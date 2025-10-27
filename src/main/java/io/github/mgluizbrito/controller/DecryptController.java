package io.github.mgluizbrito.controller;

import io.github.mgluizbrito.EasyClave;
import io.github.mgluizbrito.algorithms.Algorithms;
import io.github.mgluizbrito.algorithms.services.AESService;
import io.github.mgluizbrito.algorithms.services.DESService;
import io.github.mgluizbrito.algorithms.services.RSAService;
import io.github.mgluizbrito.utils.LoggerUtil;
import org.slf4j.Logger;
import picocli.CommandLine;

import java.io.File;
import java.security.GeneralSecurityException;

public class DecryptController {

    private static final Logger logger = LoggerUtil.getLogger(DecryptController.class);

    public static void fileDecrypt(java.util.List<File> files, Algorithms algorithm, String key) {
        LoggerUtil.logCryptoOperationStart(algorithm.NAME, "decrypt", "files");
        System.out.println("SELECTED ALGORITHM: " + algorithm.NAME);
        
        for (File file : files) {
            logger.debug("Processando arquivo: {}", file.getAbsolutePath());
            try {
                if (algorithm.equals(Algorithms.AES)) {
                    System.out.println("ORIGINAL FILE: " + file.getAbsolutePath());
                    File outputFile = new File(file.getName().replace(".aes", ".decrypted"));
                    new AESService().decryptFile(key, file, outputFile);
                    System.out.println("DECRYPTED FILE: " + outputFile.getName());
                } else if (algorithm.equals(Algorithms.DES)) {
                    System.out.println("ORIGINAL FILE: " + file.getAbsolutePath());
                    File outputFile = new File(file.getName().replace(".des", ".decrypted"));
                    new DESService().decryptFile(key, file, outputFile);
                    System.out.println("DECRYPTED FILE: " + outputFile.getName());
                } else if (algorithm.equals(Algorithms.RSA)) {
                    System.out.println("ORIGINAL FILE: " + file.getAbsolutePath());
                    File outputFile = new File(file.getName().replace(".rsa", ".decrypted"));
                    new RSAService().decryptFile(key, file, outputFile);
                    System.out.println("DECRYPTED FILE: " + outputFile.getName());
                } else {
                    System.err.println("Algoritmo " + algorithm.NAME + " não suporta descriptografia de arquivos.");
                }
            } catch (GeneralSecurityException | java.io.IOException e) {
                System.err.println("Erro ao descriptografar arquivo " + file.getName() + ": " + e.getMessage());
                throw new CommandLine.ExecutionException(new CommandLine(new EasyClave()), "Decrypt ERROR", e);
            }
            System.out.println();
        }
        
        LoggerUtil.logCryptoOperationSuccess(algorithm.NAME, "decrypt", "files", files.size());
        System.out.printf("""
                %d arquivos descriptografados com sucesso.
                ----------------------------------*----------------------------------
                %n""", files.size());
    }

    public static void msgDecrypt(String msg, Algorithms algorithm, String key) {
        LoggerUtil.logCryptoOperationStart(algorithm.NAME, "decrypt", "message");
        System.out.println("SELECTED ALGORITHM: " + algorithm.NAME);
        logger.debug("Processando mensagem com algoritmo: {}", algorithm.NAME);
        
        try {
            if (algorithm.equals(Algorithms.AES)) {
                System.out.println("ENCRYPTED MESSAGE: " + msg);
                String decryptedMsg = new AESService().decryptMessage(msg, key);
                System.out.println("DECRYPTED MESSAGE: " + decryptedMsg);
            } else if (algorithm.equals(Algorithms.DES)) {
                System.out.println("ENCRYPTED MESSAGE: " + msg);
                String decryptedMsg = new DESService().decryptMessage(msg, key);
                System.out.println("DECRYPTED MESSAGE: " + decryptedMsg);
            } else if (algorithm.equals(Algorithms.RSA)) {
                System.out.println("ENCRYPTED MESSAGE: " + msg);
                String decryptedMsg = new RSAService().decryptMessage(msg, key);
                System.out.println("DECRYPTED MESSAGE: " + decryptedMsg);
            } else {
                System.err.println("Algoritmo " + algorithm.NAME + " não suporta descriptografia de mensagens.");
                return;
            }
        } catch (GeneralSecurityException e) {
            System.err.println("Erro ao descriptografar mensagem: " + e.getMessage());
            throw new CommandLine.ExecutionException(new CommandLine(new EasyClave()), "Decrypt ERROR", e);
        }
        
        LoggerUtil.logCryptoOperationSuccess(algorithm.NAME, "decrypt", "message", 1);
        System.out.println("""
                Mensagem descriptografada com sucesso.
                ----------------------------------*----------------------------------
                """);
    }
}
