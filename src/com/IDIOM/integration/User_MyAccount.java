package com.IDIOM.integration;

/********************************************************************
Test Case Name: Verify user my account page
Objective: Verify that My Account page should accessible for any user
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/8995.aspx
Module: User Admin
@author: Shailesh Kava
@since: 26-April-2016
**********************************************************************/

import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdminMyAccount_Page;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

public class User_MyAccount extends BaseClass {
		
	@Test
	public void verifyMyAccountPage(){
		String strMsg = null;		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			//****************Test step starts here************************************************
			
			UserAdminMyAccount_Page myAccountPage = new UserAdminMyAccount_Page(driver); 
			//Step1: Open URL
			Login_Page ln = new Login_Page(driver);
			
			//Step2: Login with valid credential
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			rm.webElementSync(pageHeader.personMenu, "visibility");
			
			Thread.sleep(2000);
			//Step 3(1): Click on MyAccount link from header
			pageHeader.performAction("myaccount");
			rm.webElementSync(myAccountPage.changeResentPassButton, "visibility");
			
			if(!myAccountPage.isVisible("changepassword"))
				throw new IDIOMException("Failed to open My Account page###myAccount-failedToOpenMyAccount");
			
			CustomReporter.log("My Account page is open, verify logged in email address");
			System.out.println("My Account page is open, verify logged in email address");
			
			String emailInMyAccountPage = null;
			
			emailInMyAccountPage = myAccountPage.func_getText("email");
			
			CustomReporter.log("Logged in user email :"+strEmailId+" email in My Account page: "+emailInMyAccountPage);
			System.out.println("Logged in user email :"+strEmailId+" email in My Account page: "+emailInMyAccountPage);
			
			//Step 3 (2): Verify email on my account page it should match with logged in email id. 
			if(!strEmailId.contentEquals(emailInMyAccountPage))
				throw new IDIOMException("Logged in email address does not match in My Account Page###myAccount-emailNotMatch");
			
			CustomReporter.log("Logged in email address matched in MyAccount page");
			System.out.println("Logged in email address matched in MyAccount page");
			
			//Step 4: Verify that Your Client list section is appearing
			if(!myAccountPage.func_elementExist("youclientlistsection"))
				throw new IDIOMException("Client list section is missing###myAccount-clientListMissing");
			
			CustomReporter.log("Client list is also appearing in My Account page");
			System.out.println("Client list is also appearing in My Account page");
			
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
			rm.captureScreenShot("myAccountIssue", "fail");
		}finally{
			try{
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
		//****************Test step ends here************************************************
		
		if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}
	}
}