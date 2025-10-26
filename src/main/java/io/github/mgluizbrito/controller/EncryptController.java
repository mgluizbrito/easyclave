package io.github.mgluizbrito.controller;

import io.github.mgluizbrito.EasyClave;
import io.github.mgluizbrito.algorithms.Algorithms;
import io.github.mgluizbrito.algorithms.services.AESService;
import io.github.mgluizbrito.algorithms.services.MD5Service;
import io.github.mgluizbrito.algorithms.services.SHA256Service;
import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class EncryptController {

    public static void fileEncrypt(List<File> files, Algorithms algorithm) {
        System.out.println("SELECTED ALGORITHM: " + algorithm.NAME);
        for (File file : files){

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

                    String hash = new MD5Service().fileHash(file, Algorithms.SHA256);
                    fw.write(hash);

                } catch (IOException | NoSuchAlgorithmException e) {
                    System.out.printf(e.getMessage());
                }
            }

            if (algorithm.equals(Algorithms.AES)){
                System.out.println("ORIGINAL FILE: " + file.getAbsolutePath());
                try{
                    File outputFile = new File(file.getName()+".aes");
                    new AESService().encryptFile(file, outputFile);
                    System.out.println("ENCRYPTED FILE: " + outputFile.getName());

                } catch (GeneralSecurityException | IOException e) {
                    System.err.println("Error processing decryption: " + e.getMessage());
                    throw new CommandLine.ExecutionException(new CommandLine(new EasyClave()), "Encrypt ERROR", e);
                }
            }
            System.out.println();
        }
        System.out.printf("""
                %d files successfully encrypted.
                ----------------------------------*----------------------------------
                %n""", files.size());
    }

    public static void msgEncrypt(String msg, Algorithms algorithm){
        System.out.println("SELECTED ALGORITHM: " + algorithm.NAME);

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
                System.err.println("Error processing decryption: " + e.getMessage());
                throw new CommandLine.ExecutionException(new CommandLine(new EasyClave()), "Encrypt ERROR", e);
            }
        }

        System.out.println("""
                Message successfully encrypted.
                ----------------------------------*----------------------------------
                """);
    }
}
