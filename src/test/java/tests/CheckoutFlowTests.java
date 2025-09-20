package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ProductPage;
import pages.LoginPage;
import pages.OrderConfirmationPage;
import pages.CartPage;
import pages.CheckoutPage;
import utils.Config;
import utils.TestListener;

@Listeners(TestListener.class)
public class CheckoutFlowTests extends BaseTest {

    @Test(description = "Complete purchase flow end-to-end", priority = 1)
    public void completePurchase() {
        String item1 = "Sauce Labs Backpack";
        String item2 = "Sauce Labs Bike Light";

        getExtentTest().info("🌐 Step 1: Navigate to SauceDemo and login with valid credentials");
        ProductPage productPage = new LoginPage(getDriver())
                .openSaucedemo()
                .loginAccountAs(Config.STANDARD_USER, Config.PASSWORD);

        getExtentTest().info("📝 Step 2: Add two items to cart (" + item1 + ", " + item2 + ")");
        productPage.addItemByName(item1);
        productPage.addItemByName(item2);

        getExtentTest().info("📝 Step 3: Navigate to cart and proceed to checkout");
        CartPage cartPage = productPage.BuyProducts();
        CheckoutPage checkoutPage = cartPage.checkoutProduct();

        getExtentTest().info("📝 Step 4: Fill checkout information and proceed to order confirmation");
        OrderConfirmationPage orderConfirmationPage = checkoutPage.PurchaseProduct("first name", "last name", "code");

        getExtentTest().info("✅ Step 5: Verify order details (items, price, payment info)");
        orderConfirmationPage.verifyOrderItems(item1, item2);
        orderConfirmationPage.verifyTotalPrice(43.18);
        orderConfirmationPage.verifyPaymentInformation("SauceCard #31337", "Free Pony Express Delivery!");

        getExtentTest().info("✅ Step 6: Complete order and verify success message");
        orderConfirmationPage.clickFinishOrder();
        orderConfirmationPage.verifyThankYouMessage();

        getExtentTest().info("✅ Step 7: Return to home page and verify navigation");
        orderConfirmationPage.clickBackHome();
        productPage.verifyProductPageLoaded();

        assertFinalResult();
    }

    @Test(description = "Missing postal code shows validation error", priority = 2)
    public void missingPostalCode() {
        String item1 = "Sauce Labs Backpack";

        getExtentTest().info("🌐 Step 1: Navigate to SauceDemo and login with valid credentials");
        ProductPage productPage = new LoginPage(getDriver())
                .openSaucedemo()
                .loginAccountAs(Config.STANDARD_USER, Config.PASSWORD);

        getExtentTest().info("📝 Step 2: Add item to cart and proceed to checkout");
        productPage.addItemByName(item1);
        CartPage cartPage = productPage.BuyProducts();
        CheckoutPage checkoutPage = cartPage.checkoutProduct();

        getExtentTest().info("📝 Step 3: Submit checkout form with missing postal code");
        checkoutPage.PurchaseProduct("name a", "name B", "");

        getExtentTest().info("✅ Step 4: Verify validation error message is displayed");
        checkoutPage.verifyCheckOutErrorMessage("Error: Postal Code is required");

        assertFinalResult();
    }
}
