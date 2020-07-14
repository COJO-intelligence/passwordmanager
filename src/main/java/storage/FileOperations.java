package main.java.storage;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class FileOperations {

    public static void createFile(String outputFilePath) throws Exception {
        FileWriter csvWrite = new FileWriter(outputFilePath);
        csvWrite.flush();
        csvWrite.close();
    }

    public static ArrayList<CredentialsElement> loadAllElementsIntoList(String inputFilePath) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(inputFilePath));
        ArrayList<CredentialsElement> dataList = new ArrayList<>();
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] intermediateData = row.split(",");
            int elementID = Integer.parseInt(intermediateData[0]);
            CredentialsElement credentialsElement = new CredentialsElement(elementID, intermediateData[1], intermediateData[2], intermediateData[3], intermediateData[4], intermediateData[5]);
            dataList.add(credentialsElement);
        }
        csvReader.close();
        return dataList;
    }

    public static void writeAllElementsIntoFile(ArrayList<CredentialsElement> dataList,String outputFilePath) throws IOException {
        FileWriter csvWriter = new FileWriter(outputFilePath);
        for(CredentialsElement data : dataList) {
            csvWriter.write(Integer.toString(data.getElementID()));
            csvWriter.write(",");
            csvWriter.write(data.getDomain());
            csvWriter.write(",");
            csvWriter.write(data.getUsername());
            csvWriter.write(",");
            csvWriter.write(data.getEmail());
            csvWriter.write(",");
            csvWriter.write(data.getPassword());
            csvWriter.write(",");
            csvWriter.write(data.getAdditionalComments());
            csvWriter.write("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }

    public static void encryptFile(String inputFilePath, String outputFilePath, byte[] key, byte[] initialIV)
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, ShortBufferException, BadPaddingException, IllegalBlockSizeException {
        File inputFile = new File(inputFilePath);
        File outputFile = new File (outputFilePath);
        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(outputFile);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(initialIV);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] buffer = new byte[cipher.getBlockSize()];
        byte[] encBlock = null;
        int noBytes;
        while ((noBytes = fis.read(buffer)) != -1) {
            encBlock = new byte[cipher.getOutputSize(noBytes)];
            int noEncBytes = cipher.update(buffer, 0, noBytes, encBlock);
            fos.write(encBlock, 0, noEncBytes);
        }
        assert encBlock != null;
        int noLastBytes = cipher.doFinal(encBlock, 0);
        fos.write(encBlock, 0, noLastBytes);
        fos.close();

        fos = new FileOutputStream(inputFile);
        while(fis.read() != -1) {
            fos.write(0x01);
        }
        fis.close();
        fos.close();
        boolean isRemoved = false;
        while(!isRemoved) {
            isRemoved = inputFile.delete();
        }
        //inputFile.deleteOnExit();
    }

    public static void decryptFile(String inputFilePath, String outputFilePath, byte[] key, byte[] initialIV)
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, ShortBufferException, BadPaddingException, IllegalBlockSizeException {
        File inputFile = new File(inputFilePath);
        File outputFile = new File (outputFilePath);
        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(outputFile);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(initialIV);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] buffer = new byte[cipher.getBlockSize()];
        byte[] encBlock = null;
        int noBytes;
        while ((noBytes = fis.read(buffer)) != -1) {
            encBlock = new byte[cipher.getOutputSize(noBytes)];
            int noEncBytes = cipher.update(buffer, 0, noBytes, encBlock);
            fos.write(encBlock, 0, noEncBytes);
        }
        assert encBlock != null;
        int noLastBytes = cipher.doFinal(encBlock, 0);
        fos.write(encBlock, 0, noLastBytes);
        fos.close();

        fos = new FileOutputStream(inputFile);
        while(fis.read() != -1) {
            fos.write(0x01);
        }
        fis.close();
        fos.close();
        boolean isRemoved = false;
        while(!isRemoved) {
            isRemoved = inputFile.delete();
        }
        //inputFile.deleteOnExit();
    }

}
