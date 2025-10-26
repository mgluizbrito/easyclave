package io.github.mgluiz.controller;

import io.github.mgluiz.algorithms.Algorithms;
import io.github.mgluiz.algorithms.services.AESService;
import io.github.mgluiz.algorithms.services.MD5Service;
import io.github.mgluiz.algorithms.services.SHA256Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

                System.out.println("Encriptado do arquivo \""+file.getName()+"\" gerado como " + file.getName()+".enc (Algorítimo de AES)");
                File outputFile = new File(file.getName()+".enc");

                new AESService().fileEncrypt(file, outputFile);
                return;
            }
        }
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
    }
}
