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
        homePage = new HomePage(driver,"non visiting");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testValidLoginWithRegisteredUser() {
        JsonNode validUser = loginData.get("alreadyRegisteredUser");
        String email = validUser.get("email").asText();
        loginRegisterPopUp.loginWith(email, validUser.get("password").asText());
        int atIndex = email.indexOf("@");
        String accountName =  email.substring(0, atIndex);
        assertThat(homePage.returnAccountButtonText()).isEqualTo(accountName);
    }
}