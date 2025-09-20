package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ProductPage;
import pages.LoginPage;
import utils.Config;
import utils.TestListener;

@Listeners(TestListener.class)
public class CartTests extends BaseTest {

    @Test(description = "Add item increases cart badge", priority = 1)
    public void addToCartIncrementsBadge() {
        String item1 = "Sauce Labs Backpack";
        String item2 = "Sauce Labs Bike Light";

        getExtentTest().info("🌐 Step 1: Navigate to SauceDemo and login with valid credentials");
        ProductPage productPage = new LoginPage(getDriver())
                .openSaucedemo()
                .loginAccountAs(Config.STANDARD_USER, Config.PASSWORD);

        getExtentTest().info("📝 Step 2: Add first item (" + item1 + ") to cart");
        productPage.addItemByName(item1);
        
        getExtentTest().info("✅ Step 3: Verify cart badge shows count of 1");
        productPage.verifyCurrentItemInCartBadge(1);

        getExtentTest().info("📝 Step 4: Add second item (" + item2 + ") to cart");
        productPage.addItemByName(item2);

        getExtentTest().info("✅ Step 5: Verify cart badge shows count of 2");
        productPage.verifyCurrentItemInCartBadge(2);

        assertFinalResult();
    }
}
