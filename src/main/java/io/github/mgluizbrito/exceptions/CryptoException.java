package io.github.mgluizbrito.exceptions;

/**
 * Exceção base para erros de criptografia
 */
public class CryptoException extends Exception {
    
    public CryptoException(String message) {
        super(message);
    }
    
    public CryptoException(String message, Throwable cause) {
        super(message, cause);
    }
}
