package main.java.login;

import main.java.storage.FileOperations;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Login {

    private static final Path filePath = Paths.get(FileOperations.directoryPath, "pm.dat");
    private final static String DIGEST_ALGORITHM = "SHA-512";
    private final static String SALT = "my_mega_extra_salt";
    private final MessageDigest messageDigest;
    private char[] password;

    public Login(char[] password) throws NoSuchAlgorithmException {
        super();
        this.password = password;
        messageDigest = MessageDigest.getInstance(DIGEST_ALGORITHM);
    }

    public Login() throws NoSuchAlgorithmException {
        super();
        messageDigest = MessageDigest.getInstance(DIGEST_ALGORITHM);
    }

    /**
     * Validates the user input password to be the same as original
     *
     * @return true or false if the password's match
     */
    public boolean validateUserPassword() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] masterPassword = getHashedMasterPassword();
        byte[] userPassword = calculatePasswordHash();
        return Arrays.equals(masterPassword, userPassword);
    }

    /**
     * Sets the user password for the program, writes it in the master password file
     */
    public void setUserPassword() throws IOException, PasswordExistsException, InvalidKeySpecException, NoSuchAlgorithmException {
        if (isPasswordSet()) {
            throw new PasswordExistsException("Password is already set");
        }
        FileOutputStream fos = new FileOutputStream(String.valueOf(filePath));
        fos.write(calculatePasswordHash());
        fos.close();
    }

//    /**
//     * Deletes the master password file, to create a new one
//     *
//     * @return true or false if delete worked
//     */
//    public boolean resetPassword() throws IOException, PasswordExistsException {
//        if (!isPasswordSet())
//            throw new PasswordExistsException("Password is not set");
//
//        return LOGIN_FILE.delete();
//    }

    /**
     * Check if the master password file exists and contains a password
     *
     * @return true or false if exists
     */
    public boolean isPasswordSet() throws IOException {
        byte[] masterPass = getHashedMasterPassword();
        if (masterPass == null)
            return false;
        return masterPass.length == messageDigest.getDigestLength();
    }

    /**
     * Checks password to be at least six characters long, contain at least one letter and have at least one digit
     *
     * @return true or false if password meets criteria
     */
    public boolean checkPasswordStrength() {
        return (password.length >= 6) &&
                (password.length <= 20) &&
                Pattern.matches((".*[A-Z]+.*"), CharBuffer.wrap(password)) &&
                Pattern.matches((".*[a-z]+.*"), CharBuffer.wrap(password)) &&
                Pattern.matches((".*[0-9]+.*"), CharBuffer.wrap(password));
    }

    /**
     * Reads the content of the master password file
     *
     * @return byte[] hashed password from master file or null
     */
    private byte[] getHashedMasterPassword() throws IOException {
        if (Files.isRegularFile(filePath)) {
            return Files.readAllBytes(filePath);
        }
        return null;
    }

    /**
     * Calculates password hash based on key derivation function
     *
     * @return byte[] hash
     */
    private byte[] calculatePasswordHash() throws InvalidKeySpecException, NoSuchAlgorithmException {
        messageDigest.reset();
        return messageDigest.digest(derivePassword());
    }

    private byte[] derivePassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, SALT.getBytes(), 128, 1024);
        emptyPasswordArray();
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] derivative = skf.generateSecret(spec).getEncoded();
        spec.clearPassword();
        return derivative;
    }

    private void emptyPasswordArray()
    {
        Arrays.fill(this.password, Character.MIN_VALUE);
        this.password = null;
    }

}


