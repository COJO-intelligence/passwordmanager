package zxyrandom;

import java.io.IOException;

public class FieldOperationsPL {

    static private final int fieldsNumber = 5;
    static private final int existingElements = 2;
    static String[][] data = new String[existingElements][fieldsNumber];

    public static void loadElements(String inputFilePath) throws IOException {
        data = FileOperationsPL.loadAllElements(existingElements, inputFilePath);
    }

    public static void writeElements(String outputFilePath) throws IOException {
        FileOperationsPL.writeAllElements(data, outputFilePath);
    }

    public static void newElement() {

    }

    public String getDomain(String searchedElementID) {
        for (String[] datum : data) {
            if (datum[0].equals(searchedElementID)) {
                return datum[1];
            }
        }
        return null;
    }

    public void setDomain(String searchedElementID, String newContent) {
        for (int i = 0; i < data.length; i++) {
            if (data[i][0].equals(searchedElementID)) {
                data[i][1] = newContent;
            }
        }
    }

    public String getUsername(String searchedElementID) {
        for (String[] datum : data) {
            if (datum[0].equals(searchedElementID)) {
                return datum[1];
            }
        }
        return null;
    }

    public void setUsername(String searchedElementID, String newContent) {
        for (int i = 0; i < data.length; i++) {
            if (data[i][0].equals(searchedElementID)) {
                data[i][2] = newContent;
            }
        }
    }

    public String getEmail(String searchedElementID) {
        for (String[] datum : data) {
            if (datum[0].equals(searchedElementID)) {
                return datum[1];
            }
        }
        return null;
    }

    public void setEmail(String searchedElementID, String newContent) {
        for (int i = 0; i < data.length; i++) {
            if (data[i][0].equals(searchedElementID)) {
                data[i][2] = newContent;
            }
        }
    }

    public String getPassword(String searchedElementID) {
        for (String[] datum : data) {
            if (datum[0].equals(searchedElementID)) {
                return datum[1];
            }
        }
        return null;
    }

    public void setPassword(String searchedElementID, String newContent) {
        for (int i = 0; i < data.length; i++) {
            if (data[i][0].equals(searchedElementID)) {
                data[i][3] = newContent;
            }
        }
    }

    public String getAdditionalComments(String searchedElementID) {
        for (String[] datum : data) {
            if (datum[0].equals(searchedElementID)) {
                return datum[1];
            }
        }
        return null;
    }

    public void setAdditionalComments(String searchedElementID, String newContent) {
        for (int i = 0; i < data.length; i++) {
            if (data[i][0].equals(searchedElementID)) {
                data[i][4] = newContent;
            }
        }
    }

}
