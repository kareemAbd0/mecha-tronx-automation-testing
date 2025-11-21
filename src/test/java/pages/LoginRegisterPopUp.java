package pages;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;

public class LoginRegisterPopUp extends BasePage{

    private final By loader = By.className("page-loader-spin");
    private final By inputRegPassword = By.id("reg_password");
    private final By inputRegEmail = By.id("reg_email");
    private final By buttonRegister = By.name("register");



    private final By inputUsername = By.id("username");
    private final By inputPassword = By.id("password");
    private final By buttonLogin = By.name("login");

    private final By LoginError = By.cssSelector("div.message-container.alert-color");

    public LoginRegisterPopUp(WebDriver driver){
        super(driver);
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }
    public LoginRegisterPopUp(WebDriver driver, int timeout){
        this(driver);
        this.timeout = timeout;
    }

    public void visitPage(){
     By popUp =  By.xpath("//a[@title='Login']");
        visit("https://mecha-tronx.com/");
        explicitWait.until(ExpectedConditions.elementToBeClickable(popUp));
        click(popUp);
    }

    public void registerWith(String email, String password){
        type(inputRegEmail, email);
        type(inputRegPassword, password);
        click(buttonRegister);
    }

    public void loginWith(String email, String password){
        type(inputUsername, email);
        type(inputPassword, password);
        click(buttonLogin);
    }

    public boolean isRegisterSuccessful(){
        try {
            //upon success, the register-login popUp will disappear returning to home page
            return explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(buttonRegister));
        }catch (TimeoutException e){
            return false;
        }
    }

    public boolean isResisterDisabled(){
        try {
            String classes = driver.findElement(buttonRegister).getAttribute("class");
            if (classes == null || classes.trim().isEmpty()) {
                return false;
            }
            return Arrays.asList(classes.trim().split("\\s+")).contains("disabled");
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isLoginErrorDisplayed(){
        return isDisplayed(LoginError);
    }

    public String getErrorText(){
        return getText(LoginError);
    }


}
