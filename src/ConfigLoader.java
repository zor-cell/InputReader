import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class ConfigLoader {
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
