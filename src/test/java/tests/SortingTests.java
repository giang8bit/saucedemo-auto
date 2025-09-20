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
        ProductPage productPage = new LoginPage(getDriver())
                .openSaucedemo()
                .loginAccountAs(Config.STANDARD_USER, Config.PASSWORD);

        productPage.sortBy("lohi");
        productPage.verifyIsSortedByPriceLowHigh();
        productPage.sortBy("hilo");
        productPage.verifyIsSortedByPriceHighLow();
        assertFinalResult();
    }
}
