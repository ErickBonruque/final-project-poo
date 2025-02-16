package javali.utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.KeyStore;
import java.security.SecureRandom;

public class KeyManager {
    private static final String KEYSTORE_PATH = System.getProperty("user.home") + "/.shieldpass/keystore.jks";
    private static final String SALT_PATH = System.getProperty("user.home") + "/.shieldpass/salt.dat";
    private static final String KEYSTORE_PASSWORD = "ShieldPass" + System.getProperty("user.name") + System.getProperty("os.name");
    private static final String KEY_ALIAS = "ShieldPassKey";
    
    static {
        try {
            initializeKeyStore();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar KeyStore: " + e.getMessage());
        }
    }

    private static void initializeKeyStore() throws Exception {
        File directory = new File(System.getProperty("user.home") + "/.shieldpass");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File keystoreFile = new File(KEYSTORE_PATH);
        File saltFile = new File(SALT_PATH);

        // Verifica se o arquivo de keystore existe
        if (!keystoreFile.exists()) {
            KeyStore keyStore = KeyStore.getInstance("JCEKS"); // Salver chave de forma segura
            keyStore.load(null, KEYSTORE_PASSWORD.toCharArray());
            
            // Gerar nova chave secreta
            SecureRandom random = new SecureRandom();
            byte[] keyBytes = new byte[32];
            random.nextBytes(keyBytes);
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
            
            KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
            KeyStore.ProtectionParameter protectionParam = 
                new KeyStore.PasswordProtection(KEYSTORE_PASSWORD.toCharArray());
            
            keyStore.setEntry(KEY_ALIAS, secretKeyEntry, protectionParam);
            
            try (FileOutputStream fos = new FileOutputStream(keystoreFile)) {
                keyStore.store(fos, KEYSTORE_PASSWORD.toCharArray());
            }
        }

        if (!saltFile.exists()) {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            Files.write(saltFile.toPath(), salt);
        }
    }

    // Método para obter a chave secreta
    public static SecretKey getSecretKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        try (FileInputStream fis = new FileInputStream(KEYSTORE_PATH)) {
            keyStore.load(fis, KEYSTORE_PASSWORD.toCharArray());
        }
        
        KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(
            KEY_ALIAS, 
            new KeyStore.PasswordProtection(KEYSTORE_PASSWORD.toCharArray())
        );
        
        return secretKeyEntry.getSecretKey();
    }

    public static byte[] getSalt() throws Exception {
        return Files.readAllBytes(new File(SALT_PATH).toPath());
    }

    public static void resetKeys() {
        try {
            File keystoreFile = new File(KEYSTORE_PATH);
            File saltFile = new File(SALT_PATH);
            
            if (keystoreFile.exists()) {
                keystoreFile.delete();
            }
            if (saltFile.exists()) {
                saltFile.delete();
            }
            
            initializeKeyStore();
        } catch (Exception e) {
            System.err.println("Erro ao resetar chaves: " + e.getMessage());
        }
    }
} 