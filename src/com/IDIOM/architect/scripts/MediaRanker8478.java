package com.IDIOM.architect.scripts;

import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p><b>Test Case Name</b></p> 1216: Media Ranker: Production> Show audience panel disappear and unable to close audience panel
<p><b>Test Case ID</b></p> http://qa.digitas.com/SpiraTest/523/TestCase/8478.aspx
<p><b>Objective</b></p> Verify arrow "<" of the "Show Audience" tab should consistent while scrolling the page.
<p><b>Module</b></p> MediaRanker
@author Shailesh KAva
@since 03 Feb 2016
**********************************************************************/
public class MediaRanker8478 extends BaseClass{
	
	@Test
	public void test_MediaRanker8478(){
		
		ArchitectMediaRankerPage mediaRanker=null;
		String strMsg = null;
		boolean bProjectCreate = false;
		String strProjectName = null;
		try{
			//***************************Variables********************************
			strMsg="";
			bProjectCreate = false;
			property.getProperty("MRFilterCriteria8301");	
			String mediaRankerChannels = property.getProperty("mediaRankerItems");
			mediaRanker  = new ArchitectMediaRankerPage(driver);
			//****************Test step starts here********************************
			//Step1: Open URL
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
					throw new IDIOMException("Failed to open channel###MediaRanker8519-missingScatterPlot");
				
				if(!rm.webElementSync(mediaRanker.datepicker, "clickable"))
					throw new IDIOMException("Date picker is not clickable###MediaRanker8519-datePickerNotClickable");
				
				
				//Step 7: Clicking on Show Audience
				Thread.sleep(3000);
				CustomReporter.log("Clicking on show Audience");
				mediaRanker.func_ClickOnElement("showaudience");
				Thread.sleep(3000);
				
				if(mediaRanker.func_VerifyElementExist("showAudiencetoggleimage")){
					CustomReporter.log("Show audience panel is opened");
					System.out.println("Show audience panel is opened");
					
					Point beforeLocation = mediaRanker.func_getLocation("showaudiencetoggleimage");
					
					mediaRanker.func_ClickOnElement("individualmatriclastmetric");
					
					Point afterLocation = mediaRanker.func_getLocation("showaudiencetoggleimage");
					
					if(beforeLocation.y == afterLocation.y){
						CustomReporter.log("Show audience is appearing on scroll down the page <b>"+beforeLocation.y+"=="+afterLocation.y+"</b>");
						System.out.println("Show audience is appearing on scroll down the page "+beforeLocation.y+"=="+afterLocation.y);
					}else{
						rm.captureScreenShot("8478_showAudienceHides", "fail");
						CustomReporter.errorLog("Show Audience is hide on scroll down the page <b>"+beforeLocation.y+"=="+afterLocation.y+"</b>");
						System.out.println("Show Audience is hide on scroll down the page "+beforeLocation.y+"=="+afterLocation.y);
						BaseClass.testCaseStatus = false;
					}
					
				}else{
					rm.captureScreenShot("8478_showAudienceNotVisible", "fail");
					CustomReporter.log("Failed to open Show audience panel");
					System.out.println("Failed to open Show audience panel");
					BaseClass.testCaseStatus = false;
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
			rm.captureScreenShot("8478", "fail");
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
		
		/***************************Test step ends here******************************/
		if(BaseClass.testCaseStatus == false){
			CustomReporter.errorLog("Test case has failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case has Passed");
		}
	}
}