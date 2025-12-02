package support.listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.BaseTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.invoke.MethodHandles.lookup;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.slf4j.LoggerFactory.getLogger;

public class TestListeners implements ITestListener {


    static final Logger log = getLogger(lookup().lookupClass());

    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        WebDriver driver = ((BaseTest) testClass).driver;

        String testName = result.getName();

        try {
            takeScreenshotPNG(driver,testName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void takeScreenshotPNG(WebDriver driver, String testName) throws IOException {
        log.info("Taking screenshot for test: {}", testName);
        if (driver == null) {
            log.error("Driver is null, cannot take screenshot");
            return;
        }
        log.info("Driver is not null, proceeding with screenshot");
        TakesScreenshot ts = (TakesScreenshot) driver;
        File screenshot = ts.getScreenshotAs(OutputType.FILE);
        log.debug("Screenshot created on {}", screenshot);
        Path destination = Paths.get("target/screenshots", testName + ".png");
        log.info("Screenshot destination: {}", destination);
        Files.move(screenshot.toPath(), destination, REPLACE_EXISTING);
        log.debug("Screenshot moved to {}", destination);
    }
}