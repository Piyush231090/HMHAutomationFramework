package com.hmh.automation.pages;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * This class represents the Page Object Model (POM) for the TodoMVCapplication. 
 * It contains methods to interact with the Todo list such as adding, completing, 
 * deleting, and verifying tasks.
 */
public class TodoPage {

	WebDriverWait wait;
	WebDriver driver;
	public static final String COMPLETED = "completed";
	public static final String CLASS = "class";
	private static final Logger logger = LogManager.getLogger(TodoPage.class);

	/**
	 * Constructor to initialize the WebDriver and WebDriverWait.
	 *
	 * @param driver The WebDriver instance used to interact with the browser.
	 */
	public TodoPage(WebDriver driver) {
		logger.info("Initialising drivers.....");
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	/**
	 * WebElement representing the input field for adding new todo items.
	 */
	@FindBy(xpath = "//input[@id='todo-input']")
	private WebElement todoInput;

	private By todoItems = By.cssSelector(".todo-list li");
	/**
	 * List of WebElements representing all todo items in the list.
	 */
	@FindBy(xpath = "//ul[@class='todo-list']//li")
	private List<WebElement> todoItemList;

	/**
	 * List of WebElements representing the toggle buttons for marking tasks as
	 * completed.
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
	 * List of WebElements representing the todo task label buttons for editing
	 * test".
	 */
	@FindBy(xpath = "//ul[@class='todo-list']/li//label")
	private List<WebElement> todoLabel;

	/**
	 * List of WebElements representing the todo task label buttons for editing
	 * test".
	 */
	// Locate the input field for editing (only visible in edit mode)
	@FindBy(xpath = "//div[contains(@class, 'input-container')]//input[contains(@class, 'new-todo')]")
	private WebElement editInputField;

	// Methods

	/**
	 * Adds a new task to the Todo list.
	 *
	 * @param task The task to be added.
	 */
	public void addTodoItem(String task) {
		logger.info("Inside addTodoItem method todoItem:{}", task);
		todoInput.sendKeys(task + "\n");
	}

	/**
	 * Getting text of the todo task
	 * 
	 * @param index The index of todo task to get text
	 */
	public String getToDoText(int index) {
		logger.info("Inside getToDoText method for ToDoTask to get text at:{}", index + 1);
		return todoItemList.get(index).getText();
	}

	/**
	 * Marks a specific task as completed.
	 *
	 * @param index The index of the task to be marked as completed.
	 */
	public void completeTodoItem(int index) {
		logger.info("Inside completeTodoItem method to mark task as complete at:{}", index + 1);
		check_Buttons.get(index).click();
	}

	/**
	 * Deletes a specific task from the Todo list.
	 *
	 * @param index The index of the task to be deleted.
	 */

	public void deleteTodoItem(int index) {
		logger.info("Inside deleteTodoItem method to delete the task at:{}", index + 1);
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
		logger.info("Inside clearCompleteToDoItems method to clear the completed task");
		clearCompleted_Button.click();
		clearCompletedTask();
	}

	/**
	 * Gives the list of completed task
	 */
	public void filterCompletedItems() {
		logger.info("Inside filterCompletedItems method to filter the completed tasks");
		completed_Button.click();
	}

	/**
	 * Gives the size of completed items from the tasks
	 */
	public int listOfCompletedToDoItems() {
		logger.info("Inside listOfCompletedToDoItems method to get the size of completed tasks");
		return completedToDoItems.size();
	}

	/**
	 * clicks on Active button
	 */
	public void filterActiveToDoItems() {
		logger.info("Inside filterActiveToDoItems method to click active button ");
		active_Button.click();
	}

	/**
	 * clicks on All filter button
	 */
	public void filterAllToDoItems() {
		logger.info("Inside filterAllToDoItems method to click all button ");
		all_Button.click();
	}

	/**
	 * Gives the list of Active ToDo tasks
	 */
	public int listOfActiveToDoItems() {
		logger.info("Inside listOfActiveToDoItems method to get all active Items ");
		return activeToDoItems.size();
	}

	/**
	 * Retrieves the total count of tasks present in the Todo list.
	 *
	 * @return The number of tasks currently in the list.
	 */
	public int getTodoCount() {
		logger.info("Inside getTodoCount method to get count of all tasks ");
		return todoItemList.size();
	}

	/**
	 * Checks if a specific task is marked as completed.
	 *
	 * @param index The index of the task to check.
	 * @return True if the task is completed, otherwise false.
	 */
	public boolean isTaskCompleted(int index) {
		logger.info("Inside isTaskCompleted method to check whether task is completed or not at:{}", index + 1);
		WebElement tasks = todoItemList.get(index);
		return tasks.getDomAttribute(CLASS).contains(COMPLETED);
	}

	/**
	 * Marks all tasks in the Todo list as completed.
	 */
	public void markAllAsCompleted() {
		logger.info("Inside markAllAsCompleted method to mark all task as complete");
		toggle_all_button.click();
	}

	/**
	 * Checks if all tasks in the Todo list are marked as completed.
	 *
	 * @return True if all tasks are completed, otherwise false.
	 */
	public boolean isAllTaskCompleted() {
		logger.info("Inside isAllTaskCompleted method to check all task as complete");
		for (WebElement webElement : todoItemList) {
			if (!webElement.getDomAttribute(CLASS).contains(COMPLETED)) {

				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * This clears already completed tasks
	 */
	public void clearCompletedTask() {
		logger.info("Inside clearCompletedTask method to check clear all completed task");
		int index = 0;
		for (WebElement webElement : todoItemList) {
			if (webElement.getDomAttribute(CLASS).contains(COMPLETED)) {
				deleteTodoItem(index);
				index++;
			} else {
				index++;
			}
		}
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public void editText(int index, String text) throws InterruptedException {

		WebElement todoItem = driver.findElements(todoItems).get(index);
		Actions actions = new Actions(driver);

		// Double-click to activate edit mode
		actions.doubleClick(todoItem).perform();

		// Locate the active input field
		WebElement editInput = driver.findElement(By.cssSelector(".todo-list li.editing .edit"));

		// Clear existing text and enter new text
		editInput.sendKeys(Keys.CONTROL + "a"); // Select all text
		editInput.sendKeys(Keys.BACK_SPACE); // Clear text
		editInput.sendKeys("test");
		editInput.sendKeys(Keys.ENTER); // Press Enter to save

	}
	
	/**
	 * if present it will Deletes all task from the Todo list.
	 *
	 * @param As it is a dynamic list the Index will always be 0.
	 */

	public void deleteAllTodoItem() {
		logger.info("Inside delete all todo items");
		Actions actions = new Actions(driver);
		int index = 0;
		for(WebElement webElement:todoItemList) {
			actions.moveToElement(webElement).perform(); // Hover over the item
			wait.until(ExpectedConditions.elementToBeClickable(destroy_Buttons.get(index))).click();
			
		}
		
	}
}
