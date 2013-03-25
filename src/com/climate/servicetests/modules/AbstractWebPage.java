package com.climate.servicetests.modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.Selenium;

/**
 * Abstract class for each Page Object class that contains Selenium object and Utils methods
 * 
 * @author ariel.barragan
 * 
 */
public abstract class AbstractWebPage {

	protected static WebDriver driver = new FirefoxDriver();

	protected Selenium selenium = new WebDriverBackedSelenium(driver, "https://qa1-twi.climate.com/");

	public AbstractWebPage() {
	}

	/**
	 * Method that waits until the element passed as an argument is visible, after 30 sec if it is still not visible
	 * returns false, otherwise returns true.
	 * 
	 * @param element
	 * @return
	 * @throws InterruptedException
	 */
	protected boolean waitUntilElementIsVisible(String element) throws InterruptedException {
		int timeout = 0;

		while (!selenium.isVisible(element)) {
			if (timeout < 30)
				Thread.sleep(1000);
			else
				return false;
			timeout++;
		}
		return true;

	}

	/**
	 * Method that waits until the element passed as an argument is not visible, after 30 sec if it is still visible
	 * returns false, otherwise returns true.
	 * 
	 * @param element
	 * @return
	 * @throws InterruptedException
	 */
	protected boolean waitUntilElementIsNotVisible(String element) throws InterruptedException {
		int timeout = 0;

		while (selenium.isVisible(element)) {
			if (timeout < 30)
				Thread.sleep(1000);
			else
				return false;
			timeout++;
		}
		return true;
	}

	/**
	 * Method that waits until the element passed as an argument is present, after 30 sec if it is still not present
	 * returns false, otherwise returns true.
	 * 
	 * @param element
	 * @return
	 * @throws InterruptedException
	 */
	protected boolean waitUntilElementPresent(String element) throws InterruptedException {
		int timeout = 0;

		while (!selenium.isElementPresent(element)) {
			if (timeout < 30)
				Thread.sleep(1000);
			else
				return false;
			timeout++;
		}
		return true;
	}

	/**
	 * Method that waits until the element passed as an argument is not present, after 30 sec if it is still present
	 * returns false, otherwise returns true.
	 * 
	 * @param element
	 * @return
	 * @throws InterruptedException
	 */
	protected boolean waitUntilElementIsNotDisabled(String element) throws InterruptedException {
		int timeout = 0;

		while (!selenium.isEditable(element)) {
			if (timeout < 30)
				Thread.sleep(1000);
			else
				return false;
			timeout++;
		}
		return true;
	}

	/**
	 * Closes the browser
	 */
	public static void closeBrowser() {
		driver.quit();
	}
}
