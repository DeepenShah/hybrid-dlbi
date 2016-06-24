package com.IDIOM.ClientHomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>2657_Manage Projects_ManagingClient_Verify project name in the list after editing name and description </p>
 *  <p> <b>Objective:</b>This is to verify when user performs edit on name and description of the project and click on save button. Updated name should be displayed in the project list.</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8951.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 15 Jun 2016
 *
 */
public class ClientHomePage8951 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyUpdatedProjectNameWithoutRefresh(){		
		String strMsg="";		
		String strProjectDetails="";
		String strProjectName="";		
		
		boolean bStatus = true;
		try{
			
			//****************Test step starts here************************************************
						
			//Step1-2: Login To Selecting Client
			loginToSelectClient();			
			
			//Create project			
			strProjectName = clientListPage.createNewProject("");
			strProjectDetails = clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			//Step3: Select any project and edit its name and desc
			clientListPage.performActionOnProject("edit", strProjectName);
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			clientListPage.func_PerformAction("Project Tab");
			
			clientListPage.fillAndSaveProject("Updated-"+strProjectName, "Updated description.");
			CustomReporter.log("Updated project name to 'Updated-" + strProjectName +"' and saved.");
			
			clientListPage.func_PerformAction("Close Project");
			Thread.sleep(2000);
			
			if(!clientListPage.verfiyProjectIsExist("Updated-"+strProjectName))
				throw new IDIOMException("Updated name is not reflected on home page. It should be Updated-" + strProjectName+".###UpdatedNameIsNotReflectingOnHome");
			
			CustomReporter.log("Successfully verified immediate reflection of project name update");
			
		}catch(Exception e){
			exceptionCode(e,strClassName);
		}finally{
			try{
				
				if(!strProjectDetails.equalsIgnoreCase("") && bStatus){
					util.deleteProjectOrAudience(strProjectDetails, true);
					CustomReporter.log("Deleted Project");
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
}
