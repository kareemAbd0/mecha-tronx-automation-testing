package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage{
    private final By loader = By.className("page-loader-spin");
    private final By loginRegisterButton = By.xpath("//a[@title='Login']");
    private final By spanAccountTitle = By.className("header-account-title");
    By linkAllProducts = By.xpath("//a[@class='nav-top-link' and @href='https://mecha-tronx.com/shop/']");
    By productSearchField = By.id("woocommerce-product-search-field-0");
    By searchSubmitButton = By.xpath("//button[@value='Search']");



    public HomePage(WebDriver driver){
        super(driver);
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    public HomePage(WebDriver driver, int timeout){
        this(driver);
        this.timeout = timeout;
    }

    public void visitPage(){
        visit("https://mecha-tronx.com/");
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
    }

    public void clickProductPage(){
        click(linkAllProducts);
    }

    public void loginRegisterPopUp(){
        click(loginRegisterButton);
    }

    public void searchForProduct(String productName){
        type(productSearchField, productName);
    }

    public void clickSearchButton(){
        click(searchSubmitButton);
    }


    public boolean isAccountTitleDisplayed(){
        return isDisplayed(spanAccountTitle);
    }
    public String getAccountTitleText(){
        return find(spanAccountTitle).getText();
    }


}
