package com.IDIOM.ClientHomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Manage Projects 3.c : Cancel deletion of the project.</p>
 *  <p> <b>Objective:</b>Clicking 'Cancel' on delete project should not delete the project.</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8863.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 14 Jun 2016
 *
 */
public class ClientHomePage8863 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyCancelProjectDeletion(){		
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
			
			
			//Step5: Click on no
			clientListPage.performActionOnProject("no", strProjectName);
			Thread.sleep(2000);
			CustomReporter.log("Clicked on no button");
			
			if(!clientListPage.verfiyProjectIsExist(strProjectName)){
				bStatus = false;
				throw new IDIOMException("Failed to cancel project deletion.###FailedToCancelDelete");
			}
			
			CustomReporter.log("Successfully canceled project deletion");
			
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
