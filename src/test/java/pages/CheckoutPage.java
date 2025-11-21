package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class CheckoutPage extends BasePage {
    By inputBillingFirstName = By.id("billing_first_name");

    By inputBillingLastName = By.id("billing_last_name");

    By inputBillingAddress = By.id("billing_address_1");
    By inputBillingCity = By.id("billing_city");

    By buttonBillingCounty = By.id("select2-billing_state-container");

    By billingCountyElement ;

    By inputBillingPhone = By.id("billing_phone");

    By inputBillingEmail = By.id("billing_email");

    By buttonPlaceOrder = By.id("place_order");

    By labelSubTotal = By.xpath("//tr[@class='cart-subtotal']//bdi");




    public CheckoutPage(WebDriver driver) {
        super(driver);
    }
    public CheckoutPage(WebDriver driver, int timeout) {
        this(driver);
        this.timeout = timeout;
    }

    public void fillFullCheckOutForm(String firstName, String lastName, String address, String city, String county, String phone, String email){

        type(inputBillingFirstName, firstName);
        type(inputBillingLastName, lastName);
        type(inputBillingAddress, address);
        type(inputBillingCity, city);

        click(buttonBillingCounty);
        click(find(By.xpath(createCountyStringLocator(county))));

        type(inputBillingPhone, phone);
        type(inputBillingEmail, email);
    }

    public void fillFirstName(String firstName){
        type(inputBillingFirstName, firstName);
    }
    public void fillLastName(String lastName){
        type(inputBillingLastName, lastName);
    }
    public void fillAddress(String address){
        type(inputBillingAddress, address);
    }
    public void fillCity(String city){
        type(inputBillingCity, city);
    }
    public void fillCountry(String country){
        type(buttonBillingCounty, country);
    }
    public void fillPhoneNumber(String phone){
        type(inputBillingPhone, phone);
    }
    public void fillEmail(String email){
        type(inputBillingEmail, email);
    }

    public void ClickPlaceOrder(){
        //this is a real website, instead of clicking and making an actual order, I will just hover over the button
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(buttonPlaceOrder)).perform();
    }

    public int getSubTotal() {
        String priceText = find(labelSubTotal).getText();
        return  (int) Double.parseDouble(priceText.replaceAll("[^\\d.]", ""));
    }

    public String createCountyStringLocator(String county){
        return "//ul[@id = 'select2-billing_state-results']//li[text()='" + county + "']";
    }


}
