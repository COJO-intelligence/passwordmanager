package main.java.storage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.Serializable;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;

public class DataOperations implements Serializable {

    private ArrayList<CredentialsElement> dataList;

    public ArrayList<CredentialsElement> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<CredentialsElement> dataList) {
        this.dataList = dataList;
    }

    /**
     * Adds a new CredentialsElement to the list.
     *
     * @param credentialsElement the inputs for the new element
     */
    public void addNewElement(CredentialsElement credentialsElement) {
        dataList.add(credentialsElement);
    }

    /**
     * Calls the static write to file method from FileOperations
     */
    public void writeDataListToFile() throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, KeyStoreException, IllegalBlockSizeException {
        FileOperations.writeAllElementsIntoFile(dataList);
    }

    /**
     * Calls the static load from file method from FileOperations
     */
    public void loadDataListToDataOperationsObject() throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, UnrecoverableEntryException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, KeyStoreException, ClassNotFoundException, IllegalBlockSizeException {
        this.dataList = FileOperations.loadAllElementsIntoArrayList();
    }

    /**
     * Calls the static check if the file exists method from FileOperations
     *
     * @return true or false if the file exists
     */
    public boolean isFilePresent() {
        return FileOperations.isFilePresent();
    }

}
