package com.IDIOM.ClientHomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Def 2431_Client HomePage_Verify Cancel Functionality In Edit Overlay</p>
 *  <p> <b>Objective:</b>Verify that the data is not getting saved on updating some data in edit overlay and click on cancel</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8973.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 02 Jun 2016
 *
 */
public class ClientHomePage8973 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyCancelOnEditProject(){		
			
		String strProjectName="Automation Project - 8973";	
		String strOrigProjectName = strProjectName;
		String strMsg="";
		String strDeletionDetails="";
		
		boolean bProjectCreated = false;	
		
		try{
			
			//****************Test step starts here************************************************
			
			//Login To Selecting Client
			loginToSelectClient();			
			
			//Step3: Click on New Project			
			clientListPage.createNewProject(strProjectName);
			Thread.sleep(2000);		
			
			//Storing detail for deletion
			strDeletionDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			bProjectCreated = true;
			
			//Step5: Launch project.
			clientListPage.launchProject(strProjectName);
			rm.webElementSync(pageHeader.clientLogo, "visibility");
			CustomReporter.log("Launch the project and navigated to project dashboard");
			
			//Step6: Click on idiom logo
			pageHeader.performAction("idiomlogo");
			rm.webElementSync(clientListPage.newProjectBtn, "visibility");
			CustomReporter.log("Clicked on idiom logo and returned back to client home page");
							
			//Step7: Edit newly created project						
			clientListPage.performActionOnProject("edit", strProjectName);
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			//Step8: Change the name and clicking cancel
			strProjectName = "Cancel: " +strProjectName;
			clientListPage.fillProject(strProjectName, "");
			
			strMsg = "Updated project name to " +strProjectName +" and clicking cancel";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Step9: Cancel project
			clientListPage.func_PerformAction("Cancel Project");
			Thread.sleep(2000);
			
			//Verifying changes with updated name
			if(clientListPage.verfiyProjectIsExist(strProjectName))
				throw new IDIOMException("Cancel is not working. Project got saved.###CancelIsNotWorking");
			
			CustomReporter.log("Clicking cancel is working fine. Project name is not saved.");
			
			//Repeating same thing with close button		
						
			clientListPage.performActionOnProject("edit", strOrigProjectName);
			clientListPage.findAndSaveProjectWindow(false, strOrigProjectName);
			
			//Change the name and clicking close
			strProjectName = "Close: " +strOrigProjectName;
			clientListPage.fillProject(strProjectName, "");
			
			strMsg = "Updated project name to " +strProjectName +" and closing project overlay without saving";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Close project
			clientListPage.func_PerformAction("Close Project");
			Thread.sleep(2000);
			
			//Verifying changes with updated name
			if(clientListPage.verfiyProjectIsExist(strProjectName))
				throw new IDIOMException("Closing project overlay is not working. Project got saved.###CloseWindowIsNotWorking");
			
			CustomReporter.log("Closing project overlay is working fine. Project name is not saved.");
			
		}catch(Exception e){
			exceptionCode(e);
		}finally{
			try{
				
				//Deleting newly created project or Audience			
				if(bProjectCreated){				
					rm.deleteProjectOrAudience(strDeletionDetails, true);
					CustomReporter.log("Deleted the project");					
				}
				
				//Logout
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(strClassName + ": " + strMsg);
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
		
	public void exceptionCode(Exception ie){
		BaseClass.testCaseStatus = false;
		String strMsgAndFileName[] = ie.getMessage().split("###");
		System.out.println(strClassName + ": " + strMsgAndFileName[0]);
		ie.printStackTrace(System.out);
		
		if(strMsgAndFileName.length==1){
			CustomReporter.errorLog("Failure: "+ ie.getMessage());
			rm.captureScreenShot("8973-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8973-"+strMsgAndFileName[1], "fail");	
		}		
	}
}
