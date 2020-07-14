package main.java;

public class PasswordExistsException extends Exception{
    public PasswordExistsException(String message) {
        super(message);
    }
}
