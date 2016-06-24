package com.IDIOM.ClientHomePage;

import java.text.MessageFormat;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Manage Projects 1.a.iii : Verify drop down for audience field. </p>
 *  <p> <b>Objective:</b>To change audience in project drop down should be available.</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8819.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 03 Jun 2016
 *
 */
public class ClientHomePage8819 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyAudienceDropdownForProject(){		
			
		String strProjectName="";
		String strProjectName1="";
				
		String strMsg="";
		String strDeletionDetails="";
		String strDeletionDetails1="";
		
		boolean bProjectCreated = false;	
		boolean bProjectCreated1 = false;
		
		try{
			
			//****************Test step starts here************************************************
			
			//Login To Selecting Client
			loginToSelectClient();			
			
			//Step3: Select/Create project			
			strProjectName = clientListPage.createNewProject("");
			strDeletionDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			bProjectCreated = true;
			
			strProjectName1 = clientListPage.createNewProject("");
			strDeletionDetails1=clientListPage.getAudienceProjectClientId(strProjectName1, "");
			bProjectCreated1 = true;
			
			
			//Step4: Verifying audience drop down for Project 1.
			if(!rm.webElementSync("visibilitynowaitbyxpath",MessageFormat.format(clientListPage.strAudienceDropdownOnHome, strProjectName)))
				throw new IDIOMException("Not able to find dropdown for " + strProjectName + " project");
			
			CustomReporter.log("Dropdown is available for " + strProjectName + " project");
			
			//Step4: Verifying audience drop down for Project 2.
			if(!rm.webElementSync("visibilitynowaitbyxpath",MessageFormat.format(clientListPage.strAudienceDropdownOnHome, strProjectName1)))
				throw new IDIOMException("Not able to find dropdown for " + strProjectName1 + " project");
			
			CustomReporter.log("Dropdown is available for " + strProjectName1 + " project");
			
		}catch(Exception e){
			exceptionCode(e);
		}finally{
			try{
				
				//Deleting newly created project or Audience			
				if(bProjectCreated){				
					rm.deleteProjectOrAudience(strDeletionDetails, true);
					CustomReporter.log("Deleted the project " + strProjectName);					
				}
				
				if(bProjectCreated1){				
					rm.deleteProjectOrAudience(strDeletionDetails1, true);
					CustomReporter.log("Deleted the project " + strProjectName1);					
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
		
		String strTestCaseId = strClassName.replaceAll("\\D+","");		
		if(strMsgAndFileName.length==1){
			CustomReporter.errorLog("Failure: "+ ie.getMessage());
			rm.captureScreenShot(strTestCaseId+"-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot(strTestCaseId+"-"+strMsgAndFileName[1], "fail");	
		}		
	}	
}
