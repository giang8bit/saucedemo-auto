package pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Config;
import utils.TestContext;

public abstract class BasePage {

    protected WebDriver driver = null;
    private int shortTimeout = 3;
    private int longTimeout = 15;

    public BasePage(WebDriver driver) {
        super();
        this.driver = driver;
    }

    public void clickToElement(By pBy) {
        WebElement element = driver.findElement(pBy);
        element.click();
    }

    public void clickWhenClickable(By pBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(pBy));
        element.click();
    }

    public void clickToElementByJs(By pBy) {
        WebElement element = driver.findElement(pBy);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void sendKeyToElement(By pBy, String pTextValue) {
        WebElement element = driver.findElement(pBy);
        element.clear();
        element.sendKeys(pTextValue);
    }

    public String getTextElement(By pBy) {
        WebElement element = driver.findElement(pBy);
        return element.getText();
    }

    public String getTextElement(WebElement webElement) {
        return webElement.getText();
    }

    public void selectItemInDropdown(By pBy, String valueItem) {
        Select select = new Select(driver.findElement(pBy));
        select.selectByVisibleText(valueItem);
    }

    public List<WebElement> findElements(By pBy) {
        return driver.findElements(pBy);
    }

    public void RefreshPage() {
        driver.navigate().refresh();
    }

    public boolean isElementDisplayed(By pBy) {
        WebElement element = driver.findElement(pBy);
        waitToElementVisible(pBy);
        return element.isDisplayed();
    }

    public void waitToElementVisible(By pBy) {
        WebDriverWait waitExplicit = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
        waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(pBy));
    }

    public void verifyElementVisible(By pBy, String pElementName) {
        WebElement element = driver.findElement(pBy);
        if (!element.isDisplayed()) {
            TestContext.addFailure("\r\n" + pElementName + " is invisible, it has to be visible");
        }
    }

    public void verifyElementText(By pBy, String pElementName, String pExpectedResult) {
        WebElement element = driver.findElement(pBy);
        String actualResult = element.getText();
        if (!actualResult.contains(pExpectedResult)) {
            TestContext.addFailure("\r\n" + pElementName + " with expected text: " + pExpectedResult
                    + ". Current text is: " + actualResult);
        }
    }

    public void verifyWebsiteTitle(String pExpectedResult) {
        String ActualResult = driver.getTitle();
        if (!ActualResult.equals(pExpectedResult)) {
            TestContext.addFailure("Page title is different\nExpected to contain: '" + pExpectedResult
                    + "'\nActual title: '" + ActualResult + "'\n");
        }

    }
}
