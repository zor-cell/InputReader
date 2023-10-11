import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    //settings
    private final static String inputPath = "src/input/level3_1.in";
    private final static String outputPath = "src/output";
    private final static boolean isDebug = false;

    //DO NOT CHANGE METHOD
    public static void main(String[] args) {
        IOManager ioManager = new IOManager(inputPath, outputPath, isDebug);
        ioManager.execute();
    }

    //method for algorithm to be applied to test case
    //return type can be changed (all types get converted with toString())
    //DO NOT CHANGE PARAMETERS
    public static String solve(String line) {
        Scanner sc = new Scanner(line);

        for(int i = 0;i < 3;i++) {
            String s = sc.next();
        }

        return "0";
    }

    //function for reading the lines with a given Scanner
    //update this function according to the input requirements
    //DO NOT CHANGE METHOD PARAMETERS AND RETURN TYPE
    public static String[] getLines(Scanner scanner) throws InputMismatchException {
        int n = 0, m = 0;

        //first line
        n = scanner.nextInt();
        m = scanner.nextInt();
        scanner.nextLine(); //call required to jump to next line

        //lines after first
        String[] input = new String[n];
        for(int i = 0;i < n;i++) {
            input[i] = scanner.nextLine();
        }

        return input;
    }
}