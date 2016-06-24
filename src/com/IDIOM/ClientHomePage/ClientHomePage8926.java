package com.IDIOM.ClientHomePage;

import java.text.SimpleDateFormat;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>2481_Manage Projects_Verify Created Date and Modified Date for Duplicated Project</p>
 *  <p> <b>Objective:</b>Verify Created Date and Modified Date for Duplicated Project</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8926.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 15 Jun 2016
 *
 */
public class ClientHomePage8926 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verfiyCreateAndModifiedDateForDuplicateProject(){		
		String strMsg="";		
		String strProjectDetails="";
		String strProjectName="";
		
		boolean bStatus = true;
		try{
			
			//****************Test step starts here************************************************
						
			//Step1-2: Login To Selecting Client
			loginToSelectClient();			
			
			int iProjectId =clientListPage.getOldProjectId();
			
			if(iProjectId < 0){
				bStatus = false;
				throw new IDIOMException("Skipping this test case as no project older than today found");
			}
			
			strProjectName = clientListPage.getProjectNameById(iProjectId);
			
			//Step3: Duplicate the project
			clientListPage.performActionOnProject("duplicate", strProjectName);
			Thread.sleep(2000);
			
			clientListPage.findAndSaveProjectWindow(false, strProjectName, 2);
			clientListPage.func_PerformAction("Save Project");
			clientListPage.func_PerformAction("Close Project");
			Thread.sleep(2000);
			
			strProjectDetails = clientListPage.getAudienceProjectClientId(strProjectName, "", 2);
			
			//Step4: Check modified and created date
			String strCreatedModifiedDate = clientListPage.getCreatedModifiedDateByProjectName(strProjectName, 2);
			
			String[] strDates = strCreatedModifiedDate.split("#");			
			String strTodayDate = new SimpleDateFormat(BaseClass.strDateFormat).format(rm.getTodaysDate());
			
			if(!strDates[0].equalsIgnoreCase(strTodayDate))
				throw new IDIOMException("Created date for duplicate project is not today's date. Expected " + 
			strTodayDate + " and found " + strDates[0] +".###CreatedDateIsNotTodaysDate");
			
			CustomReporter.log("Successfully verified Created date for duplicated project from older date project. It is " + strTodayDate);
			
			if(!strDates[1].equalsIgnoreCase(strTodayDate))
				throw new IDIOMException("Modified date for duplicate project is not today's date. Expected " + 
			strTodayDate + " and found " + strDates[1] +".###ModifiedDateIsNotTodaysDate");
			
			CustomReporter.log("Successfully verified Modified date for duplicated project from older date project. It is " + strTodayDate);
			
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
		
		if(!BaseClass.testCaseStatus && !bStatus){
			CustomReporter.errorLog("Test case skipped");
			throw new SkipException("Skipping this test case as no project older than today found");
		}else if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}		
	}
}
