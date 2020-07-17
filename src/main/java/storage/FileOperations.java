package main.java.storage;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileOperations {

    private static final byte[] key = {
            0x2A, 0x4D, 0x61, 0x73,
            0x74, 0x65, 0x72, 0x20,
            0x49, 0x53, 0x4D, 0x20,
            0x32, 0x30, 0x31, 0x37
    };

    private static final byte[] initialIV = {
            0x01, 0x01, 0x01, 0x01,
            0x01, 0x01, 0x01, 0x01,
            0x01, 0x01, 0x01, 0x01,
            0x01, 0x01, 0x01, 0x01
    };

    public static void createFile(String outputFilePath) throws IOException {
        File outputFile = new File(outputFilePath);
        if(!outputFile.createNewFile()) {
            throw new FileAlreadyExistsException(outputFilePath);
        }
    }

    public static void destroyFile(String inputFilePath) throws IOException {
        File inputFile = new File (inputFilePath);
        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(inputFile);
        while(fis.read() != -1) {
            fos.write(0x01);
        }
        fis.close();
        fos.close();
        boolean isRemoved = false;
        while (!isRemoved) {
            isRemoved = inputFile.delete();
        }
    }

    public static DataOperations loadAllElementsIntoArrayList(String inputFilePath)
            throws IOException, ClassNotFoundException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(decryptContent(inputFilePath)));
        DataOperations dataList = (DataOperations) objectInputStream.readObject();
        objectInputStream.close();
        return dataList;
    }

    private static byte[] decryptContent(String inputFilePath)
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        FileInputStream fis = new FileInputStream(inputFilePath);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(initialIV);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] encryptedContent = fis.readAllBytes();
        byte[] decryptedContent = cipher.doFinal(encryptedContent);
        fis.close();
        return decryptedContent;
    }

    public static void writeAllElementsIntoFile(DataOperations dataList, String outputFilePath)
            throws IOException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(dataList);
        objectOutputStream.flush();
        objectOutputStream.close();
        encryptContent(outputFilePath, byteArrayOutputStream.toByteArray());
    }

    private static void encryptContent(String outputFilePath, byte[] inputByteArray)
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        FileOutputStream fos = new FileOutputStream(outputFilePath);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(initialIV);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        fos.write(cipher.doFinal(inputByteArray));
        fos.close();
    }

}
