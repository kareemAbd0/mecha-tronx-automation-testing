package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage{
    private final By loginRegisterButton = By.xpath("//a[@title='Login']");
    By spanAccountTitle = By.className("header-account-title");

    public HomePage(WebDriver driver){
        super(driver);
    }

    public HomePage(WebDriver driver, int timeout){
        this(driver);
        this.timeout = timeout;
    }

    public void visitPage(){
        visit("https://mecha-tronx.com/");
    }

    public void loginRegisterPopUp(){
        click(loginRegisterButton);
    }


    public boolean isAccountTitleDisplayed(){
        return isDisplayed(spanAccountTitle);
    }
    public String getAccountTitleText(){
        return find(spanAccountTitle).getText();
    }


}
