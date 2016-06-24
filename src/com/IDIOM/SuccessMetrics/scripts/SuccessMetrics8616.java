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
<p>	<b>Test Case Name:</b> Filtering Menu:Bottom SaveBar: 3(e (iii)): Verify click action on ">" icon form bottom save bar </p>
<p>	<b>Objective:</b> Verify ">" icon should save selected cards and move to next tab </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8616.aspx </p>
<p>	<b>Module:</b> Success Metrics </p>
@author: Shailesh Kava
@since: 05-May-2016
**********************************************************************/
public class SuccessMetrics8616 extends BaseClass {
	
	AudienceBuilderPage audienceBuilder = null;
	
	@Test
	public void successMetricsClickBottomSaveBarArrow(){
		String strMsg = null;
		String strProjectName="";
		boolean bProjectCreate = false;
		audienceBuilder = new AudienceBuilderPage(driver);
		
		try{
			loginToSelectClient();
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			//Step 4,5,6: Create/Select project and launch the same 
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
			
			//Step 7: Navigate to success metrics page
			CustomReporter.log("Navigate to success metrics page");
			pdp.navigateTo(property.getProperty("successMetrics"));
			
			//Step 7.1: Verifying that success metrics page is open 
			if(!audienceBuilder.isVisible("successmetricstexttitle", ""))
				throw new IDIOMException("Success metrics page is not visible###SuccessMetrics8616-pageIsNotVisible");
			
			CustomReporter.log("Verify success metrics page is open");
			
			int countSelectedMetricsInCenterArea = audienceBuilder.getTotalMetricCards();
			
			//Step 8: Adding success metrics if success metrics count is zero
			if(countSelectedMetricsInCenterArea == 0)
				audienceBuilder.selectMetricByName(property.getProperty("SelectingSuccessMetrics8685"));
			
			//Verify that Arrow is available/visible to navigate to Audience Definition tab
			if(!audienceBuilder.isVisible("successmetreicsarrowtoaudiencedef", ""))
				throw new IDIOMException("Arrow is missing to navigate to Audience Def. tab###SuccessMetrics8616-arrowMissing");
			
			CustomReporter.log("Arrow is present to move to Audience Definition page");
			
			//Step 9: Clicking on arrow to navigate to Audience definition page
			audienceBuilder.clickSuccessMetricsBottomSaveBarArrow();
			
			if(!rm.webElementSync(audienceBuilder.allMetricsLabel,"verifytext", "ALL ATTRIBUTES"))
				throw new IDIOMException("Failed to open Audience Definition page###SuccessMetrics8616-failedToOpenAudienceDef");
				
			CustomReporter.log("Audience definition page is open successfully");
			System.out.println("Audience definition page is open successfully");
			
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
			rm.captureScreenShot("8616", "fail");
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