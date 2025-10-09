package tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RegistrationTests extends BaseTest {

    private static JsonNode registrationData;

    @BeforeClass
    public static void loadTestData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/test/resources/testData.json");
        JsonNode testData = objectMapper.readTree(file);
        registrationData = testData.get("registration");
    }

    @Test
    public void testIsPasswordRequired() {
        loginRegisterPopUp.registerWith(registrationData.get("validUser").get("email").asText(), "");
        assertThat(loginRegisterPopUp.isRegisterSuccessful()).isFalse();
    }

    @Test
    public void testIsEmailRequired() {
        loginRegisterPopUp.registerWith("", registrationData.get("validUser").get("password").asText());
        assertThat(loginRegisterPopUp.isRegisterSuccessful()).isFalse();
    }

    @Test
    void testInvalidEmail() {
        JsonNode invalidEmailUser = registrationData.get("userWithInvalidEmailFormat");
        loginRegisterPopUp.registerWith(invalidEmailUser.get("email").asText(), invalidEmailUser.get("password").asText());
        assertThat(loginRegisterPopUp.isRegisterSuccessful()).isFalse();
    }

    @Test
    void testWeakPassword() {
        JsonNode weakPasswordUser = registrationData.get("userWithWeakPassword");
        loginRegisterPopUp.registerWith(weakPasswordUser.get("email").asText(), weakPasswordUser.get("password").asText());
        assertThat(loginRegisterPopUp.isRegisterSuccessful()).isFalse();
    }

    @Test
    void testAlreadyRegisteredEmail() {
        JsonNode alreadyRegisteredUser = registrationData.get("alreadyRegisteredUser");
        loginRegisterPopUp.registerWith(alreadyRegisteredUser.get("email").asText(), alreadyRegisteredUser.get("password").asText());
        assertThat(loginRegisterPopUp.isRegisterSuccessful()).isFalse();
    }
}
