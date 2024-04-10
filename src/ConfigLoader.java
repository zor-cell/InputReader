import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

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

            config.setInputPath(doc.getElementsByTagName("inputPath").item(0).getTextContent());
            config.setOutputPath(doc.getElementsByTagName("outputPath").item(0).getTextContent());
            config.setDebug(Boolean.parseBoolean(doc.getElementsByTagName("isDebug").item(0).getTextContent()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return config;
    }
}


class Config {
    private String inputPath;
    private String outputPath;
    private boolean isDebug;

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

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }
}
