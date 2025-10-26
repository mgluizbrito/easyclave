package io.github.mgluizbrito.algorithms;

import javax.xml.namespace.QName;

public enum Algorithms {
    // Symmetric (Private Key)
    AES("AES", "Advanced Encryption Standard"),
    DES("DES", "Data Encryption Standard"),

    // Asymmetric (Public Key)
    RSA("RSA", "Rivest-Shamir-Adleman"),
    ECC("ECC", "Elliptic Curve Cryptography"),

    // HASH (No Key)
    MD5("MD5", "Message Digest Algorithm"),
    SHA256("SHA-256", "Secure Hash Algorithm 256");

    public final String NAME;
    public final String DESC;

    Algorithms(String name, String desc) {
        this.NAME = name;
        this.DESC = desc;
    }
}
