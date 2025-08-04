package io.github.mgluiz;

import io.github.mgluiz.options.CryptOptions;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.ArgGroup;

import java.util.concurrent.Callable;

@Command(
        name = "easyclave",
        mixinStandardHelpOptions = true,
        description = "EasyClave é uma ferramente de criptografia e descriptografia com o objetivo de ser facil, rapida e limpa",
        version = "EasyClave version 1.0 - 08/2025 \ndev: Luiz Brito (https://github.com/mgluizbrito)")
public class EasyClave implements Runnable {

    @ArgGroup(exclusive = true)
    CryptOptions crypt;

    @Override
    public void run() {

        if (crypt == null){
            CommandLine.usage(this, System.out);
            return;
        }

        if (crypt.isEncrypt()){
            System.out.println("Cripto");
            return;
        }

        if (crypt.isDecrypt()){
            System.out.println("Decripto");
            return;
        };


    }

    public static void main(String[] args){
        int exitCode = new CommandLine(new EasyClave()).execute();
        System.exit(exitCode);
    }
}
