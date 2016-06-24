/********************************************************************
Test Case Name: 1175_My Account_Verify Menu Drop Down
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/8514.aspx
Objective:  This test case verifies menu drop down items in My account page 
Note: Step 5: {is not valid as per test case because we are showing all links in header for visiting page too. 
			  hence not adding this test case in automation}
Module: User Admin - My Account
Created By: Shailesh Kava
Date: 30 December 2015
**********************************************************************/

package com.IDIOM.UserAdmin.MyAccount.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdminMyAccount_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class MyAccount8514 extends BaseClass {
		
	@Test
	public void test_MyAccount8514() throws Exception{
		/******************************Variables********************************/
		String adminUser = property.getProperty("SuperAdminUser").trim();
		String adminPass = property.getProperty("SuperAdminpassword").trim();
		/****************************Variables End******************************/
		
		CustomReporter.log("Step 1: Open URL and login to IDIOM");
		
		Login_Page lp = new Login_Page(driver);
		
		lp.func_LoginToIDIOM(adminUser, adminPass);
		
		Thread.sleep(15000);
		
		CustomReporter.log("Step 1: Logged in with super admin user");
		
		UserAdminMyAccount_Page myAcct = new UserAdminMyAccount_Page(driver);
				
		CustomReporter.log("=======Step 2=======");
		
		//myAcct.func_click("headericon");
		myAcct.func_onHover("headericon");
		
		Thread.sleep(5000);
		
		
		
		if(myAcct.func_elementExist("dropdownopen")){
			
			CustomReporter.log("drop down opened");
			
			CustomReporter.log("Clicked on Header sandwith icon");
			
			myAcct.func_click("myaccount");
			
			Thread.sleep(3000);
			
			if(myAcct.func_elementExist("profilesetting")){
				CustomReporter.log("My profile page is opened");
			}else{
				CustomReporter.errorLog("Failed to open my profile page");
				BaseClass.testCaseStatus = false;
			}
			
		}else{
			CustomReporter.errorLog("failed to open drop down");
			BaseClass.testCaseStatus = false;
		}
	
		CustomReporter.log("=======Step 3=======");
		
		CustomReporter.log("Check the header area in My account page");
		
		if(!myAcct.func_elementExist("headerlogo")){
			CustomReporter.errorLog("Header logo is not appearing");
			BaseClass.testCaseStatus=false;
		}else{
			CustomReporter.log("Header logo is appearing");
		}
		
		if(!myAcct.func_elementExist("profilesetting")){
			CustomReporter.errorLog("Profile setting is not appearing");
			BaseClass.testCaseStatus=false;
		}else{
			CustomReporter.log("Profile setting is appearing");
		}
		
		if(!myAcct.func_elementExist("headersandwichicon")){
			CustomReporter.errorLog("Menu drop down is not appearing");
			BaseClass.testCaseStatus=false;
		}else{
			CustomReporter.log("Menu drop down is appearing");
		}
		
		if(!myAcct.func_elementExist("helpIcon")){
			CustomReporter.errorLog("Help icon is not appearing");
			BaseClass.testCaseStatus=false;
		}else{
			CustomReporter.log("Help icon is appearing");
		}
			
		CustomReporter.log("=======Step 4=======");
		
		myAcct.func_onHover("headericon");
		
		Thread.sleep(3000);
		
		CustomReporter.log("Clicked on header Menu Drop Down");
		
		if(myAcct.func_elementExist("dropdownopen")){
			
			CustomReporter.log("Drop dows has opened");
			
			if(!myAcct.func_elementExist("logout")){
				CustomReporter.errorLog("Log out link is not appearing");
				BaseClass.testCaseStatus = false;
			}else{
				CustomReporter.log("Log out link is appearing");
			}
			
			if(!myAcct.func_elementExist("adminaccess")){
				CustomReporter.errorLog("Admin access link is not appearing");
				BaseClass.testCaseStatus = false;
				
			}else{
				CustomReporter.log("Admin access link is appearing");
			}
			
			myAcct.func_click("logout");
			
			CustomReporter.log("User is logged out");
			
		}else{
			
			CustomReporter.errorLog("Failed to open drop down");
			BaseClass.testCaseStatus = false;
		}
		
		/***************************Test step ends here******************************/
		if(BaseClass.testCaseStatus == false){
			CustomReporter.errorLog("Test case has failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case has Passed");
		}
		
	}
}