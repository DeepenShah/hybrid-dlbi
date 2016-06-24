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
<p>	<b>Test Case Name:<b> Filtering Menu:Bottom SaveBar: 3(e): Verify bar shows number of saved matrics
<p>	<b>Objective:<b> Verify count of selected metrics at bottom of success metrics
<p>	<b>Test Case ID:<b> http://qa.digitas.com/SpiraTest/523/TestCase/8616.aspx
<p>	<b>Module:<b> Success Metrics
@author:<b> Shailesh Kava
@since:<b> 05-May-2016
**********************************************************************/
public class SuccessMetrics8612 extends BaseClass {
	
	AudienceBuilderPage audienceBuilder = null;
	
	@Test
	public void verifySelectedSuccessMetricsCountFromBottomSaveBar(){
		String strMsg = null;
		String strProjectName="";
		boolean bProjectCreate = false;
		audienceBuilder = new AudienceBuilderPage(driver);
		
		try{
			loginToSelectClient();
			
			//Step 4,5: Creating new project
			strProjectName = clientListPage.createNewProject("");
			System.out.println("Clicking on project name: "+strProjectName);
			CustomReporter.log("Clicking on project name: "+strProjectName);
			bProjectCreate = true;
			
			//Step 6: Launch Project
			Thread.sleep(3000);
			clientListPage.launchProject(strProjectName);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			System.out.println("Open Success Metrics page");
			CustomReporter.log("Open Success Metrics page");
			
			//Step 7: Click on destination link success metrics
			CustomReporter.log("Navigate to success metrics page");
			pdp.navigateTo(property.getProperty("successMetrics"));
			
			if(!audienceBuilder.isVisible("successmetricstexttitle", ""))
				throw new IDIOMException("Success metrics page is not visible###SuccessMetrics8612-pageIsNotVisible");
			
			CustomReporter.log("Verify success metrics page is open, adding metrics");
			
			//Step 8: Adding success metrics
			audienceBuilder.selectMetricByName(property.getProperty("SelectingSuccessMetrics8685"));
			
			CustomReporter.log("Metrics are added properly");
			
			int countSelectedMetricsInCenterArea = audienceBuilder.getTotalMetricCards();
			int countSelectedMetricsInRightBottom = audienceBuilder.getSelectedSuccessMetricsCountFromBottomRight();
			
			//Step 8: Verifying selected metrics count with bottom save bar
			if(countSelectedMetricsInRightBottom == countSelectedMetricsInCenterArea){
				CustomReporter.log("Proper count is match at right bottom save bar");
			}else{
				throw new IDIOMException("Proper count is not appearing at right bottom save bar###SuccessMetrics8612-countNotMatched");
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
			rm.captureScreenShot("8612", "fail");
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