package com.climate.servicetests.modules;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Page Object class corresponding to Multi Peril Quoting page that contains methods for interacting and verifying
 * elements from the page
 * 
 * @author ariel.barragan
 * 
 */
public class MultiPerilQuotingPage extends AbstractWebPage {

	private static final String NOT_NOW_LINK = "fb-not-now";
	private static final String VIEW_RATES_SHEET_TAB = "quick-quotes-tab";
	private static final String RATE_SHEET_MODULE = "quick-quotes";
	private static final String RATE_SHEET_TABLE = "//table[@class='table_results']";
	private static final String INDIVIDUAL_TABLE_SECTION = "//section[@id='quick-quotes-results']";
	private static final String GROUP_TABLE_SECTION = "//section[@id='quick-quotes-group-county']";
	private static final String REQUOTE_BUTTON = "//input[contains(@class, 'btn-requote2')]";
	private static final String SPINNING_RESULTS = "//div[@id='spinner-results']";
	public final String UNIT_EU = "eu";
	public final String PLAN_GRP = "GRP";
	public final String PROTECTION_RP = "rp";
	public final String LEVEL_75_PERCENT = "75-percent";
	public final String CROP_CORN = "0041";

	public MultiPerilQuotingPage() {
		selenium.open("https://qa1-twi.climate.com/apps/preso/mp/quoter.html");
	}

	/**
	 * Clicks Not Now link in email submitting popup
	 * 
	 * @throws InterruptedException
	 */
	public void clickNotNowLink() throws InterruptedException {
		selenium.click(NOT_NOW_LINK);
		waitUntilElementIsNotVisible(SPINNING_RESULTS);
	}

	/**
	 * Clicks View Rates Sheet tab
	 * 
	 * @throws InterruptedException
	 */
	public void clickViewRatesSheetTab() throws InterruptedException {
		selenium.click(VIEW_RATES_SHEET_TAB);
	}

	/**
	 * Verifies whether or not the Rate Sheet module is displayed
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public boolean verifyRateSheetModuleAppears() throws InterruptedException {
		return waitUntilElementIsVisible(RATE_SHEET_MODULE);
	}

	/**
	 * Verifies that each cell from the table has a value different than empty
	 * 
	 * @return
	 */
	public boolean verifyRateTable() {
		int rowsCount = selenium.getXpathCount(RATE_SHEET_TABLE + "/tbody/tr").intValue();

		for (int row = 1; row <= rowsCount; row++) {

			String rowXPath = RATE_SHEET_TABLE + "/tbody/tr[" + row + "]";
			String columnXPath = rowXPath + "/td";
			int columnsCount = selenium.getXpathCount(columnXPath).intValue();

			for (int column = 1; column <= columnsCount; column++) {
				if (selenium.getText(columnXPath + "[" + column + "]").equals(""))
					return false;
			}
		}
		return true;
	}

	/**
	 * Fills in the text field for County
	 * 
	 * @param newCounty
	 */
	public void setCounty(String newCounty) {
		selenium.type("county", "");
		selenium.typeKeys("county", newCounty);
	}

	/**
	 * Selects the specified county from the dropdown
	 * 
	 * @param newCounty
	 * @throws InterruptedException
	 */
	public void selectCounty(String newCounty) throws InterruptedException {
		waitUntilElementPresent("//li[contains(@data-value, \"" + newCounty + "\")]/a");
		selenium.click("//li[contains(@data-value, \"" + newCounty + "\")]/a");
	}

	/**
	 * Clicks Re-Quote button
	 * 
	 * @throws InterruptedException
	 */
	public void clickReQuote() throws InterruptedException {
		selenium.click(REQUOTE_BUTTON);
		waitUntilElementIsNotVisible(SPINNING_RESULTS);
	}

	/**
	 * Selects the specified crop from the dropdown
	 * 
	 * @param crop
	 * @throws InterruptedException
	 */
	public void selectCrop(String crop) throws InterruptedException {
		waitUntilElementIsNotDisabled("//select[@id='crop']");
		selenium.select("crop", "value=" + crop);
		// Synchronization valid one for those counties that
		waitUntilElementPresent("//input[@id='ta-aph']");
	}

	/**
	 * This method checks for every cell if the class contains the element passed as argument then verifies that the
	 * cell is not visible or not depending on the boolean parameter passed as an argument
	 * 
	 * @param tableSection
	 * @param element
	 *            - filter interacted
	 * @return
	 */
	private boolean verifyElementInTable(String tableSection, String element, boolean isVisible) {
		int rowsCount = selenium.getXpathCount(tableSection + RATE_SHEET_TABLE + "/tbody/tr").intValue();

		for (int row = 1; row <= rowsCount; row++) {

			String rowXPath = tableSection + RATE_SHEET_TABLE + "/tbody/tr[" + row + "]";
			String columnXPath = rowXPath + "/td";
			int columnsCount = selenium.getXpathCount(columnXPath).intValue();

			for (int column = 1; column <= columnsCount; column++) {

				String cellClassAttrib = selenium.getAttribute(columnXPath + "[" + column + "]@class");
				String[] classes = cellClassAttrib.split("[ ]+");
				ArrayList<String> classesList = new ArrayList<String>(Arrays.asList(classes));

				if (classesList.contains(element) && (selenium.isVisible(columnXPath + "[" + column + "]") ^ isVisible)) {
					return false;
				}

			}
		}
		return true;
	}

	/**
	 * Clicks the checkbox from a given locator
	 * 
	 * @param checkbox
	 */
	public void toogleCheckbox(String checkbox) {
		selenium.click(checkbox);
	}

	/**
	 * Verifies if specified Protection filter was removed from table
	 * 
	 * @param protection
	 * @return
	 */
	public boolean verifyProtectionRemovedColumn(String protection) {
		return verifyElementInTable(INDIVIDUAL_TABLE_SECTION, protection, false);
	}

	/**
	 * Verifies if specified Unit filter was removed from table
	 * 
	 * @param unit
	 * @return
	 */
	public boolean verifyUnitRemovedColumn(String unit) {
		return verifyElementInTable(INDIVIDUAL_TABLE_SECTION, unit, false);
	}

	/**
	 * Verifies if specified Plan filter is shown in plans table
	 * 
	 * @param plan
	 * @return
	 * @throws InterruptedException
	 */
	public boolean verifyPlanShown(String plan) throws InterruptedException {
		waitUntilElementIsVisible(GROUP_TABLE_SECTION);
		return verifyElementInTable(GROUP_TABLE_SECTION, plan, true);
	}

	/**
	 * Verifies that specified Level row is removed from tables
	 * 
	 * @param level
	 * @return
	 * @throws InterruptedException
	 */
	public boolean verifyLevelRemovedRow(Integer level) throws InterruptedException {
		String levelRow = RATE_SHEET_TABLE + "//tr[@data-coverage='" + level.toString() + "']";
		return waitUntilElementIsNotVisible(INDIVIDUAL_TABLE_SECTION + levelRow)
				&& waitUntilElementIsNotVisible(GROUP_TABLE_SECTION + levelRow);
	}
}