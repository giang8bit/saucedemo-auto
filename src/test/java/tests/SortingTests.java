package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ProductPage;
import pages.LoginPage;
import utils.Config;
import utils.TestListener;

@Listeners(TestListener.class)
public class SortingTests extends BaseTest {

    @Test(description = "Sort by price low to high/high to low", priority = 2)
    public void sortLowToHigh() {
        
        getExtentTest().info("🌐 Step 1: Navigate to SauceDemo and login with valid credentials");
        ProductPage productPage = new LoginPage(getDriver())
                .openSaucedemo()
                .loginAccountAs(Config.STANDARD_USER, Config.PASSWORD);

        getExtentTest().info("📝 Step 2: Sort products by price (Low to High)");
        productPage.sortBy("lohi");

        getExtentTest().info("✅ Step 3: Verify products are sorted by price Low to High");
        productPage.verifyIsSortedByPriceLowHigh();

        getExtentTest().info("📝 Step 4: Sort products by price (High to Low)");
        productPage.sortBy("hilo");

        getExtentTest().info("✅ Step 5: Verify products are sorted by price High to Low");
        productPage.verifyIsSortedByPriceHighLow();

        assertFinalResult();
    }
}
