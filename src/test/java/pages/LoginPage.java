package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import utils.Config;
import utils.TestContext;

public class LoginPage extends BasePage {

    private static final By usernameTxt = By.id("user-name");
    private static final By passwordTxt = By.id("password");
    private static final By loginBtn = By.id("login-button");
    private static final By errorTxt = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage openSaucedemo() {
        driver.get(Config.BASE_URL);
        return this;
    }

    public ProductPage loginAccountAs(String user, String pass) {
        sendKeyToElement(usernameTxt, user);
        sendKeyToElement(passwordTxt, pass);
        clickToElement(loginBtn);
        return new ProductPage(driver);
    }

    public void verifyLoginErrorMessage(String msg) {
        String actualError = getError();
        String expectedLower = msg.toLowerCase();
        String actualLower = actualError.toLowerCase();

        if (!actualLower.contains(expectedLower)) {
            TestContext.addFailure("Login error message verification failed!\nExpected to contain: '" + msg
                    + "'\nActual message: '" + actualError + "'\n");
        }
    }

    private String getError() {
        return getTextElement(errorTxt);
    }

}
