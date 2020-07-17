package test.java.login;

import main.java.login.Login;
import main.java.login.PasswordExistsException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    Login loginTest = new Login("gigel");

    LoginTest() throws NoSuchAlgorithmException {
    }

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws NoSuchAlgorithmException, IOException, PasswordExistsException {
        if (loginTest.isPasswordSet())
            loginTest.resetPassword();

        loginTest.setUserPassword();

    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void validateUserPassword() throws IOException, NoSuchAlgorithmException {
        assertTrue(loginTest.validateUserPassword());
    }

    @org.junit.jupiter.api.Test
    void setUserPassword() throws IOException, PasswordExistsException, NoSuchAlgorithmException {
        loginTest.resetPassword();
        assertEquals(true, loginTest.setUserPassword());
    }

    @org.junit.jupiter.api.Test
    void resetPassword() throws IOException, PasswordExistsException {
        assertEquals(true, loginTest.resetPassword());
    }

    @org.junit.jupiter.api.Test
    void isPasswordSet() throws IOException, PasswordExistsException, NoSuchAlgorithmException {
        loginTest.resetPassword();
        assertEquals(false, loginTest.isPasswordSet());
        loginTest.setUserPassword();
        assertEquals(true, loginTest.isPasswordSet());
    }
}