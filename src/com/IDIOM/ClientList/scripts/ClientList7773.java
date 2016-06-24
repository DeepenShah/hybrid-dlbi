/********************************************************************
Test Case Name: Client List_Verify Hovering Over Client Raised Effect 
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/7773.aspx 
Objective:To verify mouse hover effect present or not on Client Card
Module: Client List
Created By: Rohan Macwan
Date: 18 Aug 2015
**********************************************************************/

package com.IDIOM.ClientList.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class ClientList7773 extends BaseClass {
	
	
	@Test	
	public void test_ClientList7773() throws InterruptedException {
	//****************Variables declaration and Initialization****************************
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");
		String Client = property.getProperty("clientName");
		
	//****************Test step starts here************************************************	
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		CustomReporter.log("Enter valid email id and password");
		CustomReporter.log("Click on login to Idiom button");
		
		
		
		ln.func_LoginToIDIOM(emailid, password);	
		
		CustomReporter.log("User should land in Client List page");
		ClientList_Page CL = new ClientList_Page(driver);
		
		CustomReporter.log("Do mouse hover on Client - "+ Client);
		CL.func_DoMouseHoverOnClientCard(Client);
		
		//waitTime(3000);
		
		if(CL.func_ClientListPageElementExist("ClientCard")==true){
			CustomReporter.log("Mouse hover effect is present on Client Card");
		}
		else
		{
			BaseClass.testCaseStatus=false;
			CustomReporter.errorLog("Mouse hover effect on Client Card is not present");
		}
		
		CustomReporter.log("Click on Logout link");
		CL.func_PerformAction("Logout");
	//****************Test step ends here************************************************
		
		
		if(BaseClass.testCaseStatus==false)
		{
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
		}
				
		
	}

}
