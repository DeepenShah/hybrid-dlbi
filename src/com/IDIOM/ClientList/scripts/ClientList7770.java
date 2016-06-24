/********************************************************************
Test Case Name: Client List_Verify Logout link present at the top 
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/7770.aspx 
Objective:To verify presence of Logout link at the top
Module: Client List
Created By: Rohan Macwan
Date: 19 Aug 2015
**********************************************************************/

package com.IDIOM.ClientList.scripts;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;

import common.BaseClass;


public class ClientList7770 extends BaseClass {
		
	@Test	
	public void test_ClientList7770() throws InterruptedException {
	//****************Variables declaration and Initialization****************************
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");
		
	//****************Test step starts here************************************************	
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		CustomReporter.log("Enter valid email id and password");
		CustomReporter.log("Click on login to Idiom button");	
		
		
		ln.func_LoginToIDIOM(emailid, password);	
		
		CustomReporter.log("User should land in Client List page");
		rm.waitTime(3000);
		ClientList_Page CL = new ClientList_Page(driver);
		
		if (CL.func_ClientListPageElementExist("Logout"))
		{
			CustomReporter.log("Logout link is present at the top");
		}
		else
		{
			BaseClass.testCaseStatus=false;
			CustomReporter.errorLog("Logout link is not present");
		}
		rm.waitTime(3000);
		CustomReporter.log("Click on Logout link");
		CL.func_PerformAction("Logout");
		rm.waitTime(3000);
		
 //****************Test step ends here************************************************
		
		if(BaseClass.testCaseStatus==false)
		{
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
		}
		
	}

}
