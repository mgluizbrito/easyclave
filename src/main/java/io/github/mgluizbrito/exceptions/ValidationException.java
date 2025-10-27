package io.github.mgluizbrito.exceptions;

/**
 * Exceção para erros de validação de entrada
 */
public class ValidationException extends CryptoException {
    
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
