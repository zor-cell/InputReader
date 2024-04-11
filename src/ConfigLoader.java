import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

/**
 * The {@code ConfigLoader} class is responsible for loading and parsing
 * configuration settings from an XML file into a {@code Config} object.
 * The XML file is expected to define various settings such as input and output paths,
 * allowed file extensions, and other operational parameters for an application.
 */
public class ConfigLoader {

    /**
     * Loads configuration settings from the specified config XML file and populates a {@code Config} object with these settings.
     * @param configFile The path to the XML configuration file. It is expected to be accessible via the classloader's resources.
     * @return A {@code Config} object populated with the settings defined in the XML file. Returns a {@code Config} object with default settings if the XML file does not specify them.
     * @throws IllegalArgumentException if the XML file cannot be found, or if required settings are missing or malformed in the file. This exception is also thrown if there's an error processing the XML file.
     */
    public static Config loadConfig(String configFile) {
        Config config = new Config();
        try {
            InputStream is = ConfigLoader.class.getClassLoader().getResourceAsStream(configFile);
            if (is == null) {
                throw new IllegalArgumentException("File not found: " + configFile);
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);
            doc.getDocumentElement().normalize();

            try {
                config.setInputPath(doc.getElementsByTagName("inputPath").item(0).getTextContent());
                config.setOutputPath(doc.getElementsByTagName("outputPath").item(0).getTextContent());
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("In file there are missing required settings - " + configFile);
            }

            if (doc.getElementsByTagName("targetSpecificLevel") != null && doc.getElementsByTagName("targetSpecificLevel").item(0) != null) {
                config.setTargetSpecificLevel(doc.getElementsByTagName("targetSpecificLevel").item(0).getTextContent());
            } else {
                config.setTargetSpecificLevel("-1");
            }

            if (doc.getElementsByTagName("cleanupOutput") != null && doc.getElementsByTagName("cleanupOutput").item(0) != null) {
                config.setCleanupOutput(Boolean.parseBoolean(doc.getElementsByTagName("cleanupOutput").item(0).getTextContent()));
            } else {
                config.setCleanupOutput(false);
            }

            if (doc.getElementsByTagName("isDebug") != null && doc.getElementsByTagName("isDebug").item(0) != null) {
                config.setDebug(Boolean.parseBoolean(doc.getElementsByTagName("isDebug").item(0).getTextContent()));
            } else {
                config.setDebug(false);
            }

            try {
                NodeList allowedExtensionsList = doc.getElementsByTagName("allowedExtensions");
                if (allowedExtensionsList.getLength() > 0) {
                    NodeList extensionNodes = ((Element) allowedExtensionsList.item(0)).getElementsByTagName("extension");
                    for (int i = 0; i < extensionNodes.getLength(); i++) {
                        config.getAllowedExtensions().add(extensionNodes.item(i).getTextContent());
                    }
                }
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("In file there are missing required settings - " + configFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return config;
    }
}

/**
 * The {@code Config} class represents the configuration settings for the application.
 * It encapsulates various settings that can be loaded from a configuration file, including
 * paths for input and output, debug flags, cleanup behavior, allowed file extensions,
 * and a specific target level for processing.
 */
class Config {
    private String inputPath;
    private String outputPath;
    private String targetSpecificLevel;
    private List<String> allowedExtensions = new ArrayList<>();
    private Boolean cleanupOutput;
    private Boolean isDebug;


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

    public Boolean isDebug() {
        return isDebug;
    }

    public void setDebug(Boolean debug) {
        isDebug = debug;
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
