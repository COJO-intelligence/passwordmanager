package main.java.storage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.Serializable;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;

public class DataOperations implements Serializable  {

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

    public void writeDataListToFile() throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, KeyStoreException, IllegalBlockSizeException {
        FileOperations.writeAllElementsIntoFile(dataList);
    }

    public void loadDataListToDataOperationsObject() throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, KeyStoreException, ClassNotFoundException, IllegalBlockSizeException {
        this.dataList = FileOperations.loadAllElementsIntoArrayList();
    }

    public boolean isFilePresent() {
        return FileOperations.isFilePresent();
    }

}
