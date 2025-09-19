package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.DriverFactory;
import utils.TCResult;
import utils.TestContext;

public class BaseTest {
    public WebDriver driver;
    public TCResult Result;

    // For thread safety in parallel execution
    private static final ThreadLocal<TCResult> threadLocalResult = new ThreadLocal<>();
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setup(@Optional("chrome") String browser) {
        // Initialize TCResult for this thread
        Result = new TCResult();
        threadLocalResult.set(Result);
        TestContext.setResult(Result); // Also set in TestContext for global access

        // Initialize WebDriver for this thread
        DriverFactory.initDriver(browser);
        driver = DriverFactory.getDriver();
        threadLocalDriver.set(driver);
        TestContext.setDriver(driver); // Also set in TestContext for global access
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        DriverFactory.quitDriver();

        // Clean up ThreadLocal variables to prevent memory leaks
        threadLocalResult.remove();
        threadLocalDriver.remove();
        TestContext.cleanup(); // Clean up TestContext as well
    }

    // Helper method to perform final assertion - call this at end of each test
    protected void assertFinalResult() {
        Assert.assertTrue(Result.GetResult(), Result.GetMessage());
    }

    // Thread-safe getter methods
    protected WebDriver getThreadSafeDriver() {
        return threadLocalDriver.get();
    }

    protected TCResult getThreadSafeResult() {
        return threadLocalResult.get();
    }
}
