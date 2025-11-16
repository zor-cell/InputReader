package solver;

import config.Config;
import config.ConfigLoader;
import io.IOManager;
import log.CustomLogger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static Logger log;

    /**
     * Do not change this method. Method Loads the config.Config and initializes the io.IOManager.
     * @param args
     */
    public static void main(String[] args) {
        Config config = ConfigLoader.loadConfig("resources/config.xml");

        CustomLogger.createLogger(config);
        log = CustomLogger.getLogger();

        IOManager ioManager = new IOManager(config);
        ioManager.initialize();
        ioManager.execute();
    }


    /**
     * method for reading input from input file and writing solution to output file
     * gets applied to all given input files
     * example of a program to output line length of each line
     * DO NOT CHANGE PARAMETERS OR RETURN TYPE
     *
     * @param reader
     * @param writer
     * @throws IOException
     */
    public static void solve(Scanner reader, FileWriter writer) throws IOException {
        //read lines from input
        //and write to file using: writer.write(result + "\n");
        int maxRuns = reader.nextInt();
        reader.nextLine(); //skip linebreak

        int result = 0;
        for(int run = 0;run < maxRuns;run++) {
            log.fine("Run " + run);

            String line = reader.nextLine();
            result = line.length();

            //write result to file
            writer.write(result + "\n");
        }
    }
}
