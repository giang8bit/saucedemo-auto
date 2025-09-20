package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.DriverFactory;
import utils.TCResult;
import utils.TestContext;

public class BaseTest {
    public TCResult Result;

    private static final ThreadLocal<TCResult> threadLocalResult = new ThreadLocal<>();
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    @Parameters({ "browser" })
    @BeforeMethod(alwaysRun = true)
    public void setup(@Optional("chrome") String browser) {
        // Initialize TCResult for this thread
        Result = new TCResult();
        threadLocalResult.set(Result);
        TestContext.setResult(Result); // Also set in TestContext for global access

        // Initialize WebDriver for this thread
        DriverFactory.initDriver(browser);
        WebDriver webDriver = DriverFactory.getDriver();
        threadLocalDriver.set(webDriver);
        TestContext.setDriver(webDriver); // Also set in TestContext for global access
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        DriverFactory.quitDriver();

        threadLocalResult.remove();
        threadLocalDriver.remove();
        TestContext.cleanup();
    }

    protected void assertFinalResult() {
        Assert.assertTrue(Result.GetResult(), Result.GetMessage());
    }

    // Thread-safe getter methods
    protected WebDriver getDriver() {
        return TestContext.getDriver();
    }

    protected WebDriver getThreadSafeDriver() {
        return threadLocalDriver.get();
    }

    protected TCResult getThreadSafeResult() {
        return threadLocalResult.get();
    }
}
