package tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ProductPage;
import pages.LoginPage;
import utils.Config;
import utils.TestListener;

@Listeners(TestListener.class)
public class LoginTests extends BaseTest {

    @Test(description = "Valid login lands on product page", priority = 1)
    public void validLogin() {
        
        getExtentTest().info("🌐 Step 1: Navigate to SauceDemo website");
        LoginPage loginPage = new LoginPage(getDriver()).openSaucedemo();

        getExtentTest().info("📝 Step 2: Login with valid credentials (standard_user)");
        ProductPage productPage = loginPage.loginAccountAs(Config.STANDARD_USER, Config.PASSWORD);

        getExtentTest().info("✅ Step 3: Verify successful login and product page is displayed");
        productPage.verifyProductPageLoaded();

        assertFinalResult();
    }

    @Test(description = "Locked user shows error", priority = 2)
    public void lockedUserLogin() {

        getExtentTest().info("🌐 Step 1: Navigate to SauceDemo website");
        LoginPage loginPage = new LoginPage(getDriver()).openSaucedemo();

        getExtentTest().info("📝 Step 2: Attempt login with locked user credentials");
        loginPage.loginAccountAs(Config.LOCKED_USER, Config.PASSWORD);

        getExtentTest().info("✅ Step 3: Verify error message is displayed for locked user");
        loginPage.verifyLoginErrorMessage("Epic sadface: Sorry, this user has been locked out.");

        assertFinalResult();
    }

}
