package io.github.mgluizbrito.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilitário para logging centralizado da aplicação
 */
public class LoggerUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);
    
    /**
     * Obtém um logger para uma classe específica
     * @param clazz A classe para a qual obter o logger
     * @return Logger configurado para a classe
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
    
    /**
     * Obtém o logger principal da aplicação
     * @return Logger principal
     */
    public static Logger getLogger() {
        return logger;
    }
    
    /**
     * Log de início de operação de criptografia
     * @param algorithm Algoritmo sendo usado
     * @param operation Tipo de operação (encrypt/decrypt)
     * @param inputType Tipo de entrada (message/file)
     */
    public static void logCryptoOperationStart(String algorithm, String operation, String inputType) {
        logger.info("Iniciando operação de {} usando algoritmo {} para {}", operation, algorithm, inputType);
    }
    
    /**
     * Log de sucesso de operação de criptografia
     * @param algorithm Algoritmo usado
     * @param operation Tipo de operação realizada
     * @param inputType Tipo de entrada processada
     * @param count Número de itens processados
     */
    public static void logCryptoOperationSuccess(String algorithm, String operation, String inputType, int count) {
        logger.info("Operação de {} concluída com sucesso usando algoritmo {} - {} {} processados", 
                   operation, algorithm, count, inputType);
    }
    
    /**
     * Log de erro em operação de criptografia
     * @param algorithm Algoritmo sendo usado
     * @param operation Tipo de operação que falhou
     * @param error Erro ocorrido
     */
    public static void logCryptoOperationError(String algorithm, String operation, Throwable error) {
        logger.error("Erro durante operação de {} usando algoritmo {}: {}", 
                    operation, algorithm, error.getMessage(), error);
    }
    
    /**
     * Log de geração de chave
     * @param algorithm Algoritmo da chave
     * @param keySize Tamanho da chave em bits
     */
    public static void logKeyGeneration(String algorithm, int keySize) {
        logger.info("Chave {} gerada com {} bits", algorithm, keySize);
    }
    
    /**
     * Log de geração de hash
     * @param algorithm Algoritmo de hash
     * @param inputType Tipo de entrada (message/file)
     */
    public static void logHashGeneration(String algorithm, String inputType) {
        logger.info("Hash {} gerado para {}", algorithm, inputType);
    }
    
    /**
     * Log de validação de entrada
     * @param inputType Tipo de entrada validada
     * @param isValid Se a validação foi bem-sucedida
     */
    public static void logInputValidation(String inputType, boolean isValid) {
        if (isValid) {
            logger.debug("Validação de {} bem-sucedida", inputType);
        } else {
            logger.warn("Validação de {} falhou", inputType);
        }
    }
}
