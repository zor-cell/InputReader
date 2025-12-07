package solver;

import config.VerifierConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import log.CustomLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class SolutionVerifier {
    private static final Logger log = CustomLogger.getLogger();

    private final VerifierConfig config;
    private final WebDriver driver;
    private FileWriter writer;


    public SolutionVerifier(VerifierConfig config) {
        this.config = config;

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        if(config.headless()) {
            options.addArguments("--headless");
        }
        driver = new ChromeDriver(options);
    }

    public void checkValidity(Scanner input, Scanner output, FileWriter writer) throws IOException {
        this.writer = writer;
        Solver.verify(input, output, this);
    }

    public void checkValidity(int run, String inputContent, String outputContent) {
        try {
            // Convert local file path to URL
            String visualizerUrl = new File(config.visualizerPath()).toURI().toString();
            driver.get(visualizerUrl);

            By inputLocator = getLocator(config.inputLocator());
            WebElement inputArea = driver.findElement(inputLocator);
            inputArea.clear();
            inputArea.sendKeys(inputContent);

            By outputLocator = getLocator(config.outputLocator());
            WebElement outputArea = driver.findElement(outputLocator);
            outputArea.clear();
            outputArea.sendKeys(outputContent);

            By statusLocator = getLocator(config.statusLocator());
            WebElement resultDiv = driver.findElement(statusLocator);
            String resultText = resultDiv.getText();

            String infoText = "";
            if(config.infoLocator() != null) {
                By infoLocator = getLocator(config.infoLocator());
                WebElement infoDiv = driver.findElement(infoLocator);
                infoText += infoDiv.getText();
            }

            writer.write(String.format("Validation run %d exited with %s! Additional Info: '%s' | Input: %s | Output: %s\n", run, resultText, infoText, inputContent, outputContent));
        } catch (Exception e) {
           log.severe("Visualizer failed");
            e.printStackTrace();
        }
    }

    private By getLocator(String configLocator) {
        if (configLocator == null || configLocator.trim().isEmpty()) {
            throw new IllegalArgumentException("Locator string cannot be empty.");
        }

        String trimmed = configLocator.trim();

        //xpath
        if(trimmed.startsWith("//")) {
            return By.xpath(trimmed);
        }

        return By.cssSelector(trimmed);
    }

    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
