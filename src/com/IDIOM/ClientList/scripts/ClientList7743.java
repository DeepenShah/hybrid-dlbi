/********************************************************************
Test Case Name: Client List_Verify Empty List 
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/7743.aspx 
Objective:To verify When there are no Clients present
Module: Client List
Created By: Rohan Macwan
Date: 20 Aug 2015
**********************************************************************/
package com.IDIOM.ClientList.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class ClientList7743 extends BaseClass {
	
	@Test	
	public void test_ClientList7743() throws InterruptedException {
		
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
		
		
		
		//Below block checks whether any clients are present on the page. As per the test case there should not be any clients present on the page
		if(CL.func_ClientListPageElementExist("Clients"))
		{
			CustomReporter.errorLog("Please login with such credentials for which no Clients are present");
			BaseClass.testCaseStatus=false;
		}
		else
		{
			CustomReporter.log("No Clients are present on the page");
			
		}
		
		//Below block checks whether Missing a Client Card is present on the page or not. As per the test case it should be present.
		if(CL.func_ClientListPageElementExist("Missing a client?"))
		{
			CustomReporter.log("Missing a Client card is present on the page");
			
			
		}
		else
		{
			BaseClass.testCaseStatus=false;
			CustomReporter.errorLog("Missing a Client Card is not present on the page");
			
		}
		
		//Below block checks whether Select a Client text is present at the top or not. As per the test case it should be present. 
		if(CL.func_ClientListPageElementExist("Select a Client"))
		{
			CustomReporter.log("Select a Client Text is present on the page at the top");
			
			
		}
		else
		{
			BaseClass.testCaseStatus=false;
			CustomReporter.errorLog("Select a Client text is not present on the page at the top");
			
		}
		
				
		rm.waitTime(8000);
		CustomReporter.log("Click on Logout link");
		CL.func_PerformAction("Logout");
	//****************Test step ends here************************************************
				
		if(BaseClass.testCaseStatus==false){
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
		}
		
	}

}
