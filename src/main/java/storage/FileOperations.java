package main.java.storage;

import java.io.*;
import java.util.ArrayList;

public class FileOperations {

    public static void createFile(String outputFilePath) throws Exception {
        FileWriter csvWrite = new FileWriter(outputFilePath);
        csvWrite.flush();
        csvWrite.close();
    }

    public static ArrayList<CredentialsElement> loadAllElementsIntoList(String inputFilePath) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(inputFilePath));
        ArrayList<CredentialsElement> dataList = new ArrayList<>();
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] intermediateData = row.split(",");
            int elementID = Integer.parseInt(intermediateData[0]);
            CredentialsElement credentialsElement = new CredentialsElement(elementID, intermediateData[1], intermediateData[2], intermediateData[3], intermediateData[4], intermediateData[5]);
            dataList.add(credentialsElement);
        }
        csvReader.close();
        return dataList;
    }

    public static void writeAllElementsIntoFile(ArrayList<CredentialsElement> dataList,String outputFilePath) throws IOException {
        FileWriter csvWriter = new FileWriter(outputFilePath);
        for(CredentialsElement data : dataList) {
            csvWriter.write(Integer.toString(data.getElementID()));
            csvWriter.write(",");
            csvWriter.write(data.getDomain());
            csvWriter.write(",");
            csvWriter.write(data.getUsername());
            csvWriter.write(",");
            csvWriter.write(data.getEmail());
            csvWriter.write(",");
            csvWriter.write(data.getPassword());
            csvWriter.write(",");
            csvWriter.write(data.getAdditionalComments());
            csvWriter.write("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }

    public void encryptFile(String inputFilePath, String outputFilePath) {

    }

    public void decryptFile(String inputFilePath) {

    }

}
