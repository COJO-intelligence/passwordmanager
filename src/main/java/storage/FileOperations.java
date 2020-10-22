package main.java.storage;

import main.java.manager.KeyManager;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;

public class FileOperations {

    public static String directoryPath;
    private static Path filePath = null;

    private byte[] decryptContent()
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, CertificateException, KeyStoreException, UnrecoverableEntryException {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKey secretKey = KeyManager.getSecretKey();
        byte[] encryptedContent = Files.readAllBytes(filePath);
        AlgorithmParameterSpec gcmIv = new GCMParameterSpec(128, encryptedContent, 0, 12);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmIv);
        return cipher.doFinal(encryptedContent, 12, encryptedContent.length - 12);
    }

    private void encryptContent(byte[] inputByteArray)
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, CertificateException, KeyStoreException, UnrecoverableEntryException {
        FileOutputStream fos = new FileOutputStream(String.valueOf(filePath));
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        byte[] iv = generateIV();
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv);
        if (!KeyManager.isSecretKeySet()) {
            KeyManager.setSecretKey();
        }
        SecretKey secretKey = KeyManager.getSecretKey();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);
        byte [] encryptedData = cipher.doFinal(inputByteArray);
        ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + encryptedData.length);
        byteBuffer.put(iv);
        byteBuffer.put(encryptedData);
        byte[] cipherMessage = byteBuffer.array();
        fos.write(cipherMessage);
        fos.flush();
        fos.close();
    }

    private byte[] generateIV() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[12];
        secureRandom.nextBytes(iv);
        return iv;
    }

    /**
     * Creates the main folder of the application if not existing already
     */
    public static void startUserDirectory() throws IOException {
        String homeDir = System.getProperty("user.home");
        Path path = Paths.get(homeDir, "PMCojo");
        directoryPath = String.valueOf(path);
        File dir = new File(directoryPath);
        filePath = Paths.get(directoryPath, "pm.enc");
        if (!(dir.exists() || dir.isDirectory())) {
            Files.createDirectories(path);
        }
    }

    /**
     * Reads all entries from the file using an object input stream.
     * Decryption is performed at runtime.
     * To be used only from DataOperation class.
     *
     * @return the existing list of entries
     */
    public ArrayList<CredentialsElement> loadAllElementsIntoArrayList()
            throws IOException, ClassNotFoundException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidAlgorithmParameterException, CertificateException, KeyStoreException, UnrecoverableEntryException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(decryptContent()));
        ArrayList<CredentialsElement> dataList = (ArrayList<CredentialsElement>) objectInputStream.readObject();
        objectInputStream.close();
        return dataList;
    }

    /**
     * Writes all entries into the file using and object output stream.
     * Encryption is performed at runtime.
     * To be used only from DataOperation class.
     *
     * @param dataList the list of entries
     */
    public void writeAllElementsIntoFile(ArrayList<CredentialsElement> dataList)
            throws IOException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidAlgorithmParameterException, CertificateException, KeyStoreException, UnrecoverableEntryException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(dataList);
        objectOutputStream.flush();
        objectOutputStream.close();
        encryptContent(byteArrayOutputStream.toByteArray());
    }

    /**
     * Checks if the file is present or not.
     * To be used only from DataOperation class.
     *
     * @return true or false if the file exists
     */
    public boolean isFilePresent() {
        File file = new File(String.valueOf(filePath));
        return file.exists();
    }

}
