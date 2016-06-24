package com.IDIOM.ClientHomePage;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import com.IDIOM.pages.Analyse_Pathing_Page;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p>	<b>Test Case Name:</b> ClientHomePage_FirstTimeUserExp_Verify SelectClient When user has no Selected clients. </p>
<p>	<b>Objective:</b> Verify the functionality when user selects a client when user has no clients selected to it. </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8800.aspx </p>
<p>	<b>Module:</b> Client Home Page </p>
@author: Shailesh Kava
@since: 14-June-2016
**********************************************************************/
public class ClientHomePage8800 extends BaseClass {
	
	Analyse_Pathing_Page pathingPage;
	String PathingLink;
	String strDetails;
	String fileLocation;
	Actions action;
	String exportdatafilename;
	
	@Test
	public void verifyMessageWhenNoProjectAdded(){
		
		String expectedMessageIfNoProjectCreated = property.getProperty("messagenoprojectcreated").toLowerCase().trim();
		 
		String strMsg = null;
		boolean bStatus = true;
		boolean bProjectCreate = false;
		
		try{
			loginToSelectClient(property.getProperty("8800userhasnoproject_user").trim(),property.getProperty("8800userhasnoproject_pass").trim());
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			if(totalProjects > 0){
				bStatus = false;
				throw new IDIOMException("Skipped Test Case:Project is already created for this user###8800-idiomLogoOrPersonMenuIconIsMissing");
			}else{
				if(clientListPage.messageIfNoProjectAdded.getText().toLowerCase().trim() != null){
					if(!clientListPage.messageIfNoProjectAdded.getText().toLowerCase().trim().equalsIgnoreCase(expectedMessageIfNoProjectCreated)){
						System.out.println("expected message ["+expectedMessageIfNoProjectCreated+"] actual message ["+clientListPage.messageIfNoProjectAdded.getText().toLowerCase().trim()+"]");
						CustomReporter.errorLog("expected message ["+expectedMessageIfNoProjectCreated+"] actual message ["+clientListPage.messageIfNoProjectAdded.getText().toLowerCase().trim()+"]");
						throw new IDIOMException("Expected message is not available if no project is added###8800-unExpectedMessageWhenNoProjectAdded");
					}
				}
			}
			
			CustomReporter.log("Expected message is appearing if no project is added by current user");
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Some exception is generated, " + strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8800", "fail");
		}finally{
			try{
				
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the project");
				}
				
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
		//****************Test step ends here************************************************
		
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