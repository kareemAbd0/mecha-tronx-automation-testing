package tests;


import com.fasterxml.jackson.databind.JsonNode;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductsPage;
import support.listeners.TestListeners;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Listeners(TestListeners.class)
public class ProductSearchTests extends  BaseTest {

    private static JsonNode productSearchData;
    HomePage homePage;
    ProductsPage productsPage;


    @BeforeClass
    public void loadTestData() throws IOException {
        productSearchData = testData.get("productSearch");
    }

    @BeforeMethod
    public void setup(){

        homePage = new HomePage(driver);
        productsPage = new ProductsPage(driver);
        homePage.visitPage();
    }

    @Test
    public void testExactMatch(){

        String intendedProductName = productSearchData.get("exactMatch").asText();
        homePage.searchForProduct(intendedProductName);
        homePage.clickSearchButton();
        assertThat(productsPage.getFirstProductName()).contains(intendedProductName);
    }

    @Test
    public void testPartialMatch(){
        String intendedProductName = productSearchData.get("exactMatch").asText();
        String partialProductName = productSearchData.get("partialMatch").asText();
        homePage.searchForProduct(partialProductName);
        homePage.clickSearchButton();

        assertThat(productsPage.getFirstProductName()).contains(intendedProductName);

    }

    @Test
    public void testCaseSensitiveMatch(){
        String intendedProductName = productSearchData.get("exactMatch").asText();
        String UpperCaseProductName = productSearchData.get("caseSensitiveMatch").asText();
        homePage.searchForProduct(UpperCaseProductName);
        homePage.clickSearchButton();

        assertThat(productsPage.getFirstProductName()).contains(intendedProductName);
    }

    @Test
    public void testPluralHandlingMatch(){
        String intendedProductName = productSearchData.get("exactMatch").asText();
        String pluralProductName = productSearchData.get("pluralMatch").asText();

        homePage.searchForProduct(pluralProductName);
        homePage.clickSearchButton();

        assertThat(productsPage.getFirstProductName()).contains(intendedProductName);

    }




}
