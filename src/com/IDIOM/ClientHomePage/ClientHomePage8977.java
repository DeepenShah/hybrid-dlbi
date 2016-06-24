package com.IDIOM.ClientHomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Def 2650_Client HomePage_Verify Duplicate-Cancel Functionality</p>
 *  <p> <b>Objective:</b>Verify that when user clicks on cancel button of the duplicate project overlay, the the project is getting duplicated without saving any newly edited data </p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8977.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Abhishek Deshpande
 * @since 01 Jun 2016
 *
 */
public class ClientHomePage8977 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyEditOverlay(){		
			
		String strProjectName="Automation Project - 8977";
		String strProjectDesc = "This project is created to verify the duplicate functionality";
		String strDuplicateProj = "Duplicate project for 8977";
		String strDuplicateDesc = "To verify duplicate feature";
		String strMsg="";
		String strDeletionDetails="";
		String strDeletionDetails2 = "";
		String strAudienceName="";	
		
		boolean bProjectCreated = false;		
		
		try{
			
			//****************Test step starts here************************************************
			
			//Step 1 and 2: Login to application with Valid credentials and Select a client
			loginToSelectClient();			
			
			//Step3: Click on New Project			
			clientListPage.func_PerformAction("New Project");
			if(!rm.webElementSync(clientListPage.newProjectWindow, "visibility"))
				throw new IDIOMException("Not able to find new project overlay.###FailedToVerifyNewProjectWindow");
			
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);							
			clientListPage.findAndSaveProjectWindow(true, "");
			
			//Step4: Add project name and description and click on Save button
			clientListPage.fillProject(strProjectName,strProjectDesc);				
			clientListPage.func_PerformAction("Save Project");
			Thread.sleep(2000);
			CustomReporter.log("Project " +strProjectName + " created successfully");
			
			//Storing detail for deletion
			strDeletionDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			bProjectCreated = true;            	
			
			//Close the overlay
			clientListPage.func_PerformAction("Close Project");	
			Thread.sleep(4000);
			CustomReporter.log("Clicked on close 'X' icon");
			
			//Select the project and create a duplicate project
			clientListPage.performActionOnProject("duplicate", strProjectName);	
			CustomReporter.log("Select the project "+strProjectName+" from client home page and create a duplicate of it");
			
			//Getting details to delete this project
			Thread.sleep(2000);
			strDeletionDetails2 = clientListPage.getAudienceProjectClientId(strProjectName, "", 2);
			
			//Edit the name and description
			clientListPage.findAndSaveProjectWindow(true, "strProjectName",2);
			CustomReporter.log("Edit the project name and description");
			
			//Step4: Edit project name and description of duplicate project and click on Cancel button
			clientListPage.fillProject(strDuplicateProj,strDuplicateDesc);				
			clientListPage.func_PerformAction("Cancel Project");
			Thread.sleep(2000);
			CustomReporter.log("Do not click on 'Save' button, click on 'Cancel' button");			
			
			//The last edited changes should not be reflected
			if(clientListPage.verfiyDuplicateProjectExist(strProjectName)){
				CustomReporter.log("The last edited changes are not reflected. We have created a duplcate project with same project name");
			}else if(clientListPage.verfiyProjectIsExist(strDuplicateProj)){
				CustomReporter.errorLog("Last edited changes are saved even after clicking on cancel button");				
			}else{				
				throw new IDIOMException("Last edited changes are saved even after clicking on cancel button.###FailedToClickOnCancelButton");
			}			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8977", "fail");
		}finally{
			try{				
				//Step for Deleting newly created project
				if(bProjectCreated){				
					rm.deleteProjectOrAudience(strDeletionDetails, true);					
					CustomReporter.log("Deleted the project");					
				}
				
				if(!strDeletionDetails2.equalsIgnoreCase("")){
					rm.deleteProjectOrAudience(strDeletionDetails2, true);
					CustomReporter.log("Deleted the duplicate project");
				}
				
				//Step 10 - Click on logout 
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