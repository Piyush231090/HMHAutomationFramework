package com.hmh.automation.base;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.hmh.automation.utils.ScreenshotUtil;

public class TestDriverManager {

	protected static ExtentReports extent;
	protected static ExtentTest test;

	private static final Logger logger = LogManager.getLogger(TestDriverManager.class);
	protected WebDriver driver;

	@BeforeSuite
	public void setupExtent() {
		extent = ExtentReportManager.getInstance();
	}

	public WebDriver setup(String browser, boolean runHeadless) {
		logger.info("Initializing WebDriver...{}",runHeadless);
        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (runHeadless) {
                    chromeOptions.addArguments("--headless");
                }
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (runHeadless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		logger.info("WebDriver successfully initialized...");
		return driver;
	}

	@AfterMethod
	public void tearDown(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
			test.fail("Test Failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.pass("Test Passed");
		}
	}

	public void teardown() {
		logger.info("Ending WebDriver Session...");
		if (driver != null) {
			driver.quit();
		}
	}

	@AfterSuite
	public void generateReport() {
		ExtentReportManager.flushReport();

	}
}
