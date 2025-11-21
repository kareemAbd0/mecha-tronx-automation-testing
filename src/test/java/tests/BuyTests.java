package tests;

import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductsPage;

public class BuyTests extends BaseTest {

    @Test
    public void BuyFromMiniCartValidFlow() throws InterruptedException {

        HomePage homePage = new HomePage(driver,10);
        ProductsPage productsPage = new ProductsPage(driver,10);
        homePage.visitPage();
        homePage.clickProductPage();
        productsPage.clickFirstProduct();
        productsPage.addToCart("Black");
        productsPage.clickProduct(5);
        productsPage.addToCart();
        productsPage.hoverToMiniCart();
        productsPage.checkout();
    }
}
