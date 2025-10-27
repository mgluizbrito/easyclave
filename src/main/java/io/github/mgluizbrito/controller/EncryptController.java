package io.github.mgluizbrito.controller;

import io.github.mgluizbrito.EasyClave;
import io.github.mgluizbrito.algorithms.Algorithms;
import io.github.mgluizbrito.algorithms.services.AESService;
import io.github.mgluizbrito.algorithms.services.DESService;
import io.github.mgluizbrito.algorithms.services.MD5Service;
import io.github.mgluizbrito.algorithms.services.RSAService;
import io.github.mgluizbrito.algorithms.services.SHA256Service;
import io.github.mgluizbrito.utils.LoggerUtil;
import org.slf4j.Logger;
import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class EncryptController {

    private static final Logger logger = LoggerUtil.getLogger(EncryptController.class);

    public static void fileEncrypt(List<File> files, Algorithms algorithm) {
        LoggerUtil.logCryptoOperationStart(algorithm.NAME, "encrypt", "files");
        System.out.println("SELECTED ALGORITHM: " + algorithm.NAME);
        
        for (File file : files){
            logger.debug("Processando arquivo: {}", file.getAbsolutePath());

            if (algorithm.equals(Algorithms.MD5)){

                try (FileWriter fw = new FileWriter(file.getName() + ".md5")){

                    String hash = new MD5Service().fileHash(file, Algorithms.MD5);
                    fw.write(hash);

                } catch (IOException | NoSuchAlgorithmException e) {
                    System.out.printf(e.getMessage());
                }
            }

            if (algorithm.equals(Algorithms.SHA256)){
                try (FileWriter fw = new FileWriter(file.getName() + ".sha256")){

                    String hash = new SHA256Service().fileHash(file, Algorithms.SHA256);
                    fw.write(hash);

                } catch (IOException | NoSuchAlgorithmException e) {
                    System.err.println("Erro ao gerar hash SHA256: " + e.getMessage());
                }
            }

            if (algorithm.equals(Algorithms.DES)){
                System.out.println("ORIGINAL FILE: " + file.getAbsolutePath());
                try{
                    File outputFile = new File(file.getName()+".des");
                    new DESService().encryptFile(file, outputFile);
                    System.out.println("ENCRYPTED FILE: " + outputFile.getName());

                } catch (GeneralSecurityException | IOException e) {
                    System.err.println("Erro ao processar criptografia: " + e.getMessage());
                    throw new CommandLine.ExecutionException(new CommandLine(new EasyClave()), "Encrypt ERROR", e);
                }
            }

            if (algorithm.equals(Algorithms.RSA)){
                System.out.println("ORIGINAL FILE: " + file.getAbsolutePath());
                try{
                    File outputFile = new File(file.getName()+".rsa");
                    RSAService rsaService = new RSAService();
                    java.security.KeyPair keyPair = rsaService.generateKeyPair(2048);
                    rsaService.encryptFile(file, outputFile, keyPair.getPublic());
                    System.out.println("ENCRYPTED FILE: " + outputFile.getName());

                } catch (GeneralSecurityException | IOException e) {
                    System.err.println("Erro ao processar criptografia: " + e.getMessage());
                    throw new CommandLine.ExecutionException(new CommandLine(new EasyClave()), "Encrypt ERROR", e);
                }
            }
            System.out.println();
        }
        LoggerUtil.logCryptoOperationSuccess(algorithm.NAME, "encrypt", "files", files.size());
        System.out.printf("""
                %d files successfully encrypted.
                ----------------------------------*----------------------------------
                %n""", files.size());
    }

    public static void msgEncrypt(String msg, Algorithms algorithm){
        LoggerUtil.logCryptoOperationStart(algorithm.NAME, "encrypt", "message");
        System.out.println("SELECTED ALGORITHM: " + algorithm.NAME);
        logger.debug("Processando mensagem com algoritmo: {}", algorithm.NAME);

        if (algorithm.equals(Algorithms.MD5)){
            System.out.println("ORIGINAL MESSAGE: "+ msg);
            System.out.println("MD5 HASH: "+ new MD5Service().msgHash(msg));
        };

        if (algorithm.equals(Algorithms.SHA256)){
            System.out.println("ORIGINAL MESSAGE: "+ msg);
            System.out.println("SHA256 HASH: "+ new SHA256Service().msgHash(msg));
        };

        if (algorithm.equals(Algorithms.AES)){
            System.out.println("ORIGINAL MESSAGE: "+ msg);
            try {
                System.out.println("ENCRYPTED MESSAGE: "+ new AESService().encryptMessage(msg));
            } catch (GeneralSecurityException e) {
                System.err.println("Erro ao processar criptografia: " + e.getMessage());
                throw new CommandLine.ExecutionException(new CommandLine(new EasyClave()), "Encrypt ERROR", e);
            }
        }

        if (algorithm.equals(Algorithms.DES)){
            System.out.println("ORIGINAL MESSAGE: "+ msg);
            try {
                System.out.println("ENCRYPTED MESSAGE: "+ new DESService().encryptMessage(msg));
            } catch (GeneralSecurityException e) {
                System.err.println("Erro ao processar criptografia: " + e.getMessage());
                throw new CommandLine.ExecutionException(new CommandLine(new EasyClave()), "Encrypt ERROR", e);
            }
        }

        if (algorithm.equals(Algorithms.RSA)){
            System.out.println("ORIGINAL MESSAGE: "+ msg);
            try {
                RSAService rsaService = new RSAService();
                java.security.KeyPair keyPair = rsaService.generateKeyPair(2048);
                String encryptedMsg = rsaService.encryptMessage(msg, keyPair.getPublic());
                System.out.println("ENCRYPTED MESSAGE: "+ encryptedMsg);
            } catch (GeneralSecurityException e) {
                System.err.println("Erro ao processar criptografia: " + e.getMessage());
                throw new CommandLine.ExecutionException(new CommandLine(new EasyClave()), "Encrypt ERROR", e);
            }
        }

        LoggerUtil.logCryptoOperationSuccess(algorithm.NAME, "encrypt", "message", 1);
        System.out.println("""
                Message successfully encrypted.
                ----------------------------------*----------------------------------
                """);
    }
}
