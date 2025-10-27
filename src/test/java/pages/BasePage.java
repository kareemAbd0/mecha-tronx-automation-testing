package pages;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import java.time.Duration;

public class BasePage {

    static final Logger log = getLogger(lookup().lookupClass());
    WebDriver driver;
    WebDriverWait myWait;
    int timeout = 5;
    WebDriverWait homeLoaderWait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        //short implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        //explicit wait
        myWait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        //loading the home page in some occasions is painfully slow
         homeLoaderWait = new WebDriverWait(driver, Duration.ofSeconds(40));
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

    public String getText(WebElement element) {
       return element.getText();
    }

    public String getText(By locator) {
        return getText(find(locator));
    }

    public boolean isDisplayed(WebElement element) {
        return isDisplayed(ExpectedConditions.visibilityOf(element));
    }

    public boolean isDisplayed(By locator) {
        return isDisplayed(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public boolean isDisplayed(ExpectedCondition<?> expectedCondition) {
        try {
            myWait.until(expectedCondition);
        } catch (TimeoutException e) {
            log.warn("Timeout of {} wait for element ", timeout);
            return false;
        }
        return true;
    }

}
