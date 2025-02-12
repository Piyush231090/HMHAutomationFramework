# HMHAutomationFramework
This project is a UI automation framework for TodoMVC page built using Java, Selenium WebDriver, and TestNG, following the Page Object Model (POM) design pattern. 
The framework is designed to ensure maintainability, reusability, and scalability for web UI testing.

# Technologies Used

*Programming Language: Java 

*Automation Tool: Selenium WebDriver

*Test Framework: TestNG

Design Pattern: Page Object Model (POM)

Build Tool: Maven

Dependency Management: Maven (pom.xml)

Logging: Log4j

Reports: Extent Reports / TestNG Reports

WebDriver Management: WebDriverManager (optional)

IDE Used: Eclipse

# Features

Page Object Model (POM) implementation for better test maintenance.

TestNG-based execution with sequential/parallel test execution support.

Maven integration for easy dependency management and test execution.

Headless execution support for CI/CD environments.

Reporting with Extent Reports/TestNG Reports for test results analysis.


# Prerequisites

Ensure the following tools are installed on your system:

Java (JDK 8 or higher) - Install and set up environment variables (JAVA_HOME).

Maven - Install Maven and configure it in the system path.

ChromeDriver / FirefoxDriver - Ensure appropriate WebDriver binaries are available, or use WebDriverManager.

IDE - IntelliJ IDEA / Eclipse for running the tests.

# How to Set Up and Run Tests
## Clone the Repository
git clone https://github.com/Piyush231090/HMHAutomationFramework.git
cd ui-automation

## Install Dependencies
Run the below command to download all required dependencies:
mvn clean install

## Run Tests
Run all test cases:
mvn test

## Run the test suite using TestNG:
Run the below command:
mvn test -DxmlFilePath='src\test\resources\testng.xml'

## Report Generation
After execution, reports will be available:
TestNG Reports: test-output/index.html
TestNG Reports: test-output/emailable-report.html
Extent Reports: target/extent-reports.html

# Configuration
The config.properties file (located in src/test/resources/) contains environment-specific settings like URLs, browser, wait and runheadless.
browser=chrome ---> This suppport multiple browsers, chrome/firefox(whichever mentioned)
url=https://todomvc.com/examples/react/dist/  ---> this gives the application url
implicitWait=10  ---> used for wait
runheadless=True ---> runheadless is marked as True to sun the testcases without opening ui/browser. If false it will open UI


## Future Enhancements
Parameterization can be done to run the test on multiple set of data
Grouping of test for different test suites 
Add support for CI/CD integration (Jenkins/GitHub Actions)
Implement Docker-based Selenium Grid for distributed testing
Logging using Extent report library
