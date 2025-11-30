package tests;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductsPage;
import support.listeners.TestListeners;

import java.io.IOException;

@Listeners(TestListeners.class)
public class ProductSearchTests extends  BaseTest {


    @BeforeMethod
    public void setup(){

        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        homePage.visitPage();

    }

    @Test
    public void testExactName(){




    }




}
