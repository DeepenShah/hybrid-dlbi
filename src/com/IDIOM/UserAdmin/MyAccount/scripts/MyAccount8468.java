/********************************************************************
Test Case Name:*My Account_Clients Present For a User
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8468.aspx
Objective: http://qa.digitas.com/SpiraTest/523/TestCase/8468.aspx
Module: UserAdminAndUserPermissions
Created By:Shailesh Kava
Date: 11 Jan 2016
**********************************************************************/

package com.IDIOM.UserAdmin.MyAccount.scripts;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdminMyAccount_Page;
import com.reports.CustomReporter;

import common.BaseClass;
import common.ReusableMethods;

public class MyAccount8468 extends BaseClass{
	
	
	@Test
	public void test_MyAccount8468() throws Exception{
		
		/******************************Variables********************************/
		String adminUser = property.getProperty("SuperAdminUser").trim();
		String adminPass = property.getProperty("SuperAdminpassword").trim();
		/****************************Variables End******************************/
		
		CustomReporter.log("=======Step 1=======");
		
		CustomReporter.log("Open URL and login to IDIOM");
		
		Login_Page lp = new Login_Page(driver);
		
		lp.func_LoginToIDIOM(adminUser, adminPass);
		
		Thread.sleep(5000);
		
		CustomReporter.log("Logged in with super admin user");
		
		CustomReporter.log("=======Step 2=======");
		
		ArrayList<String> listClientOnClientListPage = new ArrayList();
		
		CustomReporter.log("In client list page, note down the clients present");
		
		ClientList_Page clientListPage = new ClientList_Page(driver);
		
		listClientOnClientListPage = clientListPage.func_getList("userclients");
		
		int totalAssignedClentOnClientListPage = listClientOnClientListPage.size();
		
		UserAdminMyAccount_Page myAcct = new UserAdminMyAccount_Page(driver);
				
		myAcct.func_onHover("headericon");
		
		Thread.sleep(5000);
		
		CustomReporter.log("Clicked on header Menu Drop Down");
		
		if(myAcct.func_elementExist("dropdownopen")){
			
			CustomReporter.log("drop down opened");
			
			CustomReporter.log("Clicked on Header sandwith icon");
			
			myAcct.func_click("myaccount");
			
			Thread.sleep(3000);
			
			if(myAcct.func_elementExist("profilesetting")){
				CustomReporter.log("My profile page is opened");
				
				ArrayList<String> listClientsforLoggedUser = new ArrayList(); 
				
				listClientsforLoggedUser = myAcct.func_getList("userclients"); 
				
				int totalAssignedClients = listClientsforLoggedUser.size();	
				
				CustomReporter.log("Step 3: Verify the clients present in My account page is same as the ones present in Client list page");
				
				if(rm.compareArray(listClientsforLoggedUser, listClientOnClientListPage)){
					CustomReporter.log("Client name are matched");
				}else{
					CustomReporter.errorLog("Client name are mismatched");
					CustomReporter.errorLog("Clients on Client list page: "+listClientOnClientListPage+" Clients on my profile: "+listClientsforLoggedUser);
					BaseClass.testCaseStatus = false;
				}
				
				CustomReporter.log("Step 4: Verify that number of clients present in My account page is proper");
				
				if(totalAssignedClients != totalAssignedClentOnClientListPage){
					CustomReporter.errorLog("Count of clients are mismatch client list have "+totalAssignedClentOnClientListPage+" my profile have "+totalAssignedClients);
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log("Count of assigned clients are matched = "+totalAssignedClentOnClientListPage);
				}
				
				
				
			}else{
				CustomReporter.errorLog("Failed to open my profile page");
				BaseClass.testCaseStatus = false;
			}
			
		}else{
			CustomReporter.errorLog("failed to open drop down");
			BaseClass.testCaseStatus = false;
		}
		
		
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
