/********************************************************************
Test Case Name: *For Any User Verify the presence of My Account menu option
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/8452.aspx
Objective:  Verify presence of My Account Menu option
Module: User Admin - My Account
Created By: Rohan Macwan
Date: 01-February-2016
**********************************************************************/

package com.IDIOM.UserAdmin.MyAccount.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.IDIOM.pages.UserAdminMyAccount_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class MyAccount8452 extends BaseClass{

	@Test
	public void test_MyAccount8452() throws Exception{
		int i=0;
		String adminUser = "";
		String adminPass = "";
		/*String ClientadminUser = "";
		String ClientadminPass = "";
		String GeneralUser = "";
		String GeneralPass = "";*/
		String LoggedInUser="";
		for (i=0;i<3;i++)
		{
			/******************************Variables********************************/
			if (i==0)
			{
				adminUser = property.getProperty("SuperAdminUser").trim();
				adminPass = property.getProperty("SuperAdminpassword").trim();	
				LoggedInUser = "Super Admin User ";
			}
			else if(i==1)
			{
				adminUser = property.getProperty("ClientAdminEmail").trim();
				adminPass = property.getProperty("ClientAdminPassword").trim();
				LoggedInUser = "Client Admin User ";
			}
			else
			{
				adminUser = property.getProperty("GeneralUserEmail").trim();
				adminPass = property.getProperty("GeneralUserPassword").trim();
				LoggedInUser = "Normal Application User ";
			}
			
			String clientName = property.getProperty("clientName");
			/****************************Variables End******************************/
			
			CustomReporter.log("=======Step 1=======");
			
			CustomReporter.log("Open URL and login to IDIOM");
			
			Login_Page lp = new Login_Page(driver);
			
			lp.func_LoginToIDIOM(adminUser, adminPass);
			
			Thread.sleep(10000);
			
			CustomReporter.log("Logged in with " + LoggedInUser + " - " + adminUser);
			
			CustomReporter.log("=======Step 2=======");
			
			CustomReporter.log("Client List page has loaded");
			
			ClientList_Page clientListPage = new ClientList_Page(driver);
		
			UserAdminMyAccount_Page myAcct = new UserAdminMyAccount_Page(driver);
			Thread.sleep(10000);
			//myAcct.func_onHover("headericon111");
			myAcct.func_click("headericon");
			Thread.sleep(10000);
			CustomReporter.log("Clicked on header Menu Drop Down");
			
			if(myAcct.func_elementExist("dropdownopen")){
				if (func_CheckMyAccountLinkInMenu())
				{
					CustomReporter.log("My Account link is found on Client List Page");
				}
				else
				{
					CustomReporter.errorLog("My Account link is not found on Client List Page");
					BaseClass.testCaseStatus = false;
				}
			}
			else
			{
				
				CustomReporter.errorLog("Failed to open drop down in Client List Page");
				BaseClass.testCaseStatus = false;
			}
			
			Thread.sleep(10000);
			
			//Manage Idiom Page
			CustomReporter.log("Select  client :"+clientName+" from client list page");
			CustomReporter.log("####### Manage Idiom Page has loaded #######");
			clientListPage.func_SelectClient(clientName);
			
			Thread.sleep(10000);
			myAcct.func_click("headericon");
			Thread.sleep(5000);
			CustomReporter.log("Clicked on header Menu Drop Down");
			
			if(myAcct.func_elementExist("dropdownopen")){
				if (func_CheckMyAccountLinkInMenu())
				{
					CustomReporter.log("My Account link is found on Manage Idiom Page");
				}
				else
				{
					CustomReporter.errorLog("My Account link is not found on Manage Idiom Page");
					BaseClass.testCaseStatus = false;
				}
			}
			else
			{
				
				CustomReporter.errorLog("Failed to open drop down in Manage Idiom Page");
				BaseClass.testCaseStatus = false;
			}
			
			
			//Manage Idiom Page Completed
			
			//Audience page
			Thread.sleep(10000);
			ManageIdiom_Page mi= new ManageIdiom_Page(driver);
			//mi.func_CreateNewIdiomOrRename("Test", "NotBlank");
			String IdiomName=mi.func_PerformClickAction("SelectFirstIdiom");
		    CustomReporter.log("The idiom :"+IdiomName+" is selected");
			
			CustomReporter.log("####### Audience Page has loaded #######");
			
			Thread.sleep(10000);
			myAcct.func_click("headericon");
			Thread.sleep(5000);
			CustomReporter.log("Clicked on header Menu Drop Down");
			
			if(myAcct.func_elementExist("dropdownopen")){
				if (func_CheckMyAccountLinkInMenu())
				{
					CustomReporter.log("My Account link is found on Audience Page");
				}
				else
				{
					CustomReporter.errorLog("My Account link is not found on Audience Page");
					BaseClass.testCaseStatus = false;
				}
			}
			else
			{
				
				CustomReporter.errorLog("Failed to open drop down in Audience Page");
				BaseClass.testCaseStatus = false;
			}
			//Audience page completed
		
			//Analyze page started
			Audience_Page ad = new Audience_Page(driver);
			Thread.sleep(5000);
			ad.func_ClickElement("AnalyzeLabel");
			Thread.sleep(10000);
			
			CustomReporter.log("####### Analyze Page has loaded #######");
			
			Thread.sleep(10000);
			myAcct.func_click("headericon");
			Thread.sleep(5000);
			CustomReporter.log("Clicked on header Menu Drop Down");
			
			if(myAcct.func_elementExist("dropdownopen")){
				if (func_CheckMyAccountLinkInMenu())
				{
					CustomReporter.log("My Account link is found on Analyze Page");
				}
				else
				{
					CustomReporter.errorLog("My Account link is not found on Analyze Page");
					BaseClass.testCaseStatus = false;
				}
			}
			else
			{
				
				CustomReporter.errorLog("Failed to open drop down in Analyze Page");
				BaseClass.testCaseStatus = false;
			}
			//Analyze page completed
			
			//Media Ranker page started
			ArchitectMediaRankerPage arMediaRankerPage = new ArchitectMediaRankerPage(driver);
			arMediaRankerPage.func_ClickOnElement("architect");
			String strMsg = "Clicked on Architect";
			CustomReporter.log(strMsg);
			
			CustomReporter.log("####### Media Ranker Page has loaded #######");
			
			Thread.sleep(10000);
			myAcct.func_click("headericon");
			Thread.sleep(5000);
			CustomReporter.log("Clicked on header Menu Drop Down");
			
			if(myAcct.func_elementExist("dropdownopen")){
				if (func_CheckMyAccountLinkInMenu())
				{
					CustomReporter.log("My Account link is found on Media Ranker Page");
				}
				else
				{
					CustomReporter.errorLog("My Account link is not found on Media Ranker Page");
					BaseClass.testCaseStatus = false;
				}
			}
			else
			{
				
				CustomReporter.errorLog("Failed to open drop down in Media Ranker Page");
				BaseClass.testCaseStatus = false;
			}
			//Media Ranker page completed
			
			
			//User Admin page started
			Thread.sleep(5000);
			if (i<2)
			{
				arMediaRankerPage.func_ClickOnElement("AdminAccess");

				CustomReporter.log("####### User Admin Page has loaded #######");
				
				Thread.sleep(10000);
				myAcct.func_click("headericon");
				Thread.sleep(5000);
				CustomReporter.log("Clicked on header Menu Drop Down");
				
				if(myAcct.func_elementExist("dropdownopen")){
					if (func_CheckMyAccountLinkInMenu())
					{
						CustomReporter.log("My Account link is found on User Admin Page");
					}
					else
					{
						CustomReporter.errorLog("My Account link is not found on User Admin Page");
						BaseClass.testCaseStatus = false;
					}
				}
				else
				{
					
					CustomReporter.errorLog("Failed to open drop down in User Admin Page");
					BaseClass.testCaseStatus = false;
				}
				
			}
			//User Admin page completed
			
			Thread.sleep(5000);
			
			if(myAcct.func_elementExist("helpicon")){
				CustomReporter.log("####### Help Page has loaded #######");
				myAcct.func_click("helpicon");
				Thread.sleep(10000);
				myAcct.func_click("helpcenter");
				Thread.sleep(10000);
				if(myAcct.func_elementExist("dropdownopen")){
					Thread.sleep(5000);
					myAcct.func_click("headericon");
					System.out.println("Help Center page");
					Thread.sleep(5000);
					if (func_CheckMyAccountLinkInMenu())
					{
						CustomReporter.log("My Account link is found on Help Page");
					}
					else
					{
						CustomReporter.errorLog("My Account link is not found on Help Page");
						BaseClass.testCaseStatus = false;
					}
				}
				else
				{
					
					CustomReporter.errorLog("Failed to open drop down in Help Page");
					BaseClass.testCaseStatus = false;
				}
				
			}
			else
			{	if(i<2)
				{
				CustomReporter.errorLog("Failed to open Help drop down in User Admin Page");	
				}
				
				BaseClass.testCaseStatus = false;
			}
			
			
			Thread.sleep(10000);
			CustomReporter.log("User is logged out");
			myAcct.func_click("logout");
			
			/***************************Test step ends here******************************/
			if(BaseClass.testCaseStatus == false){
				CustomReporter.errorLog("Test case has failed");
				Assert.assertTrue(false);
			}else{
				CustomReporter.log("Test case has Passed for " + LoggedInUser + " - " + adminUser);
			}
		}
		
		
		
	}
	
	public boolean func_CheckMyAccountLinkInMenu() throws Exception{
		boolean status=false;
		UserAdminMyAccount_Page myAcct = new UserAdminMyAccount_Page(driver);
					
					
			if (myAcct.func_elementExist("my account link in menu"))
			{
				status=true;
			}
			
		return status;
	}
	
}
