package solver;

import config.Config;
import config.VisualizerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class VisualizerVerifier {
    private final VisualizerConfig config;
    private final WebDriver driver;
    private FileWriter writer;


    public VisualizerVerifier(VisualizerConfig config) {
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
        Main.verify(input, output, this);
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

            writer.write(String.format("Validation run %d exited with %s! Input: %s, Output: %s\n", run, resultText, inputContent, outputContent));
        } catch (Exception e) {
            System.err.println("Visualizer failed");
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
