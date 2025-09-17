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
public class LoginTests extends BaseTest {

    @Test(description = "Valid login lands on inventory page")
    public void validLogin() {
        var driver = DriverFactory.getDriver();
        InventoryPage inventory = new LoginPage(driver)
                .open()
                .loginAs(Config.STANDARD_USER, Config.PASSWORD);
        Assert.assertTrue(inventory.isLoaded(), "Inventory page should be visible after login");
    }

    @Test(description = "Locked user shows error")
    public void lockedUserLogin() {
        var driver = DriverFactory.getDriver();
        LoginPage lp = new LoginPage(driver).open();
        lp.loginAs(Config.LOCKED_USER, Config.PASSWORD);
        Assert.assertTrue(lp.getError().toLowerCase().contains("locked"),
                "Expect locked out user error message");
    }
}
