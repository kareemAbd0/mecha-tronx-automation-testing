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
    public void testValidLoginWithEmail() {
        JsonNode validUser = loginData.get("alreadyRegisteredUserWithEmail");
        String email = validUser.get("email").asText();
        String password = validUser.get("password").asText();
        loginRegisterPopUp.loginWith(email, password);
        int atIndex = email.indexOf("@");
        String username =  email.substring(0, atIndex);
        assertThat(homePage.isAccountTitleDisplayed()).isTrue();
        assertThat(homePage.getAccountTitleText()).isEqualTo(username);
    }

    @Test
    public void testValidLoginWithUsername() {
        JsonNode validUser = loginData.get("alreadyRegisteredUserWithUsername");
        String username = validUser.get("username").asText();
        String password = validUser.get("password").asText();
        loginRegisterPopUp.loginWith(username, password);
        int atIndex = username.indexOf("@");
        assertThat(homePage.isAccountTitleDisplayed()).isTrue();
        assertThat(homePage.getAccountTitleText()).isEqualTo(username);
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

    @Test
    public void testNonRegisteredEmail() {
        JsonNode nonRegisteredUser = loginData.get("nonRegisteredEmail");
        String email = nonRegisteredUser.get("email").asText();
        String password = nonRegisteredUser.get("password").asText();
        loginRegisterPopUp.loginWith(email, password);
        assertThat(loginRegisterPopUp.isLoginErrorDisplayed()).isTrue();
        assertThat(loginRegisterPopUp.getErrorText()).isEqualTo("Unknown email address. Check again or try your username.");
    }

    @Test
    public void testNonRegisteredUsername() {
        JsonNode nonRegisteredUser = loginData.get("nonRegisteredUsername");
        String username = nonRegisteredUser.get("username").asText();
        String password = nonRegisteredUser.get("password").asText();
        loginRegisterPopUp.loginWith(username, password);
        assertThat(loginRegisterPopUp.isLoginErrorDisplayed()).isTrue();
        assertThat(loginRegisterPopUp.getErrorText()).isEqualTo("Error: The username "+ username + " is not registered on this site. " +
                "If you are unsure of your username, try your email address instead.");



    }

    @Test
    public void testEmptyEmail() {
        JsonNode emptyEmail = loginData.get("alreadyRegisteredUserWithEmail");
        String password = emptyEmail.get("password").asText();
        loginRegisterPopUp.loginWith("", password);
        assertThat(loginRegisterPopUp.isLoginErrorDisplayed()).isTrue();
        assertThat(loginRegisterPopUp.getErrorText()).isEqualTo("Error: Username is required.");
    }

    @Test
    public void testEmptyPassword() {
        JsonNode emptyPassword = loginData.get("alreadyRegisteredUserWithEmail");
        String email = emptyPassword.get("email").asText();
        loginRegisterPopUp.loginWith(email, "");
        assertThat(loginRegisterPopUp.isLoginErrorDisplayed()).isTrue();
        assertThat(loginRegisterPopUp.getErrorText()).isEqualTo("Error: The password field is empty.");
    }
}