package io.github.mgluiz.algorithms.services;

import io.github.mgluizbrito.algorithms.Algorithms;
import io.github.mgluizbrito.algorithms.services.MD5Service;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

class MD5ServiceTest {

    @Test
    void msgHash() throws Exception {
        if (!Objects.equals(new MD5Service().msgHash("Oii"), "56b72b203a2deac46c7b8a4e3c8abad7")) throw new Exception();
    }

    @Test
    void fileHash()throws Exception {
        if (!Objects.equals(new MD5Service().fileHash(new File(".gitignore"), Algorithms.MD5), "c828c4c208e90ddabb6687a90897bbf4")) throw new Exception();
    }
}