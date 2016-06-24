/********************************************************************
Test Case Name: 497_Manage Idioms_Veirfy Idioms updated as per selected client 
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/8236.aspx 
Objective:Verify Idioms updated as per selected client
Module: Client List
Created By: Rohan Macwan
Date: 17 Aug 2015
**********************************************************************/

package com.IDIOM.ClientList.scripts;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.reports.CustomReporter;

import common.BaseClass;


public class ClientList8236 extends BaseClass {

	@Test	
	public void test_ClientList8236() throws InterruptedException {
		
	//****************Variables declaration and Initialization****************************
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");
		String client1 = property.getProperty("clientName");
		String client2 = property.getProperty("clientName2");
	
	//****************Test step starts here************************************************
		try{
			//Step 1:Open url and login with valid creds
			CustomReporter.log("Launch Browser and Enter URL");
			Login_Page ln = new Login_Page(driver);	
			
			CustomReporter.log("Enter valid email id and password");
			CustomReporter.log("Click on login to Idiom button");		
			
			
			ln.func_LoginToIDIOM(emailid, password);	
			
			CustomReporter.log("User should land in Client List page");
			ClientList_Page CL = new ClientList_Page(driver);
			
			
			//Step 2:Click on any client, 
			rm.waitTime(3000);
			
			CustomReporter.log("Select Client - " + client1 +  " from Client list page");
			
			CL.func_SelectClient(client1);
			
			rm.waitTime(3000);
			
			CustomReporter.log("Manage Idiom page is displayed");
			
			ManageIdiom_Page mi = new ManageIdiom_Page(driver);
			Thread.sleep(3000);
			
			String Name="";
			//Step 3:	Create new idiom and save
			Name= mi.func_CreateIdiomName("Idiom", "New");
			Thread.sleep(3000);
			
			mi.func_CreateNewIdiomOrRename(Name, "NotBlank");
			Thread.sleep(3000);
			CustomReporter.log("New Idiom - "+ Name + " Created");
			Thread.sleep(3000);
			
			//Step 4:Click on change client
			Audience_Page Ad = new Audience_Page(driver);
			Ad.func_ClickElement("ChangeClient");
			Thread.sleep(3000);
			CustomReporter.log("Client List page is opened");
			
			//Step 5:Click on any other client,
			
			CustomReporter.log("Select Client " + client2 + " from Client list page");
			CL.func_SelectClient(client2);
			
			Thread.sleep(3000);
			
			
			//Step 6:Verify only ebay idioms appear in manage idiom page
			if(mi.func_SelectExistingIdiomByName(Name)){
				BaseClass.testCaseStatus=false;
				CustomReporter.errorLog("Idiom" + Name + " is present for the Client" + client2+" which we have created for "+client1);
			}
			else{
				CustomReporter.log("Created Idiom "+ Name + " is rightly not present for the client - " + client2);
			}
			
			//Step 7:	Click on change client and select client say Delta
			mi.func_PerformClickAction("ChangeClient");
			Thread.sleep(3000);
			CustomReporter.log("Client List page is opened");
			
			rm.waitTime(3000);
			
			CustomReporter.log("Select Client " + client1 + " from Client list page");
			CL.func_SelectClient(client1);
			rm.waitTime(5000);
			
			//Step 8:Verify idiom created in step 3 appears in list
			if (mi.func_SelectExistingIdiomByName(Name)){
				CustomReporter.log("Created Idiom "+ Name +" correctly found for the Client - " + client1);
			}
			else{
				BaseClass.testCaseStatus=false;
				CustomReporter.errorLog("Idiom - "+ Name + " should not be present for the Client - " + client1);
			}
			
			
			//Step 9:Logout of app
			rm.waitTime(3000);
			CustomReporter.log("Click on Logout link");
			Ad.func_ClickElement("Logout");
		}
		
		catch(Exception e){
			BaseClass.testCaseStatus = false;
			CustomReporter.errorLog(" The exception is generated, " + e.getMessage() + ". ");
			e.printStackTrace(System.out);
			rm.captureScreenShot("ClientList8236", "fail");
		}
	//****************Test step ends here************************************************
		
		if(BaseClass.testCaseStatus==false){
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
		}
				
		
	}

}	
			
	