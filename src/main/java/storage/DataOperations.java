package main.java.storage;

import java.io.Serializable;
import java.util.ArrayList;

public class DataOperations implements Serializable {

    private ArrayList<CredentialsElement> dataList;

    public ArrayList<CredentialsElement> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<CredentialsElement> dataList) {
        this.dataList = dataList;
    }

}
