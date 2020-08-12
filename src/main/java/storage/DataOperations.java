package main.java.storage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.Serializable;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;

//TODO remove inputFilePath
//TODO MAYBE sort
public class DataOperations extends FileOperations implements Serializable  {

    private ArrayList<CredentialsElement> dataList;

    public ArrayList<CredentialsElement> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<CredentialsElement> dataList) {
        this.dataList = dataList;
    }

    public void addNewElement(CredentialsElement credentialsElement) {
        dataList.add(credentialsElement);
    }

    public void writeDataListToFile(String filePath) throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, KeyStoreException, IllegalBlockSizeException {
        FileOperations.writeAllElementsIntoFile(dataList, filePath);
    }

    public void loadDataListToDataOperationsObject(String filePath) throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, KeyStoreException, ClassNotFoundException, IllegalBlockSizeException {
        this.dataList = FileOperations.loadAllElementsIntoArrayList(filePath);
    }


}
