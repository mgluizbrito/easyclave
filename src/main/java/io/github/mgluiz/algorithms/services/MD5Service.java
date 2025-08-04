package io.github.mgluiz.algorithms.services;

import io.github.mgluiz.algorithms.interfaces.HashAlgorithm;
import org.apache.commons.codec.digest.DigestUtils;

public class MD5Service implements HashAlgorithm {

    @Override
    public String msgHash(String msg) {
        return DigestUtils.md5Hex(msg);
    }
}
