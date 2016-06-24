	package com.IDIOM.architect.scripts;

import java.text.MessageFormat;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p>	<b>Test Case Name:</b> Media Ranker_Verify Checking date selection[DTASIDIOM-2479] </p>
<p>	<b>Objective:</b> Verify the date picker functionality on closing without saving the date selector in media ranker page after deselecting all dates. </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8918.aspx </p>
<p>	<b>Module:</b> Media Ranker </p>
@author: Shailesh Kava
@since: 20-May-2016
**********************************************************************/
public class MediaRanker8918 extends BaseClass {
	
	String datePickerValAfterDeselectAll;
	String datePickerValBeforeDeselectAll;
	AudienceBuilderPage audienceBuilder;
	ArchitectMediaRankerPage mediaRanker;
	String strDigitalRanker;
	String strTVRanker;
	String arrMediaRankerChannels;
	String strDetails;
	List<String> selectedDates;
	String fileLocation;
	Actions action;
	String exportdatafilename;
	
	@Test
	public void verifyCancelActionOnDatePicker(){
		
		strDigitalRanker = property.getProperty("digitalRanker");
		strTVRanker = property.getProperty("tvRanker");
		arrMediaRankerChannels = property.getProperty("mediaRankerItems");
		exportdatafilename = property.getProperty("exportdatafilename"); 
				
		String strMsg = null;
		String strProjectName="";
		boolean bProjectCreate = false;
		audienceBuilder = new AudienceBuilderPage(driver);
		mediaRanker = new ArchitectMediaRankerPage(driver);
		fileLocation = System.getProperty("user.dir");
		
		action = new Actions(driver);
		
		try{
			loginToSelectClient();
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			//Step 3: Create/Select project and launch the same 
			if(totalProjects == 0){
				
				CustomReporter.log("Creating new project as no project for this client");
				System.out.println("Creating new project as no project for this client");
				strProjectName = clientListPage.createNewProject("");
				
				//Getting project and client id to delete through REST service
				strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
				
				clientListPage.func_PerformAction("Launch Project");
				bProjectCreate = true;
			}else{
				CustomReporter.log("Selecting existing project");
				System.out.println("Selecting existing project");
				clientListPage.clickProjectById(1);
			}
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, strDigitalRanker.trim()));
			Thread.sleep(2000);
			
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			String[] channels = arrMediaRankerChannels.split(",");
			for(int i=0; i<channels.length; i++){
				CustomReporter.log("Navigating to ["+channels[i]+"]");
				System.out.println("Navigating to ["+channels[i]+"]");
				
				if(browserName.equalsIgnoreCase("ie") && i>=1){
					System.out.println("clicking through action");
					pdp.navigateToByActionClass(channels[i].trim());
				}else{
					System.out.println("clicking through link");
					pdp.navigateTo(channels[i].trim());
				}
				
				performAction(channels[i].trim());
				
				System.out.println("Clicking on IDIOM Logo");
				action.moveToElement(pageHeader.clientLogo).click().perform();
				System.out.println("Visibility tested-Client Logo");
				rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, channels[i].trim()));
				System.out.println("Visibility tested-End Client logo");
				Thread.sleep(2000);
			}
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Some exception is generated, " + strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8918", "fail");
		}finally{
			try{
				
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the project");
				}
				
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
		//****************Test step ends here************************************************
		
		if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}
	}
	
	public void performAction(String channel) throws Exception{
		
		System.out.println(browserName);
		if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
			throw new IDIOMException("Failed to open media ranker###MediaRanker8918-missingScatterPlot");
		
		mediaRanker.func_VerifyVisibilityOfElement("showmorebuttonheader");
		
		CustomReporter.log("Clicking on date picker of ["+channel+"]");
		System.out.println("Clicking on date picker of ["+channel+"]");
		
		mediaRanker.func_ClickOnElement("datepicker");
		
		datePickerValBeforeDeselectAll = mediaRanker.datepicker.getText().trim();
		
		if(!mediaRanker.func_VerifyVisibilityOfElement("calenderapplybtn"))
			throw new IDIOMException("Failed to open date picker###MediaRanker8918-datePickerNotOpen");
		
		System.out.println("Date picker is appearing");
		CustomReporter.log("Date picker is appearing");
		
		//Getting list of dates which are selected
		List<WebElement> listDates = mediaRanker.selectedDatesList;
		
		int totalSelectedDates = mediaRanker.selectedDatesList.size();
		
		CustomReporter.log("Total selected dates before deselect ["+totalSelectedDates+"]");
		System.out.println("Total selected dates before deselect ["+totalSelectedDates+"]");
		
		//Clear all selected dates
		mediaRanker.calenderClearlBtn.click();
		
		int totalSelectedDatesAfterDeselect = mediaRanker.selectedDatesList.size();
		
		CustomReporter.log("Total selected dates after deselect ["+totalSelectedDatesAfterDeselect+"]");
		System.out.println("Total selected dates after deselect ["+totalSelectedDatesAfterDeselect+"]");
		
		if(totalSelectedDatesAfterDeselect >= 1)
			throw new IDIOMException("Failed to deselect dates###MediaRanker8918-failedDeselectAllDates");
		
		//Clicking on calender arrow to close date picker
		mediaRanker.closeDatePickerByArrow();
		Thread.sleep(2000);
		
		if(mediaRanker.func_VerifyVisibilityOfElement("calenderapplybtn"))
			throw new IDIOMException("Failed to close date picker###MediaRanker8918-datePickerNotClosed");
		
		//Getting date range from date picker 
		datePickerValAfterDeselectAll = mediaRanker.datepicker.getText().trim();
		
		System.out.println("datePickerValAfterDeselectAll ["+datePickerValAfterDeselectAll+"]");
		
		if(!datePickerValAfterDeselectAll.equals(datePickerValBeforeDeselectAll)){
			CustomReporter.errorLog("Date range should be same if clearing all dates and close date picker, "
					+ "Date range before clear +["+datePickerValBeforeDeselectAll+"] and date range after clear date ["+datePickerValAfterDeselectAll+"]");
			rm.captureScreenShot("MediaRanker8918_dateRangeIsChanged", "fail");
			BaseClass.testCaseStatus = false;
		}else{
			CustomReporter.log("Date range is not changed like [0 or Invalid date]");
			System.out.println("Date range is not changed like [0 or Invalid date]");
		}
	}
}