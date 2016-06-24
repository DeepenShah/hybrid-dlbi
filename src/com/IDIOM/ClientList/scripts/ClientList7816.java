
/********************************************************************
Test Case Name: Client List_Verify "Select a client" Text Present in the Top bar menu
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/7816.aspx
Module: Client List
Created By: Abhishek Deshpande
Date: 03 Aug 2015
**********************************************************************/

package com.IDIOM.ClientList.scripts;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;
//import com.msat.frameworks.data_driven.generic.reusables.GenericComponents;

import common.BaseClass;

public class ClientList7816 extends BaseClass {
		
	@Test
	public void test_ClientList7816() throws InterruptedException {
		
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		CustomReporter.log("Enter valid email id and password");		
		//Reading email and password value from property file
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");
		ln.func_LoginToIDIOM(emailid, password);	
		
		CustomReporter.log("User should land in Client List page");
		ClientList_Page CL = new ClientList_Page(driver);
		
		CustomReporter.log("Verify IDIOM logo is displayed");		
		CL.func_ClientListPageElementExist("IDIOM Logo");
		
		CustomReporter.log("Verify Select a Client label is displayed");
		CL.func_ClientListPageElementExist("Select a Client");
		
		CustomReporter.log("Verify Missing a client icon is displayed");
		if(CL.func_ClientListPageElementExist("Missing a client?"))
		{
			CustomReporter.log("'Missing Client' is existing");
		}
		else{
			CustomReporter.errorLog("The missing client is Not Existing");
			BaseClass.testCaseStatus=false;
		}
		
		
		if(CL.func_ClientListPageElementExist("Clients"))
		{

			CustomReporter.log("Clients are existing");
		}else
		{
			CustomReporter.errorLog("Clients are NOT existing");
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
