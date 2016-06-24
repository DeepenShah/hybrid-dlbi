package com.IDIOM.ClientHomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Def 2316_Client HomePage_Verify Edit Overlay Navigation </p>
 *  <p> <b>Objective:</b>Verify that the user is geting navigated to audience tab in edit overlay on clicking on save button.
 *  Also verify that when user navigates back from project home page to clinet home page, no  overlayes are open</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8967.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 01 Jun 2016
 *
 */
public class ClientHomePage8967 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyEditOverlay(){		
			
		String strProjectName="Automation Project - 8967";
		String strProjectDesc = "This project is created to verify overlay remains open even after navigating to another page.";
		String strMsg="";
		String strDeletionDetails="";
		
		boolean bProjectCreated = false;	
		
		try{
			
			//****************Test step starts here************************************************
			
			//Login To Selecting Client
			loginToSelectClient();			
			
			//Step3: Click on New Project			
			clientListPage.func_PerformAction("New Project");
			if(!rm.webElementSync(clientListPage.newProjectWindow, "visibility"))
				throw new IDIOMException("Not able to find new project overlay.###FailedToVerifyNewProjectWindow");
			
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
							
			clientListPage.findAndSaveProjectWindow(true, "");
			
			//Step4: Fill details and click save
			clientListPage.fillProject(strProjectName,strProjectDesc);				
			clientListPage.func_PerformAction("Save Project");
			Thread.sleep(2000);
			CustomReporter.log("Prorject " +strProjectName + " created successfully");
			
			//Storing detail for deletion
			strDeletionDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			bProjectCreated = true;
			
			//Step5: Click on 'Launch Project'.
			clientListPage.func_PerformAction("Launch Project");
			rm.webElementSync(pageHeader.clientLogo, "visibility");
			CustomReporter.log("Launch the project and navigated to project dashboard");
			
			//Step6: Click on idiom logo
			pageHeader.performAction("idiomlogo");
			rm.webElementSync(clientListPage.newProjectBtn, "visibility");
			CustomReporter.log("Clicked on idiom logo and returned back to client home page");
							
			//Verifying project overlay.
			if(clientListPage.isVisible("projectwindow")){
				exceptionCode(new IDIOMException("Project overlay found open.###NewProjectOverlayFoundOpen"));
				clientListPage.func_PerformAction("Close Project");
				
			}else{
				CustomReporter.log("Project overlay is closed");
				rm.captureScreenShot("NoOverlay-New", "pass");
			}
			
			//Step7: Edit project window
			clientListPage.performActionOnProject("edit", strProjectName);
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			//Step8: Rename the project
			strProjectName = strProjectName + " Renamed";
			strProjectDesc = "Updated: " + strProjectDesc;
			clientListPage.fillAndSaveProject(strProjectName, strProjectDesc);
			
			strMsg = "Project rename to " + strProjectName;
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Step9: Launch project
			clientListPage.func_PerformAction("Launch Project");
			rm.webElementSync(pageHeader.clientLogo, "visibility");
			CustomReporter.log("Launch the project and navigated to project dashboard");
			
			//Step10: Click on idiom logo
			pageHeader.performAction("idiomlogo");
			rm.webElementSync(clientListPage.newProjectBtn, "visibility");
			CustomReporter.log("Clicked on idiom logo and returned back to client home page");
			
			if(clientListPage.isVisible("projectwindow")){
				exceptionCode(new IDIOMException("Edit project overlay found open.###EditProjectOverlayFoundOpen"));
				clientListPage.func_PerformAction("Close Project");				
			}else{
				CustomReporter.log("Project overlay is closed");
				rm.captureScreenShot("NoOverlay-Edit", "pass");
			}
			
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
			rm.captureScreenShot("8967-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("8967-"+strMsgAndFileName[1], "fail");	
		}		
	}
}
