package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Comparator;
import java.util.List;

public class InventoryPage {
    private final WebDriver driver;
    private final By inventoryItems = By.cssSelector(".inventory_item");
    private final By sortSelect = By.cssSelector(".product_sort_container");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");

    public InventoryPage(WebDriver driver) { this.driver = driver; }

    public boolean isLoaded() {
        return !driver.findElements(inventoryItems).isEmpty();
    }

    public InventoryPage addItemByName(String itemName) {
        List<WebElement> items = driver.findElements(inventoryItems);
        for (WebElement it : items) {
            String name = it.findElement(By.className("inventory_item_name")).getText();
            if (name.equalsIgnoreCase(itemName)) {
                it.findElement(By.tagName("button")).click();
                break;
            }
        }
        return this;
    }

    public InventoryPage sortBy(String value) {
        driver.findElement(sortSelect).click();
        driver.findElement(By.cssSelector("option[value='" + value + "']")).click(); // az, za, lohi, hilo
        return this;
    }

    public boolean isSortedByPriceLowHigh() {
        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        var doubles = prices.stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .toList();
        return doubles.stream().sorted(Comparator.naturalOrder()).toList().equals(doubles);
    }

    public String cartCount() {
        return driver.findElements(cartBadge).isEmpty() ? "0" : driver.findElement(cartBadge).getText();
    }

    public CartPage goToCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
        return new CartPage(driver);
    }
}
