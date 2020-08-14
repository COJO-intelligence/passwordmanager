package main.java.login;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Login {

    private final static File LOGIN_FILE = new File("master.dat");
    private final static String DIGEST_ALGORITHM = "SHA-512";
    private final static String SALT = "my_mega_extra_salt";
    private final MessageDigest messageDigest;
    private String password;

    public Login(String password) throws NoSuchAlgorithmException {
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
    public boolean validateUserPassword() throws IOException {
        byte[] masterPassword = getHashedMasterPassword();
        byte[] userPassword = calculatePasswordHash();
        return Arrays.equals(masterPassword, userPassword);
    }

    /**
     * Sets the user password for the program, writes it in the master password file
     */
    public void setUserPassword() throws IOException, PasswordExistsException {
        if (isPasswordSet()) {
            throw new PasswordExistsException("Password is already set");
        }

        FileOutputStream fos = new FileOutputStream(LOGIN_FILE);
        fos.write(calculatePasswordHash());
        fos.close();
    }

    /**
     * Deletes the master password file, to create a new one
     *
     * @return true or false if delete worked
     */
    public boolean resetPassword() throws IOException, PasswordExistsException {
        if (!isPasswordSet())
            throw new PasswordExistsException("Password is not set");

        return LOGIN_FILE.delete();
    }

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
	 * @return true or false if password meets criteria
	 */
	public boolean checkPasswordStrength() {
		return (password.length() >= 6) &&
				(password.length() <= 20) &&
				(password.matches(".*[A-Z]+.*")) &&
				(password.matches(".*[a-z]+.*")) &&
				(password.matches(".*[0-9]+.*"));
	}

    /**
     * Reads the content of the master password file
     *
     * @return byte[] hashed password from master file or null
     */
    private byte[] getHashedMasterPassword() throws IOException {
        if (LOGIN_FILE.exists()) {
//            FileInputStream fis = new FileInputStream(LOGIN_FILE);
            byte[] fileContent = Files.readAllBytes(Paths.get(LOGIN_FILE.getPath()));
//            fis.close();
            return fileContent;
        }
        return null;
    }

    /**
     * Calculates password + salt hash
     *
     * @return byte[] hash of the password
     */
    private byte[] calculatePasswordHash() {
        String finalPassword = SALT + password + SALT;
        messageDigest.reset();
        return messageDigest.digest(finalPassword.getBytes());
    }

}


