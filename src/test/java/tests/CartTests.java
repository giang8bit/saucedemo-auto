package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.Config;
import utils.DriverFactory;
import utils.TestListener;

@Listeners(TestListener.class)
public class CartTests extends BaseTest {

    @Test(description = "Add item increases cart badge")
    public void addToCartIncrementsBadge() {
        var driver = DriverFactory.getDriver();
        InventoryPage inv = new LoginPage(driver)
                .open()
                .loginAs(Config.STANDARD_USER, Config.PASSWORD);

        String target = "Sauce Labs Backpack";
        inv.addItemByName(target);
        Assert.assertEquals(inv.cartCount(), "1", "Cart badge should show 1 after adding one item");
    }
}
