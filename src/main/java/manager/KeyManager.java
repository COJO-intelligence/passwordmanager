package main.java.manager;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Scanner;

public class KeyManager {
    private static final String algorithmEncrypt = "AES";
    private static final String keyStoreLocation = "keystore.p12";
    private static final String keyName = "MainKey";
    private static final String randomSalt = "woa11A2@##";



    public static void setSecretKey() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(null, null);

        SecretKey key = generateSecretKey();
        char[] password = getHardwarePassword();

        KeyStore.SecretKeyEntry keyEntry = new KeyStore.SecretKeyEntry(key);
        KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(password);

        keyStore.setEntry(keyName, keyEntry, pp);

        keyStore.store(new FileOutputStream(keyStoreLocation), password);
    }

    public static SecretKey getSecretKey() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableEntryException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        char[] password = getHardwarePassword();

        KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(password);
        keyStore.load(new FileInputStream(keyStoreLocation), password);
        KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(keyName, pp);

        SecretKey secretKey = secretKeyEntry.getSecretKey();
        return secretKey;
    }

    public static boolean isSecretKeySet() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        char[] password = getHardwarePassword();

        KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(password);
        File file = new File(keyStoreLocation);
        if(!file.exists()) {
            return false;
        }
        keyStore.load(new FileInputStream(keyStoreLocation), password);

        return keyStore.isKeyEntry(keyName);
    }

    private static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithmEncrypt);
        SecureRandom secRandom = new SecureRandom();
        keyGen.init(secRandom);
        SecretKey secretKey = keyGen.generateKey();
        return secretKey;
    }

    private static char[] getHardwarePassword() throws NoSuchAlgorithmException, IOException {
        Process p = Runtime.getRuntime().exec("wmic baseboard get serialnumber");
        p.getOutputStream().close();
        Scanner sc = new Scanner(p.getInputStream());
        String property = sc.next();
        String serial = sc.next();
        String secret = property + randomSalt + serial;
        System.out.println("The secret is:" + secret);
        return secret.toCharArray();
    }
}
