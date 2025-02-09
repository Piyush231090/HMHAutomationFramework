package com.hmh.automation.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.hmh.automation.base.WebBrowserDriverManager;
import com.hmh.automation.pages.TodoPage;
import com.hmh.automation.utils.ConfigReader;

/**
 * Test class for verifying the functionality of the TodoMVC application.
 * This class extends WebBrowserDriverManager to manage browser setup and teardown.
 */
public class TodoPageTest extends WebBrowserDriverManager {
    
    private WebDriver driver;
    private TodoPage todoPage;

    /**
     * Sets up the WebDriver and initializes the TodoPage object before running the tests.
     * The browser type and URL are fetched from the configuration file.
     */
    @BeforeClass
    public void setUpTest() {
        driver = setup(ConfigReader.getConfig().getProperty("browser"));  // Start browser
        driver.get(ConfigReader.getConfig().getProperty("url"));  // Navigate to the application
        todoPage = new TodoPage(driver);  // Initialize Page Object Model (POM)
    }

    /**
     * Test case to verify adding a single todo item.
     * It adds one todo item and verifies that the count is updated correctly.
     */
    @Test(priority = 1)
    public void testAddTodoItem() {
        todoPage.addTodoItem("Buy groceries");
        Assert.assertEquals(todoPage.getTodoCount(), 1, "Todo count mismatch!");
    }

    /**
     * Test case to verify adding multiple todo items.
     * It adds multiple tasks and checks if the count matches the expected number.
     *
     * @throws InterruptedException to handle any thread sleep delay.
     */
    @Test(priority = 2)
    public void testAddMultipleTodoItems() throws InterruptedException {
        todoPage.addTodoItem("Read book");
        todoPage.addTodoItem("Go for a walk");
        Assert.assertEquals(todoPage.getTodoCount(), 3, "Incorrect number of todos added!");
    }

    /**
     * Test case to mark a specific todo item as completed.
     * It completes the first task and asserts whether it is marked as completed.
     *
     * @throws InterruptedException to handle UI synchronization delay.
     */
    @Test(priority = 3)
    public void testCompleteTodo() throws InterruptedException {
        todoPage.completeTodoItem(0);  // Complete the first task
        Assert.assertTrue(todoPage.isTaskCompleted(0), "Task was not marked as completed!");
    }

    /**
     * Test case to mark all tasks as completed.
     * This method ensures that every task in the list is marked as completed.
     *
     * @throws InterruptedException to handle UI synchronization delay.
     */
    @Test(priority = 4)
    public void testAllMarkedAsCompleted() throws InterruptedException {
        todoPage.markAllAsCompleted();  // Click the button to mark all tasks as complete
        Assert.assertTrue(todoPage.isAllTaskCompleted(), "Tasks are still marked as not completed!");
    }

    /**
     * Test case to verify the deletion of a todo item.
     * Deletes the first item from the list and checks if the count is reduced accordingly.
     *
     * @throws InterruptedException to handle UI synchronization delay.
     */
    @Test(priority = 5)
    public void testDeleteTask() throws InterruptedException {
        todoPage.deleteTodoItem1(0);  // Delete the first task
        Assert.assertEquals(todoPage.getTodoCount(), 2, "Todo count mismatch after deletion!");
    }

    /**
     * Cleans up and closes the browser after test execution.
     */
    @AfterClass
    public void tearDownTest() {
        teardown();  
    }
}
