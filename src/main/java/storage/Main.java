package main.java.storage;

import main.java.manager.KeyManager;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static void printList(DataOperations dataList, String testingMethod) {
        System.out.println();
        System.out.println(testingMethod + ":");
        ArrayList<CredentialsElement> testList = dataList.getDataList();
        for(CredentialsElement data : testList) {
            System.out.print(data.getElementID() + ", " + data.getDomain() + ", " + data.getUsername() + ", " + data.getEmail() + ", " + data.getPassword() + ", "+ data.getAdditionalComments());
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        /*

        KeyManager keymanager = new KeyManager();
        keymanager.setSecretKey();

        String filePath = "test.encrypt";
        try {
            FileOperations.createFile(filePath);
        } catch (FileAlreadyExistsException e) {
            System.out.println("File already exists!");
        }





        CredentialsElement element1 = new CredentialsElement(1,"1.com", "1", "1@1.com", "111", "1comment");
        CredentialsElement element2 = new CredentialsElement(2,"2.com", "2", "2@2.com", "222", "2comment");
        CredentialsElement element3 = new CredentialsElement(3,"3.com", "3", "3@3.com", "333", "3comment");
        CredentialsElement element4 = new CredentialsElement(4,"4.com", "4", "4@4.com", "444", "4comment");
        CredentialsElement element5 = new CredentialsElement(5,"5.com", "5", "5@5.com", "555", "5comment");

        ArrayList<CredentialsElement> testList = new ArrayList<>();
        testList.add(element1);
        testList.add(element2);
        testList.add(element3);

        DataOperations dataList = new DataOperations();
        dataList.setDataList(testList);
        printList(dataList,"Data List Set");

        dataList.getDataList().add(element4);
        FileOperations.writeAllElementsIntoFile(dataList, filePath);
        printList(dataList,"Adding One Element + Printing to File");

        dataList.getDataList().add(1, element5);
        printList(dataList,"Adding One Element to Position");

        dataList.getDataList().remove(2);
        FileOperations.writeAllElementsIntoFile(dataList, filePath);
        printList(dataList,"Removing One Element + Printing to File");

        dataList = FileOperations.loadAllElementsIntoArrayList(filePath);
        printList(dataList, "File Content");

        dataList.getDataList().get(1).setElementID(10);
        printList(dataList, "New ElementID");

        dataList.getDataList().get(0).setDomain("New Domain");
        printList(dataList, "New Domain");

        dataList.getDataList().get(2).setUsername("New Username");
        printList(dataList, "New Username");

        dataList.getDataList().get(1).setEmail("New Email");
        printList(dataList, "New Email");

        dataList.getDataList().get(2).setPassword("New Password");
        printList(dataList, "New Password");

        dataList.getDataList().get(1).setAdditionalComments("New AdditionalComments");
        printList(dataList, "New AdditionalComments");

        FileOperations.writeAllElementsIntoFile(dataList, filePath);
        printList(dataList,"Printing to File");

        dataList.getDataList().clear();
        printList(dataList,"Clear Content");

        dataList = FileOperations.loadAllElementsIntoArrayList(filePath);
        printList(dataList, "File Content");

        FileOperations.destroyFile(filePath);
        System.out.println("File Destroyed");

        FileOperations.writeAllElementsIntoFile(dataList, filePath);
        printList(dataList,"Printing to File");

         */
    }

}
