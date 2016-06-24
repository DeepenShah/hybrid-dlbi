package com.IDIOM.architect.scripts;

import java.util.ArrayList;
import java.util.List;

import common.BaseClass;
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
<p>	<b>Test Case Name:</b> *Media Ranker Overview_1.b.i.2Display and Timeframe_.b_Verify_TimeFrame_Click_Opens_Date_Widget </p>
<p>	<b>Objective:</b> Verify whether Time Frame click opens Date Widget </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8332.aspx </p>
<p>	<b>Module:</b> Media Ranker </p>
@author: Rohan Macwan
@since: 18-May-2016
**********************************************************************/

public class MediaRanker8332 extends BaseClass {

@Test
	
	public void	MediaRanker8332(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName="";
		AudienceBuilderPage abPage=null;
		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			
			String strAudienceAttributes1=property.getProperty("commonAudienceAttribute4");
			String strMediaRankerItems=property.getProperty("mediaRankerItems");
			
			String strClientName=property.getProperty("clientName");
			
			//****************Test step starts here************************************************
			
			clientListPage = new ClientList_Page(driver);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			Login_Page ln = new Login_Page(driver);
			
			//Step1: Launch browser and enter IDIOM Url
			//Step2: Enter valid username and password Click on 'login to idiom' button
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			//Step 3 In client home page, select a client from dropdown
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			
			//Step 4 - Click on new project button
			//Step 5 - Enter valid name and description for project and click on Save button
			strProjectName = clientListPage.createNewProject("");
			bProjectCreate=true;
			
			//step 6 - Click on launch project
			//Step 7 - 	Define few success metrics or do not define. ### Here it has not been added.
			clientListPage.launchProject(strProjectName);
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			//Step 8 - Click on Audience Definition link from project dashboard
			System.out.println("Open Success Metrics page");
			CustomReporter.log("Open Success Metrics page");
			
			pdp.navigateTo(property.getProperty("successMetrics"));
			
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);

			CustomReporter.log("Check whether Success Metrics Tab is Active when Audience Builder Section gets loaded");
					
			if (!((audienceBuilder.isVisible("selectedactivetab", "") && (audienceBuilder.SelectedActiveTab.getText().trim().toLowerCase().equalsIgnoreCase("Success Metrics")))))
				throw new IDIOMException("Success Metrics Tab seems to be not available or there may be some issues on the page. ###8332_SuccessMetricsTabNotAvailable");
			
			CustomReporter.log("Success Metrics Tab is available and visible on the page and currently Active");
			
			//Step 9 - Select few attributes and add them 
			rm.webElementSync(audienceBuilder.successMetricsOraudienceDefinitionArrowLink,"visibility");
			audienceBuilder.arrowLinkClickForSuccessMetricsOrAudienceDefinition();
				
			rm.webElementSync(audienceBuilder.allMetricsLabel,"visibility");
			CustomReporter.log("Navigated to audience definition page");
			
			CustomReporter.log("Select attribute");
			audienceBuilder.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			audienceBuilder.performAction("addattribute");
			
			rm.webElementSync(audienceBuilder.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			
			
			ArchitectMediaRankerPage AM= new ArchitectMediaRankerPage(driver);
			String [] strMediaRanker =pageHeader.getSplittedItems(strMediaRankerItems);
			for (int i=0;i<strMediaRanker.length;i++){
				
				System.out.println(strMediaRanker[i]);
				Thread.sleep(3000);
				
				//Step 10 - Click on mega menu and click on digital ranker/tv ranker link
				CustomReporter.log("Click on channel - '" + strMediaRanker[i] +"' from Mega Menu");
				pageHeader.megaMenuLinksClick(strMediaRanker[i]);
				
				rm.webElementSync("idiomspinningcomplete");
								
				if(!AM.func_VerifyVisibilityOfElement("datepicker"))
					   throw new IDIOMException("Failed to open channel###MediaRanker8332-missingScatterPlot");
				
				if(!AM.func_VerifyVisibilityOfElement("slowtvranker"))
			        throw new IDIOMException("Failed to open channel###MissingScatterPlot_8332");
				
				//Step 11 - Verify whether Time Frame click (both Arrow and Date Text) opens Date Widget
				CustomReporter.log("Click on Datepicker to expand the calendar");
				
				AM.func_ClickOnElement("datepicker");
				
				CustomReporter.log("Calendar has expanded. Now check the visibility of Dates, Apply button, Cancel button and Clear button");
				
				if(!AM.func_VerifyVisibilityOfElement("listDatesOnCalender"))
					   throw new IDIOMException("Failed to find Dates###MediaRanker8332-FailedToFindDates");
				
				CustomReporter.log("Dates are found successfully");
				
				if(!AM.func_VerifyVisibilityOfElement("calenderApplyBtn"))
					   throw new IDIOMException("Failed to Find Apply button###MediaRanker8332-FailedToFindApplyButton");
				
				CustomReporter.log("Apply button is found");
				
				if(!AM.func_VerifyVisibilityOfElement("calenderCancelBtn"))
					   throw new IDIOMException("Failed to find Cancel button###MediaRanker8332-FailedToFindCancelButton");
				
				CustomReporter.log("Cancel button is found");
				
				if(!AM.func_VerifyVisibilityOfElement("calenderClearlBtn"))
					   throw new IDIOMException("Failed to find Clear button###MediaRanker8332-FailedToFindClearButton");
				
				CustomReporter.log("Clear button is found");
			}
			
						
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("" + strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8332", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					pageHeader.navigateTo("projecthomepage");
					rm.webElementSync(pageHeader.personMenu,"visibility");					
					Thread.sleep(3000);
					clientListPage.func_PerformAction("Close Project");
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
					Thread.sleep(2000);
					CustomReporter.log("Deleted the project");
				}
				
				//Step 12 - Click on logout
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
}
