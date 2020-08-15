package main.java.storage;

import java.io.Serializable;
import java.util.ArrayList;

public class DataOperations extends FileOperations implements Serializable {

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

}
