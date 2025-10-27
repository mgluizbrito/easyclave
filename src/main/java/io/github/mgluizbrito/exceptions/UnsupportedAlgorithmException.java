package io.github.mgluizbrito.exceptions;

/**
 * Exceção para erros de algoritmo não suportado
 */
public class UnsupportedAlgorithmException extends CryptoException {
    
    public UnsupportedAlgorithmException(String algorithm) {
        super("Algoritmo não suportado: " + algorithm);
    }
    
    public UnsupportedAlgorithmException(String algorithm, Throwable cause) {
        super("Algoritmo não suportado: " + algorithm, cause);
    }
}
