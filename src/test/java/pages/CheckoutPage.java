package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.TestContext;

public class CheckoutPage extends BasePage{

    private final By errorTxt = By.xpath("//*[@data-test='error']");
    private final By continueBtn = By.id("continue");

    private final By firstNameTxt = By.id("first-name");
    private final By lastNameTxt = By.id("last-name");
    private final By postalCodeTxt = By.id("postal-code");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }
    


    // Method to fill postal code separately
    public CheckoutPage fillPostalCode(String postalCode) {
        sendKeyToElement(postalCodeTxt, postalCode);
        return this;
    }

    // Complete checkout form with all fields
    public CheckoutPage fillCheckoutForm(String firstName, String lastName, String postalCode) {
        sendKeyToElement(firstNameTxt, firstName);
        sendKeyToElement(lastNameTxt, lastName);
        sendKeyToElement(postalCodeTxt, postalCode);
        return this;
    }
    
    public OrderConfirmationPage PurchaseProduct(String firstName, String lastName, String postalCode) {
        fillCheckoutForm(firstName, lastName, postalCode);
        clickToElement(continueBtn);
        return new OrderConfirmationPage(driver) ;
    }  

    public void verifyCheckOutErrorMessage(String msg) {
        String actualError = getTextElement(errorTxt);
        String expectedLower = msg.toLowerCase();
        String actualLower = actualError.toLowerCase();

        if (!actualLower.contains(expectedLower)) {
            TestContext.addFailure("Checkout error message verification failed!\nExpected to contain: '" + msg + "'\nActual message: '" + actualError + "'\n");
        } 
    }

}
