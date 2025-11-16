package config;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * The {@code config.Config} class represents the configuration settings for the application.
 * It encapsulates various settings that can be loaded from a configuration file, including
 * paths for input and output, debug flags, cleanup behavior, allowed file extensions,
 * and a specific target level for processing.
 */
public class Config {
    private String inputPath;
    private String outputPath;
    private String targetSpecificLevel;
    private List<String> allowedExtensions = new ArrayList<>();
    private Boolean cleanupOutput;
    private Level logLevel;


    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public Boolean getCleanupOutput() {
        return cleanupOutput;
    }

    public void setCleanupOutput(Boolean cleanupOutput) {
        this.cleanupOutput = cleanupOutput;
    }

    public List<String> getAllowedExtensions() {
        return allowedExtensions;
    }

    public void setAllowedExtensions(List<String> allowedExtensions) {
        this.allowedExtensions = allowedExtensions;
    }

    public void setTargetSpecificLevel(String targetSpecificLevel) {
        this.targetSpecificLevel = targetSpecificLevel;
    }

    public String getTargetSpecificLevel() {
        return targetSpecificLevel;
    }
}
