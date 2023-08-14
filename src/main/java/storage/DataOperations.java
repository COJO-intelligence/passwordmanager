package storage;

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

    public void addNewElement(CredentialsElement credentialsElement) {
        dataList.add(credentialsElement);
    }

}
