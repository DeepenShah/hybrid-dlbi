/********************************************************************
Objective: This test case is meant to verify whether Change Client menu option works from Manage Idiom page
Test Case Name: Client List_Verify Client Change on selecting "Change Client" 
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/7774.aspx 
Module: Client List Page
Created By: Rohan Macwan
Date: 10 Sep 2015
**********************************************************************/
package com.IDIOM.ClientList.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class ClientList7774 extends BaseClass{

	
	@Test	
	public void test_ClientList7774() throws InterruptedException {
		
	//****************Variables declaration and Initialization****************************
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");
		String client1 = property.getProperty("clientName");
		String client2 = property.getProperty("clientName2");
	
	//****************Test step starts here************************************************
		//Step 1 - User launches web browser and types the application URL.
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		CustomReporter.log("Enter valid email id and password");
		CustomReporter.log("Click on login to Idiom button");		
		//Reading email and password value from property file
		
		
		//Step 2 - User enters valid email and password. 
		ln.func_LoginToIDIOM(emailid, password);	
		
		
		//Step 3 - User selects a client.
		CustomReporter.log("User should land in Client List page");
		ClientList_Page CL = new ClientList_Page(driver);
		
		rm.waitTime(3000);
		
		CustomReporter.log("Select Client - " + client1 +  " from Client list page");
		
		CL.func_SelectClient(client1);
		
		rm.waitTime(3000);
		
		//Step 4 - User clicks on the client's icon. 
		
		CustomReporter.log("Manage Idiom page is displayed");
				
		//Step 5 - User selects the "Change client" option.
		ManageIdiom_Page mi = new ManageIdiom_Page(driver);
		Thread.sleep(3000);
		
		if (mi.func_TopClientElementExists("ChangeClient"))
		{
			CustomReporter.log("Click on Change Client menu option");
			mi.func_PerformClickAction("ChangeClient");
		}
		else
		{
			BaseClass.testCaseStatus=false;
			CustomReporter.errorLog("Change Client menu option does not exist on the page");
		}
		
		rm.waitTime(3000);
		
		//Step 6 - User clicks on the logout button. 
		CustomReporter.log("Click on Logout link");
		CL.func_PerformAction("Logout");
		
		//****************Test step ends here************************************************
		
		if(BaseClass.testCaseStatus==false){
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
		}
	}
	
	
}
