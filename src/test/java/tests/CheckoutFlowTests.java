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

    @Test(description = "Complete purchase flow end-to-end")
    public void completePurchase() {
        String item1 = "Sauce Labs Backpack";
        String item2 = "Sauce Labs Bike Light";
        
        ProductPage productPage = new LoginPage(driver)
                .openSaucedemo()
                .loginAccountAs(Config.STANDARD_USER, Config.PASSWORD);         
        
        productPage.addItemByName(item1);
        productPage.addItemByName(item2);
        CartPage cartPage = productPage.BuyProducts();
        CheckoutPage checkoutPage = cartPage.checkoutProduct();
        OrderConfirmationPage orderConfirmationPage = checkoutPage.PurchaseProduct("first name","last name","code");
        orderConfirmationPage.verifyOrderItems(item1,item2);
        orderConfirmationPage.verifyTotalPrice(43.18);
        orderConfirmationPage.verifyPaymentInformation("SauceCard #31337","Free Pony Express Delivery!");
        assertFinalResult();
    }

    @Test(description = "Missing postal code shows validation error")
    public void missingPostalCode() {   
        String item1 = "Sauce Labs Backpack";
        
        ProductPage productPage = new LoginPage(driver)
                .openSaucedemo()
                .loginAccountAs(Config.STANDARD_USER, Config.PASSWORD);         
        
        productPage.addItemByName(item1);
        CartPage cartPage = productPage.BuyProducts();
        CheckoutPage checkoutPage = cartPage.checkoutProduct();
        checkoutPage.PurchaseProduct("name a","name B","");
        checkoutPage.verifyCheckOutErrorMessage("Error: Postal Code is required");

        assertFinalResult();
    }
}
