/********************************************************************
Test Case Name: Client List_Validate "Select a Client" Message 
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/7772.aspx 
Module: Client List
Created By: Rohan Macwan
Date: 06 Aug 2015
**********************************************************************/


package com.IDIOM.ClientList.scripts;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class ClientList7772 extends BaseClass {
		
	@Test
	public void test_ClientList7772() throws InterruptedException {
		
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		CustomReporter.log("Enter valid email id and password");
		CustomReporter.log("Click on login to Idiom button");		
		//Reading email and password value from property file
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");
		ln.func_LoginToIDIOM(emailid, password);	
		
		CustomReporter.log("User should land in Client List page");
		ClientList_Page CL = new ClientList_Page(driver);
		
		rm.waitTime(3000);
		
		CustomReporter.log("Verify 'Select a Client' Text is present at the top");		
		if (CL.func_ClientListPageElementExist("Select a Client"))
		{
			CustomReporter.log("'Select a Client' text is found");
		}
		else
		{
			CustomReporter.errorLog("'Select a Client' text is not found");
			BaseClass.testCaseStatus=false;
		}
		
		CustomReporter.log("Click on Logout link");
		CL.func_PerformAction("Logout");
		
		if(BaseClass.testCaseStatus==false){
			CustomReporter.errorLog("Test case has failed");
			Assert.assertTrue(false);			
		}
		
			
		
	}

}
