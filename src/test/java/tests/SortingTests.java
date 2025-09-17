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
public class SortingTests extends BaseTest {

    @Test(description = "Sort by price low to high")
    public void sortLowToHigh() {
        var driver = DriverFactory.getDriver();
        InventoryPage inv = new LoginPage(driver)
                .open()
                .loginAs(Config.STANDARD_USER, Config.PASSWORD);

        inv.sortBy("lohi");
        Assert.assertTrue(inv.isSortedByPriceLowHigh(), "Items should be sorted by price low→high");
    }
}
