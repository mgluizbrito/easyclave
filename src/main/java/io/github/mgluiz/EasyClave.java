package io.github.mgluiz;

import io.github.mgluiz.options.CryptOptions;
import io.github.mgluiz.options.InputOptions;
import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.List;

@Command(
        name = "easyclave",
        mixinStandardHelpOptions = true,
        description = "EasyClave é uma ferramente de criptografia e descriptografia com o objetivo de ser facil, rapida e limpa",
        version = "EasyClave version 1.0 - 08/2025 \ndev: Luiz Brito (https://github.com/mgluizbrito)")
public class EasyClave implements Runnable {

    @ArgGroup(exclusive = true)
    private CryptOptions crypt;

    @ArgGroup(exclusive = false, multiplicity = "1..2")
    private InputOptions input;

    @Option(names = {"-k", "--key"}, arity = "1", description = "Chave a ser utilizada para criptografia/descriptografia simétrica")
    private String key;

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
        int exitCode = new CommandLine(new EasyClave()).execute("-t", "pom.xml");
        System.exit(exitCode);
    }
}
