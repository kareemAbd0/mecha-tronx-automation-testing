package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage{
    By loginRegisterButton = By.xpath("//a[@title='Login']");

    public HomePage(WebDriver driver){
        super(driver);
        visit("https://mecha-tronx.com/");
    }

    public HomePage(WebDriver driver, int timeout){
        this(driver);
        this.timeout = timeout;

    }

    //if no visit url intialization is needed
    public  HomePage(WebDriver driver, String nonVisiting){
        super(driver);

    }

    public void loginRegisterPopUp(){
        click(loginRegisterButton);
    }


    public String returnAccountButtonText(){
        By accountButton = By.className("header-account-title");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(accountButton));
        }catch (TimeoutException e){
            throw new TimeoutException();

        }
        return find(accountButton).getText();
    }

}
