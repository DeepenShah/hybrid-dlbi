package com.IDIOM.integration;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;

import common.BaseClass;
import common.ReusableMethods;


/** 
 * <p> <b>Test Case Name:</b> Verify Client home page </p>
 * <p> <b>Objective:</b> This is part of continuous integration. To verify user is able to create a new project and verify in the project list</p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8989.aspx </p>
 * <p> <b>Module:</b> Client Home Page</p>
 * @author Deepen Shah
 * @since 19 April 2016
 *
 */
public class ClientHomePageProject extends BaseClass {
		
	@Test
	public void createAndVerifyProject(){
				
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		
		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			strProjectName=property.getProperty("projectNameScenario1");
			String strProjectDescription=property.getProperty("projectDescriptionScenario1");
			
			
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
			if(clientListPage.func_ClientListPageElementExist("audiencetab")){
				BaseClass.testCaseStatus = false;
				rm.captureScreenShot("Integration-Scenario1-AudienceTabFound-OnNewProjectwindow", "fail");
			}else{ 
				
				//If audience tab is not found then everything is good
			
				clientListPage.fillProject(strProjectName,strProjectDescription);				
				clientListPage.func_PerformAction("Save Project");
				
				//Step6: Verifying if project is saved and landed to audience tab
				if(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")){
					strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
					CustomReporter.log(strMsg);
					System.out.println(getClass().getSimpleName() + ": " + strMsg);
					
					//Step7: Close project window
					clientListPage.func_PerformAction("Close Project");
					strMsg = "Closed project window";
					CustomReporter.log(strMsg);
					System.out.println(getClass().getSimpleName() + ": " + strMsg);
					
					//Step8: Verify saved project is found in the list.
					if(clientListPage.verfiyProjectIsExist(strProjectName)){
						strMsg = "Project found in the list.";
						CustomReporter.log(strMsg);
						System.out.println(getClass().getSimpleName() + ": " + strMsg);
						bProjectCreate = true;
					}else{
						BaseClass.testCaseStatus = false;
						strMsg = "Fails verify project in project list.";
						CustomReporter.log(strMsg);
						System.out.println(getClass().getSimpleName() + ": " + strMsg);
						
						ReusableMethods.scrollToBottom(driver);
						rm.captureScreenShot("Integration-Scenario1-ProjectNotFoundFromTheList", "fail");
					}
					
				}else{
					BaseClass.testCaseStatus = false;
					strMsg = "Fails to land on Audience tab. Problem might be in saving project";
					CustomReporter.log(strMsg);
					System.out.println(getClass().getSimpleName() + ": " + strMsg);
					rm.captureScreenShot("Integration-Scenario1-AudienceTabNotFound", "fail");
				}
			}
			
		}catch(Exception e){
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("Smoke-Scenario1", "fail");
		}finally{
			try{
				
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
