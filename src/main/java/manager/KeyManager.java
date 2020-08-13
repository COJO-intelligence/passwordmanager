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

    //TODO find solution
    //TODO find solution for macOS - Catalina and Mojave + Ubuntu 16.04, 18.04, 20.04 and Windows 7 and Windows 10
    private static char[] getHardwarePassword() throws IOException {

        String serial;

        String OSName=  System.getProperty("os.name");
        if(OSName.contains("Windows")){
            serial = getWindowsMotherboard_SerialNumber();
        }
        else{
            serial = GetLinuxMotherBoard_serialNumber();
        }

        String secret = randomSalt + serial;
        return secret.toCharArray();
    }

    public static String getWindowsMotherboard_SerialNumber() throws IOException {
        Process p = Runtime.getRuntime().exec("wmic baseboard get serialnumber");
        p.getOutputStream().close();
        Scanner sc = new Scanner(p.getInputStream());
        String property = sc.next();
        String serial = sc.next();
        return property + serial;
    }

    public static String GetLinuxMotherBoard_serialNumber() throws IOException {

        String result = "";
        String maniBord_cmd = "dmidecode | grep 'Serial Number' | awk '{print $3}' | tail -1";
        Process p;
            p = Runtime.getRuntime().exec(new String[] { "sh", "-c", maniBord_cmd });// Pipe
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
                break;
            }
            br.close();
        return result;
    }

}
