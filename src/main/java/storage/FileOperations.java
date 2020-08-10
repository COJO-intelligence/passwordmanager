package main.java.storage;

import main.java.manager.KeyManager;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;

public class FileOperations {

    private static final byte[] initialIV = {
            0x01, 0x01, 0x01, 0x01,
            0x01, 0x01, 0x01, 0x01,
            0x01, 0x01, 0x01, 0x01,
            0x01, 0x01, 0x01, 0x01
    };

    protected static ArrayList<CredentialsElement> loadAllElementsIntoArrayList(String inputFilePath)
            throws IOException, ClassNotFoundException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidAlgorithmParameterException, CertificateException, KeyStoreException, UnrecoverableEntryException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(decryptContent(inputFilePath)));
        ArrayList<CredentialsElement> dataList = (ArrayList<CredentialsElement>) objectInputStream.readObject();
        objectInputStream.close();
        return dataList;
    }

    private static byte[] decryptContent(String inputFilePath)
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, CertificateException, KeyStoreException, UnrecoverableEntryException {
        FileInputStream fis = new FileInputStream(inputFilePath);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey secretKey = KeyManager.getSecretKey();
        IvParameterSpec ivSpec = new IvParameterSpec(initialIV);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] encryptedContent = fis.readAllBytes();
        byte[] decryptedContent = cipher.doFinal(encryptedContent);
        fis.close();
        return decryptedContent;
    }

    protected static void writeAllElementsIntoFile(ArrayList<CredentialsElement> dataList, String outputFilePath)
            throws IOException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidAlgorithmParameterException, CertificateException, KeyStoreException, UnrecoverableEntryException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(dataList);
        objectOutputStream.flush();
        objectOutputStream.close();
        encryptContent(outputFilePath, byteArrayOutputStream.toByteArray());
    }

    private static void encryptContent(String outputFilePath, byte[] inputByteArray)
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, CertificateException, KeyStoreException, UnrecoverableEntryException {
        FileOutputStream fos = new FileOutputStream(outputFilePath);
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
