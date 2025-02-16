package javali.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CryptoUtils {
    private static final String ALGORITHM = "AES";

    // Método para criptografar uma string
    public static String encrypt(String data) {
        try {
            SecretKey key = KeyManager.getSecretKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            System.err.println("Erro ao criptografar: " + e.getMessage());
            return data;
        }
    }

    // Método para descriptografar uma string
    public static String decrypt(String encryptedData) {
        try {
            SecretKey key = KeyManager.getSecretKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.err.println("Erro ao descriptografar: " + e.getMessage());
            return encryptedData;
        }
    }
} 