package com.IDIOM.ClientHomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Manage Projects 3.c : Delete the project.</p>
 *  <p> <b>Objective:</b>To verify delete project.</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8862.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 14 Jun 2016
 *
 */
public class ClientHomePage8862 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void vrerifyDeleteProject(){		
		String strMsg="";		
		String strProjectDetails="";
		String strProjectName="";
		
		boolean bStatus = true;
		try{
			
			//****************Test step starts here************************************************
						
			//Step1-3: Login To Selecting Client
			loginToSelectClient();			
			
			//Select/Create project			
			strProjectName = clientListPage.createNewProject("");
			strProjectDetails = clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			//Step4: Delete Project
			clientListPage.performActionOnProject("delete", strProjectName);
			CustomReporter.log("Clicked on Deleted button");
			
			if(clientListPage.isVisible("deleteyes")){
				CustomReporter.log("Verified yes button for delete action");
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to find yes button for delete action");
			}
			
			if(clientListPage.isVisible("deleteno")){
				CustomReporter.log("Verified no button for delete action");
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to find no button for delete action");
			}
			
			if(!bStatus)
				rm.captureScreenShot("YesNoButtonNotFound", "fail");
			
			bStatus = true;
			//Step5: Click on Yes
			clientListPage.performActionOnProject("yes", strProjectName);
			Thread.sleep(2000);
			CustomReporter.log("Clicked on Yes button");
			
			if(clientListPage.verfiyProjectIsExist(strProjectName)){
				bStatus = false;
				throw new IDIOMException("Failed to delete project.###FailedToDelete");
			}
			
			CustomReporter.log("Successfully deleted the project");
			
		}catch(Exception e){
			exceptionCode(e,strClassName);
		}finally{
			try{
				
				if(!strProjectDetails.equalsIgnoreCase("") && !bStatus){
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
