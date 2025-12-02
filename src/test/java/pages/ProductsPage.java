package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ProductsPage extends BasePage{
    By firstProduct = By.xpath("(//div[contains(@class, 'row-small')]//img)[1]");
    By firstQuickView =By.className("quick-view");
    By addToCartButton = By.className("single_add_to_cart_button");
    By disabledAddToCartButton = By.cssSelector("button.single_add_to_cart_button.disabled");
    By miniCartButton = By.xpath("//a[contains(@class,'header-cart-link') and contains(@href,'https://mecha-tronx.com/cart/')]");
    By checkoutHref = By.xpath("//a[contains(@href, 'https://mecha-tronx.com/checkout/')]");
    By labelPrice= By.xpath("(//span[contains(text(),'EGP')])/ancestor::bdi");
    By firstProductName = By.xpath("(//a[contains(@class,'woocommerce-loop-product__link')])[1]");
    private static int sumOfPrices;

    Actions actions = new Actions(driver);

    public ProductsPage(WebDriver driver){
        super(driver);
        explicitWait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
    }
    public ProductsPage(WebDriver driver, int timeout){
        this( driver);
        this.timeout = timeout;
    }
    public void visitPage(){
        visit("https://mecha-tronx.com/shop/");
    }


    public void clickProduct(int index){

        By productLocator = By.xpath(createProductStringLocator(index));
        By productQuickView = By.xpath(createProductStringQuickView(index));

        WebElement productElement = find(productLocator);
        actions.scrollToElement(productElement).moveToElement(productElement).perform();
        explicitWait.until(ExpectedConditions.elementToBeClickable(productQuickView));
        actions.moveToElement(find(productQuickView)).click().perform();


    }

    public void clickFirstProduct(){
        actions.moveToElement(find(firstProduct)).moveToElement(find(firstQuickView)).click().build().perform();
    }

    public void addToCart(String option) throws InterruptedException {
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(addToCartButton));
        Select color = new Select(driver.findElement(By.id("colour")));

        color.selectByValue(option);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(disabledAddToCartButton));

        click(addToCartButton);

        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(addToCartButton));
    }

    public void addToCart() throws InterruptedException {
        WebDriverWait quickViewWait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
        quickViewWait.until(ExpectedConditions.visibilityOfElementLocated(addToCartButton));
        click(addToCartButton);
        quickViewWait.until(ExpectedConditions.invisibilityOfElementLocated(addToCartButton));
    }

    public void hoverToMiniCart(){

        actions.moveToElement(find(miniCartButton)).pause(Duration.ofSeconds(1)).build().perform();

    }

    public void addToPriceTotal(){

        actions.pause(Duration.ofSeconds(2)).perform();

        String priceText = find(labelPrice).getText();
        int tempPrice = (int) Double.parseDouble(priceText.replaceAll("[^\\d.]", ""));
        sumOfPrices+= (tempPrice);


    }

    public String getFirstProductName(){
        return getText(firstProductName);
    }

    public void checkout(){
        click(checkoutHref);
    }


    public static int getSumOfPrices() {
        return sumOfPrices;
    }

    private String createProductStringLocator(Integer index){
        return "(//div[contains(@class, 'row-small')]//img)["  + index.toString() + "]";
    }
    private String createProductStringQuickView(Integer index){
        return  "(//div[contains(@class, 'row-small')]//a[contains(@class, 'quick-view')])[" + index.toString() + "]" ;
    }
}
