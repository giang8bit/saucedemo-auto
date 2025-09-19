package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {
    private final By checkOutBtn = By.name("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage checkoutProduct() {
        clickToElement(checkOutBtn);
        return new CheckoutPage(driver);
    }

}
