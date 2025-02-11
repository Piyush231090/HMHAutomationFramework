package com.hmh.automation.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class represents the Page Object Model (POM) for the TodoMVC application.
 * It contains methods to interact with the Todo list such as adding, completing, 
 * deleting, and verifying tasks.
 */
public class TodoPage {
	
    WebDriverWait wait;
    WebDriver driver;
    public static final String COMPLETED="completed";
	public static final String CLASS="class";
	
    /**
     * Constructor to initialize the WebDriver and WebDriverWait.
     *
     * @param driver The WebDriver instance used to interact with the browser.
     */
    public TodoPage(WebDriver driver) {
    	this.driver=driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /**
     * WebElement representing the input field for adding new todo items.
     */
    @FindBy(xpath = "//input[@id='todo-input']")
    private WebElement todoInput;

    /**
     * List of WebElements representing all todo items in the list.
     */
    @FindBy(xpath = "//ul[@class='todo-list']//li")
    private List<WebElement> todoItemList;

    /**
     * List of WebElements representing the toggle buttons for marking tasks as completed.
     */
    @FindBy(xpath = "//ul[@class='todo-list']/li//input[@class='toggle']")
    private List<WebElement> check_Buttons;

    /**
     * WebElement representing the toggle-all button to mark all tasks as completed.
     */
    @FindBy(xpath = "//input[@id='toggle-all']")
    private WebElement toggle_all_button;

    /**
     * List of WebElements representing the delete buttons for removing tasks.
     */
    @FindBy(xpath = "//ul[@class='todo-list']/li//button[@class='destroy']")
    private List<WebElement> destroy_Buttons;
    
    /**
     * List of WebElements representing the Clear Completed option
     */
    @FindBy(xpath = "//button[normalize-space()='Clear completed']")
    private WebElement clearCompleted_Button;
    
    /**
     * This is completed filter button 
     */
    @FindBy(xpath = "//a[normalize-space()='Completed']")
    private WebElement completed_Button;
    
    /**
     * It gives the list of tasks that are marked as completed
     */
    @FindBy(xpath = "//ul[@class='todo-list']//li[@class='completed']")
    private List<WebElement> completedToDoItems;
    
    /**
     * This is Active filter button 
     */
    @FindBy(xpath = "//a[normalize-space()='Active']")
    private WebElement active_Button;
    
    
    /**
     * It gives the list of tasks that are marked as Active
     */
    @FindBy(xpath = "//ul[@class='todo-list']//li[not(contains(@class, 'completed'))]")
    private List<WebElement> activeToDoItems;
    
    /**
     * This is All filter Button
     */
    @FindBy(xpath = "//a[normalize-space()='All']")
    private WebElement all_Button;
    

    /**
     * List of WebElements representing the tado task label buttons for editing test".
     */
    @FindBy(xpath = "//ul[@class='todo-list']/li//label")
    private List<WebElement> todoLabel;
    
    /**
     * List of WebElements representing the todo task label buttons for editing test".
     */
    @FindBy(xpath = "(//div[@class='input-container'])[2]")
    private WebElement editInput;
    
    
    // Methods

    /**
     * Adds a new task to the Todo list.
     *
     * @param task The task to be added.
     */
    public void addTodoItem(String task) {
        todoInput.sendKeys(task + "\n");
    }
    
    /**
     * Getting text of the todo task
     * @param index The index of todo task to get text
     */
    public String getToDoText(int index) {
        return todoItemList.get(index).getText();
    }

    /**
     * Marks a specific task as completed.
     *
     * @param index The index of the task to be marked as completed.
     */
    public void completeTodoItem(int index) {
        check_Buttons.get(index).click();
    }

    /**
     * Deletes a specific task from the Todo list.
     *
     * @param index The index of the task to be deleted.
     */

    public void deleteTodoItem(int index) {
    	 Actions actions = new Actions(driver);
    	    WebElement todoItem = todoItemList.get(index);
    	    actions.moveToElement(todoItem).perform(); // Hover over the item
    	    wait.until(ExpectedConditions.elementToBeClickable(destroy_Buttons.get(index))).click();    
    }
    
    /**
     * Clears the tasks that are marked as completed
     * 
     */
    public void clearCompleteToDoItems() {
    	clearCompleted_Button.click();
    	clearCompletedTask();
    }
    
    /**
     * Gives the list of completed task
     */
    public void filterCompletedItems() {
    	completed_Button.click();
    }
    
    /**
     * Gives the size of completed items from the tasks
     */
    public int listOfCompletedToDoItems() {
    	return completedToDoItems.size();
    }
    
    /**
     * clicks on Active button
     */
    public void filterActiveToDoItems() {
    	active_Button.click();
    }
    
    /**
     * clicks on All filter button
     */
    public void filterAllToDoItems() {
    	all_Button.click();
    }
    
    
    /**
     * Gives the list of Active ToDo tasks
     */
    public int listOfActiveToDoItems() {
    	return activeToDoItems.size();
    }
   
    /**
     * Retrieves the total count of tasks present in the Todo list.
     *
     * @return The number of tasks currently in the list.
     */
    public int getTodoCount() {
        return todoItemList.size();
    }

    /**
     * Checks if a specific task is marked as completed.
     *
     * @param index The index of the task to check.
     * @return True if the task is completed, otherwise false.
     */
    public boolean isTaskCompleted(int index) {
        WebElement tasks = todoItemList.get(index);
        return tasks.getDomAttribute(CLASS).contains(COMPLETED);
    }

    /**
     * Marks all tasks in the Todo list as completed.
     */
    public void markAllAsCompleted() {
        toggle_all_button.click(); 
    }

    /**
     * Checks if all tasks in the Todo list are marked as completed.
     *
     * @return True if all tasks are completed, otherwise false.
     */
    public boolean isAllTaskCompleted() {
    	for(WebElement webElement:todoItemList) {
    		if(!webElement.getDomAttribute(CLASS).contains(COMPLETED)) {
    			return false;
    		}
    	}
    return true;
    }
   
    /**
     * 
     * 
     */
    public void clearCompletedTask() {
    	int index = 0;
    	for(WebElement webElement:todoItemList) {
    		if(webElement.getDomAttribute(CLASS).contains(COMPLETED)) {
    			deleteTodoItem(index);
    			index++;
    		}
    		else {
    		index++;
    	    }
        }
    }
    
    
    /**
     * @throws InterruptedException 
     * 
     */
    public void editText(int index,String text){
    	//Actions actions = new Actions (driver);
    	//actions.doubleClick(todoLabel.get(index)).perform();
    	WebElement taskToEdit = todoLabel.get(index);
    	 ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('dblclick', {bubbles: true}))", taskToEdit);

    	WebElement editField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='view']//input[@id='todo-input']")));
    	editField.clear();
    	editField.sendKeys(text);
    	editField.sendKeys(Keys.ENTER);
    }
}
