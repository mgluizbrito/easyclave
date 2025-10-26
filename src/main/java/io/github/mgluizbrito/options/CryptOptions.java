package io.github.mgluizbrito.options;

import picocli.CommandLine.Option;

public class CryptOptions {

    @Option(names = {"-e", "--encrypt"}, description = "Ecrypt files or passwords with a key/algorithm of your choice")
    public boolean encrypt;

    @Option(names = {"-d", "--decrypt"}, description = "Decrypt files or passwords with a key/algorithm of your choice")
    public boolean decrypt;

    public boolean isEncrypt() {
        return encrypt;
    }

    public boolean isDecrypt() {
        return decrypt;
    }
}
