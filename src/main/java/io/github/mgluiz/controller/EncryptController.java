package io.github.mgluiz.controller;

import io.github.mgluiz.algorithms.Algorithms;
import io.github.mgluiz.algorithms.services.AESService;
import io.github.mgluiz.algorithms.services.MD5Service;

import java.io.File;
import java.util.List;

public class EncryptController {

    public static void fileEncrypt(List<File> files, Algorithms algorithm) {
        if (algorithm.equals(Algorithms.AES)){
            for (File file : files){

                System.out.println("Encriptado do arquivo \""+file.getName()+"\" gerado como " + file.getName()+".enc (Algorítimo de AES)");
                File outputFile = new File(file.getName()+".enc");

                new AESService().fileEncrypt(file, outputFile);
                return;
            }
        }

        if (algorithm.equals(Algorithms.MD5)){
            for (File file : files){

                System.out.println("Encriptado do arquivo \""+file.getName()+"\" gerado como " + file.getName()+".enc (Algorítimo de MD5)");
                File outputFile = new File(file.getName()+".md5");

                new MD5Service().fileEncrypt(file, outputFile);
                return;
            }
        }
    }
}
