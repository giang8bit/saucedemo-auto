# 🚀 SauceDemo Test Automation Framework

A comprehensive test automation framework for [SauceDemo](https://www.saucedemo.com) e-commerce application using modern testing technologies and best practices.

## 🛠️ Tech Stack

- **Java 17+** - Programming language
- **Selenium 4** - Web automation framework
- **TestNG** - Testing framework with parallel execution
- **Maven** - Build and dependency management
- **WebDriverManager** - Automatic driver management
- **ExtentReports** - Rich HTML test reporting
- **Page Object Model (POM)** - Design pattern for maintainable tests

## 📋 Prerequisites

### Required Software
- **Java 17+** - Verify with `java -version`
- **Maven 3.6+** - Verify with `mvn -version`
- **Chrome Browser** - Latest version recommended
- **Git** - For version control

### Optional
- **Edge Browser** - For cross-browser testing
- **Firefox Browser** - For cross-browser testing

## �️ Windows Setup Instructions

### Step 1: Install Java 17+
1. **Download Java JDK 17+**:
   - Visit [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)
   - Download Windows x64 installer (.msi)
   - Run installer and follow setup wizard

2. **Set JAVA_HOME Environment Variable**:
   ```cmd
   # Open Command Prompt as Administrator
   setx JAVA_HOME "C:\Program Files\Java\jdk-17" /M
   setx PATH "%JAVA_HOME%\bin;%PATH%" /M
   ```

3. **Verify Installation**:
   ```cmd
   java -version
   javac -version
   ```

### Step 2: Install Maven 3.6+
1. **Download Maven**:
   - Visit [Apache Maven](https://maven.apache.org/download.cgi)
   - Download Binary zip archive (apache-maven-3.x.x-bin.zip)
   - Extract to `C:\Program Files\Apache\maven`

2. **Set Maven Environment Variables**:
   ```cmd
   # Open Command Prompt as Administrator
   setx MAVEN_HOME "C:\Program Files\Apache\maven" /M
   setx PATH "%MAVEN_HOME%\bin;%PATH%" /M
   ```

3. **Verify Installation**:
   ```cmd
   mvn -version
   ```

### Step 3: Install Git
1. **Download Git**:
   - Visit [Git for Windows](https://git-scm.com/download/win)
   - Download and run the installer
   - Use default settings during installation

2. **Verify Installation**:
   ```cmd
   git --version
   ```

### Step 4: Install Chrome Browser
1. **Download Chrome**:
   - Visit [Google Chrome](https://www.google.com/chrome/)
   - Download and install latest version
   - Chrome will be automatically detected by WebDriverManager

## �🚀 Quick Start

### 1. Clone Repository
```cmd
# Open Command Prompt or PowerShell
git clone <repository-url>
cd saucedemo-auto
```

### 2. Verify Setup
```cmd
# Check all prerequisites
java -version
mvn -version
git --version
```

### 3. Run All Tests
```cmd
mvn clean test
```

### 4. View Reports
```cmd
# Open report in default browser
start reports\extent-report.html

# Or navigate manually to:
# <project-folder>\reports\extent-report.html
```

## 🧪 Test Execution Options

### Run Specific Test Classes
```bash
# Login functionality tests
mvn test -Dtest=LoginTests

# Shopping cart tests
mvn test -Dtest=CartTests

# Product sorting tests
mvn test -Dtest=SortingTests

# Complete checkout flow tests
mvn test -Dtest=CheckoutFlowTests
```

### Run Specific Test Methods
```bash
# Single test method
mvn test -Dtest=LoginTests#validLogin

# Multiple test methods
mvn test -Dtest=LoginTests#validLogin,CartTests#addToCartIncrementsBadge
```

### Cross-Browser Testing
```bash
# Chrome browser
mvn clean test -Dbrowser=edge

# Chrome browser
mvn clean test -Dbrowser=chrome
```

### 🖥️ Headless Mode Execution

Run tests without browser UI for faster execution and CI/CD environments.

#### Basic Headless Commands
```bash
# All tests in headless mode
mvn clean test -Dheadless=true

# Specific test class headless
mvn test -Dtest=LoginTests -Dheadless=true

```
#### Cross-Browser Headless
```bash

# Edge headless
mvn clean test -Dbrowser=edge -Dheadless=true

# Chrome headless
mvn clean test -Dbrowser=firefox -Dheadless=true
```

#### Parallel + Headless (Recommended for CI/CD)
```bash
# Fast parallel execution without UI
mvn clean test -Dheadless=true

# Custom thread count with headless
mvn clean test -Dheadless=true -DthreadCount=2
```

### Parallel Execution
Tests run in parallel by default (configured in `testng.xml`):
- **Parallel Level**: Classes
- **Thread Count**: 3
- **Data Provider Threads**: 1

To modify parallel execution:
```xml
<!-- In testng.xml -->
<suite name="SauceDemo Suite" parallel="classes" thread-count="2">
```

## 📁 Project Structure

```
saucedemo-auto/
├── src/
│   └── test/java/                   # All test-related code consolidated here
│       ├── pages/                   # Page Object Model classes
│       │   ├── BasePage.java        # Base page with common methods
│       │   ├── LoginPage.java       # Login page actions
│       │   ├── ProductPage.java     # Product listing page
│       │   ├── CartPage.java        # Shopping cart page
│       │   ├── CheckoutPage.java    # Checkout form page
│       │   └── OrderConfirmationPage.java # Order confirmation
│       ├── tests/                   # Test classes
│       │   ├── BaseTest.java        # Base test setup/teardown
│       │   ├── LoginTests.java      # Login functionality tests
│       │   ├── CartTests.java       # Cart functionality tests
│       │   ├── SortingTests.java    # Product sorting tests
│       │   └── CheckoutFlowTests.java # End-to-end checkout tests
│       └── utils/                   # Utilities and configuration
│           ├── Config.java          # Test configuration constants
│           ├── DriverFactory.java   # WebDriver management
│           ├── TestContext.java     # Thread-safe test context
│           ├── TestListener.java    # TestNG listener for reporting
│           └── TCResult.java        # Test result management
├── reports/                         # Generated test reports
├── target/                          # Maven build output
├── testng.xml                       # TestNG configuration
├── pom.xml                          # Maven dependencies
└── README.md                        # This file
```

## 📊 Test Reports

### ExtentReports (Primary)
- **Location**: `reports/extent-report.html`
- **Features**: Rich HTML report with screenshots, logs, and charts

### TestNG Reports (Secondary)
- **Location**: `target/surefire-reports/index.html`
- **Features**: Standard TestNG HTML reports

## ⚙️ Configuration
### Test Data Configuration
Edit `src/test/java/utils/Config.java` for test data:
```java
public static final String BASE_URL = "https://www.saucedemo.com/";
public static final String STANDARD_USER = "standard_user";
public static final String LOCKED_USER = "locked_out_user";
public static final String PASSWORD = "secret_sauce";
```

### TestNG Configuration
Edit `testng.xml` for execution settings:
```xml
<suite name="SauceDemo Suite" parallel="classes" thread-count="3">
  <test name="Chrome">
    <parameter name="browser" value="chrome"/>
    <classes>
      <class name="tests.LoginTests"/>
      <class name="tests.CartTests"/>
      <class name="tests.SortingTests"/>
      <class name="tests.CheckoutFlowTests"/>
    </classes>
  </test>
</suite>
```

