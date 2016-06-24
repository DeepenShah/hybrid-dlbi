package com.IDIOM.ClientHomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Manage Projects 2.a.iv : Do not save the project by clicking 'Cancel'.</p>
 *  <p> <b>Objective:</b>To check cancel button effect</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8830.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 09 Jun 2016
 *
 */
public class ClientHomePage8830 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyCancelProject(){		
		String strMsg="";
		
		try{			
			//****************Test step starts here************************************************
						
			//Login To Selecting Client
			loginToSelectClient();			
			
			//Clicking on 'New Project'
			clientListPage.func_PerformAction("New Project");
			if(!rm.webElementSync(clientListPage.newProjectWindow, "visibility"))
				throw new IDIOMException("No new project window found.###NoNewProjectWindow");
			
			CustomReporter.log("New project window found");
			clientListPage.findAndSaveProjectWindow(true, "");
			
			clientListPage.fillProject(strClassName, "This class is not going to be created");
			CustomReporter.log("Fill the details for the project. Name " + strClassName);
			
			clientListPage.func_PerformAction("Cancel Project");
			CustomReporter.log("Clicked on 'Cancel' ");
			
			//Verifying project window is closed
			if(rm.webElementSync(clientListPage.projectWindow, "visibilitynowait"))
				throw new IDIOMException("After clicking Cancel, project window should get closed but it is still open.###ProjectWindowFoundOpen");
			
			CustomReporter.log("Project window get closed");
			
			//Verifing project should not get saved
			if(clientListPage.verfiyProjectIsExist(strClassName))
				throw new IDIOMException("After clicking Cancel, project should not get saved but it is saved###ProjectNameSavedOnClickingCancel");
			
			CustomReporter.log("Verified: Project is not saved");
			
			
		}catch(Exception e){
			exceptionCode(e,strClassName);
		}finally{
			try{
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
		
	
}
