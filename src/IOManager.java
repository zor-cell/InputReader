import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;

public class IOManager {
    private List<File> inputFiles;
    private List<File> outputFiles;
    private List<String> allowedExtensions;
    private boolean isDebug;
    private int targetSpecificLevelInt;
    private boolean targetExampleFiles;
    private String inputPath;
    private String outputPath;
    private boolean isCleanupOutput;

    public IOManager(String inputPath, String outputPath, List<String> allowedExtensions) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.allowedExtensions = allowedExtensions;
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
            if( (targetSpecificLevelInt != -1 && !fileName.contains(String.format("level%d", targetSpecificLevelInt)))
                    || (targetExampleFiles && !fileName.contains("example"))) return;
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

    }

    public void setDebug(Boolean debug) {
        if(debug == null) isDebug = false;
        else isDebug = debug;
    }
    public void setCleanupOutput(Boolean cleanupOutput) {
        if(cleanupOutput == null) this.isCleanupOutput = false;
        else this.isCleanupOutput = cleanupOutput;
    }

    public void setTargetSpecificLevel(String targetSpecificLevel) {
        this.targetSpecificLevelInt = -1;
        this.targetExampleFiles = false;

        if (targetSpecificLevel == null || targetSpecificLevel.isBlank()) {
            return;
        }

        targetSpecificLevel = targetSpecificLevel.trim();

        // check format
        if (!targetSpecificLevel.matches("^-?\\d*e?$")) {
            throw new IllegalArgumentException("The targetSpecificLevel value doesn't match expectation. Please check it in config. Has to be of format <number><e> (both optional)");
        }

        // check 'e'
        if (targetSpecificLevel.endsWith("e")) {
            targetExampleFiles = true;
            targetSpecificLevel = targetSpecificLevel.substring(0, targetSpecificLevel.length() - 1);
        }

        // check number
        if (!targetSpecificLevel.isEmpty()) {
            try {
                targetSpecificLevelInt = Integer.parseInt(targetSpecificLevel);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("The targetSpecificLevel value doesn't match expectation. Please check it in config. Has to be of format <number><e> (both optional)");
            }
        }
    }

    private void cleanDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    cleanDirectory(file);
                }
                file.delete();
            }
        }
    }

    public void initialize() {
        File inputDirectory = new File(inputPath);
        File outputDirectory = new File(outputPath);
        if (!inputDirectory.exists() || !inputDirectory.isDirectory() || !outputDirectory.exists() || !outputDirectory.isDirectory()) {
            throw new IllegalArgumentException("Value of input or output directory is invalid. No such directory exists. Please look into config file");
        }

        inputFiles = new ArrayList<>();
        this.debug("Reading files from " + inputPath);
        this.initInputFiles(inputPath);
        this.debug("inputFiles (" + inputFiles.size() + "): " + inputFiles);

        if (isCleanupOutput) {
            this.debug("Cleaning up output directory");
            cleanDirectory(outputDirectory);
        }
        outputFiles = new ArrayList<>();
        this.debug("Initiation of output files to " + outputPath);
        this.initOutputFiles(outputPath);
        this.debug("outputFiles (" + this.outputFiles.size() + "): " + outputFiles);
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