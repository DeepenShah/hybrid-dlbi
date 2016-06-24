package com.IDIOM.architect.scripts;

import org.openqa.selenium.Dimension;
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
<p><b>Test Case Name</b></p> 1028_Media Ranker_Verify scatter plot expands when channel panel is closed
<p><b>Test Case ID</b></p> http://qa.digitas.com/SpiraTest/523/TestCase/8519.aspx
<p><b>Objective</b></p> Verify scatter plot expands when channel panel is closed
<p><b>Module</b></p> MediaRanker
@author Shailesh Kava
@since 02 Feb 2016
**********************************************************************/
public class MediaRanker8519 extends BaseClass{
	boolean bProjectCreate;
	String strMsg;
	String strProjectName;
	
	@Test
	public void test_MediaRanker8519(){
		
		ArchitectMediaRankerPage mediaRanker=null;
		
		try{
			strProjectName="";
			strMsg="";
			bProjectCreate = false;
			property.getProperty("MRFilterCriteria8301");	
			String mediaRankerChannels = property.getProperty("mediaRankerItems");
			mediaRanker  = new ArchitectMediaRankerPage(driver);
			
			//****************Test step starts here************************************************
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
				
				rm.webElementSync("idiomspinningcomplete");
				if(channels[i].trim().toLowerCase().contains("tv"))
					mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "");
				
				if(!mediaRanker.func_VerifyVisibilityOfElement("datepicker"))
					throw new IDIOMException("Failed to open channel###MediaRanker8519-missingScatterPlot");
				
				if(!rm.webElementSync(mediaRanker.datepicker, "clickable"))
					throw new IDIOMException("Date picker is not clickable###MediaRanker8519-datePickerNotClickable");
				
				if(!rm.webElementSync(mediaRanker.closeCategoryBtn, "visible"))
					throw new IDIOMException("Category close (X) is not visible###MediaRanker8519-closeBtnNotVisible");
				
				if(!rm.webElementSync(mediaRanker.closeCategoryBtn, "clickable"))
					throw new IDIOMException("Category close (X) is not clickable###MediaRanker8519-closeBtnNotClickable");
				
				//Step 7: Clicking on close category button
				mediaRanker.func_ClickOnElement("closecatbutton");
				Thread.sleep(2000);
				Dimension beforeDim = mediaRanker.func_GetSizeOfElement("scatteredplot");
				
				//Step 8: Clicking on close category button
				mediaRanker.func_ClickOnElement("closecatbutton");
				Thread.sleep(2000);
				Dimension afterDim = mediaRanker.func_GetSizeOfElement("scatteredplot");
				
				if(beforeDim.width > afterDim.width){
					CustomReporter.log("On close width of scatter plot is expanded: <b>"+beforeDim.width+"</b>==<b>"+afterDim.width+"</b>");
					System.out.println("On close width of scatter plot is expanded "+beforeDim.width+"=="+afterDim.width+"</b>");
				}else{
					rm.captureScreenShot("8519_scatterPlotNotExpanded", "fail");
					CustomReporter.errorLog("On close width of sctter plot is not expanded <b>"+beforeDim.width+"</b>***<b>"+afterDim.width+"</b>");
					System.out.println("On close width of sctter plot is not expanded <b>"+beforeDim.width+"</b>==<b>"+afterDim.width+"</b>");
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
			rm.captureScreenShot("8519", "fail");
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