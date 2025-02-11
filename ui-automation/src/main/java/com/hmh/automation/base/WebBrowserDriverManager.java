package com.hmh.automation.base;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;




public class WebBrowserDriverManager {
	
	private static final Logger logger = LogManager.getLogger(WebBrowserDriverManager.class);
	protected WebDriver driver;
    public WebDriver setup(String browser) {
    	logger.info("Initializing WebDriver...");
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver(); 
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
		return driver;
    }

    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

