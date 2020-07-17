package main.java.login;

public class PasswordExistsException extends Exception{
    public PasswordExistsException(String message) {
        super(message);
    }
}
