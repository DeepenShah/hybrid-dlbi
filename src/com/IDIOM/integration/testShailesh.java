/********************************************************************
Test Case Name: Verify Analyze pages (Profile, Pathing, HVA and Webnet)
Objective: Verify Analyze pages (Profile, Pathing, HVA and Webnet) should be accessible
Test Case ID: qa.digitas.com/SpiraTest/523/TestCase/8992.aspx
Module: User Analyze
@author: Shailesh Kava
@since: 27-April-2016
**********************************************************************/

package com.IDIOM.integration;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

public class testShailesh extends BaseClass {
		
	@Test
	public void analysePagesVerification(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strProjectName=property.getProperty("projectName");
			String strProjectDescription=property.getProperty("projectDescription");
			//****************Test step starts here************************************************
			
			clientListPage = new ClientList_Page(driver);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			Login_Page ln = new Login_Page(driver);
			
			//Step1: Open URL
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			clientListPage.clickProjectById(1);
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			System.out.println("Clicking on Profile link from Project dashboard page");
			CustomReporter.log("Clicking on Profile link from Project dashboard page");
			
			//Step4: Click on new profile page
			//pdp.navigateTo("Success Metrics");
			pdp.navigateTo("Audience Definition");
			Thread.sleep(10000);
			
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);
			
			Thread.sleep(10000);
			//String groupLogic = audienceBuilder.getMainGroupLogic("1");
			//audienceBuilder.selectGroup("2.1");
			//System.out.println("==="+groupLogic+"===");
			
			//Success metrics get values from left side
			/*
			
			HashMap<Integer, String> successMetricsValueAndPerncetage = new HashMap<>();
			
			successMetricsValueAndPerncetage = audienceBuilder.getSuccessMetricsNamenPercentageFromLeftPanel();
			
			System.out.println(successMetricsValueAndPerncetage);*/
			
			//audienceBuilder.addNewGroup();
			
			//audienceBuilder.addSubGroup(1, "1.1");
			//audienceBuilder.deleteGroup("3.1");
			//audienceBuilder.selectGroup("3.1");
			
			/*String selectAudienceCategory = property.getProperty("addcategoryinaudiencedefinition");
			System.out.println(selectAudienceCategory);*/
			/*String selectAudienceCategory = property.getProperty("addsuccessMetric");
			
			audienceBuilder.seletMetricByName(selectAudienceCategory);
			
			Thread.sleep(5000);
			System.out.println(audienceBuilder.isVisible("searchiconvisibility", ""));
			Thread.sleep(5000);
			audienceBuilder.performAction("searchbutton");
			Thread.sleep(2000);
			System.out.println("searchmegnifyicon "+audienceBuilder.isVisible("searchmegnifyicon", ""));
			System.out.println("searchtextbar "+audienceBuilder.isVisible("searchtextbar", ""));*/
			
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
			rm.captureScreenShot("AnalysePagesIssue", "fail");
		}finally{
			try{
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