package com.climate.servicetests.tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import com.climate.servicetests.modules.AbstractWebPage;
import com.climate.servicetests.modules.MultiPerilQuotingPage;

public class RatesSheet {

	@AfterSuite
	public void tearDown() {
		AbstractWebPage.closeBrowser();
	}

	/**
	 * Test that verifies the Rate Sheet appears correctly on the page
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void initialRateSheet() throws InterruptedException {

		MultiPerilQuotingPage mPQuoting = new MultiPerilQuotingPage();
		mPQuoting.clickNotNowLink();
		mPQuoting.clickViewRatesSheetTab();

		assertTrue(mPQuoting.verifyRateSheetModuleAppears());
		assertTrue(mPQuoting.verifyRateTable());

	}

	/**
	 * Test that verifies that Rate Sheet appears correctly according to selected changes on the filters (Level, Unit,
	 * Protection and Plan)
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void interactedRateSheet() throws InterruptedException {

		MultiPerilQuotingPage mPQuoting = new MultiPerilQuotingPage();
		mPQuoting.clickNotNowLink();
		mPQuoting.clickViewRatesSheetTab();

		String county = "Perry, IL";
		mPQuoting.setCounty(county);
		mPQuoting.selectCounty(county);

		mPQuoting.selectCrop(mPQuoting.CROP_CORN);
		mPQuoting.clickReQuote();

		// Unchecking RP Protection filter
		mPQuoting.toogleCheckbox(mPQuoting.PROTECTION_RP);
		assertTrue(mPQuoting.verifyProtectionRemovedColumn(mPQuoting.PROTECTION_RP));

		// Unchecking EU Unit filter
		mPQuoting.toogleCheckbox(mPQuoting.UNIT_EU);
		assertTrue(mPQuoting.verifyUnitRemovedColumn(mPQuoting.UNIT_EU));

		// Checking GRP Plan filter
		mPQuoting.toogleCheckbox(mPQuoting.PLAN_GRP);
		assertTrue(mPQuoting.verifyPlanShown(mPQuoting.PLAN_GRP));

		// Unchecking 75 percent Level filter
		mPQuoting.toogleCheckbox(mPQuoting.LEVEL_75_PERCENT);
		assertTrue(mPQuoting.verifyLevelRemovedRow(75));

	}
}
