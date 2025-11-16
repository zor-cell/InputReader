package config;

import java.io.File;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

/**
 * The {@code config.ConfigLoader} class is responsible for loading and parsing
 * configuration settings from an XML file into a {@code config.Config} object.
 * The XML file is expected to define various settings such as input and output paths,
 * allowed file extensions, and other operational parameters for an application.
 */
public class ConfigLoader {

    /**
     * Loads configuration settings from the specified config XML file and populates a {@code config.Config} object with these settings.
     * @param configPath The path to the XML configuration file. It is expected to be accessible via the classloader's resources.
     * @return A {@code config.Config} object populated with the settings defined in the XML file. Returns a {@code config.Config} object with default settings if the XML file does not specify them.
     * @throws IllegalArgumentException if the XML file cannot be found, or if required settings are missing or malformed in the file. This exception is also thrown if there's an error processing the XML file.
     */
    public static Config loadConfig(String configPath) {
        Config config = new Config();
        try {
            File file = new File(configPath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            try {
                config.setInputPath(doc.getElementsByTagName("inputPath").item(0).getTextContent());
                config.setOutputPath(doc.getElementsByTagName("outputPath").item(0).getTextContent());
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("In file there are missing required settings - " + configPath);
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

            if (doc.getElementsByTagName("logLevel") != null && doc.getElementsByTagName("logLevel").item(0) != null) {
                config.setLogLevel(Level.parse(doc.getElementsByTagName("logLevel").item(0).getTextContent().toUpperCase()));
            } else {
                config.setLogLevel(Level.INFO);
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
                throw new IllegalArgumentException("In file there are missing required settings - " + configPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return config;
    }
}

