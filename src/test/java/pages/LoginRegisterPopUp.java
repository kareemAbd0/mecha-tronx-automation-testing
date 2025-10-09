package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginRegisterPopUp extends BasePage{
    By inputRegPassword = By.id("reg_password");

    By inputRegEmail = By.id("reg_email");

    By buttonRegister = By.name("register");

    public LoginRegisterPopUp(String browser){
        super(browser);
        visit("https://mecha-tronx.com/");
        click(By.xpath("//a[@title='Login']"));
    }
    public LoginRegisterPopUp(String browser, int timeout){
        this(browser);
        this.timeout = timeout;
    }

    public void registerWith(String email, String password){

        type(inputRegEmail, email);
        type(inputRegPassword, password);

        click(buttonRegister);

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
