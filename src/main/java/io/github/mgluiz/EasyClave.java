package io.github.mgluiz;

import io.github.mgluiz.algorithms.Algorithms;
import io.github.mgluiz.options.CryptOptions;
import io.github.mgluiz.options.InputOptions;
import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
        name = "easyclave",
        mixinStandardHelpOptions = true,
        description = "EasyClave é uma ferramente de criptografia e descriptografia com o objetivo de ser facil, rápida e limpa",
        version = "EasyClave version 1.0 - 08/2025 \ndev: Luiz Brito (https://github.com/mgluizbrito)")
public class EasyClave implements Runnable {

    @ArgGroup(exclusive = true)
    private CryptOptions crypt;

    @ArgGroup(exclusive = false)
    private InputOptions input;

    @Option(names = {"-a", "--algo"}, arity = "1", paramLabel = "ALGORITHM", description = """
            Algorítimo a ser utilizado para criptografia ou descriptografia.
            
            Algorítmos Simétricos (Opcional: inserir númer de bits da chave com o argumento -kb ou --keybits): AES, DES
            Algorítmos Asimétricos (Utiliza-se de chaves públicas): RSA, ECC
            Algorítmos de HASH (Não possuem chave, nem decrypt): SHA256, MD5
            """)
    private Algorithms algorithms;

    @Option(names = {"-k", "--keyBits"}, arity = "1", description = "Número de bits da chave a ser utilizada para criptografia/descriptografia simétrica", paramLabel = "128")
    private int keyBits = 128;

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
        int exitCode = new CommandLine(new EasyClave()).execute(args);
        System.exit(exitCode);
    }
}
