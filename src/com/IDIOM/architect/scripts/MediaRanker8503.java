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
<p><b>Test Case Name</b></p> 1105: Media Ranker: We can select more than 100 percentage value also in Weighted Ranker section <br>
<p><b>Test Case ID</b><p> http://qa.digitas.com/SpiraTest/523/TestCase/8503.aspx <br>
<p><b>Objective</b></p> Verify that ranker should not allow to enter/select more than 100 for any rank <br>
Module</b></p> MediaRanker <br>
@author Shailesh Kava
@since 01 Feb 2016
**********************************************************************/
public class MediaRanker8503 extends BaseClass{
	boolean bProjectCreate;
	String strMsg;
	String strProjectName;	
	@Test
	public void test_MediaRanker8503(){
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
					throw new IDIOMException("Failed to open channel###MediaRanker8503-missingScatterPlot");
				
				if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
					throw new IDIOMException("Failed to open channel###MediaRanker8503-missingScatterPlot");
				
				if(mediaRanker.func_VerifyElementExist("metricbubbleplot")){
					CustomReporter.log("Clicked on channel");
					mediaRanker.func_ClickOnElement("weightedranker");
					
					CustomReporter.log("Clicked on Weighted ranker");
					
					Thread.sleep(3000);
					
					mediaRanker.func_EnterWeightedRankerPercentage("reach", "105");
					
					String totalPercentageBeforeChange = mediaRanker.func_GetValue("weightedrankermetricpercentagevalue");
					
					CustomReporter.log("Total % value after changed: "+totalPercentageBeforeChange);
					
					if(!mediaRanker.func_isEnabled("savechangebutton")){
						CustomReporter.log("Does not allow to save changes as weighted ranker has >100 % values");
					}else{
						rm.captureScreenShot("8503_savingMorethan100Per", "fail");
						CustomReporter.errorLog("Allows to change more than 100% values in weighted ranker = "+totalPercentageBeforeChange+" screenshot '8503_savingMorethan100Per.png'");
						BaseClass.testCaseStatus = false;
					}
					
				}else{
					CustomReporter.errorLog("Failed to open media ranker page");
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
			rm.captureScreenShot("8503", "fail");
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