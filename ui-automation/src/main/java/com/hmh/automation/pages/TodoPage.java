package com.hmh.automation.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
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
    		    WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(destroy_Buttons.get(index)));
    		    deleteButton.click();
    }

    public void deleteTodoItem1(int index) {
    	 Actions actions = new Actions(driver);
    	    WebElement todoItem = todoItemList.get(index);
    	    actions.moveToElement(todoItem).perform(); // Hover over the item
    	    wait.until(ExpectedConditions.elementToBeClickable(destroy_Buttons.get(index))).click();
    	    
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
}
