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
    private static final String TASK_NAME_LONG_TEXT="this is supposed to be a very long task and you have to perform it in very less time";

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
     */
    @Test(priority = 2)
    public void testAddMultipleTodoItems(){
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
        todoPage.completeTodoItem(0);  
        Assert.assertTrue(todoPage.isTaskCompleted(0), "Task was not marked as completed!");
    }

    /**
     * Test case to mark all tasks as completed.
     * This method ensures that every task in the list is marked as completed.
     */
    @Test(priority = 4)
    public void testAllMarkedAsCompleted() {
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
        todoPage.deleteTodoItem(0);  // Delete the first task
        Assert.assertEquals(todoPage.getTodoCount(), 2, "Todo count mismatch after deletion!");
    }


    /**
     * Test case to verify the Clear Completed Button.
     * Deletes the completed task and reduces the list by 1.
     */
    @Test(priority = 6)
    public void testClearCompletedTask() {
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
    	todoPage.filterAllToDoItems();
    	Assert.assertTrue(todoPage.getTodoCount()>0);
    }
    
    
    /**
     * Test case to verify the 'All' button returns All tasks in
     */
    @Test(priority = 10)
    public void testWhenAllToDOAreComplete() {
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
    	todoPage.markAllAsCompleted();
    	todoPage.clearCompleteToDoItems();
    
    	todoPage.addTodoItem("test1");
    	
    	todoPage.completeTodoItem(0);

    	Assert.assertEquals(todoPage.listOfCompletedToDoItems(),1);
    }

    
    
    /**
     * Cleans up and closes the browser after test execution.
     */
    @AfterClass
    public void tearDownTest() {
        teardown();  
    }
}
