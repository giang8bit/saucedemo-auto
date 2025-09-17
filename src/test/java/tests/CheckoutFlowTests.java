package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.Config;
import utils.DriverFactory;
import utils.TestListener;

@Listeners(TestListener.class)
public class CheckoutFlowTests extends BaseTest {

    @Test(description = "Complete purchase flow end-to-end")
    public void completePurchase() {
        var driver = DriverFactory.getDriver();
        InventoryPage inv = new LoginPage(driver)
                .open()
                .loginAs(Config.STANDARD_USER, Config.PASSWORD);

        inv.addItemByName("Sauce Labs Bike Light")
          .goToCart()
          .checkout();

        // Checkout Step One
        driver.findElement(By.id("first-name")).sendKeys("Jane");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("10001");
        driver.findElement(By.id("continue")).click();

        // Step Two → Finish
        driver.findElement(By.id("finish")).click();

        // Complete
        String header = driver.findElement(By.className("complete-header")).getText();
        Assert.assertTrue(header.toLowerCase().contains("thank you"), "Should see thank you message");
    }

    @Test(description = "Missing postal code shows validation error")
    public void missingPostalCode() {
        var driver = DriverFactory.getDriver();
        new LoginPage(driver).open().loginAs(Config.STANDARD_USER, Config.PASSWORD);
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys("A");
        driver.findElement(By.id("last-name")).sendKeys("B");
        driver.findElement(By.id("continue")).click();
        String err = driver.findElement(By.cssSelector("[data-test='error']")).getText();
        Assert.assertTrue(err.toLowerCase().contains("postal code"), "Expect postal code required error");
    }
}
