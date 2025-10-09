package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginRegisterPopUp;

public abstract class BaseTest {

    protected LoginRegisterPopUp loginRegisterPopUp;

    @BeforeMethod
    public void setup() {
        loginRegisterPopUp = new LoginRegisterPopUp("chrome");
    }

    @AfterMethod
    public void tearDown() {
        loginRegisterPopUp.quit();
    }
}
