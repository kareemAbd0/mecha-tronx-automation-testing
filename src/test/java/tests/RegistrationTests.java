package tests;

import com.fasterxml.jackson.databind.JsonNode;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginRegisterPopUp;
import support.listeners.TestListeners;

import java.io.IOException;
import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Listeners(TestListeners.class)

public class RegistrationTests extends BaseTest {

    private static JsonNode registrationData;

    protected LoginRegisterPopUp loginRegisterPopUp;

    WebDriverWait  wait;

    @BeforeClass
    public void loadTestData() throws IOException {
        registrationData = testData.get("registration");
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }


    @BeforeMethod
    public void setup() throws InterruptedException {
        loginRegisterPopUp = new LoginRegisterPopUp(driver);
        loginRegisterPopUp.visitPage();

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
    public void testIsEmailRequired() throws InterruptedException {
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
    void testWeakPassword(){
        JsonNode weakPasswordUser = registrationData.get("userWithWeakPassword");
        String email = weakPasswordUser.get("email").asText();
        String password = weakPasswordUser.get("password").asText();
        loginRegisterPopUp.fillRegistrationEmail(email);
        loginRegisterPopUp.fillRegistrationPassword(password);
        loginRegisterPopUp.clickShowPassword();


        assertThat(loginRegisterPopUp.isResisterDisabled()).isTrue();
    }

    @Test
    void testAlreadyRegisteredEmail() {
        JsonNode alreadyRegisteredUser = registrationData.get("alreadyRegisteredUser");
        String email = alreadyRegisteredUser.get("email").asText();
        String password = alreadyRegisteredUser.get("password").asText();
        loginRegisterPopUp.registerWith(email, password);
        Assertions.assertThat(loginRegisterPopUp.isLoginErrorDisplayed()).isTrue();
        Assertions.assertThat(loginRegisterPopUp.getErrorText()).isEqualTo("Error: An account is already registered with "+email+". Please log in or use a different email address.");
    }
}
