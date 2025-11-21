package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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


    public ProductsPage(WebDriver driver){
        super(driver);
    }
    public ProductsPage(WebDriver driver, int timeout){
        this( driver);
        this.timeout = timeout;
    }
    public void visitPage(){
        visit("https://mecha-tronx.com/shop/");
    }


    public void clickProduct(int index){
        Actions actions = new Actions(driver);
        WebDriverWait quickViewWait = new WebDriverWait(driver,Duration.ofSeconds(timeout));

        By product = By.xpath(createProductStringLocator(index));
        By productQuickView = By.xpath(createProductStringQuickView(index));

        actions.moveToElement(find(product)).perform();
        quickViewWait.until(ExpectedConditions.elementToBeClickable(productQuickView));
        actions.moveToElement(find(productQuickView)).click().perform();


    }

    public void clickFirstProduct(){
        Actions actions = new Actions(driver);
        actions.moveToElement(find(firstProduct)).moveToElement(find(firstQuickView)).click().build().perform();
    }

    public void addToCart(String option) throws InterruptedException {
        WebDriverWait quickViewWait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
        quickViewWait.until(ExpectedConditions.visibilityOfElementLocated(addToCartButton));
        Select color = new Select(driver.findElement(By.id("colour")));

        color.selectByValue(option);
        quickViewWait.until(ExpectedConditions.invisibilityOfElementLocated(disabledAddToCartButton));

        click(addToCartButton);

        quickViewWait.until(ExpectedConditions.invisibilityOfElementLocated(addToCartButton));
    }

    public void addToCart() throws InterruptedException {
        WebDriverWait quickViewWait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
        quickViewWait.until(ExpectedConditions.visibilityOfElementLocated(addToCartButton));
        click(addToCartButton);
        quickViewWait.until(ExpectedConditions.invisibilityOfElementLocated(addToCartButton));
    }

    public void hoverToMiniCart(){

        Actions actions = new Actions(driver);
        actions.moveToElement(find(miniCartButton)).pause(Duration.ofSeconds(1)).build().perform();

    }

    public void checkout(){
        click(checkoutHref);
    }

    private String createProductStringLocator(Integer index){
        return "(//div[contains(@class, 'row-small')]//img)["  + index.toString() + "]";
    }
    private String createProductStringQuickView(Integer index){
        return  "(//div[contains(@class, 'row-small')]//a[contains(@class, 'quick-view')])[" + index.toString() + "]" ;
    }
}
