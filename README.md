# saucedemo-automation (Java + Selenium + TestNG + Maven + POM)

Full suite for https://www.saucedemo.com using Selenium 4, TestNG, WebDriverManager, and ExtentReports.

## Prereqs (Windows)
- Java 17+ (`java -version`)
- Maven (`mvn -v`)
- Chrome installed

## Run
```bash
mvn clean test
```
Open report:
- `reports/extent-report.html`

### Cross-browser
Edit `testng.xml` `<parameter name="browser" value="edge"/>` or run:
```bash
mvn clean test -Dbrowser=edge
```

### Video
Record `mvn test` with **Xbox Game Bar** (Win+G) or **OBS**; save to `artifacts/automation_run.mp4`.
