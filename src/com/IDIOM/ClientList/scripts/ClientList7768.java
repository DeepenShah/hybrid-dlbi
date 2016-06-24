/********************************************************************
Test Case Name: Client List_Verify Client Change 
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/7768.aspx 
Objecive: To verify after clicking on Change Client menu options from Idiom List page redirects user to Client List page
Module: Client List
Created By: Rohan Macwan
Date: 24 Aug 2015
**********************************************************************/

package com.IDIOM.ClientList.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class ClientList7768 extends BaseClass {
	
	@Test	
	public void test_ClientList7768() throws InterruptedException {
		
	//****************Variables declaration and Initialization****************************
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");
		String client1 = property.getProperty("clientName");
	
	//****************Test step starts here************************************************
		try{
			//Step 1:	Launch Browser and enter URL
			CustomReporter.log("Launch Browser and Enter URL");
			Login_Page ln = new Login_Page(driver);	
			
			
			//Step 2:Enter valid email and password.
			//Step 3:Click on Log in to idiom button.
			CustomReporter.log("Enter valid email id and password");
			CustomReporter.log("Click on login to Idiom button");		
			ln.func_LoginToIDIOM(emailid, password);	
			
			CustomReporter.log("User should land in Client List page");
			ClientList_Page CL = new ClientList_Page(driver);
			
			rm.waitTime(3000);
			
			//Step 4:Click on Any client
			CustomReporter.log("Select Client - " + client1 +  " from Client list page");
			
			CL.func_SelectClient(client1);
			
			rm.waitTime(3000);
			
			CustomReporter.log("Manage Idiom page is displayed");
			
			ManageIdiom_Page mi = new ManageIdiom_Page(driver);
			Thread.sleep(7000);
			
			//Step 5:Click on Client Logo at the top
			//Step 6:Click on change client
			mi.func_PerformClickAction("ChangeClient");
			Thread.sleep(5000);
							
			CustomReporter.log("Verify 'Select a Client' Text is present at the top");		
			if (CL.func_ClientListPageElementExist("Select a Client"))
			{
				CustomReporter.log("'Select a Client' text is found at the top and hence user has reached Client List page successfully");
			}
			else
			{
				CustomReporter.errorLog("'Select a Client' text is not found and hence User has not reached Client List page successfully");
				BaseClass.testCaseStatus=false;
			}
			
			
			//Step 7:	Click on Logout menu option.
			rm.waitTime(3000);
			CustomReporter.log("Click on Logout link");
			CL.func_PerformAction("Logout");
		}
		
		catch(Exception e){
			BaseClass.testCaseStatus = false;
			CustomReporter.errorLog(" The exception is generated, " + e.getMessage() + ". ");
			e.printStackTrace(System.out);
			rm.captureScreenShot("ClientList7768", "fail");
		}
	//****************Test step ends here************************************************
		
		if(BaseClass.testCaseStatus==false){
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
		}
				
		
	}

}
