package ro.team.valor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SimpleHashTest {
    private static void printHex(byte[] hashValues, String algorithm) {
        System.out.print(algorithm + ": ");
        for(byte hashValue:hashValues) {
            System.out.printf("%02X", hashValue);
        }
        System.out.println(". The length of the hash is " + hashValues.length * 8 + ";");
    }

    private static void applyHash(byte[] inputBytes,String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] outputBytes = md.digest(inputBytes);
        printHex(outputBytes, algorithm);
    }

    public static void main(String[] args) {
        System.out.println("--------------------START--------------------");
        try {
            File inputFile = new File("src/testFile.txt");
            FileInputStream fis = new FileInputStream(inputFile);
            byte[] inputBytes = fis.readAllBytes();
            System.out.println("The hashes of the file are:");
            applyHash(inputBytes, "MD5");
            applyHash(inputBytes, "SHA-1");
            applyHash(inputBytes, "SHA-256");
            applyHash(inputBytes, "SHA-512");
        } catch(IOException exception) {
        System.out.println("Something is wrong with the file!");
        exception.printStackTrace();
        } catch(NoSuchAlgorithmException exception) {
            System.out.println("Something is wrong with the hash algorithm!");
            exception.printStackTrace();
        }
        System.out.println("---------------------STOP--------------------");
    }
}
