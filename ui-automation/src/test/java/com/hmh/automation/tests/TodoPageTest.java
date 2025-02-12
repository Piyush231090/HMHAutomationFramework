package com.hmh.automation.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.hmh.automation.base.ExtentReportManager;
import com.hmh.automation.base.TestDriverManager;
import com.hmh.automation.pages.TodoPage;
import com.hmh.automation.utils.ConfigReader;

/**
 * Test class for verifying the functionality of the TodoMVC application.
 * This class extends WebBrowserDriverManager to manage browser setup and teardown.
 */
public class TodoPageTest extends TestDriverManager{
    
    private WebDriver driver;
    private TodoPage todoPage;
    private static final String TASK_NAME_LONG_TEXT="this is supposed to be a very long task and you have to perform it in very less time";
    private static final Logger logger = LogManager.getLogger(TodoPageTest.class);


    /**
     * Sets up the WebDriver and initializes the TodoPage object before running the tests.
     * The browser type and URL are fetched from the configuration file.
     */
    @BeforeClass
    public void setUpTest() {
    	logger.info("Setting up the test..");
    	test = ExtentReportManager.createTest("ToDo list test report");
        driver = setup(ConfigReader.getConfig().getProperty("browser"),Boolean.valueOf(ConfigReader.getConfig().getProperty("runheadless")));  // Start browser
        driver.get(ConfigReader.getConfig().getProperty("url"));  // Navigate to the application
        todoPage = new TodoPage(driver);  // Initialize Page Object Model (POM)
    }

    /**
     * Test case to verify adding a single todo item.
     * It adds one todo item and verifies that the count is updated correctly.
     */
    @Test(priority = 1)
    public void testAddTodoItem() {
    	logger.info("Inside testAddTodoItem to add task in todo ..");
        todoPage.addTodoItem("Buy groceries");
        Assert.assertEquals(todoPage.getTodoCount(), 1, "Todo count mismatch!");
        
    }


    /**
     * Test case to verify adding multiple todo items.
     * It adds multiple tasks and checks if the count matches the expected number.
     */
    @Test(priority = 2)
    public void testAddMultipleTodoItems(){
    	logger.info("Inside testAddMultipleTodoItems to add multiple tasks in todo ..");
        todoPage.addTodoItem("Read book");
        todoPage.addTodoItem("Go for a walk");
        Assert.assertEquals(todoPage.getTodoCount(), 3, "Incorrect number of todos added!");
    }
    
    
    /**
     * Test case to mark a specific todo item as completed.
     * It completes the first task and asserts whether it is marked as completed.
     */
    @Test(priority = 3)
    public void testCompleteTodo() {
    	logger.info("Inside testCompleteTodo to complete a task");
        todoPage.completeTodoItem(0);  
        Assert.assertTrue(todoPage.isTaskCompleted(0), "Task was not marked as completed!");
    }

    /**
     * Test case to mark all tasks as completed.
     * This method ensures that every task in the list is marked as completed.
     */
    @Test(priority = 4)
    public void testAllMarkedAsCompleted() {
    	logger.info("Inside testAllMarkedAsCompleted method to mark all task as complete");
        todoPage.markAllAsCompleted();
        Assert.assertTrue(todoPage.isAllTaskCompleted(), "Tasks are still marked as not completed!");
    }

    /**
     * Test case to verify the deletion of a todo item.
     * Deletes the first item from the list and checks if the count is reduced accordingly.
     *
     */
    @Test(priority = 5)
    public void testDeleteTask() {
    	logger.info("Inside testDeleteTask method to delete a task");
        todoPage.deleteTodoItem(0);  // Delete the first task
        Assert.assertEquals(todoPage.getTodoCount(), 2, "Todo count mismatch after deletion!");
    }


    /**
     * Test case to verify the Clear Completed Button.
     * Deletes the completed task and reduces the list by 1.
     */
    @Test(priority = 6)
    public void testClearCompletedTask() {
    	logger.info("Inside testClearCompletedTask method to delete a completed task");
    	Assert.assertEquals(todoPage.getTodoCount(), 2);
    	todoPage.completeTodoItem(0);
    	todoPage.clearCompleteToDoItems();
    	Assert.assertEquals(todoPage.getTodoCount(), 1);
    }
    
    
    /**
     * Test case to verify the 'completed' button returns completed task list and remaining task should be in active list
     */
    @Test(priority = 7)
    public void testCompletedTaskFilter() {
    	logger.info("Inside testCompletedTaskFilter method to check completed task list");
    	Assert.assertEquals(todoPage.listOfCompletedToDoItems(),0);
    	todoPage.completeTodoItem(0);
    	todoPage.filterCompletedItems();
    	Assert.assertEquals(todoPage.listOfCompletedToDoItems(),1);
    	Assert.assertEquals(todoPage.listOfActiveToDoItems(),0);
    }
    
    /**
     * Test case to verify the 'Active' button returns Active task list and remaining tasks should be in completed list
     */
    @Test(priority = 8)
    public void testActiveTaskFilter() {
    	logger.info("Inside testActiveTaskFilter method to check Active task list");
    	
    	todoPage.addTodoItem("go for a spin");
    	todoPage.addTodoItem("watch movie");
    	
    	todoPage.filterActiveToDoItems();
    	Assert.assertTrue(todoPage.listOfActiveToDoItems()>0);
    	Assert.assertEquals(todoPage.listOfCompletedToDoItems(),0);
    }
    
