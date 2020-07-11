package ro.team.valor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StoreData {
    //TODO DEFINE INTERACTIONS!
    //aiming for a cvs file https://stackabuse.com/reading-and-writing-csvs-in-java/
    //elementID, Domain, Username, Email, Password, AdditionalComments
    final private static String[] defaultData = {"-", "-", "-", "-", "-", "-"};

    public static void setFirstApplicationRunElement(String[] arguments, String outputFilePath) {
        FileWriter csvWriter = null;
        try {
            csvWriter = new FileWriter(outputFilePath);
            for (String argument : arguments) {
                csvWriter.write(argument);
                csvWriter.write(",");
            }
            csvWriter.write("\n");
            csvWriter.flush();
        } catch (IOException e) {
            System.out.println("Vezi ca sunt probleme la fisier!");
        } finally {
            try {
                if (csvWriter != null) {
                    csvWriter.close();
                }
            } catch (IOException e) {
                System.out.println("Vezi ca sunt probleme la fisier! Ce nu intelegi!?");
            }
        }
    }

    public static String[] getElementInfo(String inputFilePath, String elementID) {
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(inputFilePath));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                if (data[0].equals(elementID)) {
                    return data;
                }
            }
        } catch (IOException e) {
            System.out.println("Vezi ca sunt probleme la fisier!");
        } finally {
            try {
                if (csvReader != null) {
                    csvReader.close();
                }
            } catch (IOException e) {
                System.out.println("Vezi ca sunt probleme la fisier! Ce nu intelegi!?");
            }
        }
        return defaultData;
    }

    public static void setNewElementInfo(String[] arguments, String outputFilePath) {
        FileWriter csvWriter = null;
        try {
            csvWriter = new FileWriter(outputFilePath, true);
            for (String argument : arguments) {
                csvWriter.write(argument);
                csvWriter.write(",");
            }
            csvWriter.write("\n");
            csvWriter.flush();
        } catch (IOException e) {
            System.out.println("Vezi ca sunt probleme la fisier!");
        } finally {
            try {
                if (csvWriter != null) {
                    csvWriter.close();
                }
            } catch (IOException e) {
                System.out.println("Vezi ca sunt probleme la fisier! Ce nu intelegi!?");
            }
        }
    }

    /*
    public void setExistingElementInfo(String[] arguments, String outputFilePath) throws IOException {
        //TODO edit existing element fields
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
        //TODO
    }
    */


}
