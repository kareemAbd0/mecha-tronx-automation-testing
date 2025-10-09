package pages;


import org.openqa.selenium.By;

public class HomePage extends BasePage{
    By loginRegisterButton = By.xpath("//a[@title='Login']");



    public HomePage(String browser){
        super(browser);
        visit("https://mecha-tronx.com/");
    }
    public HomePage(String browser, int timeout){
        this(browser);
        this.timeout = timeout;
    }

    public void loginRegisterPopUp(){
        click(loginRegisterButton);
    }

    public void openLoginPopUp(){
        click(loginRegisterButton);
    }


}
