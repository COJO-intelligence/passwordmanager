package main.java;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean setPasswordOkay;
		boolean matchPasswordOkay;

//		sunt jmek3r
		Scanner scanner = new Scanner(System.in);
		System.out.println("Set your password: ");
		String initPassword = scanner.next();
		
		Login login = new Login(initPassword);
		try {
			setPasswordOkay = login.setUserPassword();
			if (setPasswordOkay)
			{
				System.out.println("You did well!");
			}
			
		} catch (NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
