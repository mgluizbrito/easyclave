package io.github.mgluiz;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(
        name = "easyclave",
        mixinStandardHelpOptions = true,
        version = "checksum 4.0")
public class EasyClave implements Runnable {

    @Override
    public void run() {
        CommandLine.usage(this, System.out);
    }

    public static void main(String[] args){
        int exitCode = new CommandLine(new EasyClave()).execute(args);
        System.exit(exitCode);
    }
}
