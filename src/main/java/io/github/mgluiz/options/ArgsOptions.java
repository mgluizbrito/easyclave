package io.github.mgluiz.options;

import io.github.mgluiz.algorithms.Algorithms;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Option;

public abstract class ArgsOptions {
    @ArgGroup(exclusive = true)
    public CryptOptions crypt;

    @ArgGroup(exclusive = false)
    public InputOptions input;

    @Option(names = {"-a", "--algo"}, arity = "1", paramLabel = "ALGORITHM", description = """
            Algorítimo a ser utilizado para criptografia ou descriptografia.
            
            Algorítmos Simétricos (Opcional: inserir númer de bits da chave com o argumento -kb ou --keybits): AES, DES
            Algorítmos Asimétricos (Utiliza-se de chaves públicas): RSA, ECC
            Algorítmos de HASH (Não possuem chave, nem decrypt): SHA256, MD5
            """)
    public Algorithms algorithm;

    @Option(names = {"-k", "--keyBits"}, arity = "1", description = "Número de bits da chave a ser utilizada para criptografia/descriptografia simétrica", paramLabel = "128")
    public int keyBits = 128;
}
