package io.github.mgluiz.options;

import picocli.CommandLine.Option;

import java.io.File;
import java.util.List;

public class InputOptions {

    @Option(names = {"-t", "--text"},
            arity = "1",
            description = "Aceita um texto para serem processados",
            paramLabel = "TEXT")
    public String text;

    @Option(names = {"-f", "--files"},
            arity = "1..*",
            description = "Aceita um ou mais arquivos a serem processados pelo algorítimo",
            paramLabel = "FILES")
    public List<File> files;
}
