package com.IDIOM.integration;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** This scenario is part of continuous integration. 
 *  It will verify success metric and audience definition page
 *  
 *  Step 1: Launch browser and open url
 *  Step 2: Login with valid user
 *  Step 3: Select a client
 *  Step 4: Create a project
 *  Step 5: Fill the detail
 *  Step 6: Save project
 *  Step 7: Launch the project
 *  Step 8: Click on Success Metric from project dashboard
 *  Step 9: Verify success metric page
 *  Step 10: Click on any category on right side
 *  Step 11: Select and verify metric
 *  Step 12: Click on Audience Definition tab
 *  Step 13: Verify audience definition page
 *  Step 14: Select and add any attribute
 *  Step 15: Delete the project
 *  Step 16: logout
 * 
 *
 */
public class testproject extends BaseClass {
		
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
			String strSuccessMetrics1=property.getProperty("successMetricScenario4");
			
			String strpopulationtabs=property.getProperty("populationtabs");
			String strAudienceAttributes=property.getProperty("audiencesScenario3");
			String strAudienceAttributes1=property.getProperty("audienceScenario5");
			
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
			successMetricPage.isVisible("nosuccessmetrictext", "");
			CustomReporter.log("Successfully landed on Success Metric page");
			
			//Getting count before adding card
			//int iBefore = successMetricPage.getTotalMetricCards();
			
			//Step10: Add any metric
			/*
			
			/*
			successMetricPage.verifyPopulationPercentage();
			
			
			Thread.sleep(2000);
			successMetricPage.verifyPopulationPercentage();
			
			successMetricPage.verifyOptimalAudience();
			
			
			Thread.sleep(2000);
		successMetricPage.performAction("searchbutton");
		Thread.sleep(2000);
		successMetricPage.verifySearchfunctionality("222222222");
		
		*/
		
		/*successMetricPage.verifyActualAndProjectedTabs(strpopulationtabs);
			
			*/
			
			successMetricPage.selectAllSuccessMetrics();
			Thread.sleep(6000);
			successMetricPage.verifyOptimalAudience();
			
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
