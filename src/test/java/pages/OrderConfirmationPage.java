package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.TestContext;
import java.util.List;
import java.util.ArrayList;

public class OrderConfirmationPage extends BasePage {

    private final By orderItemsContainer = By.className("cart_list");
    private final By cartItems = By.className("cart_item");
    private final By itemNames = By.className("inventory_item_name");
    private final By itemPrices = By.className("inventory_item_price");

    private final By subtotalLabel = By.className("summary_subtotal_label");
    private final By taxLabel = By.className("summary_tax_label");
    private final By totalLabel = By.className("summary_total_label");

    private final By paymentInfoLabel = By
            .xpath("//div[@class='summary_info_label' and text()='Payment Information:']");
    private final By paymentInfoValue = By
            .xpath("//div[@class='summary_info_label' and text()='Payment Information:']/following-sibling::div");
    private final By shippingInfoLabel = By
            .xpath("//div[@class='summary_info_label' and text()='Shipping Information:']");
    private final By shippingInfoValue = By
            .xpath("//div[@class='summary_info_label' and text()='Shipping Information:']/following-sibling::div");

    private final By finishBtn = By.id("finish");
    private final By backHomeBtn = By.id("back-to-products");

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public OrderConfirmationPage verifyOrderItems(String... expectedItems) {
        // Get all item names from the order summary
        List<WebElement> itemElements = findElements(itemNames);
        List<String> actualItems = new ArrayList<>();

        for (WebElement item : itemElements) {
            actualItems.add(getTextElement(item));
        }

        // Check if all expected items are present
        for (String expectedItem : expectedItems) {
            boolean itemFound = false;
            for (String actualItem : actualItems) {
                if (actualItem.equals(expectedItem)) {
                    itemFound = true;
                    break;
                }
            }

            if (!itemFound) {
                TestContext.addFailure("Order item verification failed!\nExpected item " + expectedItem
                        + " not found in order\nActual items: " + actualItems);
                return this;
            }
        }

        return this;
    }

    public OrderConfirmationPage verifyPaymentInformation(String expectedPaymentInfo, String expectedShippingInfo) {
        // Get actual payment and shipping information from the page
        String actualPaymentInfo = getTextElement(paymentInfoValue);
        String actualShippingInfo = getTextElement(shippingInfoValue);

        // Verify payment information (using contains for flexibility)
        if (!actualPaymentInfo.contains(expectedPaymentInfo)) {
            TestContext.addFailure(
                    "Payment information verification failed!\nExpected to contain: " + expectedPaymentInfo
                            + "\nActual: " + actualPaymentInfo);
        }
        // Verify shipping information
        if (!actualShippingInfo.contains(expectedShippingInfo)) {
            TestContext.addFailure("Shipping information verification failed!\nExpected to contain: "
                    + expectedShippingInfo + "\nActual: " + actualShippingInfo);
        }
        return this;
    }

    public OrderConfirmationPage verifyTotalPrice(double expectedTotal) {
        String subtotalText = getTextElement(subtotalLabel);
        String taxText = getTextElement(taxLabel);
        String totalText = getTextElement(totalLabel);

        double subtotal = extractPriceFromText(subtotalText);
        double tax = extractPriceFromText(taxText);
        double actualTotal = extractPriceFromText(totalText);

        if (actualTotal != expectedTotal) {
            TestContext.addFailure(
                    "Total price verification failed!\nExpected: $" + expectedTotal + "\nActual: $" + actualTotal
                            + "\nSubtotal: $" + subtotal + " + Tax: $" + tax);
        }

        return this;
    }

    private double extractPriceFromText(String text) {
        try {
            // Remove all non-numeric characters except decimal point
            String priceText = text.replaceAll("[^0-9.]", "");
            return Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            // If parsing fails, return 0 handle the error
            return 0.0;
        }
    }

    public OrderConfirmationPage clickFinishOrder() {
        clickToElement(finishBtn);
        return this;
    }

    public OrderConfirmationPage clickBackHome() {
        clickToElement(backHomeBtn);
        return this;
    }

    public void verifyThankYouMessage() {
        By thankYouHeader = By.className("complete-header");
        String expectedMessage = "Thank you for your order!";

        String actualMessage = getTextElement(thankYouHeader);
        if (!actualMessage.contains(expectedMessage)) {
            TestContext.addFailure("Thank you message verification failed!\nExpected to contain: '" + expectedMessage
                    + "'\nActual: '" + actualMessage + "'");
        }

    }

}