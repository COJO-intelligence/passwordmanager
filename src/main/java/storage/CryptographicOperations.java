package storage;

import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class CryptographicOperations {

    public static byte[] decryptContent(byte[] encryptedContent, SecretKey secretKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        AlgorithmParameterSpec gcmIv = new GCMParameterSpec(128, encryptedContent, 0, 12);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmIv);
        return cipher.doFinal(encryptedContent, 12, encryptedContent.length - 12);
    }

    public static byte[] encryptContent(byte[] inputByteArray, SecretKey secretKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        byte[] iv = generateIV();
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);
        byte [] encryptedData = cipher.doFinal(inputByteArray);
        ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + encryptedData.length);
        byteBuffer.put(iv);
        byteBuffer.put(encryptedData);
        return byteBuffer.array();
    }

    private static byte[] generateIV() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[12];
        secureRandom.nextBytes(iv);
        return iv;
    }

    public static String hashPasswordBcrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static byte[] getHash(char[] userPassword)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] hashValue;
        byte[] salt = "passwordManager".getBytes();
        PBEKeySpec key = new PBEKeySpec(userPassword, salt, 500, 128);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        hashValue = secretKeyFactory.generateSecret(key).getEncoded();
        return hashValue;
    }

    public static SecretKey getAESKeyFromPassword(char[] password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = "passwordManager".getBytes();
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

}
