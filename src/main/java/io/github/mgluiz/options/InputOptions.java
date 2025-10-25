package io.github.mgluiz.options;

import picocli.CommandLine.Option;

import java.io.File;
import java.util.List;

public class InputOptions {

    @Option(names = {"-m", "--msg"},
            arity = "1",
            description = "Aceita um texto para serem processados",
            paramLabel = "MSG")
    public String msg;

    @Option(names = {"-f", "--files"},
            arity = "1..*",
            description = "Aceita um ou mais arquivos a serem processados pelo algorítimo",
            paramLabel = "FILES")
    public List<File> files;

    public boolean hasMsg(){
        return msg != null;
    }
    public boolean hasFiles(){
        return files != null;
    }
}
