import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.Arrays;

public class IOManager {
    private File[] inputFiles;
    private File[] outputFiles;
    private boolean isDebug;
    public IOManager(String inputPath, String outputPath, boolean isDebug) {
        this.isDebug = isDebug;

        this.initInputFiles(inputPath);
        this.initOutputFiles(outputPath);
    }

    private void debug(String message) {
        if(this.isDebug) {
            System.out.println("[DEBUG]: " + message);
        }
    }

    private void initInputFiles(String inputPath) {
        //file reader for input
        File file = new File(inputPath);

        if(file.isDirectory()) {
            this.inputFiles = file.listFiles();
        } else {
            this.inputFiles = new File[] {file};
        }

        this.debug("Reading files from " + inputPath + "!");
        this.debug("inputFiles (" + this.inputFiles.length + "): " + Arrays.deepToString(this.inputFiles));
    }

    private void initOutputFiles(String outputPath) {
        //create an output file for every input file
        this.outputFiles = new File[this.inputFiles.length];

        for(int i = 0;i < this.outputFiles.length;i++) {
            String filePath = outputPath + "/";
            String fileName =  this.inputFiles[i].getName().split("\\.")[0] + ".txt";

            this.outputFiles[i] = new File(filePath + fileName);
        }

        this.debug("Writing files to " + outputPath + "!");
        this.debug("outputFiles (" + this.outputFiles.length + "): " + Arrays.deepToString(this.outputFiles));
    }

    public void execute() {
        //execute Main.solve() for every input file
        for(int i = 0;i < this.inputFiles.length;i++) {
            File inputFile = this.inputFiles[i];
            File outputFile = this.outputFiles[i];

            //Create Scanner for reading from file and writer for writing to file
            Scanner reader;
            FileWriter writer;
            try {
                reader = new Scanner(inputFile);
                writer = new FileWriter(outputFile);
            } catch(Exception e) {
                e.printStackTrace();
                continue;
            }

            //write to file in solve method
            try {
                Main.solve(reader, writer);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            //close used resources
            reader.close();
            try {
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.debug("File " + (i + 1) + " done!");
        }
        this.debug("All Files done!");
    }
}