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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RegistrationTests {

    private static JsonNode registrationData;

    protected LoginRegisterPopUp loginRegisterPopUp;
    private WebDriver driver;

    @BeforeClass
    public static void loadTestData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/test/resources/testData.json");
        JsonNode testData = objectMapper.readTree(file);
        registrationData = testData.get("registration");
    }


    @BeforeMethod
    public void setup() {
        driver = WebDriverManager.getInstance("chrome").create();
        loginRegisterPopUp = new LoginRegisterPopUp(driver);
        loginRegisterPopUp.visitPage();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

//    @Test
//    public void testValidRegister() {
//        JsonNode validUser = registrationData.get("validUser");
//        String email = validUser.get("email").asText();
//        String password = validUser.get("password").asText();
//        loginRegisterPopUp.registerWith(email, password);
//        assertThat(loginRegisterPopUp.isRegisterSuccessful()).isTrue();
//    }


    @Test
    public void testIsPasswordRequired() {
        JsonNode validUser = registrationData.get("validUser");
        String email = validUser.get("email").asText();
        loginRegisterPopUp.registerWith(email, "");
        assertThat(loginRegisterPopUp.isRegisterSuccessful()).isFalse();
    }

    @Test
    public void testIsEmailRequired() {
        JsonNode validUser = registrationData.get("validUser");
        String password = validUser.get("password").asText();
        loginRegisterPopUp.registerWith("", password);
        assertThat(loginRegisterPopUp.isRegisterSuccessful()).isFalse();
    }

    @Test
    void testInvalidEmail() {
        JsonNode invalidEmailUser = registrationData.get("userWithInvalidEmailFormat");
        String email = invalidEmailUser.get("email").asText();
        String password = invalidEmailUser.get("password").asText();
        loginRegisterPopUp.registerWith(email, password);
        assertThat(loginRegisterPopUp.isRegisterSuccessful()).isFalse();
    }

    @Test
    void testWeakPassword() {
        JsonNode weakPasswordUser = registrationData.get("userWithWeakPassword");
        String email = weakPasswordUser.get("email").asText();
        String password = weakPasswordUser.get("password").asText();
        loginRegisterPopUp.registerWith(email, password);
        assertThat(loginRegisterPopUp.isRegisterSuccessful()).isFalse();
    }

    @Test
    void testAlreadyRegisteredEmail() {
        JsonNode alreadyRegisteredUser = registrationData.get("alreadyRegisteredUser");
        String email = alreadyRegisteredUser.get("email").asText();
        String password = alreadyRegisteredUser.get("password").asText();
        loginRegisterPopUp.registerWith(email, password);
        assertThat(loginRegisterPopUp.isRegisterSuccessful()).isFalse();
    }

}
