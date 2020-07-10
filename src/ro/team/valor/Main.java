package ro.team.valor;


public class Main extends StoreData{

    public static void main(String[] args) {
        String inputPath = "test.csv";
        String[] initialTestArguments = {"0", "Ma-ta.com", "adi","adi@valoare.no", "ceaMaiParola-Parola", "nu"};

        setFirstApplicationRunElement(initialTestArguments, inputPath);

        String[] testArguments = {"1", "IaGaseste-oP-asta.com", "adi","adi@valoare.no", "ceaMaiParola-Parola", "nu"};
        setNewElementInfo(testArguments, "test.csv");
        testArguments = new String[]{"2", "Ma-ta.com", "adi", "adi@valoare.no", "ceaMaiParola-Parola", "nu"};
        setNewElementInfo(testArguments, "test.csv");

        String[] tests = getElementInfo(inputPath, "1");
        for(String test:tests) {
            System.out.println(test + ", ");
        }
        System.out.println("\n");


    }
}
