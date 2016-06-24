package com.IDIOM.integration;

/********************************************************************
Test Case Name: Create new user by super admin user
Objective: Verify that Super Admin user should able to create new user
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/8994.aspx
Module: User Admin
@author: Shailesh Kava
@since: 26-April-2016
**********************************************************************/

import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

public class CreateNewUser extends BaseClass {
		
	@Test
	public void createNewUser(){
		String strMsg = null;		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");	
			
			
			//****************Test step starts here************************************************
			
			UserAdmin_UserPermissions_Page userAdmin = new UserAdmin_UserPermissions_Page(driver); 
			//Step1: Open URL
			Login_Page ln = new Login_Page(driver);
			
			//Step2: Login with valid credential
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			rm.webElementSync(pageHeader.personMenu, "visibility");
			
			//Step 3: Click on Admin Access link from header
			pageHeader.performAction("adminaccess");
			CustomReporter.log("Clicked on Admin Access link from header");
			System.out.println("Clicked on Admin Access link from header");
			
			rm.webElementSync(userAdmin.firstUsersEmailToWait, "visibility");
			rm.webElementSync("pageload");
			
			if(!userAdmin.isVisible("createuserbutton"))
				throw new IDIOMException("Create user button is missing###createNewUser-createUserButtonIsMissing");
			
			CustomReporter.log("Admin access page is open, clicking on create new user");
			System.out.println("Admin access page is open, clicking on create new user");
			
			String userEmail = "user_"+System.currentTimeMillis()+"@gmail.com";
			String userName = "smoke user";
			
			//Step 4,5,6: Click on Create new user button and add create with valid username and email address and assign client
			CustomReporter.log("User Name ["+userName+"] and user email is ["+userEmail+"]" );
			
			userAdmin.func_CreateUser(userName,userEmail,1,"Add New User");
			rm.webElementSync("idiomspinningcomplete");
			rm.webElementSync("pageload");
			
			rm.webElementSync("idiomspinningcomplete");
			rm.webElementSync(userAdmin.firstUsersEmailToWait, "visibility");
			rm.webElementSync("pageload");
			
			CustomReporter.log("Searching created user in admin access page");
			System.out.println("Searching created user in admin access page");
			
			//Step 7: Now search the same email address which used to create new user  
			if(!userAdmin.func_CheckSearchFunctionality(userEmail))
				throw new IDIOMException("Unable to search created user in admin access page###createNewUser-unableToSearch");
			
			CustomReporter.log("User is registered and available in admin access page");
			System.out.println("User is registered and available in admin access page");
			
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
			rm.captureScreenShot("createNewUser", "fail");
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