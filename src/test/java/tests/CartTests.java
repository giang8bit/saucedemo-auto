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

        ProductPage productPage = new LoginPage(getDriver())
                .openSaucedemo()
                .loginAccountAs(Config.STANDARD_USER, Config.PASSWORD);

        productPage.addItemByName(item1);
        productPage.verifyCurrentItemInCartBadge(1);
        productPage.addItemByName(item2);
        productPage.verifyCurrentItemInCartBadge(2);
        assertFinalResult();
    }
}
