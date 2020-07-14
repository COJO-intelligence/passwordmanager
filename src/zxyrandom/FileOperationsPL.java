package zxyrandom;

import java.io.*;

public class FileOperationsPL {
    //aiming for a cvs file https://stackabuse.com/reading-and-writing-csvs-in-java/
    //elementID, Domain, Username, Email, Password, AdditionalComments
    //final private static String[] defaultData = {"-", "-", "-", "-", "-", "-"};
    static private final int fieldsNumber = 7;
    //static private final int existingElements = 0;

    public static void createFirstApplicationRunElement(String[] arguments, String outputFilePath) throws IOException {
        FileWriter csvWriter = new FileWriter(outputFilePath);
        for (String argument : arguments) {
            csvWriter.write(argument);
            csvWriter.write(",");
        }
        csvWriter.write("\n");
        csvWriter.flush();
        csvWriter.close();
    }

    public static String[][] loadAllElements(int existingElements, String inputFilePath) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(inputFilePath));
        String row;
        String[][] data = new String[existingElements][fieldsNumber];
        int i = 0;
        while ((row = csvReader.readLine()) != null) {
            String[] intermediateData = row.split(",");
            System.arraycopy(intermediateData, 0, data[i], 0, intermediateData.length);
            if(data[i][0] == null) {
                break;
            }
            i++;
        }
        csvReader.close();
        return data;
    }

    public static void writeAllElements(String[][] data, String outputFilePath) throws IOException {
        FileWriter csvWriter = new FileWriter(outputFilePath);
        for (String[] datum : data) {
            for (int j = 0; j < data[0].length; j++) {
                csvWriter.write(datum[j]);
                csvWriter.write(",");
            }
            csvWriter.write("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }

    /*
    public static void setNewElementInfo(String[] arguments, String outputFilePath) throws IOException {
        FileWriter csvWriter = new FileWriter(outputFilePath, true);
        for (String argument : arguments) {
            csvWriter.write(argument);
            csvWriter.write(",");
        }
        csvWriter.write("\n");
        csvWriter.flush();
        csvWriter.close();
    }

    public static String[] getSpecificElementInfo(String inputFilePath, String elementID) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(inputFilePath));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            if (data[0].equals(elementID)) {
                csvReader.close();
                return data;
            }
        }
        csvReader.close();
        return defaultData;
    }

    */

    /*
    public void setExistingElementInfo(String[] arguments, String outputFilePath) throws IOException {

        BufferedReader csvReader = new BufferedReader(new FileReader(outputFilePath));
        String row;
        boolean elementFound = false;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            if(data[0].equals(arguments[0])) {
                setNewElementInfo(arguments, outputFilePath);
            }
        }
        csvReader.close();
    }

    public void deleteExistingElement(String elementID, String outputFilePath) {

    }
    */


}
