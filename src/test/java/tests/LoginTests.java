package tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ProductPage;
import pages.LoginPage;
import utils.Config;
import utils.TestListener;

@Listeners(TestListener.class)
public class LoginTests extends BaseTest {

    @Test(description = "Valid login lands on inventory page")
    public void validLogin() {
        LoginPage loginPage = new LoginPage(driver).openSaucedemo();
        ProductPage productPage;
        
        productPage = loginPage.loginAccountAs(Config.STANDARD_USER, Config.PASSWORD);
        // Verify that we successfully landed on the product page
        productPage.verifyProductPageLoaded("Swag Labs");

        assertFinalResult();
    }

    @Test(description = "Locked user shows error")
    public void lockedUserLogin() {
        LoginPage loginPage = new LoginPage(driver).openSaucedemo();

        loginPage.loginAccountAs(Config.LOCKED_USER, Config.PASSWORD);
        loginPage.verifyLoginErrorMessage("Epic sadface: Sorry, this user has been locked out.");
        
        assertFinalResult();
    }

}
