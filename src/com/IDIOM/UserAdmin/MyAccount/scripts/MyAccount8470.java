/********************************************************************
Test Case Name: My Account_Change Password Functionality 
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8470.aspx
Objective: Verify that the forget password functionality is  working as expected in My account page
Module: UserAdminAndUserPermissions
Created By:Shailesh Kava
Date: 11 Jan 2016
**********************************************************************/

package com.IDIOM.UserAdmin.MyAccount.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdminMyAccount_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class MyAccount8470 extends BaseClass{
		
	@Test
	public void test_myAccount8470() throws Exception{
		
		/******************************Variables********************************/
		String adminUser = property.getProperty("SuperAdminUser").trim();
		String adminPass = property.getProperty("SuperAdminpassword").trim();
		/****************************Variables End******************************/
		
		UserAdminMyAccount_Page myAcct = new UserAdminMyAccount_Page(driver);
		
		CustomReporter.log("Open URL and login to IDIOM");
		
		Login_Page lp = new Login_Page(driver);
		
		lp.func_LoginToIDIOM(adminUser, adminPass);
		
		ClientList_Page cp = new ClientList_Page(driver);
		
		rm.webElementSync(null, "idiomspinningcomplete", "",null);
		Thread.sleep(2000);
		myAcct.func_click("headericon");
			
		CustomReporter.log("Logged in with super admin user");
		
		Thread.sleep(2000);
		
		CustomReporter.log("drop down opened");
		
		myAcct.func_click("myaccount");
		
		if(myAcct.func_elementExist("profilesetting")){
			CustomReporter.log("My profile page is opened");
			
		}else{
			CustomReporter.errorLog("Failed to open My Account page");
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("changePassButton"), "visibility", "",null)){
			CustomReporter.log("Change password is appearing");
		}else{
			CustomReporter.errorLog("Change password is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(myAcct.func_getText("role").equals("Super Admin")){
			CustomReporter.log("User role is Super Admin");
		}else{
			CustomReporter.errorLog("User role is not [super admin]");
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("emailAddress"), "visibility", "",null)){
			CustomReporter.log("Email address is appearing");
		}else{
			CustomReporter.errorLog("Email is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("userName"), "visibility", "",null)){
			CustomReporter.log("User name is appearing");
		}else{
			CustomReporter.errorLog("User name not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		//Step 3
		CustomReporter.log("Clicking on change password");
		
		Thread.sleep(5000);
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("changeResentPassButton"), "visibility", "",null)){
			CustomReporter.log("Change password button is visible/available");
			
			myAcct.func_click("changepass");
			
			if(rm.webElementSync(myAcct.func_GetLocalWebElement("resendPassButton"), "visibility", "",null)){
				CustomReporter.log("Clicked on change password button");
			}else{
				CustomReporter.errorLog("Failed to click on Change password button");
				BaseClass.testCaseStatus=false;
			}
			
			if(rm.webElementSync(myAcct.func_GetLocalWebElement("passChangeMessage"), "visibility", "",null)){
				String changePassMessage = myAcct.func_getText("changepassmessage");
				
				if(changePassMessage.contains("Change Password Email Sent @")){
					CustomReporter.log("Change password message is proper");
				}else{
					CustomReporter.errorLog("Change pass message is not expected");
					BaseClass.testCaseStatus=false;
				}
			}else{
				CustomReporter.errorLog("Change password message is not appearing");
				BaseClass.testCaseStatus=false;
			}
			
		}else{
			CustomReporter.errorLog("Change password button is not visible/available");
			BaseClass.testCaseStatus=false;
		}
		
		myAcct.func_click("logout");
		
		/***************************Test step ends here******************************/
		if(BaseClass.testCaseStatus == false){
			CustomReporter.errorLog("Test case has failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case has Passed");
		}
		
	}
}
