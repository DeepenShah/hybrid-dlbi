package com.IDIOM.architect.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p><b>Test Case Name</b></p> 1151_Media_Ranker_Verify_Firstly_Appeared_Data_With_Second_Time_Audience_Tab
<p><b>Test Case ID</b></p> http://qa.digitas.com/SpiraTest/523/TestCase/8509.aspx
<p><b>Objective</b></p> 1151_Media_Ranker_Verify_Firstly_Appeared_Data_With_Second_Time_Audience_Tab
<p><b>Module</b></p> MediaRanker
@author Shailesh Kava
@since 22 Jan 2016
**********************************************************************/
public class MediaRanker8509 extends BaseClass{
	boolean bProjectCreate;
	String strMsg;
	String strProjectName;
	@Test
	public void test_MediaRanker8509(){
		try{
			strProjectName="";
			strMsg="";
			bProjectCreate = false;
			String strFilters=property.getProperty("MRFilterCriteria8301");	
			String mediaRankerChannels = property.getProperty("mediaRankerItems");
			ArchitectMediaRankerPage mediaRanker;
			
			//****************Test step starts here************************************************
			//Step1: Open URL
			loginToSelectClient();
			
			mediaRanker = new ArchitectMediaRankerPage(driver);
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			//Step 3: Create/Select project and launch the same 
			if(totalProjects == 0){
				CustomReporter.log("Creating new project as no project for this client");
				System.out.println("Creating new project as no project for this client");
				strProjectName = clientListPage.createNewProject("");
				clientListPage.func_PerformAction("Launch Project");
				bProjectCreate = true;
				
			}else{
				CustomReporter.log("Selecting existing project");
				System.out.println("Selecting existing project");
				int selectProjectId = totalProjects;
				clientListPage.clickProjectById(selectProjectId);
			}
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			String[] channels = mediaRankerChannels.split(",");
			
			for(int i=0; i<channels.length; i++){
				CustomReporter.log("Navigating to ["+channels[i]+"]");
				System.out.println("Navigating to ["+channels[i]+"]");
				
				if(i==0){
					System.out.println("clicking through action");
					pdp.navigateToByActionClass(channels[i].trim());
				}else{
					System.out.println("clicking through link");
					pageHeader.megaMenuLinksClick(channels[i].trim());
				}
				
				if(channels[i].trim().toLowerCase().contains("tv"))
					mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "");
				
				if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
					throw new IDIOMException("Failed to open channel###MediaRanker8535-missingScatterPlot");
				
				if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
					throw new IDIOMException("Failed to open channel###MediaRanker8535-missingScatterPlot");
			
				//Existing
				int universeBubblesOnAudienceTab = mediaRanker.func_GetCount("circlesinplot");
				
				if(universeBubblesOnAudienceTab <= 1){
					rm.captureScreenShot("8509_oneBubbleInPlot", "fail");
					CustomReporter.errorLog("Found only one bubble in Audience tab, screenshot '8509_oneBubbleInPlot.png'");
					System.out.println("Found only one bubble in Audience tab, screenshot '8509_oneBubbleInPlot.png'");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log("Found more than 1 bubbles on Audience Tab : ["+universeBubblesOnAudienceTab+"]");
					System.out.println("Found more than 1 bubbles on Audience Tab : ["+universeBubblesOnAudienceTab+"]");
				}
				
				System.out.println("Clicking on Universe Tab");
				rm.webElementSync(mediaRanker.chartHeaderUniverseTab, "clickable");
				mediaRanker.chartHeaderUniverseTab.click();
				rm.captureScreenShot("8509_clickedOnUniverse", "pass");
				System.out.println("Clicked on Universe Tab");
				
				Thread.sleep(3000);
				
				if(mediaRanker.func_VerifyElementExist("chartHeaderUniverseTablected")){
					CustomReporter.log("Universe tab is open");
				}else{
					rm.captureScreenShot("8509_uniVerseTabNotOpen", "fail");
					CustomReporter.errorLog("Problem to open Universe tab, screenshot '8509_uniVerseTabNotOpen.png'");
					BaseClass.testCaseStatus = false;
				}
				
				//clicking on Audience tab again
				mediaRanker.func_ClickOnElement("chartheaderunirversetab");
				
				Thread.sleep(3000);
				
				if(mediaRanker.func_VerifyElementExist("chartHeaderUniverseTablected")){
					CustomReporter.log("Audience tab is open");
				}else{
					rm.captureScreenShot("8509_failedAudienceTab", "fail");
					CustomReporter.errorLog("Failed to open Audience Tab, screenshot '8509_failedAudienceTab.png'");
					BaseClass.testCaseStatus = false;
				}
				
				int universeBubblesOnAudienceTabAfterVisitedUniverseTab = mediaRanker.func_GetCount("circlesinplot");
				
				if(universeBubblesOnAudienceTab != universeBubblesOnAudienceTabAfterVisitedUniverseTab){
					rm.captureScreenShot("8509_countNotMatch","fail");
					CustomReporter.errorLog("Problem with count of bubbles in Audience ["+universeBubblesOnAudienceTab+"] newly countt : ["+universeBubblesOnAudienceTabAfterVisitedUniverseTab+"], screenshot '8509_countNotMatch.png'");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log("Bubbles count are same once user come back from Universe tab to Audience Tab");
				}
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
			rm.captureScreenShot("8509", "fail");
		}finally{
			try{
				
				if(bProjectCreate){
					pageHeader.navigateTo("projecthomepage");
					rm.webElementSync(pageHeader.personMenu,"visibility");					
					Thread.sleep(4000);
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
					Thread.sleep(2000);
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
}