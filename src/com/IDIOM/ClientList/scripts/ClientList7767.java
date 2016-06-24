
/********************************************************************
Test Case Name: Client List_Verify  Client Change in dropdown
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/7767.aspx
Module: Client List
Created By: Abhishek Deshpande
Date: 14 Aug 2015
**********************************************************************/

package com.IDIOM.ClientList.scripts;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.reports.CustomReporter;
//import com.msat.frameworks.data_driven.generic.reusables.GenericComponents;

import common.BaseClass;

public class ClientList7767 extends BaseClass {
		
	@Test
	public void test_ClientList7767() throws InterruptedException {
	//****************Variables declaration and Initialization****************************
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");
		String clientName = property.getProperty("clientName");
		
		
	//****************Test step starts here************************************************
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		
		CustomReporter.log("Enter valid email id and password");		
		ln.func_LoginToIDIOM(emailid, password);	
		
		
		CustomReporter.log("User should land in Client List page");
		ClientList_Page CL = new ClientList_Page(driver);
		
		rm.waitTime(3000);
		
		
		CustomReporter.log("Select any client");
		CL.func_SelectClient(clientName);
		rm.waitTime(3000);
		
		
		CustomReporter.log("Click on any existing IDIOM");
		ManageIdiom_Page MI = new ManageIdiom_Page(driver);
		MI.func_SelectExistingIdiomByNumber(2);
		rm.waitTime(3000);
		Audience_Page AP = new Audience_Page(driver);
		
		CustomReporter.log("Click on top RHS client icon");
		AP.func_ClickElement("ClientIcon");
		
		CustomReporter.log("Select change client option");
		AP.func_ClickElement("ChangeClient");
		/*if (AP.func_RClientElementExists("ChangeClient")){
		AP.func_ClickElement("ChangeClient");
		}
		*/
		rm.waitTime(3000);
		CustomReporter.log("Verify user is navigated to client list page");
		if (CL.func_ClientListPageElementExist("Select a Client")){
			CustomReporter.log("User is navigated to Client List page");
		} else{
			CustomReporter.errorLog("User is NOT navigated to Client List page");
			BaseClass.testCaseStatus=false;
		}
		
		
		CustomReporter.log("Click on Logout button");
		CL.func_PerformAction("Logout");	
		
	//****************Test step ends here************************************************
				
		if(BaseClass.testCaseStatus==false){
			CustomReporter.errorLog("Test case has failed");
			Assert.assertTrue(false);
		}
		
				
	}

}
