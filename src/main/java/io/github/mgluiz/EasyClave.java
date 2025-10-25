package io.github.mgluiz;

import io.github.mgluiz.controller.EncryptController;
import io.github.mgluiz.options.ArgsOptions;
import io.github.mgluiz.options.InputOptions;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        name = "easyclave",
        mixinStandardHelpOptions = true,
        description = "EasyClave é uma ferramente de criptografia e descriptografia com o objetivo de ser facil, rápida e limpa",
        version = "EasyClave version 1.0 - 2025.08 \ndev: Luiz Brito (https://github.com/mgluizbrito)")
public class EasyClave extends ArgsOptions implements Runnable {

    @Override
    public void run() {
        if (crypt == null || input == null){
            CommandLine.usage(this, System.out);
            return;
        }

        if (crypt.isEncrypt()){

            if (input.hasMsg()) return;
            if (input.hasFiles()) EncryptController.fileEncrypt(this.input.files, this.algorithm);
        }

        if (crypt.isDecrypt()){
            System.out.println("Decripto");
            return;
        };
    }

    public static void main(String[] args){
        int exitCode = new CommandLine(new EasyClave()).execute("-e", "-a=MD5", "-f=pom.xml");
        System.exit(exitCode);
    }
}
