import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class IOManager {
    private File[] inputFiles;
    private File[] outputFiles;
    private boolean isDebug;

    //private boolean showLineIndex = false;
    public IOManager(String inputPath, String outputPath, boolean isDebug) {
        this.isDebug = isDebug;

        initInputFiles(inputPath);
        initOutputFiles(outputPath);
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

        debug("inputFiles (" + this.inputFiles.length + "): " + Arrays.deepToString(this.inputFiles));
    }

    private void initOutputFiles(String outputPath) {
        //create an output file for every input file
        this.outputFiles = new File[this.inputFiles.length];

        for(int i = 0;i < this.outputFiles.length;i++) {
            String filePath = outputPath + "/";
            String fileName =  this.inputFiles[i].getName().split("\\.")[0] + ".txt";

            this.outputFiles[i] = new File(filePath + fileName);
        }

        debug("outputFiles (" + this.outputFiles.length + "): " + Arrays.deepToString(this.outputFiles));
    }

    public String[][] readInputs() {
        String[][] inputs = new String[this.inputFiles.length][];

        for(int i = 0;i < inputs.length;i++) {
            File file = this.inputFiles[i];

            //Create Scanner to read from every input file
            Scanner reader;
            try {
                reader = new Scanner(file);
            } catch(Exception e) {
                e.printStackTrace();
                continue;
            }

            inputs[i] = getLines(reader);
        }

        debug("inputs (" + inputs.length + "): " + Arrays.deepToString(inputs));
        return inputs;
    }


    //function for reading the lines with a given Scanner
    //update this function according to the input requirements
    private String[] getLines(Scanner reader) {
        int n = 0;

        //first line
        if(reader.hasNextInt()) {
            n = reader.nextInt();
            reader.nextLine();
        }

        String[] input = new String[n];
        for(int i = 0;i < n;i++) {
            input[i] = reader.nextLine();
        }

        return input;
    }

    //writes the result of the Main.solve function
    //to an output file for each input file
    public void writeOutputs(String[][] inputs) {
        for(int i = 0;i < inputs.length;i++) {
            //Create file writer for every outputFile
            FileWriter writer;
            try {
                writer = new FileWriter(this.outputFiles[i]);
            } catch(Exception e) {
                e.printStackTrace();
                continue;
            }

            //write result of solve function to current outputFile
            for(String s : inputs[i]) {
                String result = Main.solve(s).toString();

                try {
                    writer.write(result + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }

            //finally close file writer
            try {
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
