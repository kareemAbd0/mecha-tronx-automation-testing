package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import java.time.Duration;

public class BasePage {

    static final Logger log = getLogger(lookup().lookupClass());
    WebDriver driver;
    WebDriverWait wait;
    int timeout = 3;


    public BasePage(String browser) {
        driver = WebDriverManager.getInstance(browser).create();
        //short implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        //explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }


    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void visit(String url) {
        driver.get(url);
    }

    public void quit() {
        driver.quit();
    }
    public WebElement find(By element){
        return driver.findElement(element);
    }

    public void click(WebElement element) {
        element.click();
    }

    public void click(By element) {
        click(find(element));
    }

    public void type(WebElement element, String text) {
        element.sendKeys(text);
    }

    public void type(By element, String text) {
        type(find(element), text);
    }

    public boolean isDisplayed(WebElement element) {
        return isDisplayed(ExpectedConditions.visibilityOf(element));
    }

    public boolean isDisplayed(By locator) {
        return isDisplayed(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public boolean isDisplayed(ExpectedCondition<?> expectedCondition) {
        try {
            wait.until(expectedCondition);
        } catch (TimeoutException e) {
            log.warn("Timeout of {} wait for element ", timeout);
            return false;
        }
        return true;
    }

}
