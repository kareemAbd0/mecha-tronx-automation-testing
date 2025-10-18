package tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginRegisterPopUp;

import java.io.File;
import java.io.IOException;

import pages.HomePage;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginTests {
    private static JsonNode loginData;
    protected LoginRegisterPopUp loginRegisterPopUp;
    private WebDriver driver;
    protected HomePage homePage;

    @BeforeClass
    public static void loadTestData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/test/resources/testData.json");
        JsonNode testData = objectMapper.readTree(file);
        loginData = testData.get("login");
    }

    @BeforeMethod
    public void setup() {
        driver = WebDriverManager.getInstance("chrome").create();
        loginRegisterPopUp = new LoginRegisterPopUp(driver);
        loginRegisterPopUp.visitPage();
        homePage = new HomePage(driver);
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void testValidLoginWithRegisteredUser() {
        JsonNode validUser = loginData.get("alreadyRegisteredUser");
        String email = validUser.get("email").asText();
        String password = validUser.get("password").asText();
        loginRegisterPopUp.loginWith(email, password);
        int atIndex = email.indexOf("@");
        String accountName =  email.substring(0, atIndex);
        assertThat(homePage.isAccountTitleDisplayed()).isTrue();
        assertThat(homePage.getAccountTitleText()).isEqualTo(accountName);
    }

    @Test
    public void testIncorrectPassword() {
        JsonNode incorrectPassword = loginData.get("incorrectPasswordForRegisteredEmail");
        String email = incorrectPassword.get("email").asText();
        String password = incorrectPassword.get("password").asText();
        loginRegisterPopUp.loginWith(email, password);
        assertThat(loginRegisterPopUp.isLoginErrorDisplayed()).isTrue();
        assertThat(loginRegisterPopUp.getErrorText()).isEqualTo("Error: The password you entered for the email address "+
                email+ " is incorrect. Lost your password?");

    }

}