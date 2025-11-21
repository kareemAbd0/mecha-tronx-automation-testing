package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage{
    private final By loader = By.className("page-loader-spin");
    private final By loginRegisterButton = By.xpath("//a[@title='Login']");
    private final By spanAccountTitle = By.className("header-account-title");
    By linkAllProducts = By.xpath("//a[@class='nav-top-link' and @href='https://mecha-tronx.com/shop/']");



    public HomePage(WebDriver driver){
        super(driver);
    }

    public HomePage(WebDriver driver, int timeout){
        this(driver);
        this.timeout = timeout;
    }

    public void visitPage(){
        visit("https://mecha-tronx.com/");
        homeLoaderWait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
    }

    public void clickProductPage(){
        click(linkAllProducts);
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
