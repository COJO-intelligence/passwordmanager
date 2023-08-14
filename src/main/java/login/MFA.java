package login;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import de.taimos.totp.TOTP;
import launcher.MainUI;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import storage.APIRequests;
import storage.CryptographicOperations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class MFA {

    public String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    public String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
        return "otpauth://totp/"
                + URLEncoder.encode(issuer + ":" + account, StandardCharsets.UTF_8).replace("+", "%20")
                + "?secret=" + URLEncoder.encode(secretKey, StandardCharsets.UTF_8).replace("+", "%20")
                + "&issuer=" + URLEncoder.encode(issuer, StandardCharsets.UTF_8).replace("+", "%20");

    }

    public String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        String result = base32.encodeToString(bytes);
        try {
            //FileOutputStream fileOutputStream = new FileOutputStream("mfaKey.txt");
            byte[] resultC = CryptographicOperations.encryptContent(result.getBytes(),
                    CryptographicOperations.getAESKeyFromPassword(MainUI.user.getPassword().toCharArray()));
            //.write(resultC);
            resultC = Base64.getEncoder().encode(resultC);
            if(APIRequests.setMFAKey(new String(resultC)) != 201) {
                throw new Exception("Error setting MFA KEY");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public byte[] getQrImage(String barCodeUrl) throws IOException, WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(barCodeUrl, BarcodeFormat.QR_CODE,
                400, 400);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "png", byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return byteArray;
    }

}
