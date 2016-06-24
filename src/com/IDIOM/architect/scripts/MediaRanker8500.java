package com.IDIOM.architect.scripts;

import java.text.MessageFormat;
import java.util.List;

import org.openqa.selenium.WebElement;
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
<p>	<b>Test Case Name:</b> 1089_Media_Ranker_Different_Dates_Date_Range_Should_Be_Allowed_To_Be_Selected </p>
<p>	<b>Objective:</b> Different Dates in Date Range Should Be Allowed To Be Selected. </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8500.aspx </p>
<p>	<b>Module:</b> Media Ranker </p>
@author: Shailesh Kava
@since: 19-May-2016
**********************************************************************/
public class MediaRanker8500 extends BaseClass {
	
	AudienceBuilderPage audienceBuilder;
	ArchitectMediaRankerPage mediaRanker;
	String strDigitalRanker;
	String strTVRanker;
	String arrMediaRankerChannels;
	String strDetails;
	List<String> selectedDates;
	
	@Test
	public void verifyDatesAreAllowedToChange(){
		
		strDigitalRanker = property.getProperty("digitalRanker");
		strTVRanker = property.getProperty("tvRanker");
		arrMediaRankerChannels = property.getProperty("mediaRankerItems");
		
		String strMsg = null;
		String strProjectName="";
		boolean bProjectCreate = false;
		audienceBuilder = new AudienceBuilderPage(driver);
		mediaRanker = new ArchitectMediaRankerPage(driver);
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
				pdp.navigateTo(channels[i].trim());
				
				//Perfirming test case action
				performAction(channels[i].trim());
				
				pageHeader.performAction("clientlogo");				
				rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, channels[i].trim()));
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
			rm.captureScreenShot("8500", "fail");
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
		
		if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
			throw new IDIOMException("Failed to open media ranker###MediaRanker8500-missingScatterPlot");
		
		//Waiting if element is clickable
	    rm.webElementSync(mediaRanker.datepicker, "clickable");
		//Click on calender
		mediaRanker.func_ClickOnElement("datepicker");
		
		if(!mediaRanker.func_VerifyVisibilityOfElement("calenderapplybtn"))
			throw new IDIOMException("Failed to open date picker###MediaRanker8500-datePickerNotOpen");
		
		CustomReporter.log("Date picker is appearing");
		
		//Getting list of dates which are selected
		List<WebElement> listDates = mediaRanker.selectedDatesList;
		
		int totalSelectedDates = mediaRanker.selectedDatesList.size();
		
		CustomReporter.log("Total selected dates before deselect ["+totalSelectedDates+"]");
		System.out.println("Total selected dates before deselect ["+totalSelectedDates+"]");
		
		int deSelectDateID;
		
		if(totalSelectedDates > 2)
			deSelectDateID = Math.round(totalSelectedDates/2);
		else
			deSelectDateID = totalSelectedDates-1;
		 
		System.out.println("Deselecting date ["+deSelectDateID+"]");
		CustomReporter.log("Deselecting date ["+deSelectDateID+"]");
		
		//deselecting one date from selected range
		listDates.get(deSelectDateID).click();
		
		if(!mediaRanker.func_VerifyVisibilityOfElement("calenderapplybtn"))
			throw new IDIOMException("Failed to open date picker###MediaRanker8500-datePickerNotOpen");
		
		//Getting list of dates after deselecting
		int totalSelectedDatesAfterDeselectOne = mediaRanker.selectedDatesList.size();
		
		//Verifying that date is deselected properly
		if(totalSelectedDatesAfterDeselectOne < totalSelectedDates){
			CustomReporter.log("Allowed to change date from selected dates for ["+channel+"]");
			System.out.println("Allowed to change date from selected dates for ["+channel+"]");
		}else{
			CustomReporter.errorLog("Unabled to change date from selected dates for ["+channel+"], screenshot "+channel+"_8500");
			rm.captureScreenShot(channel+"_8500", "fail");
			BaseClass.testCaseStatus = false;
		}
	}
}