package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginRegisterPopUp extends BasePage{
    private final By inputRegPassword = By.id("reg_password");
    private final By inputRegEmail = By.id("reg_email");
    private final By buttonRegister = By.name("register");

    private final By inputUsername = By.id("username");
    private final By inputPassword = By.id("password");
    private final By buttonLogin = By.name("login");

    private final By LoginError = By.cssSelector("div.message-container.alert-color");

    public LoginRegisterPopUp(WebDriver driver){
        super(driver);
    }
    public LoginRegisterPopUp(WebDriver driver, int timeout){
        this(driver);
        this.timeout = timeout;
    }

    public void visitPage(){
        visit("https://mecha-tronx.com/");
        click(By.xpath("//a[@title='Login']"));
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
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(buttonRegister));
        }catch (TimeoutException e){
            return false;
        }
    }

    public boolean isLoginErrorDispalyed(){
        return isDisplayed(LoginError);
    }

    public String GetErrorText(){
        String errorText = find(LoginError).getText();
        return errorText;
    }


}
