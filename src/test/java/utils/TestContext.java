package utils;

import org.openqa.selenium.WebDriver;

/**
 * Thread-safe test context for managing WebDriver and TCResult instances
 * across different threads during parallel execution.
 */
public class TestContext {
    
    private static final ThreadLocal<TCResult> threadLocalResult = new ThreadLocal<>();
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    
    // TCResult management
    public static void setResult(TCResult result) {
        threadLocalResult.set(result);
    }
    
    public static TCResult getResult() {
        TCResult result = threadLocalResult.get();
        if (result == null) {
            result = new TCResult();
            threadLocalResult.set(result);
        }
        return result;
    }
    
    // WebDriver management
    public static void setDriver(WebDriver driver) {
        threadLocalDriver.set(driver);
    }
    
    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }
    
    // Cleanup methods
    public static void cleanup() {
        threadLocalResult.remove();
        threadLocalDriver.remove();
    }
    
    // Helper methods for verification
    public static void addSuccess(String message) {
        getResult().SetMessage("✓ " + message);
    }
    
    public static void addFailure(String message) {
        TCResult result = getResult();
        result.SetResult(false);
        result.SetMessage("✗ " + message);
    }
    
    public static void verifyCondition(boolean condition, String successMessage, String failureMessage) {
        if (condition) {
            addSuccess(successMessage);
        } else {
            addFailure(failureMessage);
        }
    }
}
