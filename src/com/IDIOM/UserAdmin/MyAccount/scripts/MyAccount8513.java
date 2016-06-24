/********************************************************************
Test Case Name: 1177_My Account_VerifyHeader Area_IdiomNAme_Absent 
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/8513.aspx
Objective: This test case verifies header area in My account page and verifies idiom name is absent in My account header area
Module: UserAdminAndUserPermissions
Created By:Shailesh Kava
Date: 20 Jan 2016
**********************************************************************/

package com.IDIOM.UserAdmin.MyAccount.scripts;

import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.IDIOM.pages.UserAdminMyAccount_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class MyAccount8513 extends BaseClass{

	@Test
	public void test_MyAccount8513() throws Exception{
		
		/******************************Variables********************************/
		String clientAdminUser = property.getProperty("ClientAdminEmail").trim();
		String clientAdminPass = property.getProperty("ClientAdminPassword").trim();
		String client = property.getProperty("clientName").trim();
		
		String dbInstance = property.getProperty("dbinstance").trim();
		String dbUser = property.getProperty("dbuser").trim();
		String dbPass = property.getProperty("dbpass").trim();
		/****************************Variables End******************************/
		UserAdminMyAccount_Page myAcct = new UserAdminMyAccount_Page(driver);
		
		CustomReporter.log("Open URL and login to IDIOM");
		
		Login_Page lp = new Login_Page(driver);
		
		lp.func_LoginToIDIOM(clientAdminUser, clientAdminPass);
		
		ClientList_Page cp = new ClientList_Page(driver);
		
		rm.webElementSync(null, "idiomspinningcomplete", "", null);
		
		//Step 2
		cp.func_SelectClient(client);
		
		rm.webElementSync(null, "idiomspinningcomplete", "", null);
		
		CustomReporter.log("Selected a client");
		
		ManageIdiom_Page manageIdidom = new ManageIdiom_Page(driver);
		
		//Step 3
		if(manageIdidom.func_ElementExist("CreateNewIdiom")){
			CustomReporter.log("Manage idiom page is opened");
		}else{
			CustomReporter.errorLog("Failed to open manage idiom page");
			BaseClass.testCaseStatus = false;
		}
		
		//Step 4
		CustomReporter.log("Clicking on my account link from manage idiom page");
		
		if(rm.webElementSync(manageIdidom.func_GetLocalWebElement("btn_createNewIdiom"), "visibility", null, null)){
			
			manageIdidom.func_PerformClickAction("myaccount");
			rm.webElementSync(null, "idiomspinningcomplete", "", null);
			
			if(myAcct.func_elementExist("profilesetting")){
				CustomReporter.log("My profile page is opened");
				
			}else{
				CustomReporter.log("Failed to open My Account page");
				BaseClass.testCaseStatus = false;
			}
		}
		
		//Step 5
		
		Thread.sleep(5000);
		rm.webElementSync("idiomspinningcomplete");
		
		if(myAcct.func_elementExist("headerlogo")){
			CustomReporter.log("Idiom logo is appearing");
		}else{
			CustomReporter.errorLog("IDIOM logo is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(myAcct.func_elementExist("helpIcon")){
			CustomReporter.log("Help Icon is appearing");
		}else{
			CustomReporter.errorLog("Help Icon is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(myAcct.func_elementExist("profilesetting")){
			CustomReporter.log("Profile Setting is appearing");
		}else{
			CustomReporter.errorLog("Profile Setting is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		Thread.sleep(1000);
		
		ArrayList<String> listLinksinHeader = new ArrayList();
		
		listLinksinHeader = myAcct.func_getListofElementVal("listlinksunderheadersandwichicon");
		
		if(listLinksinHeader.get(0).equals("MY ACCOUNT")){
			CustomReporter.log("My Account link is appearing");
		}else{
			CustomReporter.errorLog("My Account link is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(listLinksinHeader.size()>2){
			if(listLinksinHeader.get(2).equals("LOGOUT")){
				CustomReporter.log("Logout link is appearing");
			}else{
				CustomReporter.errorLog("Logout link is not appearing");
				BaseClass.testCaseStatus = false;
			}
		}else{
			if(listLinksinHeader.get(1).equals("LOGOUT")){
				CustomReporter.log("Logout link is appearing");
			}else{
				CustomReporter.errorLog("Logout link is not appearing");
				BaseClass.testCaseStatus = false;
			}
		}
		
		//Step 6
		if(myAcct.func_elementExist("headerIdiomNameExist")){
			CustomReporter.errorLog("Audience name is available");
			BaseClass.testCaseStatus = false;
		}else{
			CustomReporter.log("Audience name is not appearing");
		}
		
		//Step 7
		myAcct.func_click("logout");
		
		rm.webElementSync("idiomspinningcomplete");
		
		if(lp.func_ElementExist("Login Button")){
			CustomReporter.log("User is logged out successfully!");
		}else{
			CustomReporter.errorLog("Unable to log out from application");
			BaseClass.testCaseStatus=false;
		}
		
		//Step 8
		lp.func_LoginToIDIOM(clientAdminUser, clientAdminPass);
		
		rm.webElementSync("idiomspinningcomplete");
		
		//Step 9
		cp.func_PerformAction("My Account");
		
		rm.webElementSync("idiomspinningcomplete");
		
		CustomReporter.log("Clicked on My Account link from Client List Page");
		
		//Step 10
		if(myAcct.func_elementExist("headerlogo")){
			CustomReporter.log("Idiom logo is appearing");
		}else{
			CustomReporter.errorLog("IDIOM logo is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(myAcct.func_elementExist("helpicon")){
			CustomReporter.log("Help Icon is appearing");
		}else{
			CustomReporter.errorLog("Help Icon is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(myAcct.func_elementExist("profilesetting")){
			CustomReporter.log("Profile Setting is appearing");
		}else{
			CustomReporter.errorLog("Profile Setting is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		Thread.sleep(1000);
		
		listLinksinHeader = myAcct.func_getListofElementVal("listlinksunderheadersandwichicon");
		
		if(listLinksinHeader.get(0).equals("MY ACCOUNT")){
			CustomReporter.log("My Account link is appearing");
		}else{
			CustomReporter.errorLog("My Account link is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(listLinksinHeader.size()>2){
			if(listLinksinHeader.get(2).equals("LOGOUT")){
				CustomReporter.log("Logout link is appearing");
			}else{
				CustomReporter.errorLog("Logout link is not appearing");
				BaseClass.testCaseStatus = false;
			}
		}else{
			if(listLinksinHeader.get(1).equals("LOGOUT")){
				CustomReporter.log("Logout link is appearing");
			}else{
				CustomReporter.errorLog("Logout link is not appearing");
				BaseClass.testCaseStatus = false;
			}
		}
		
		
		
		
		//Step 11
		if(myAcct.func_elementExist("headerIdiomNameExist")){
			CustomReporter.errorLog("Audience name is available");
			BaseClass.testCaseStatus = false;
		}else{
			CustomReporter.log("Audience name is not appearing");
		}
		
		//Step 12
		myAcct.func_click("logout");
		
		rm.webElementSync("idiomspinningcomplete");
		
		if(lp.func_ElementExist("Login Button")){
			CustomReporter.log("User is logged out successfully!");
		}else{
			CustomReporter.errorLog("Unable to log out from application");
			BaseClass.testCaseStatus=false;
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