package com.IDIOM.SuccessMetrics.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p>	<b>Test Case Name:<b> Success Metrics - 2.d.i - Verify the components of the search bar
<p>	<b>Objective:<b> Verify the components of the search bar
<p>	<b>Test Case ID:<b> http://qa.digitas.com/SpiraTest/523/TestCase/8602.aspx
<p>	<b>Module:<b> Success Metrics
@author:<b> Shailesh Kava
@since:<b> 03-May-2016
**********************************************************************/
public class SuccessMetrics8602 extends BaseClass {
		
	@Test
	public void verifySearchComponent(){
		String strMsg = null;
		String strProjectName="";
		boolean bProjectCreate = false;
		try{
			loginToSelectClient();
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			//Step 2,3: Selected a client now selecting/creating new project 
			if(totalProjects == 0){
				strProjectName = clientListPage.createNewProject("");
				clientListPage.func_PerformAction("Launch Project");
				bProjectCreate = true;
				
			}else{
				int selectProjectId = totalProjects;
				System.out.println("Clicking on project id: "+selectProjectId);
				CustomReporter.log("Clicking on project id: "+selectProjectId);
				clientListPage.clickProjectById(selectProjectId);
			}
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			System.out.println("Open Success Metrics page");
			CustomReporter.log("Open Success Metrics page");
			
			//Step4: Click on destination link success metrics
			CustomReporter.log("Navigate to success metrics page");
			pdp.navigateTo(property.getProperty("successMetrics"));
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);
			
			CustomReporter.log("Verify success metrics page is open");
			if(!audienceBuilder.isVisible("successmetricstexttitle", ""))
				throw new IDIOMException("Success metrics page is not visible###successMetricsPage-pageIsNotVisible");
			
			//Step 5: Check the components of the search bar
			CustomReporter.log("Click search button");
			audienceBuilder.performAction("searchbutton");
			System.out.println("Clicked on search");
			
			if(!audienceBuilder.isVisible("successmetricstexttitle", ""))
				throw new IDIOMException("Success metrics page is not visible###successMetricsPage-pageIsNotVisible");
			
			//Step 5: Check the components of the search bar
			if(!audienceBuilder.isVisible("searchtextbar", "")){
				BaseClass.testCaseStatus = false;
				CustomReporter.errorLog("Search text bar is not appearing");
			}else{
				CustomReporter.log("Search text bar is appearing");
			}
				
			if(!audienceBuilder.isVisible("searchmegnifyicon", "")){
				BaseClass.testCaseStatus = false;
				CustomReporter.errorLog("Search megnify icon is not appearing");
			}else{
				CustomReporter.log("Search megnify icon is appearing");
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
			rm.captureScreenShot("8602", "fail");
		}finally{
			try{
				
				//Deleting newly created project
				if(bProjectCreate){
					pageHeader.navigateTo("projecthomepage");
					rm.webElementSync(pageHeader.personMenu,"visibility");
					
					clientListPage.func_PerformAction("Close Project");
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