package login;

import launcher.MainUI;
import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONObject;
import storage.APIRequests;
import storage.CryptographicOperations;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.CharBuffer;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.regex.Pattern;

public class Login {

    public Login() {

    }

    public boolean isValidEmailAddress(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public boolean validateTextField(String input) {
        return (input.length() < 8 || input.length() > 60);
    }

    public boolean checkPasswords(String password, String retypedPassword) {
        return (password.equals(retypedPassword));
    }

    public boolean checkPasswordStrength(String password) {
        return (password.length() >= 8) &&
                (password.length() <= 30) &&
                Pattern.matches((".*[A-Z]+.*"), CharBuffer.wrap(password)) &&
                Pattern.matches((".*[a-z]+.*"), CharBuffer.wrap(password)) &&
                Pattern.matches((".*[0-9]+.*"), CharBuffer.wrap(password));
    }

    public int register(String email, String firstName, String lastName, String password) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        return APIRequests.register(email, firstName, lastName, password);
    }


    public String getMFAKey() throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, UnrecoverableEntryException, CertificateException, NoSuchAlgorithmException, BadPaddingException, KeyStoreException, InvalidKeyException, InvalidKeySpecException, KeyManagementException {
        String mfaKeyResponse = APIRequests.getMFAKey();
        JSONObject jsonObject = new JSONObject(mfaKeyResponse);
        byte[] mfaKey = CryptographicOperations.decryptContent(Base64.getDecoder().decode(jsonObject.getString("mfaKey")),
                CryptographicOperations.getAESKeyFromPassword(MainUI.user.getPassword().toCharArray()));
        return new String(mfaKey);
    }

}


