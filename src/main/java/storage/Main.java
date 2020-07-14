package main.java.storage;


import zxyrandom.FileOperationsPL;

import java.util.ArrayList;

public class Main extends FileOperationsPL {

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
        String filePath = "test.csv";
        FileOperations.createFile(filePath);


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
        printList(dataList,"Simple Print");

        dataList.getDataList().add(element4);
        dataList.writeDataListToFile(filePath);
        printList(dataList,"Adding One Element + Printing to File");

        dataList.getDataList().set(1, element5);
        printList(dataList,"Adding One Element to Position");

        dataList.getDataList().remove(2);
        dataList.writeDataListToFile(filePath);
        printList(dataList,"Removing One Element + Printing to File");

        dataList.getDataList().clear();
        printList(dataList,"Clear Content");

        dataList.initializeDataListFromFile(filePath);
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

        dataList.writeDataListToFile(filePath);
        printList(dataList,"Printing to File");

        dataList.getDataList().clear();
        printList(dataList,"Clear Content");

        dataList.initializeDataListFromFile(filePath);
        printList(dataList, "File Content");



        byte[] key = {
                0x2A, 0x4D, 0x61, 0x73,
                0x74, 0x65, 0x72, 0x20,
                0x49, 0x53, 0x4D, 0x20,
                0x32, 0x30, 0x31, 0x37
        };
        byte[] initialIV = new byte[key.length];
        for(int i = 0; i < key.length; i++){
            initialIV[i] = 1;
        }
        String inputFile = "test.csv";
        String intermediateFile = "test.encrypt";
        String outputFile = "test.csv";

        System.out.println("Vezi ca bag criptarea!");
        FileOperations.encryptFile(inputFile, intermediateFile, key, initialIV);
        System.out.println("Vezi ca bag decriptarea!");
        FileOperations.decryptFile(intermediateFile, outputFile, key, initialIV);
    }

}
