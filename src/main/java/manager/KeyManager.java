package main.java.manager;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Scanner;

public class KeyManager {
    private static final String algorithmEncrypt = "AES";
    private static final String keyStoreLocation = "keystore.p12";
    private static final String keyName = "MainKey";
    private static final String randomSalt = "woa11A2@##";


    /**
     * Generates a pseudo random encryption key and stores it in a KeyStore
     */
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

    /**
     * Gets the encryption key from the keystore
     *
     * @return SecretKey object
     */
    public static SecretKey getSecretKey() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableEntryException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        char[] password = getHardwarePassword();

        KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(password);
        keyStore.load(new FileInputStream(keyStoreLocation), password);
        KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(keyName, pp);

        return secretKeyEntry.getSecretKey();
    }

    /**
     * @return true or false if the encryption key exists in the keystore
     */
    public static boolean isSecretKeySet() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        char[] password = getHardwarePassword();

        File file = new File(keyStoreLocation);
        if (!file.exists()) {
            return false;
        }
        keyStore.load(new FileInputStream(keyStoreLocation), password);

        return keyStore.isKeyEntry(keyName);
    }

    private static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithmEncrypt);
        SecureRandom secRandom = new SecureRandom();
        keyGen.init(secRandom);
        return keyGen.generateKey();
    }

    private static char[] getHardwarePassword() throws IOException {

        String serial;

        String OSName = System.getProperty("os.name");
        if (OSName.contains("Windows")) {
            serial = getWindowsMotherboard_SerialNumber();
        } else {
            serial = GetLinuxMotherBoard_serialNumber();
        }

        String secret = randomSalt + serial;
        return secret.toCharArray();
    }

    private static String getWindowsMotherboard_SerialNumber() throws IOException {
        Process p = Runtime.getRuntime().exec("wmic baseboard get serialnumber");
        p.getOutputStream().close();
        Scanner sc = new Scanner(p.getInputStream());
        String property = sc.next();
        String serial = sc.next();
        return property + serial;
    }

    private static String GetLinuxMotherBoard_serialNumber() throws IOException {
        String maniBord_cmd = "dmidecode | grep 'Serial Number' | awk '{print $3}' | tail -1";
        Process p;
        p = Runtime.getRuntime().exec(new String[]{"sh", "-c", maniBord_cmd});
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String result = br.readLine();
        br.close();
        return result;
    }

}
