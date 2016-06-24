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
<p>	<b>Test Case Name:</b> Search filtering: Verify search with special characters </p>
<p>	<b>Objective:</b> Results should not appear for special characters. </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8642.aspx </p>
<p>	<b>Module:</b> Audience Builder </p>
@author: Shailesh Kava
@since: 16-May-2016
**********************************************************************/
public class SuccessMetrics8642 extends BaseClass {
	
	AudienceBuilderPage audienceBuilder = null;
	
	@Test
	public void verifySearchResultForSpecialChars(){
		String strMsg = null;
		String strProjectName="";
		boolean bProjectCreate = false;
		audienceBuilder = new AudienceBuilderPage(driver);
		
		try{
			//****************Variables declaration and Initialization****************************	
			String selMetric = property.getProperty("addsuccessMetric").trim();
			String searchString = property.getProperty("search8642").trim();
			//****************Test step starts here************************************************
			
			loginToSelectClient();
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			//Step 4,5,6: Create/Select project and launch the same 
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
			
			//Step 7: Click on Success Metrics to go to Audience Builder Section 
			CustomReporter.log("Navigate to success metrics page");
			System.out.println("Navigate to success metrics page");
			
			pdp.navigateTo(property.getProperty("successMetrics"));
			
			if(!audienceBuilder.isVisible("successmetricstexttitle", ""))
				throw new IDIOMException("Success metrics page is not visible###SuccessMetrics8642-successMetricsNotOpen");
			
			//Step 8: Select any category of success metrics say Transaction 
			CustomReporter.log("Success metrics page is open, clicking on ["+selMetric+"]");
			
			audienceBuilder.selectMetricByName(selMetric);
			
			if(!audienceBuilder.isVisible("searchbutton",""))
				throw new IDIOMException("Search button is not visible###SuccessMetrics8642-searchButtonIsNotVisible");
			
			//Step 9: Click on  search icon to search any success metrics
			CustomReporter.log("Clicking on search button");
			audienceBuilder.performAction("searchButton");
			
			if(!audienceBuilder.isVisible("searchtextbar", ""))
				throw new IDIOMException("Search text box is not visible###SuccessMetrics8642-searchTextBoxIsNotVisible");
			
			//Step 10: Search with special character
			CustomReporter.log("Input value as search string ["+searchString+"]");
			audienceBuilder.inputSearchValue(searchString);
			if(audienceBuilder.selectSuccessMetricsByRowID(1))
				throw new IDIOMException("Search results found for special characters###SuccessMetrics8642-searchResultFound");
			
			CustomReporter.log("Search result not appearing for entered value ["+searchString+"]");
			
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
			rm.captureScreenShot("8642", "fail");
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