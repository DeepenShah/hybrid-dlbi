/********************************************************************
Test Case Name: Client_List_Logos should be displayed all the clients 
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/8229.aspx 
Objective:To verify whether Client Cards has its Logo in it
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

public class ClientList8229 extends BaseClass{
	
	
	@Test	
	public void test_ClientList8229() throws InterruptedException {
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
		
		CL.func_CheckLogosPresent();
		
		rm.waitTime(3000);
		CustomReporter.log("Click on Logout link");
		CL.func_PerformAction("Logout");
	//****************Test step ends here************************************************
				
		if(BaseClass.testCaseStatus==false){
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
		}
		
	}

}
