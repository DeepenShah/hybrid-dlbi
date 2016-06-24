/********************************************************************
Test Case Name: Client List_Verify Main Menu
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/7810.aspx
Module: Client List
Created By: Abhishek Deshpande
Date: 04th AUG 2015
**********************************************************************/

package com.IDIOM.ClientList.scripts;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;
//import com.msat.frameworks.data_driven.generic.reusables.GenericComponents;

import common.BaseClass;

public class ClientList7810 extends BaseClass {
	
	@Test
	public void test_ClientList7810() throws InterruptedException {
		
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		CustomReporter.log("Enter valid email id and password");		
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");
		ln.func_LoginToIDIOM(emailid, password);	
		
		CustomReporter.log("User should land in Client List page");
		ClientList_Page CL = new ClientList_Page(driver);
		
		CustomReporter.log("Check whether clients got displayed");
		if(CL.func_ClientListPageElementExist("Clients")){
			CustomReporter.log("The usr has laned in client list page and clients are existing");
			if(CL.func_ClientListPageElementExist("Missing a client?")){
				CustomReporter.log("Missing a client? label is existing");
			}else{
				CustomReporter.errorLog("Missing a client? label is NOT existing");
			}
		}
		else{
			CustomReporter.errorLog("The clients are NOT existing");
			BaseClass.testCaseStatus=false;
		}
		
	
		CustomReporter.log("Verify IDIOM logo is displayed");		
		if(CL.func_ClientListPageElementExist("IDIOM Logo")){
			CustomReporter.log("Idiom logo is existing");
		}else{
			CustomReporter.errorLog("The idiom logo is NOt existing");
			BaseClass.testCaseStatus=false;
		}
		
		CustomReporter.log("Verify Select a Client label is displayed");
		if(CL.func_ClientListPageElementExist("Select a Client")){
			CustomReporter.log("'Select a Client' label is existing");
		}
		else{
			CustomReporter.errorLog("The 'Select a Client' label is NOt existing");
			BaseClass.testCaseStatus=false;
		}
		
	
		CustomReporter.log("Verify Help Icon is displayed");
		if(CL.func_ClientListPageElementExist("Help Icon")){
			CustomReporter.log("Help Icon is existing");
		}
		else{
			CustomReporter.errorLog("Help Icon is NOt existing");
			BaseClass.testCaseStatus=false;
		}
		
		if(CL.func_ClientListPageElementExist("Logout")){
			CustomReporter.log("Logout Icon is existing");
		}
		CustomReporter.log("Click on Logout link");
		CL.func_PerformAction("Logout");
		
		if(BaseClass.testCaseStatus==false){
			CustomReporter.errorLog("Test case has failed");
			Assert.assertTrue(false);
		}
		
				
	}

}

