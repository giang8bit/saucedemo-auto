package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.TCResult;
import utils.TestContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductPage extends BasePage {
    private final By sortSelect = By.cssSelector(".product_sort_container");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartLnk = By.className("shopping_cart_link");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public ProductPage addItemByName(String itemName) {
        clickToElement(By.xpath("//div[@class='inventory_item_name '][text()='" + itemName
                + "']/ancestor::div[@class='inventory_item']//button"));
        return this;
    }

    public ProductPage verifyCurrentItemInCartBadge(int epxectedNumber) {
        int actualItemNumber = Integer.parseInt(getTextElement(cartBadge));
        if (actualItemNumber != epxectedNumber) {
            TestContext.addFailure("Cart badge item number verification failed!\nExpected: " + epxectedNumber
                    + "\nActual: " + actualItemNumber + "\n");
        }
        return this;
    }

    public ProductPage sortBy(String value) {
        clickToElement(sortSelect);
        clickToElement(By.cssSelector("option[value='" + value + "']"));// az, za, lohi, hilo
        return this;
    }

    public String cartCount() {
        return driver.findElements(cartBadge).isEmpty() ? "0" : driver.findElement(cartBadge).getText();
    }

    public CartPage goToCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
        return new CartPage(driver);
    }

    public void verifyProductPageLoaded() {
        verifyWebsiteTitle("Swag Labs");

    }

    private List<Double> extractPricesFromPage() {
        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        List<Double> actualPrices = new ArrayList<>();
        for (WebElement priceElement : prices) {
            String priceText = priceElement.getText().replace("$", "");
            Double price = Double.parseDouble(priceText);
            actualPrices.add(price);
        }
        return actualPrices;
    }

    public ProductPage verifyIsSortedByPriceLowHigh() {
        // Extract actual prices using helper method
        List<Double> actualPrices = extractPricesFromPage();

        // Create expected sorted order (low to high)
        List<Double> expectedSortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedSortedPrices);

        // Compare actual vs expected order
        if (!actualPrices.equals(expectedSortedPrices)) {
            TestContext.addFailure("Price sorting verification failed!\nExpected order (low to high): "
                    + expectedSortedPrices + "\nActual order: " + actualPrices + "\n");
        }

        return this;
    }

    public ProductPage verifyIsSortedByPriceHighLow() {
        // Extract actual prices using helper method
        List<Double> actualPrices = extractPricesFromPage();

        // Create expected sorted order (high to low)
        List<Double> expectedSortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedSortedPrices, Collections.reverseOrder());

        // Compare actual vs expected order
        if (!actualPrices.equals(expectedSortedPrices)) {
            TestContext.addFailure("Price sorting verification failed!\nExpected order (high to low): "
                    + expectedSortedPrices + "\nActual order: " + actualPrices + "\n");
        }

        return this;
    }

    public CartPage BuyProducts() {
        clickToElement(cartLnk);
        return new CartPage(driver);
    }

}
