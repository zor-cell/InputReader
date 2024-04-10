import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.Arrays;

public class IOManager {
    private List<File> inputFiles;
    private List<File> outputFiles;
    private List<String> allowedExtensions;
    private boolean isDebug;
    private int targetSpecificLevel;
    private String inputPath;
    private String outputPath;

    public IOManager(String inputPath, String outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    private void debug(String message) {
        if(this.isDebug) {
            System.out.println("[DEBUG]: " + message);
        }
    }

    private void initInputFiles(String inputPath) {
        //file reader for input
        File file = new File(inputPath);

        // normal file
        if (file.isFile()) {
            String fileName = file.getName();
            int lastDotIndex = fileName.lastIndexOf('.');
            if(targetSpecificLevel != -1 && !fileName.contains(String.format("level%d", targetSpecificLevel))) return;
            if (lastDotIndex > 0) {
                String extension = fileName.substring(lastDotIndex).toLowerCase();
                if (allowedExtensions.contains(extension)) {
                    inputFiles.add(file);
                }
            }
            return;
        }

        // directory
        File[] filesArray = file.listFiles();
        if (filesArray != null) {
            for (File fileInDirectory : filesArray) {
                initInputFiles(fileInDirectory.getPath());
            }
        }
    }

    private void initOutputFiles(String outputPath) {
        //create an output file for every input file

        for (File inputFile : this.inputFiles) {
            String filePath = outputPath + "/";
            String fileName = inputFile.getName().split("\\.")[0] + ".txt";

            this.outputFiles.add(new File(filePath + fileName));
        }

        this.debug("Writing files to " + outputPath + "!");
        this.debug("outputFiles (" + this.outputFiles.size() + "): " + outputFiles);
    }
    public void setAllowedExtensions(List<String> allowedExtensions) {
        this.allowedExtensions = allowedExtensions;
    }

    public void setDebug(Boolean debug) {
        if(debug == null) isDebug = false;
        else isDebug = debug;
    }

    public void setTargetSpecificLevel(Integer targetSpecificLevel) {
        if(targetSpecificLevel == null) this.targetSpecificLevel = -1;
        else this.targetSpecificLevel = targetSpecificLevel;
    }

    public void initilize() {
        inputFiles = new ArrayList<>();
        this.debug("Reading files from " + inputPath + "!");
        this.initInputFiles(inputPath);
        this.debug("inputFiles (" + inputFiles.size() + "): " + inputFiles);

        outputFiles = new ArrayList<>();
        this.initOutputFiles(outputPath);
    }
    public void execute() {
        //execute Main.solve() for every input file
        for(int i = 0; i < inputFiles.size(); i++) {
            File inputFile = this.inputFiles.get(i);
            File outputFile = this.outputFiles.get(i);
            this.debug("File " + inputFile.getName() + " in progress!");

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
        }
        this.debug("All Files done!");
    }
}