package io.github.mgluizbrito.utils;

import io.github.mgluizbrito.algorithms.Algorithms;
import io.github.mgluizbrito.utils.LoggerUtil;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utilitário para validação de entradas e parâmetros
 */
public class ValidationUtil {
    
    private static final Pattern BASE64_PATTERN = Pattern.compile("^[A-Za-z0-9+/]*={0,2}$");
    private static final Pattern HEX_PATTERN = Pattern.compile("^[0-9a-fA-F]+$");
    
    /**
     * Valida se uma string é um Base64 válido
     * @param input String a ser validada
     * @return true se for Base64 válido, false caso contrário
     */
    public static boolean isValidBase64(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        
        boolean isValid = BASE64_PATTERN.matcher(input).matches();
        LoggerUtil.logInputValidation("Base64", isValid);
        return isValid;
    }
    
    /**
     * Valida se uma string é um hexadecimal válido
     * @param input String a ser validada
     * @return true se for hexadecimal válido, false caso contrário
     */
    public static boolean isValidHex(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        
        boolean isValid = HEX_PATTERN.matcher(input).matches();
        LoggerUtil.logInputValidation("Hexadecimal", isValid);
        return isValid;
    }
    
    /**
     * Valida se um arquivo existe e é legível
     * @param file Arquivo a ser validado
     * @return true se o arquivo é válido, false caso contrário
     */
    public static boolean isValidFile(File file) {
        if (file == null) {
            LoggerUtil.logInputValidation("File", false);
            return false;
        }
        
        boolean isValid = file.exists() && file.isFile() && file.canRead();
        LoggerUtil.logInputValidation("File", isValid);
        return isValid;
    }
    
    /**
     * Valida uma lista de arquivos
     * @param files Lista de arquivos a ser validada
     * @return true se todos os arquivos são válidos, false caso contrário
     */
    public static boolean isValidFileList(List<File> files) {
        if (files == null || files.isEmpty()) {
            LoggerUtil.logInputValidation("FileList", false);
            return false;
        }
        
        boolean allValid = files.stream().allMatch(ValidationUtil::isValidFile);
        LoggerUtil.logInputValidation("FileList", allValid);
        return allValid;
    }
    
    /**
     * Valida se uma mensagem não é nula ou vazia
     * @param message Mensagem a ser validada
     * @return true se a mensagem é válida, false caso contrário
     */
    public static boolean isValidMessage(String message) {
        boolean isValid = message != null && !message.trim().isEmpty();
        LoggerUtil.logInputValidation("Message", isValid);
        return isValid;
    }
    
    /**
     * Valida se um algoritmo é suportado para criptografia
     * @param algorithm Algoritmo a ser validado
     * @return true se o algoritmo é suportado para criptografia, false caso contrário
     */
    public static boolean isValidEncryptionAlgorithm(Algorithms algorithm) {
        if (algorithm == null) {
            LoggerUtil.logInputValidation("EncryptionAlgorithm", false);
            return false;
        }
        
        boolean isValid = algorithm == Algorithms.AES || 
                         algorithm == Algorithms.DES || 
                         algorithm == Algorithms.RSA;
        LoggerUtil.logInputValidation("EncryptionAlgorithm", isValid);
        return isValid;
    }
    
    /**
     * Valida se um algoritmo é suportado para descriptografia
     * @param algorithm Algoritmo a ser validado
     * @return true se o algoritmo é suportado para descriptografia, false caso contrário
     */
    public static boolean isValidDecryptionAlgorithm(Algorithms algorithm) {
        if (algorithm == null) {
            LoggerUtil.logInputValidation("DecryptionAlgorithm", false);
            return false;
        }
        
        boolean isValid = algorithm == Algorithms.AES || 
                         algorithm == Algorithms.DES || 
                         algorithm == Algorithms.RSA;
        LoggerUtil.logInputValidation("DecryptionAlgorithm", isValid);
        return isValid;
    }
    
    /**
     * Valida se um algoritmo é suportado para hash
     * @param algorithm Algoritmo a ser validado
     * @return true se o algoritmo é suportado para hash, false caso contrário
     */
    public static boolean isValidHashAlgorithm(Algorithms algorithm) {
        if (algorithm == null) {
            LoggerUtil.logInputValidation("HashAlgorithm", false);
            return false;
        }
        
        boolean isValid = algorithm == Algorithms.MD5 || algorithm == Algorithms.SHA256;
        LoggerUtil.logInputValidation("HashAlgorithm", isValid);
        return isValid;
    }
    
    /**
     * Valida se o tamanho da chave é válido para o algoritmo especificado
     * @param algorithm Algoritmo a ser usado
     * @param keySize Tamanho da chave em bits
     * @return true se o tamanho da chave é válido, false caso contrário
     */
    public static boolean isValidKeySize(Algorithms algorithm, int keySize) {
        if (algorithm == null) {
            LoggerUtil.logInputValidation("KeySize", false);
            return false;
        }
        
        boolean isValid = switch (algorithm) {
            case AES -> keySize == 128 || keySize == 192 || keySize == 256;
            case DES -> keySize == 56;
            case RSA -> keySize >= 1024 && keySize <= 4096 && keySize % 8 == 0;
            case MD5, SHA256 ->
                // Algoritmos de hash não usam chaves, então sempre válido
                    true;
            default -> false;
        };

        LoggerUtil.logInputValidation("KeySize", isValid);
        return isValid;
    }
    
    /**
     * Valida se uma chave é válida para o algoritmo especificado
     * @param algorithm Algoritmo a ser usado
     * @param key Chave em Base64
     * @return true se a chave é válida, false caso contrário
     */
    public static boolean isValidKey(Algorithms algorithm, String key) {
        if (!isValidBase64(key)) {
            LoggerUtil.logInputValidation("Key", false);
            return false;
        }
        
        try {
            byte[] keyBytes = java.util.Base64.getDecoder().decode(key);
            
            // Para RSA, não validamos o tamanho baseado nos bytes decodificados
            // pois a estrutura da chave é mais complexa
            if (algorithm == Algorithms.RSA) {
                LoggerUtil.logInputValidation("Key", true);
                return true;
            }
            
            boolean isValid = isValidKeySize(algorithm, keyBytes.length * 8);
            LoggerUtil.logInputValidation("Key", isValid);
            return isValid;
        } catch (IllegalArgumentException e) {
            LoggerUtil.logInputValidation("Key", false);
            return false;
        }
    }
    
    /**
     * Valida se uma mensagem não excede o tamanho máximo para RSA
     * @param message Mensagem a ser validada
     * @param keySize Tamanho da chave RSA em bits
     * @return true se a mensagem é válida para RSA, false caso contrário
     */
    public static boolean isValidRSAMessageSize(String message, int keySize) {
        if (message == null) {
            LoggerUtil.logInputValidation("RSAMessageSize", false);
            return false;
        }
        
        // RSA pode criptografar até (keySize/8 - padding) bytes
        int maxBytes = (keySize / 8) - 11; // PKCS1Padding usa 11 bytes de padding
        boolean isValid = message.getBytes().length <= maxBytes;
        LoggerUtil.logInputValidation("RSAMessageSize", isValid);
        return isValid;
    }
}
