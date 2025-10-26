package io.github.mgluiz.algorithms.services;

import io.github.mgluiz.algorithms.interfaces.HashAlgorithm;
import org.apache.commons.codec.digest.DigestUtils;

public class SHA256Service implements HashAlgorithm {

    @Override
    public String msgHash(String msg) {
        return DigestUtils.sha256Hex(msg);
    }
}
