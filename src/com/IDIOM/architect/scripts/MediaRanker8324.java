package com.IDIOM.architect.scripts;

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
<p>	<b>Test Case Name:</b> MediaRanker_Weighted Ranker_Percentage_NotEquals100 </p>
<p>	<b>Objective:</b> Verify that if percentage is Not equals to 100, error message is coming and 'Save Changes' button is getting disabled. </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8324.aspx </p>
<p>	<b>Module:</b> Media Ranker </p>
@author: Shailesh Kava
@since: 17-May-2016
**********************************************************************/
public class MediaRanker8324 extends BaseClass {
	
	AudienceBuilderPage audienceBuilder;
	ArchitectMediaRankerPage mediaRanker;
	String strDigitalRanker;
	String strTVRanker;
	String rankerPercentageVal = "80";
	String coveragePercentageVal = "30";
	String coveragePercentageVal1 = "20";
	String ranker1;
	String ranker2;
	String mediaRankerChannels;
	
	@Test
	public void verifySaveButtonOn100Percentage(){
		
		strDigitalRanker = property.getProperty("digitalRanker");
		strTVRanker = property.getProperty("tvRanker");
		ranker1 = property.getProperty("raneker1");
		ranker2 = property.getProperty("raneker2");
		mediaRankerChannels = property.getProperty("mediaRankerItems");
		
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
			
			//Clicking on Digital Ranker link
			
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
				
				performAction(channels[i].trim());
			}
			
			/*CustomReporter.log("Navigating to [Digital Ranker]");
			pdp.navigateTo(strDigitalRanker);
			performAction();
			
			//Clicking on TV ranker
			CustomReporter.log("Navigating to [TV Ranker]");
			pageHeader.megaMenuLinksClick(strTVRanker);
			mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "");
			
			performAction();*/
			
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
			rm.captureScreenShot("8324", "fail");
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
	
	public void performAction(String channel) throws Exception{
		
		if(channel.trim().toLowerCase().contains("tv"))
			mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "");
		
		if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
			throw new IDIOMException("Failed to open channel###MediaRanker8324-missingScatterPlot");
		
		Thread.sleep(3000);
		CustomReporter.log("Media ranker page is open");
		//Clicking on rank
		mediaRanker.func_ClickOnElement("weightedranker");
		
		Thread.sleep(2000);
		if(!mediaRanker.func_VerifyVisibilityOfElement("weightedrankercancelbtn"))
			throw new IDIOMException("Failed to open weightedranker###MediaRanker8324-missingWeightedRanker");
		
		CustomReporter.log("weighted ranker is opened");
		
		//Reset all values of weighted ranker
		mediaRanker.func_ClearAllRankerPercentage();
		
		Thread.sleep(1000);
		//Setting value in one weighted ranker and verifying grayed bar graph width
		mediaRanker.func_EnterWeightedRankerPercentage(ranker1, rankerPercentageVal);
		
		if(BaseClass.browserName.equals("IE"))
			Thread.sleep(5000);
		else
			Thread.sleep(2000);
		//Getting bar graph width value
		int percentageOnBar = mediaRanker.func_getWeightedRankerBarWidth(ranker1);
		System.out.println("Width of bar after changing == "+percentageOnBar);
		if(percentageOnBar == Integer.parseInt(rankerPercentageVal))
			CustomReporter.log("Weighted ranker width is changed as expected ["+percentageOnBar+"]");
		else{
			rm.captureScreenShot("MediaRanker8324-weightedRankerBarIsNotMoved", "fail");
			CustomReporter.errorLog("Weighted ranker width is not as expected ["+rankerPercentageVal+"], it is ["+percentageOnBar+"]");
			BaseClass.testCaseStatus = false;
		}
		
		//Adding percentage value in one more weighted ranker
		mediaRanker.func_EnterWeightedRankerPercentage(ranker2, coveragePercentageVal);
		mediaRanker.dummyClick.click();
		if(BaseClass.browserName.equals("IE"))
			Thread.sleep(7000);
		else
			Thread.sleep(2000);
		if(mediaRanker.func_isEnabled("savechangebutton"))
			throw new IDIOMException("Save button should disabled###MediaRanker8324-saveButtonShouldDisabled");
		
		CustomReporter.log("Save button is disabled for more than 100% of weighted ranker");
		
		//Updating value to 100%
		mediaRanker.func_EnterWeightedRankerPercentage(ranker2, coveragePercentageVal1);
		mediaRanker.dummyClick.click();
		
		if(BaseClass.browserName.equals("IE"))
			Thread.sleep(7000);
		else
			Thread.sleep(2000);
		if(!mediaRanker.func_isEnabled("savechangebutton"))
			throw new IDIOMException("Save button should enable###MediaRanker8324-saveButtonShouldEnabled");
		
		CustomReporter.log("Save Changes");
		
		//Clicking on save button
		mediaRanker.func_ClickOnElement("rankersavechanges");
		
		rm.webElementSync("idiomspinningcomplete");
		
		//again clicking on weighted ranker to verify values
		mediaRanker.func_ClickOnElement("weightedranker");
		
		if(!mediaRanker.func_VerifyVisibilityOfElement("weightedrankercancelbtn"))
			throw new IDIOMException("Failed to open weightedranker###MediaRanker8324-missingWeightedRanker");
		
		CustomReporter.log("weighted ranker is opened");
		String weightedRankerUpdatedVal = mediaRanker.func_GetValue("weightedrankermetricpercentagevalue");
		
		System.out.println("weightedRankerUpdatedVal ["+weightedRankerUpdatedVal+"]");
	}
}