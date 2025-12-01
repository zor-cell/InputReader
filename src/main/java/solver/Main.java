package solver;

import config.Config;
import config.ConfigLoader;
import io.IOManager;
import io.IOUtils;
import log.CustomLogger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static Logger log;

    /**
     * Do not change this method. Method Loads the config and initializes the IOManager.
     */
    public static void main(String[] args) {
        try {
            Config config = ConfigLoader.loadConfig("config.xml");

            CustomLogger.createLogger(config);
            log = CustomLogger.getLogger();

            IOManager ioManager = new IOManager(config);
            ioManager.initialize();
            ioManager.execute();
        } catch (Exception e) {
            log.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * The verification method to read content from a given {@code input} to verify whether a solution from a given {@code output} was correct.
     * This method is only applied if a visualizer is provided, which can verify correctness.
     * DO NOT CHANGE PARAMETERS OR RETURN TYPE
     */
    public static void verify(Scanner input, Scanner output, VisualizerVerifier verifier) throws IOException {
        int maxRuns = input.nextInt();
        input.nextLine(); //skip linebreak

        for(int run = 0;run < maxRuns;run++) {
            String inputLine = input.nextLine();
            String outputLine = output.nextLine();

            verifier.checkValidity(run, inputLine, outputLine);
        }
    }


    /**
     * The solving method to read from an input file via {@code reader} and write the solution to an output file via {@code writer}.
     * This method is applied to all input files.
     * DO NOT CHANGE PARAMETERS OR RETURN TYPE
     */
    public static void solve(Scanner reader, FileWriter writer) throws IOException {
        //read lines from input
        //and write to file using: writer.write(result + "\n");
        int maxRuns = reader.nextInt();
        reader.nextLine(); //skip linebreak

        for(int run = 0;run < maxRuns;run++) {
            log.fine("Run " + run);

            //compute sum of each line
            List<Integer> list = IOUtils.readList(reader, " ", Integer::parseInt);
            int sum = list.stream().mapToInt(Integer::intValue).sum();

            //write result to file
            writer.write(sum + "\n");
        }
    }
}
