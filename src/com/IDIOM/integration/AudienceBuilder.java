package com.IDIOM.integration;

import org.testng.Assert;

import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.IDIOM.pages.AudienceBuilderPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b> Verify Success metrics and Audience Definition tabs in Audience Builder page </p>
 * <p> <b>Objective:</b> Verify Success metrics and Audience Definition tabs in Audience Builder page</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8991.aspx </p>
 * <p> <b>Module:</b> Audience Builder</p>
 * @author Deepen Shah
 * @since 25 April 2016
 *
 */

public class AudienceBuilder extends BaseClass {
		
	@Test
	public void addAndVerifySuccessMetricAndAttribute(){
				
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			strProjectName=property.getProperty("projectNameScenario3");
			String strProjectDescription=property.getProperty("projectDescriptionScenario3");
			String strSuccessMetrics=property.getProperty("successMetricScenario3");
			String strAudienceAttributes=property.getProperty("audiencesScenario3");
			
			
			//****************Test step starts here************************************************
			//Step1: Open URL
			strMsg = "Launched Browser and Enter URL";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			Login_Page ln = new Login_Page(driver);
			
			//Step2: Login with valid credential
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    strMsg = "Logged in successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Step3: Select client
			clientListPage = new ClientList_Page(driver);
			
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
						
			//Step4: Click on new project
			clientListPage.func_PerformAction("New Project");
			rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Step5: Fill the project
			clientListPage.findAndSaveProjectWindow(true, "");
			
			//Before filling details, checking 'Audience' tab is exist or not. It should be false.
			if(clientListPage.func_ClientListPageElementExist("audiencetab"))
				throw new IDIOMException("Not able to verify new project window###SmokeScenario3-CreateProjectWindow");
										
			//If audience tab is not found then everything is good
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			//Step6: Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###SmokeScenario3-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			bProjectCreate = true;
			
			//Step7: Launch the project
			clientListPage.func_PerformAction("Launch Project");
			strMsg = "Clicked on Launch Project";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
				
			//Waiting till page get loaded
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			//Step8: Click on success metric link
			pdp.navigateTo(property.getProperty("successMetrics"));
			CustomReporter.log("Navigated to success metric page");
		
			AudienceBuilderPage successMetricPage = new AudienceBuilderPage(driver);
			
			//Step9: Verify success metric page.
			successMetricPage.isVisible("nosuccessmetrictext","");
			CustomReporter.log("Successfully landed on Success Metric page");
			
			//Getting count before adding card
			int iBefore = successMetricPage.getTotalMetricCards();
			
			//Step10: Add any metric
			successMetricPage.selectMetricByName(strSuccessMetrics);
			Thread.sleep(2000);
			
			//Getting count after adding card
			int iAfter = successMetricPage.getTotalMetricCards();
			
			//Step11: Verifying success metric card
			if(iAfter <= iBefore)
				throw new IDIOMException("Failed to add success metric ###SmokeScenario3-NoSuccessMetricAdded");
			
			CustomReporter.log("Successfully added first metric card");
			rm.captureScreenShot("SmokeScenario3-MetricCardHasBeenAdded", "pass");
			
			//Step12: Navigate to audience definition
			successMetricPage.performAction("gotoaudiencedefinition");
			rm.webElementSync(successMetricPage.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on Audience Definition tab");
			
			
			//Step13: Verify audience definition page
			if(successMetricPage.getGroupCount() != 1)
				throw new IDIOMException("Not able to verify default group ###SmokeScenario3-FailedToFindDefaultGroup");
			
			CustomReporter.log("Successfully verify default group on audience definition");
		
			//Getting count before adding any attribute
			iBefore = successMetricPage.getTotalQueryItems();
			
			//Step14: Add attribute			
			successMetricPage.selectAttributeOnAudienceDefinition(strAudienceAttributes);
			successMetricPage.performAction("addattribute");
			Thread.sleep(2000);
			
			//Getting count after adding attribute
			iAfter = successMetricPage.getTotalQueryItems();
			
			if(iAfter <= iBefore)
				throw new IDIOMException("Failed to add attribute on audience definition page. Attributes count before " + iBefore + " and now " + iAfter +" ###SmokeScenario3-FailedToAddAttribute");
			
			CustomReporter.log("Successfully added attribute in the default group");
			rm.captureScreenShot("SmokeScenario3-AttributeAdded", "pass");	
			
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
			rm.captureScreenShot("Smoke-Scenario3", "fail");
		}finally{
			try{
				
				//Step 13: Deleting newly created project
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
		
		if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}
	
	}
}
