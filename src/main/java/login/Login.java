package main.java.login;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Login {
	
	private String password;
	private final static File LOGIN_FILE = new File("master.dat");
	private final static String DIGEST_ALGORITHM = "SHA-512";
	private final static String SALT = "my_mega_extra_salt";
	private MessageDigest messageDigest;
	
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
	 * @return true or false if the password's match
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public boolean validateUserPassword() throws NoSuchAlgorithmException, IOException
	{
		byte[] masterPassword = getHashedMasterPassword();
		byte[] userPassword = calculatePasswordHash();
		return Arrays.equals(masterPassword, userPassword);
	}

	/**
	 * Sets the user password for the program, writes it in the master password file
	 * @return true of false if the password was set
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws PasswordExistsException
	 */
	public boolean setUserPassword() throws NoSuchAlgorithmException, IOException, PasswordExistsException {
		if(isPasswordSet())
			throw new PasswordExistsException("Password is already set");

		FileOutputStream fos = new FileOutputStream(LOGIN_FILE);
		fos.write(calculatePasswordHash());
		fos.close();
		// TODO validation method that this shit worked
		return true;
	}

	/**
	 * Deletes the master password file, to create a new one
	 * @return true or false if delete worked
	 * @throws IOException
	 * @throws PasswordExistsException
	 */
	public boolean resetPassword() throws IOException, PasswordExistsException {
		if(!isPasswordSet())
			throw new PasswordExistsException("Password is not set");

		return LOGIN_FILE.delete();
	}

	/**
	 * Reads the content of the master password file
	 * @return hashed password from master file or null
	 * @throws IOException
	 */
	private byte[] getHashedMasterPassword() throws IOException
	{
		if (LOGIN_FILE.exists())
		{
			FileInputStream fis = new FileInputStream(LOGIN_FILE);
			byte[] fileContent =  fis.readAllBytes();
			fis.close();
			return fileContent;
		}
		return null;
	}

	/**
	 * Calculates password + salt hash
	 * @return byte[] hash of the password
	 */
	private byte[] calculatePasswordHash()
	{
		String finalPassword = SALT + password + SALT;
		messageDigest.reset();
		return messageDigest.digest(finalPassword.getBytes());
	}

	/**
	 * Check if the master password file exists and contains a password
	 * @return true or false if exists
	 * @throws NoSuchAlgorithmException
	 */
	public boolean isPasswordSet() throws IOException {
		byte[] masterPass = getHashedMasterPassword();
		if (masterPass == null)
			return false;
		return masterPass.length == messageDigest.getDigestLength();
	}

//	public void setPassword(String password) {
//		this.password = password;
//	}
}
