
/********************************************************************
Test Case Name: Client List_Verify Idiom logo
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/7809.aspx
Module: Client List
Created By: Abhishek Deshpande
Date: 29th JULY 2015
Updated By:Amrutha Nair
Date:12th Aug 2015
**********************************************************************/

package com.IDIOM.ClientList.scripts;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;
//import com.msat.frameworks.data_driven.generic.reusables.GenericComponents;

import common.BaseClass;

public class ClientList7809 extends BaseClass {
		
	@Test
	public void test_ClientList7809() throws InterruptedException {
		
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
		else{
			CustomReporter.errorLog("Logout Icon is NOt existing");
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
