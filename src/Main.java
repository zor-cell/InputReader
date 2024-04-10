import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    //DO NOT CHANGE METHOD
    public static void main(String[] args) {
        Config config = ConfigLoader.loadConfig("config.xml");

        IOManager ioManager = new IOManager(config.getInputPath(), config.getOutputPath(), config.isDebug());
        ioManager.execute();
    }

    //method for reading input from input file and writing solution to output file
    //gets applied to all given input files
    //example of a program to output line length of each line
    //DO NOT CHANGE PARAMETERS OR RETURN TYPE
    public static void solve(Scanner reader, FileWriter writer) throws IOException {
        //read lines from input
        //and write to file using: writer.write(result + "\n");
        int n = reader.nextInt();
        reader.nextLine(); //skip linebreak

        int result = 0;
        for(int i = 0;i < n;i++) {
            String line = reader.nextLine();
            result = line.length();

            //write result to file
            writer.write(result + "\n");
        }
    }
}
