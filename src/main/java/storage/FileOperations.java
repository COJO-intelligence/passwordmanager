package storage;

import launcher.MainUI;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;

public class FileOperations {


    public ArrayList<CredentialsElement> loadAllElementsIntoArrayList()
            throws IOException, ClassNotFoundException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidAlgorithmParameterException, CertificateException, KeyStoreException, KeyManagementException, InvalidKeySpecException {

        String userData1 = APIRequests.getUserContent();

        if(userData1.equals("[]")) {
            CredentialsElement firstElement = new CredentialsElement(9,"Welcome!", "", "", "", "", "");
            ArrayList<CredentialsElement> dataList = new ArrayList<>();
            dataList.add(firstElement);
            return dataList;
        } else {
            String content = APIRequests.getUserContent();
            JSONArray jsonArray = new JSONArray(content);
            JSONObject jsonObject = (JSONObject) jsonArray.get(jsonArray.length()-1);
            content = jsonObject.getString("encrypted_data");
            System.out.println(content);
            //content = content.replace(" ", "+");
            byte[] decryptedContent = CryptographicOperations.decryptContent(Base64.getDecoder().decode(content),
                    CryptographicOperations.getAESKeyFromPassword(MainUI.user.getPassword().toCharArray()));
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(decryptedContent));
            ArrayList<CredentialsElement> dataList = (ArrayList<CredentialsElement>) objectInputStream.readObject();
            objectInputStream.close();
            return dataList;
        }

    }

    public void writeAllElementsIntoFile(ArrayList<CredentialsElement> dataList)
            throws IOException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidAlgorithmParameterException, CertificateException, KeyStoreException, InvalidKeySpecException, KeyManagementException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(dataList);
        objectOutputStream.flush();
        objectOutputStream.close();
        byte[] resultC = CryptographicOperations.encryptContent(byteArrayOutputStream.toByteArray(),
                CryptographicOperations.getAESKeyFromPassword(MainUI.user.getPassword().toCharArray()));
        resultC = Base64.getEncoder().encode(resultC);
        APIRequests.setUserContent(new String(resultC));
    }

}
