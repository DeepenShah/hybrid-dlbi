/********************************************************************
Test Case Name: *My Account_Client Admin user Login 
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/8467.aspx
Objective: Verify 'My account page' when logging with client admin user credential
Module: UserAdminAndUserPermissions
Created By:Shailesh Kava
Date: 19 Jan 2016
**********************************************************************/

package com.IDIOM.UserAdmin.MyAccount.scripts;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.IDIOM.pages.UserAdminMyAccount_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class MyAccount8467 extends BaseClass{
	
	@Test
	public void test_MyAccount8467() throws Exception{
		
		/******************************Variables********************************/
		String clientAdminUser = property.getProperty("ClientAdminEmail").trim();
		String clientAdminPass = property.getProperty("ClientAdminPassword").trim();
		String client = property.getProperty("clientName").trim();
		
		String dbInstance = property.getProperty("dbinstance").trim();
		String dbUser = property.getProperty("dbuser").trim();
		String dbPass = property.getProperty("dbpass").trim();
		/****************************Variables End******************************/
		UserAdminMyAccount_Page myAcct = new UserAdminMyAccount_Page(driver);
		
		String query = "SELECT client.name as name FROM idiom.client JOIN idiom.user_client " +
				"ON user_client.client_id = client.client_id JOIN idiom.users " +
				"ON user_client.user_id = users.id " +
				"WHERE user_client.status='ACTIVE' AND client.status='ACTIVE' AND users.email_address='"+clientAdminUser+"'";
		
		ArrayList<String> listAssignedClients = new ArrayList(); 
		listAssignedClients = myAcct.func_getAssignedClietFromDB(query, dbInstance, dbUser, dbPass);
		
		CustomReporter.log("Open URL and login to IDIOM");
		
		Login_Page lp = new Login_Page(driver);
		
		lp.func_LoginToIDIOM(clientAdminUser, clientAdminPass);
		
		ClientList_Page cp = new ClientList_Page(driver);
		
		rm.webElementSync(null, "idiomspinningcomplete", "", null);
		
		//Step 2
		myAcct.func_click("headericon");
			
		CustomReporter.log("Logged in with Client Admin"); 
		
		Thread.sleep(2000);
		
		CustomReporter.log("drop down opened");
		
		myAcct.func_click("myaccount");
		
		rm.webElementSync(null, "idiomspinningcomplete", "", null);
		
		if(myAcct.func_elementExist("profilesetting")){
			CustomReporter.log("My profile page is opened");
			
		}else{
			CustomReporter.errorLog("Failed to open My Account page");
			BaseClass.testCaseStatus = false;
		}
		
		//Step 3
		ArrayList<String> listClientsforLoggedUser = new ArrayList(); 
		
		listClientsforLoggedUser = myAcct.func_getList("userclients");
		
		if(rm.compareArray(listAssignedClients, listClientsforLoggedUser)){
			CustomReporter.log("Clients are matched");
		}else{
			CustomReporter.errorLog("Clients mismatch DB Clients :"+listAssignedClients+" Application clients :"+listClientsforLoggedUser);
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("changePassButton"), "visibility", "", null)){
			CustomReporter.log("Change password is appearing");
		}else{
			CustomReporter.errorLog("Change password is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("userClientAdminRole"), "verifytext", "Client Admin",null)){
			CustomReporter.log("User role is Client Admin");
		}else{
			CustomReporter.errorLog("User role is not [Client Admin] it is: "+myAcct.func_getText("clientadminrole"));
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("emailAddress"), "visibility", "", null)){
			CustomReporter.log("Email address is appearing");
		}else{
			CustomReporter.errorLog("Email is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("userName"), "visibility", "", null)){
			CustomReporter.log("User name is appearing");
		}else{
			CustomReporter.errorLog("User name not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		//Step 4
		CustomReporter.log("===Step 4===");
		myAcct.func_browserBack();
		
		rm.webElementSync(null, "idiomspinningcomplete", "", null);
		
		if(cp.func_ClientListPageElementExist("Missing a client?")){
			CustomReporter.log("Client list page is appearing");
		}else{
			CustomReporter.errorLog("Failed to open client list page");
			BaseClass.testCaseStatus = false;
		}
		
		//Step 5
		CustomReporter.log("===Step 5===");
		cp.func_SelectClient(client);
		
		rm.webElementSync(null, "idiomspinningcomplete", "", null);
		
		ManageIdiom_Page manageIdidom = new ManageIdiom_Page(driver);
		
		if(manageIdidom.func_ElementExist("CreateNewIdiom")){
			CustomReporter.log("Manage idiom page is opened");
		}else{
			CustomReporter.errorLog("Failed to open manage idiom page");
			BaseClass.testCaseStatus = false;
		}
		
		//Step 6
		CustomReporter.log("===Step 6===");
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
		
		//Step 7
		CustomReporter.log("===Step 7===");
		listClientsforLoggedUser = myAcct.func_getList("userclients");
		
		if(rm.compareArray(listAssignedClients, listClientsforLoggedUser)){
			CustomReporter.log("Clients are matched");
		}else{
			CustomReporter.errorLog("Clients mismatch DB Clients :"+listAssignedClients+" Application clients :"+listClientsforLoggedUser);
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("changePassButton"), "visibility", "", null)){
			CustomReporter.log("Change password is appearing");
		}else{
			CustomReporter.errorLog("Change password is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("userClientAdminRole"), "verifytext", "Client Admin",null)){
			CustomReporter.log("User role is Client Admin");
		}else{
			CustomReporter.errorLog("User role is not [Client Admin] it is: "+myAcct.func_getText("clientadminrole"));
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("emailAddress"), "visibility", "", null)){
			CustomReporter.log("Email address is appearing");
		}else{
			CustomReporter.errorLog("Email is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("userName"), "visibility", "", null)){
			CustomReporter.log("User name is appearing");
		}else{
			CustomReporter.errorLog("User name not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		//Step 8
		CustomReporter.log("===Step 8===");
		myAcct.func_browserBack();
		
		rm.webElementSync(null, "idiomspinningcomplete", "", null);
		
		if(manageIdidom.func_ElementExist("CreateNewIdiom")){
			CustomReporter.log("Manage idiom page is opened");
		}else{
			CustomReporter.errorLog("Failed to open manage idiom page");
			BaseClass.testCaseStatus = false;
		}
		
		//step 9
		CustomReporter.log("===Step 9===");
		String genereateIdiomName = manageIdidom.func_CreateIdiomName("myAccount8467", "New");
		
		manageIdidom.func_CreateNewIdiomOrRename(genereateIdiomName, "NotBlank");
		
		//manageIdidom.func_SelectExistingIdiomByNumber(1);
				
		Audience_Page audiencePage = new Audience_Page(driver);
				
		rm.webElementSync("idiomspinningcomplete");
				
		if(audiencePage.func_AudiencePageElementExist("AnalyzeLabel")){
			CustomReporter.log("Audience page is appearing");
		}else{
			CustomReporter.errorLog("Failed to open Audience page");
			BaseClass.testCaseStatus=false;
		}
		
		//Step 10
		CustomReporter.log("===Step 10===");
		audiencePage.func_ClickElement("myaccount");
		
		rm.webElementSync("idiomspinningcomplete");
		
		if(myAcct.func_elementExist("profilesetting")){
			CustomReporter.log("My profile page is opened");
			
		}else{
			CustomReporter.log("Failed to open My Account page");
			BaseClass.testCaseStatus = false;
		}
		
		
		//Step 11
		CustomReporter.log("===Step 11===");
		listClientsforLoggedUser = myAcct.func_getList("userclients");
		
		if(rm.compareArray(listAssignedClients, listClientsforLoggedUser)){
			CustomReporter.log("Clients are matched");
		}else{
			CustomReporter.errorLog("Clients mismatch DB Clients :"+listAssignedClients+" Application clients :"+listClientsforLoggedUser);
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("changePassButton"), "visibility")){
			CustomReporter.log("Change password is appearing");
		}else{
			CustomReporter.errorLog("Change password is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("userClientAdminRole"), "verifytext", "Client Admin",null)){
			CustomReporter.log("User role is Client Admin");
		}else{
			CustomReporter.errorLog("User role is not [Client Admin] it is: "+myAcct.func_getText("clientadminrole"));
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("emailAddress"), "visibility")){
			CustomReporter.log("Email address is appearing");
		}else{
			CustomReporter.errorLog("Email is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("userName"), "visibility")){
			CustomReporter.log("User name is appearing");
		}else{
			CustomReporter.errorLog("User name not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		//Step 12
		CustomReporter.log("===Step 12===");
		myAcct.func_browserBack();
		
		rm.webElementSync("idiomspinningcomplete");
		
		if(audiencePage.func_AudiencePageElementExist("AnalyzeLabel")){
			CustomReporter.log("Audience page is appearing");
		}else{
			CustomReporter.errorLog("Failed to open Audience page");
			BaseClass.testCaseStatus=false;
		}
		
		//Step 13
		CustomReporter.log("===Step 13===");
		
		rm.webElementSync("idiomspinningcomplete");
		audiencePage.func_ClickElement("AnalyzeLabel");
		
		rm.webElementSync("idiomspinningcomplete");
		
		Analyse_Profile_Page analyzePage = new Analyse_Profile_Page(driver);
		
		if(analyzePage.func_ElementExist("AnalysePageExists")){
			CustomReporter.log("Analyze page is opened");
		}else{
			CustomReporter.errorLog("Failed to open Analyze tab");
			BaseClass.testCaseStatus=false;
		}
		
		
		//Step 14
		CustomReporter.log("===Step 14===");
		analyzePage.func_ClickElement("myaccount");
		
		rm.webElementSync("idiomspinningcomplete");
		
		if(myAcct.func_elementExist("profilesetting")){
			CustomReporter.log("My profile page is opened");
			
		}else{
			CustomReporter.log("Failed to open My Account page");
			BaseClass.testCaseStatus = false;
		}
		
		//step 15
		CustomReporter.log("===Step 15===");
		if(rm.compareArray(listAssignedClients, listClientsforLoggedUser)){
			CustomReporter.log("Clients are matched");
		}else{
			CustomReporter.errorLog("Clients mismatch DB Clients :"+listAssignedClients+" Application clients :"+listClientsforLoggedUser);
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("changePassButton"), "visibility")){
			CustomReporter.log("Change password is appearing");
		}else{
			CustomReporter.errorLog("Change password is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("userClientAdminRole"), "verifytext", "Client Admin",null)){
			CustomReporter.log("User role is Client Admin");
		}else{
			CustomReporter.errorLog("User role is not [Client Admin] it is: "+myAcct.func_getText("clientadminrole"));
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("emailAddress"), "visibility")){
			CustomReporter.log("Email address is appearing");
		}else{
			CustomReporter.errorLog("Email is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("userName"), "visibility")){
			CustomReporter.log("User name is appearing");
		}else{
			CustomReporter.errorLog("User name not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		//Step 16
		CustomReporter.log("===Step 16===");
		myAcct.func_browserBack();
		
		rm.webElementSync("idiomspinningcomplete");
		
		if(analyzePage.func_ElementExist("AnalysePageExists")){
			CustomReporter.log("Analyze page is opened");
		}else{
			CustomReporter.errorLog("Failed to open Analyze tab");
			BaseClass.testCaseStatus=false;
		}
		
		//Step 17
		CustomReporter.log("===Step 17===");
		analyzePage.func_ClickElement("Architect");
		
		rm.webElementSync("idiomspinningcomplete");
			
		ArchitectMediaRankerPage archPage = new ArchitectMediaRankerPage(driver);
		
		if(archPage.func_VerifyElementExist("mediarankertextinmenu")){
			CustomReporter.log("Architect page is opened");
		}else{
			CustomReporter.errorLog("Failed to open architact tab");
			BaseClass.testCaseStatus = false;
		}
		
		//Step 18
		CustomReporter.log("===Step 18===");
		archPage.func_ClickOnElement("myaccount");
		
		rm.webElementSync("idiomspinningcomplete");
		
		if(myAcct.func_elementExist("profilesetting")){
			CustomReporter.log("My profile page is opened");
			
		}else{
			CustomReporter.log("Failed to open My Account page");
			BaseClass.testCaseStatus = false;
		}
		
		//Step 19
		CustomReporter.log("===Step 19===");
		if(rm.compareArray(listAssignedClients, listClientsforLoggedUser)){
			CustomReporter.log("Clients are matched");
		}else{
			CustomReporter.errorLog("Clients mismatch DB Clients :"+listAssignedClients+" Application clients :"+listClientsforLoggedUser);
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("changePassButton"), "visibility")){
			CustomReporter.log("Change password is appearing");
		}else{
			CustomReporter.errorLog("Change password is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("userClientAdminRole"), "verifytext", "Client Admin",null)){
			CustomReporter.log("User role is Client Admin");
		}else{
			CustomReporter.errorLog("User role is not [Client Admin] it is: "+myAcct.func_getText("clientadminrole"));
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("emailAddress"), "visibility")){
			CustomReporter.log("Email address is appearing");
		}else{
			CustomReporter.errorLog("Email is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		if(rm.webElementSync(myAcct.func_GetLocalWebElement("userName"), "visibility")){
			CustomReporter.log("User name is appearing");
		}else{
			CustomReporter.errorLog("User name not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		//Step 20
		CustomReporter.log("Log out from application");
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