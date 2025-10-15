package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginRegisterPopUp extends BasePage{
    By inputRegPassword = By.id("reg_password");
    By inputRegEmail = By.id("reg_email");
    By buttonRegister = By.name("register");

    By inputUsername = By.id("username");
    By inputPassword = By.id("password");
    By buttonLogin = By.name("login");

    public LoginRegisterPopUp(WebDriver driver){
        super(driver);
        visit("https://mecha-tronx.com/");

        click(By.xpath("//a[@title='Login']"));
    }
    public LoginRegisterPopUp(WebDriver driver, int timeout){
        this(driver);
        this.timeout = timeout;
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


}
