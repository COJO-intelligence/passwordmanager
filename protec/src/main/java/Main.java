package main.java;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		boolean setPasswordOkay;
		boolean matchPasswordOkay;

		Scanner scanner = new Scanner(System.in);
		System.out.println("Set your password: ");
		String initPassword = scanner.next();
		
		Login login = new Login(initPassword);
		try {
			if (login.isPasswordSet())
			{
				login.resetPassword();
			}
			setPasswordOkay = login.setUserPassword();
			if (setPasswordOkay)
			{
				System.out.println("You did well!");
			}
			
		} catch (NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		catch (PasswordExistsException e) {
			e.printStackTrace();
			System.out.println("PASSWORD IS ALREADY SET");
			System.exit(2);
		}
		
		
		System.out.println("Enter your password: ");
		String managerPassword = scanner.next();
		
		Login login2 = new Login(managerPassword);
		try {
			matchPasswordOkay = login2.validateUserPassword();
			if (matchPasswordOkay)
			{
				System.out.println("You got the same passwords!");
			}
			else
			{
				System.out.println("Wrong passwords");
			}
			
		} catch (NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

	}

}
