package main.java.storage;

import main.java.manager.KeyManager;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;

public class FileOperations {

    //TODO - Random IV -saved in key manager
    private static final byte[] initialIV = {
            0x01, 0x01, 0x01, 0x01,
            0x01, 0x01, 0x01, 0x01,
            0x01, 0x01, 0x01, 0x01,
            0x01, 0x01, 0x01, 0x01
    };

    private static final String filePath = "pm.enc";

    protected static ArrayList<CredentialsElement> loadAllElementsIntoArrayList()
            throws IOException, ClassNotFoundException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidAlgorithmParameterException, CertificateException, KeyStoreException, UnrecoverableEntryException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(decryptContent()));
        ArrayList<CredentialsElement> dataList = (ArrayList<CredentialsElement>) objectInputStream.readObject();
        objectInputStream.close();
        return dataList;
    }

    protected static void writeAllElementsIntoFile(ArrayList<CredentialsElement> dataList)
            throws IOException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidAlgorithmParameterException, CertificateException, KeyStoreException, UnrecoverableEntryException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(dataList);
        objectOutputStream.flush();
        objectOutputStream.close();
        encryptContent(byteArrayOutputStream.toByteArray());
    }

    protected static boolean isFilePresent() {
        File file = new File(filePath);
        return file.exists();
    }

    private static byte[] decryptContent()
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, CertificateException, KeyStoreException, UnrecoverableEntryException {
        FileInputStream fis = new FileInputStream(filePath);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey secretKey = KeyManager.getSecretKey();
        IvParameterSpec ivSpec = new IvParameterSpec(initialIV);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
//        byte[] encryptedContent = fis.readAllBytes();
        byte[] encryptedContent = Files.readAllBytes(Paths.get(inputFilePath));
        byte[] decryptedContent = cipher.doFinal(encryptedContent);
        fis.close();
        return decryptedContent;
    }

    private static void encryptContent(byte[] inputByteArray)
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, CertificateException, KeyStoreException, UnrecoverableEntryException {
        FileOutputStream fos = new FileOutputStream(filePath);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        if (!KeyManager.isSecretKeySet()) {
            KeyManager.setSecretKey();
        }
        SecretKey secretKey = KeyManager.getSecretKey();
        IvParameterSpec ivSpec = new IvParameterSpec(initialIV);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        fos.write(cipher.doFinal(inputByteArray));
        fos.flush();
        fos.close();
    }

}
