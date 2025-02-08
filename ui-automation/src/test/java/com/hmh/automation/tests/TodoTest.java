package com.hmh.automation.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.hmh.automation.base.WebBrowserDriverManager;
import com.hmh.automation.pages.TodoPage;
import com.hmh.automation.utils.ConfigReader;

public class TodoTest extends WebBrowserDriverManager{
	private WebDriver driver;
    private TodoPage todoPage;

    @BeforeClass
    public void setUpTest() {
        driver = setup(ConfigReader.getConfig().getProperty("browser"));  // Start browser
        driver.get(ConfigReader.getConfig().getProperty("url"));
        todoPage = new TodoPage(driver);
    }


    @AfterClass
    public void tearDownTest() {
        teardown();  // Close browser
    }

}
