package com.IDIOM.ClientHomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Def 2288_Client HomePage_Verify new project button click is not enabled when new project overlay is opened</p>
 *  <p> <b>Objective:</b>Verify that the user is not able click on New project button when already New project overlay is opened</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8962.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 02 Jun 2016
 *
 */
public class ClientHomePage8962 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyClickOnNewProjectWithOpenOverlay(){		
		String strMsg="";
		try{
			
			//****************Test step starts here************************************************
			
			//Login To Selecting Client
			loginToSelectClient();			
			
			//Step3: Click on New Project
			clientListPage.func_PerformAction("New Project");
			
			if(!clientListPage.isVisible("projectwindow"))
				throw new IDIOMException("No new project window found.###NoNewProjectOverlay");
			
			CustomReporter.log("Verified new project window after clicking 'New Project' button");
			
			//Step4: Again check if new project button is clickable or not
			clientListPage.func_PerformAction("New Project");
			
			if(clientListPage.blankProjectRows.size() > 0)
				throw new IDIOMException("New project button is still clickable.###AbleToClickOnNewProjectBtn");
			
			CustomReporter.log("Successfully verified, 'New Project' button is not clickable");
			
			/*if(rm.webElementSync(clientListPage.newProjectBtn, "clickable"))
				throw new IDIOMException("New project button is still clickable.###AbleToClickOnNewProjectBtn");
			
			CustomReporter.log("Successfully verified, 'New Project' button is not clickable");*/
			
		}catch(Exception e){
			exceptionCode(e);
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
