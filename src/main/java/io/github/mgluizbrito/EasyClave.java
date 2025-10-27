package io.github.mgluizbrito;

import io.github.mgluizbrito.controller.EncryptController;
import io.github.mgluizbrito.options.ArgsOptions;
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

        System.out.println("""
                  ______                 _____ _                \s
                 |  ____|               / ____| |               \s
                 | |__   __ _ ___ _   _| |    | | __ ___   _____\s
                 |  __| / _` / __| | | | |    | |/ _` \\ \\ / / _ \\
                 | |___| (_| \\__ \\ |_| | |____| | (_| |\\ V /  __/
                 |______\\__,_|___/\\__, |\\_____|_|\\__,_| \\_/ \\___| v1.0
                                   __/ |                        \s
                                  |___/                         \s
                """);

        if (crypt == null || input == null) {
            CommandLine.usage(this, System.out);
            return;
        }
        if (crypt.decrypt && key == null) {
            System.err.println("ERROR: ao usar decrypt, é obrigatório também passar uma key (-k).");
            return;
        }

        if (crypt.isEncrypt()){
            System.out.println("CRYPT MODE: " + "Encrypt");
            if (input.hasMsg()) EncryptController.msgEncrypt(this.input.msg, this.algorithm);
            if (input.hasFiles()) EncryptController.fileEncrypt(this.input.files, this.algorithm);
        }

        if (crypt.isDecrypt()){
            System.out.println("CRYPT MODE: " + "Decrypt");
            return;
        };
    }

    public static void main(String[] args){
        int exitCode = new CommandLine(new EasyClave()).execute("-d", "-a=AES", "-m", "osuR+tdvWg0eEYfKCv0tFQ==");
        System.exit(exitCode);
    }
}
