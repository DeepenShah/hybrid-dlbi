package com.IDIOM.integration;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;
import common.ReusableMethods;

/**
 * Test Case Name: Create audience in manage project page
 * Objective: This is part of continuous integration. To verify create audience functionality on Manage project page
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8990.aspx </p>
 * <p> <b>Module:</b> Client Home Page </p>
 * @author Deepen Shah
 * @since 22 April 2016
 *
 */
public class ClientHomePageAudience extends BaseClass {
		
	@Test
	public void createAndVerifyAudience(){
				
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			strProjectName=property.getProperty("projectNameScenario2");
			String strProjectDescription=property.getProperty("projectDescriptionScenario2");
			String strAudienceName = property.getProperty("audienceNameScenario2");
			
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
				throw new IDIOMException("Not able to verify new project window###SmokeScenario2-CreateProjectWindow");
										
			//If audience tab is not found then everything is good
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			//Step6: Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###SmokeScenario2-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			bProjectCreate = true;
			
			//Step7: Close project window
			clientListPage.func_PerformAction("Close Project");
			strMsg = "Closed project window";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Step8: Verify saved project is found in the list.
			if(!clientListPage.verfiyProjectIsExist(strProjectName))
				throw new IDIOMException("Fails verify project in project list.###SmokeScenario2-ProjectNotFoundFromTheList");
			
			CustomReporter.log("Project " + strProjectName + " verified in the list");
			ReusableMethods.scrollToBottom(driver);
			rm.captureScreenShot("SmokeScenario2-Project", "pass");
		
			//Step9: Edit project
			clientListPage.performActionOnProject("edit",strProjectName);
			CustomReporter.log("Open project in Edit mode");
			
			//Step 10: Go to audience tab
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			clientListPage.func_PerformAction("Audience Tab");
			if(!clientListPage.isVisible("newaudiencebtn"))
				throw new IDIOMException(" Button '+New Audience' is not visible ###SmokeScenario2-NewAudienceBtnMissing");
			
			//Getting count before clicking on new
			int iBefore = clientListPage.getAudienceCount();		
			
			//Step 11: Create new audience
			clientListPage.func_PerformAction("New Audience");
			CustomReporter.log("Clicked on '+New Audience' link");
						
			//Verify new elements for create new audience
			if(!clientListPage.isVisible("newaudiencerow"))
				throw new IDIOMException("Failed to verify new row for audience###SmokeScenario2-NoNewAudienceRowFound");
			
			CustomReporter.log("Verified visibility of new audience row");
			
			//Fill the name
			clientListPage.fillAudience(strAudienceName,"");
			
			//Save the audience
			clientListPage.performActionOnAudience("", "createandsave");
			Thread.sleep(3000);
			
			//Get count
			int iAfter = clientListPage.getAudienceCount();			
			
			if(iAfter < (iBefore+1))
				throw new IDIOMException("Failed to create new audience###SmokeScenario2-FailedToCreateNewAudience");
			
			CustomReporter.log("Successfully created new audience");
			rm.captureScreenShot("SmokeScenario2-NewAudience", "pass");
			
			//Step 12: Close project
			clientListPage.func_PerformAction("Close Project");
			Thread.sleep(2000);
			
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
			rm.captureScreenShot("Smoke-Scenario2", "fail");
		}finally{
			try{
				
				//Step 13: Deleting newly created project
				if(bProjectCreate){
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
