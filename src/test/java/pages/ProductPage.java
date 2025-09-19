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
        clickToElement(By.xpath("//div[@class='inventory_item_name '][text()='" + itemName + "']/ancestor::div[@class='inventory_item']//button"));
        return this;
    }

    public ProductPage verifyCurrentItemInCartBadge(int epxectedNumber) {
        int actualItemNumber = Integer.parseInt(getTextElement(cartBadge));
        if (actualItemNumber != epxectedNumber) {
            TestContext.addFailure(String.format("Cart badge item number verification failed!\nExpected: %d\nActual: %d\n",
                epxectedNumber, actualItemNumber));
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

    public void verifyProductPageLoaded(String expectedTitle) {
        String actualTitle = driver.getTitle();
        
        if (!actualTitle.contains(expectedTitle)) {
            TestContext.addFailure(String.format("Page title is different\nExpected to contain: '%s'\nActual message: '%s'\n", expectedTitle, actualTitle));
        } else {
            TestContext.addSuccess("Page title verification passed: " + actualTitle);
        }
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
            TestContext.addFailure(String.format("Price sorting verification failed!\nExpected order (low to high): %s\nActual order: %s\n", 
                expectedSortedPrices, actualPrices));
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
            TestContext.addFailure(String.format("Price sorting verification failed!\nExpected order (high to low): %s\nActual order: %s\n", 
                expectedSortedPrices, actualPrices));
        }
        
        return this;
    }    

    public CartPage BuyProducts() {
        clickToElement(cartLnk);
        return new CartPage(driver);
    }

}





