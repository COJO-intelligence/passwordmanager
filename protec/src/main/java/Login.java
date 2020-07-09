package main.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Login {
	
	private String password;
	private final static File loginFile = new File("master.dat");
//	private final static Scanner scanner = new Scanner(System.in);
	
	public Login(String password) {
		super();
		this.password = password;
	}
	
	public boolean validateUserPassword() throws NoSuchAlgorithmException, IOException
	{
		
		byte[] masterPassword = getHashedMasterPassword();
		byte[] userPassword = calculatePasswordHash();

		return Arrays.equals(masterPassword, userPassword);
	}
	
	public boolean setUserPassword() throws NoSuchAlgorithmException, IOException
	{
		//TODO just for testing now
		if (loginFile.exists())
			loginFile.delete();
		
		FileOutputStream fos = new FileOutputStream(loginFile);
		fos.write(calculatePasswordHash());
		fos.close();
		// TODO validation method that this shit worked
		return true;
	}
	
	private byte[] getHashedMasterPassword() throws IOException
	{
		if (loginFile.exists())
		{
			FileInputStream fis = new FileInputStream(loginFile);
			byte[] fileContent =  fis.readAllBytes();
			fis.close();
			return fileContent;
		}
		return null;
	}
	
	private byte[] calculatePasswordHash() throws NoSuchAlgorithmException
	{
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
		return messageDigest.digest(password.getBytes());
	}

}