    /**
     * Test case to verify the 'All' button returns All tasks in the list
     */
    @Test(priority = 9)
    public void testAllTasksFilter() {
    	logger.info("Inside testAllTasksFilter method to check all tasks in the list");
    	todoPage.filterAllToDoItems();
    	Assert.assertTrue(todoPage.getTodoCount()>0);
    }
    
    
    /**
     * Test case to verify the 'All' button returns All tasks in
     */
    @Test(priority = 10)
    public void testWhenAllToDOAreComplete() {
    	logger.info("Inside testAllTasksFilter method to check all completed task list");
    	todoPage.markAllAsCompleted();
    	Assert.assertTrue(todoPage.listOfCompletedToDoItems()>0);
    	Assert.assertTrue(todoPage.getTodoCount()>0);
    	Assert.assertEquals(todoPage.listOfActiveToDoItems(),0);
    	
    }
    
    /**
     * Test case to verify the 'All' button returns All tasks in
     */
    @Test(priority = 11)
    public void testWhenAllToDOAreActive() {
    	logger.info("Inside testAllTasksFilter method to check all Active task list");
    	todoPage.markAllAsCompleted();
    	Assert.assertEquals(todoPage.listOfCompletedToDoItems(),0);
    	Assert.assertTrue(todoPage.getTodoCount()>0);
    	Assert.assertTrue(todoPage.listOfActiveToDoItems()>0);

    }
    
    /**
     * Test case to filter after deleting a completed task
     */
    @Test(priority = 12)
    public void testFilterAfterDeletingCompletedTask() {
    	logger.info("Inside testAllTasksFilter method to check list in all filters after deleting completed task");
    	todoPage.addTodoItem("this is good");
    	todoPage.addTodoItem("this is really good");
    	todoPage.completeTodoItem(0);
    	todoPage.completeTodoItem(1);
    	todoPage.deleteTodoItem(0);
    	Assert.assertEquals(todoPage.listOfCompletedToDoItems(),1);
    	Assert.assertTrue(todoPage.getTodoCount()>0);
    	Assert.assertTrue(todoPage.listOfActiveToDoItems()>0);
    	
    }
    
    /**
     * Test case to filter after deleting an active task
     */
    @Test(priority = 13)
    public void testFilterAfterDeletingActiveTask() {
    	logger.info("Inside testAllTasksFilter method to check list in all filters after deleting active task");
    	todoPage.addTodoItem("this is good");
    	todoPage.completeTodoItem(0);
    	todoPage.deleteTodoItem(2);
    	Assert.assertEquals(todoPage.listOfCompletedToDoItems(),0);
    	Assert.assertTrue(todoPage.getTodoCount()>0);
    	Assert.assertEquals(todoPage.listOfActiveToDoItems(),4);
    	
    }
   
    /**
     * Test entering a long name
     */
    @Test(priority = 14)
    public void testEnteringLongName() {
    	logger.info("Inside testAllTasksFilter method to check whether a long task can be included or not");
    	todoPage.markAllAsCompleted();
    	todoPage.clearCompleteToDoItems();
    	todoPage.addTodoItem(TASK_NAME_LONG_TEXT);
    	Assert.assertEquals(todoPage.getToDoText(0),TASK_NAME_LONG_TEXT);

    }
    
    /**
     * Test entering a string with spaces at the start and end
     */
    @Test(priority = 15)
    public void testEnteringSpaceBeforeAfterString() {
    	logger.info("Inside testEnteringSpaceBeforeAfterString method to check trimming of string");
    	todoPage.markAllAsCompleted();
    	todoPage.clearCompleteToDoItems();
    	todoPage.addTodoItem("  This is new task  ");
    	Assert.assertEquals(todoPage.getToDoText(0),"This is new task");
    }
   
    /**
     * Test entering special characters
     */
    @Test(priority = 16)
    public void testEnteringSpecialCharacter() {
    	logger.info("Inside testEnteringSpecialCharacter method to check special character can be added or not");
    	todoPage.markAllAsCompleted();
    	todoPage.clearCompleteToDoItems();
    	String specialChar="!@#$%^";
    	todoPage.addTodoItem(specialChar);
    	Assert.assertEquals(todoPage.getToDoText(0),specialChar);
    }
    
    /**
     * Test entering special characters
     */
    @Test(priority = 17)
    public void testDuplicateToDoAtsk() {
    	logger.info("Inside testDuplicateToDoAtsk method to check duplicate tasks can be entered or not");
    	todoPage.markAllAsCompleted();
    	todoPage.clearCompleteToDoItems();
    	todoPage.addTodoItem("test1");
    	todoPage.addTodoItem("test1");
    	Assert.assertEquals(todoPage.getTodoCount(),2);
    }
    
    /**
     * Test entering special characters
     */
    @Test(priority = 18)
    public void testMarkAsCompletedAlreadyCompletedTask() {
    	logger.info("Inside testMarkAsCompletedAlreadyCompletedTask method to uncheck the current completed task");
    	todoPage.markAllAsCompleted();
    	todoPage.clearCompleteToDoItems();
    
    	todoPage.addTodoItem("test1");
    	
    	todoPage.completeTodoItem(0);
    	todoPage.completeTodoItem(0);

    	Assert.assertEquals(todoPage.listOfCompletedToDoItems(),0);
    }    
   
    
    /**
     * Cleans up and closes the browser after test execution.
     */
    @AfterClass
    public void tearDownTest() {
        teardown();  
    }
}
