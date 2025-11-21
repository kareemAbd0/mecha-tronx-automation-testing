package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {
    By inputBillingFirstName = By.id("billing_first_name");

    By inputBillingLastName = By.id("billing_last_name");

    By inputBillingAddress = By.id("billing_address_1");
    By inputBillingCity = By.id("billing_city");

    By inputBillingCounty = By.id("select2-billing_state-container");

    By inputBillingPhone = By.id("billing_phone");

    By inputBillingEmail = By.id("billing_email");

    By buttonPlaceOrder = By.id("place_order");



    public CheckoutPage(WebDriver driver) {
        super(driver);
    }
    public CheckoutPage(WebDriver driver, int timeout) {
        this(driver);
        this.timeout = timeout;
    }


}
