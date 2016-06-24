package com.IDIOM.ClientHomePage;

import java.text.SimpleDateFormat;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>2476_Manage Projects_ManagingClient_Verify Create Date, Modified date immediately after once Project is edited</p>
 *  <p> <b>Objective:</b>Verify Create Date, Modified date immediately after once Project is edited</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8959.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 16 Jun 2016
 *
 */
public class ClientHomePage8959 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyCreateModifedDateImmediateAfterProjectUpdate(){		
		String strMsg="";		
		String strProjectDetails="";
		String strExistingProjectName="";		
		String strExistingProjectDesc="";
		
		boolean bStatus = true;
		boolean bUpdated = false;
		try{
			
			//****************Test step starts here************************************************
						
			//Step1-2: Login To Selecting Client
			loginToSelectClient();			
			
			//Getting any existing project older than a day
			int iProjectId = clientListPage.getOldModifiedProject();
			
			if(iProjectId < 0){
				bStatus = false;
				throw new IDIOMException("No any existing project last modified before than a day found.###NoOldProjectFound");
			}
			
			CustomReporter.log("Old project found and Order of project is " + iProjectId);
			
			//Editing project
			strExistingProjectName = clientListPage.getProjectNameById(iProjectId);
			strProjectDetails = clientListPage.getAudienceProjectClientId(strExistingProjectName, "");
			
			clientListPage.performActionOnProject("edit", strExistingProjectName);
			clientListPage.findAndSaveProjectWindow(false, strExistingProjectName);
			
			strExistingProjectDesc = clientListPage.getProjectNameAndDescriptionFromOverlay()[1];
			
			CustomReporter.log("Existing project details Name: " + strExistingProjectName +" and description: " + strExistingProjectDesc);
			
			//Fill new name and save
			clientListPage.fillAndSaveProject("TimeChange: " + strExistingProjectName, strExistingProjectDesc);
			clientListPage.func_PerformAction("Close Project");
			Thread.sleep(2000);
			
			bUpdated = true;
			CustomReporter.log("Updated project and now getting modifed date");
			
			String strTodaysDate = new SimpleDateFormat(BaseClass.strDateFormat).format(rm.getTodaysDate());
			String strModifiedDate = clientListPage.getCreatedModifiedDateByProjectName("TimeChange: " +strExistingProjectName, 1).split("#")[1];
			
			if(!strModifiedDate.equalsIgnoreCase(strTodaysDate))
				throw new IDIOMException("Modified date is not reflecting as todays date. Expected " + strTodaysDate +
						" and found " + strModifiedDate+".###ModifiedDateIsNotReflectingImmediately");
			
			CustomReporter.log("Successfully verified immediate reflection in modified date. Modified date is " + strModifiedDate);
			
		}catch(Exception e){
			exceptionCode(e,strClassName);
		}finally{
			try{
				
				if(bUpdated){
					util.updateProjectNameAndDescriptionByHttpClient(strProjectDetails, strExistingProjectName, strExistingProjectDesc);
					CustomReporter.log("Updated project back to its original value");
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
		
		if(!BaseClass.testCaseStatus && !bStatus){
				CustomReporter.errorLog("Test case skipped");
				throw new SkipException("Skipping this test case as no project with last modified before than today found ");
		  }else if(!BaseClass.testCaseStatus){
			  CustomReporter.errorLog("Test case failed");
			  Assert.assertTrue(false);
		  }else{
			  CustomReporter.log("Test case passed");
		  }	
	}
}
