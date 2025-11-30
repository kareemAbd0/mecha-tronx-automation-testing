package tests;

import com.fasterxml.jackson.databind.JsonNode;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductsPage;
import pages.CheckoutPage;
import support.listeners.TestListeners;

import static org.assertj.core.api.Assertions.assertThat;

@Listeners(TestListeners.class)
public class BuyTests extends BaseTest {


    private HomePage homePage;
    private ProductsPage productsPage;
    private CheckoutPage checkoutPage;

    @BeforeMethod
    public void setup() {

         homePage = new HomePage(driver, 10);
         productsPage = new ProductsPage(driver,10);
        checkoutPage = new CheckoutPage(driver);
    }


    @Test
    public void testBuyFromMiniCartValidFlow() throws InterruptedException {

        JsonNode FullValidData = testData.get("checkoutForm");
        String firstName = FullValidData.get("FirstName").asText();
        String lastName = FullValidData.get("LastName").asText();
        String email = FullValidData.get("EmailAddress").asText();
        String phone = FullValidData.get("Phone").asText();
        String streetAddress = FullValidData.get("StreetAddress").asText();
        String city = FullValidData.get("Town/City").asText();
        String county = FullValidData.get("State/County").asText();



        homePage.visitPage();
        homePage.clickProductPage();

        productsPage.clickFirstProduct();
        productsPage.addToCart("Black");
        productsPage.clickProduct(5);
        productsPage.addToCart();
        productsPage.hoverToMiniCart();
        productsPage.checkout();

        checkoutPage.fillFullCheckOutForm(firstName, lastName, streetAddress,city,county,phone,email);

        //not real assert , should assert for order completion page
        assertThat(driver.getCurrentUrl()).isEqualTo("https://mecha-tronx.com/checkout/");
    }


    @Test
    public void testIfCartTotalIsCorrect() throws InterruptedException {


        homePage.visitPage();
        homePage.clickProductPage();

        productsPage.clickFirstProduct();
        productsPage.addToPriceTotal();
        productsPage.addToCart("Black");

        productsPage.clickProduct(2);
        productsPage.addToPriceTotal();
        productsPage.addToCart("Blue");

        productsPage.hoverToMiniCart();
        productsPage.checkout();

        assertThat(ProductsPage.getSumOfPrices()).isEqualTo(checkoutPage.getSubTotal());



    }








}
