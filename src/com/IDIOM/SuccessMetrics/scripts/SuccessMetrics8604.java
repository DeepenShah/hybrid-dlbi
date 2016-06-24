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
<p>	<b>Test Case Name:<b> Success Metrics - 2.d.ii - Verify search bar or search icon click
<p>	<b>Objective:<b> Verify search bar or search icon click
<p>	<b>Test Case ID:<b> http://qa.digitas.com/SpiraTest/523/TestCase/8604.aspx
<p>	<b>Module:<b> Success Metrics
@author:<b> Shailesh Kava
@since:<b> 03-May-2016
**********************************************************************/
public class SuccessMetrics8604 extends BaseClass {
		
	@Test
	public void verifySearchTextBox(){
		String strMsg = null;
		String strProjectName="";
		boolean bProjectCreate = false;
		try{
			//****************Variables declaration and Initialization****************************	
			String searchString = "search";
			//****************Test step starts here************************************************
			
			loginToSelectClient();
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			//Step 2,3: Selected a client now selecting/creating new project 
			if(totalProjects == 0){
				strProjectName = clientListPage.createNewProject("");
				clientListPage.func_PerformAction("Launch Project");
				
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
				throw new IDIOMException("Success metrics page is not visible###SuccessMetrics8604-pageIsNotVisible");
			
			CustomReporter.log("Select a success metrics");
			//Select metric by name
			int countSelectedMetricsInCenterArea = audienceBuilder.getTotalMetricCards();
			if(countSelectedMetricsInCenterArea == 0)
				audienceBuilder.selectMetricByName(property.getProperty("addsuccessMetric"));
			
			//Enter search string in search text box
			CustomReporter.log("Click on search button");
			audienceBuilder.performAction("searchbutton");
			Thread.sleep(2000);
			CustomReporter.log("Search string ["+searchString+"]");
			audienceBuilder.inputSearchString(searchString);
			Thread.sleep(2000);
			
			CustomReporter.log("Verify that user could enter search string");
			if(!audienceBuilder.getSearchedStringFromTextBox().equals(searchString)){
				throw new IDIOMException("Search string is not matched with input string###SuccessMetrics8604-searchStringNotMatched");
			}else{
				CustomReporter.log("Search string is matched");
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
			rm.captureScreenShot("8604", "fail");
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