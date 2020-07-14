package main.java.storage;

import java.io.IOException;
import java.util.ArrayList;

public class DataOperations {

    private ArrayList<CredentialsElement> dataList;

    public ArrayList<CredentialsElement> getDataList() {
        return this.dataList;
    }

    public void setDataList(ArrayList<CredentialsElement> dataList) {
        this.dataList = dataList;
    }

    public void initializeDataListFromFile(String inputFilePath) throws IOException {
        this.dataList = FileOperations.loadAllElementsIntoList(inputFilePath);
    }

    public void writeDataListToFile(String outputFilePath) throws IOException {
        FileOperations.writeAllElementsIntoFile(this.dataList, outputFilePath);
    }

}
