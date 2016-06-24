package com.IDIOM.SuccessMetrics.scripts;

import java.util.List;

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
<p>	<b>Test Case Name:<b> Common Elements - 1.c.ii Navigation - Verify the saving while tabbing between the two sub-navs.
<p>	<b>Objective:<b> Verify the saving while tabbing between the two sub-navs.
<p>	<b>Test Case ID:<b> http://qa.digitas.com/SpiraTest/523/TestCase/8568.aspx
<p>	<b>Module:<b> Success Metrics
@author:<b> Abhishek Deshpande
@since:<b> 12-May-2016
**********************************************************************/

public class SuccessMetrics8568 extends BaseClass {
	
	@Test
	public void verifyBottomSaveBar(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		String strProjectName="Test Case 8568";
		boolean bProjectCreate = false;
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strClientName=property.getProperty("clientName");
			String strProjectDescription=property.getProperty("projectDescription");
			String strSuccessMetrics=property.getProperty("SelectingSuccessMetrics8598");
			String strSuccessMetrics2=property.getProperty("successMetricScenarioCommon");
			String strAudienceAttributes=property.getProperty("cmmonAudienceAttribute");
			String strAudienceAttribute2=property.getProperty("commonAudienceAttribute2");
			
			
			//****************Test step starts here************************************************
			
			clientListPage = new ClientList_Page(driver);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			Login_Page ln = new Login_Page(driver);
			AudienceBuilderPage successMetricPage = new AudienceBuilderPage(driver);
			
			//Step 1: Open IDIOM URL and login with valid credentials
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);
	        
	        Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			CustomReporter.log(strMsg);			
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//count total number of projects
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			System.out.println("Create new project");
			CustomReporter.log("Create new project");	
			clientListPage.func_PerformAction("New Project");
			
			
			rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			clientListPage.findAndSaveProjectWindow(true, "");
			
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			Thread.sleep(2000);
			clientListPage.func_PerformAction("Launch Project");
			bProjectCreate=true;
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");			
			
			//Step 4: Click on destination link success metrics
			CustomReporter.log("Navigate to success metrics page");
			pdp.navigateTo("Success Metrics");
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);
			
			CustomReporter.log("Verify success metrics page is open");
			if(!audienceBuilder.isVisible("successmetricstexttitle", ""))
				throw new IDIOMException("Success metrics page is not visible###testcase8568-pageIsNotVisible");
			
			//Verify success metric page.
			successMetricPage.isVisible("nosuccessmetrictext","");
			CustomReporter.log("Successfully landed on Success Metric page");			
			
			
			//Getting count before adding card
			int iBeforeSM = successMetricPage.getTotalMetricCards();
			
			//Add any metric
			successMetricPage.selectMetricByName(strSuccessMetrics);
			Thread.sleep(2000);
			
			//Getting count after adding card
			int iAfterSM = successMetricPage.getTotalMetricCards();
			
			System.out.println("count before add ["+iBeforeSM+"] count adter add ["+iAfterSM+"]");
			//Verifying success metric card
			if(iAfterSM <= iBeforeSM)
				throw new IDIOMException("Failed to add success metric ###testcase8568-NoSuccessMetricAdded");
			
			CustomReporter.log("Successfully added Success metrics");
			rm.captureScreenShot("Test8580MetricCardHasBeenAdded", "pass");
			
			//Step 5: Navigate to Audience definition page			
			successMetricPage.performAction("gotoaudiencedefinition");
			rm.webElementSync(successMetricPage.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Navigated to Audience Definition tab");	
			
			//Get count of attribute in default group
			int iBeforeAD = successMetricPage.getQueryItemCountInGroup("1");
			System.out.println("Count Before:" + iBeforeAD);
			CustomReporter.log("Count is "+ iBeforeAD + " before adding query");
			
			//Step 6: Select an attribute
			successMetricPage.selectAttributeOnAudienceDefinition(strAudienceAttributes);
			successMetricPage.performAction("addattribute");
			Thread.sleep(2000);
			
			int iAfterAD = successMetricPage.getQueryItemCountInGroup("1");
			
			if(iAfterAD <= iBeforeAD)
				throw new IDIOMException("Not able to add query to default group.###8568-FailToAddAttribute");
			
			CustomReporter.log("Count is "+ iAfterAD + " after adding query");
			
			//Go back to Success Metrics page
			successMetricPage.performAction("gotosuccessmetric");
			CustomReporter.log("Navigated to Success Metrics tab");	
			
			//Delete all the success metrics added in previous steps
			successMetricPage.removeAllSuccessMetricsOrAudienceDefinitionQueries();
			
			//added as it will wait till default message not appear after remove all metrics
			Thread.sleep(5000);
			rm.webElementSync(audienceBuilder.successMetricsDefaultMessage, "visibility");
			//Verify no success metrics message
			successMetricPage.isVisible("nosuccessmetrictext");
			
			//Getting count before adding card
			iBeforeSM = successMetricPage.getTotalMetricCards();
			
			//Add new success metrics - 2nd time
			successMetricPage.selectMetricByName(strSuccessMetrics2);
			Thread.sleep(2000);
			
			//Verifying success metric cards are added successfully - 2nd time
			System.out.println("count before add ["+iBeforeSM+"] count adter add ["+iAfterSM+"]");
			if(iAfterSM <= iBeforeSM)
				throw new IDIOMException("Failed to add success metric 2nd time ###testcase8568-NoSuccessMetricAdded");
			
			//Switch to Audience Definition tab
			successMetricPage.performAction("gotoaudiencedefinition");
			Thread.sleep(2000);
			
			//Delete all attributes in Audience Definition
			successMetricPage.removeAllSuccessMetricsOrAudienceDefinitionQueries();
			Thread.sleep(2000);
			
			//Get count of attribute in default group
			iBeforeAD = successMetricPage.getQueryItemCountInGroup("1");
			
			//Add new attributes in Audience definition page - 2nd time
			successMetricPage.selectAttributeOnAudienceDefinition(strAudienceAttribute2);
			successMetricPage.performAction("addattribute");
			Thread.sleep(2000);
			
            //Get count of attributes in default group
			iAfterAD = successMetricPage.getQueryItemCountInGroup("1");
			
			//Verify that attributes are added in Audience definition page - 2nd time			
			if(iAfterAD <= iBeforeAD)
				throw new IDIOMException("Not able to add query to default group 2nd time.###8568-FailToAddAttribute");
			
			
			
				
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
		
	