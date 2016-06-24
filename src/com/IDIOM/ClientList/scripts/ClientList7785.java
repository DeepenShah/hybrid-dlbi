/********************************************************************
Test Case Name: Clients List_Verify Current Client Icon at Top Right Corner 
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/7785.aspx 
Module: Client List
Created By: Rohan Macwan
Date: 11 Aug 2015
**********************************************************************/

package com.IDIOM.ClientList.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.reports.CustomReporter;

import common.BaseClass;



public class ClientList7785 extends BaseClass {
		
	@Test	
	public void test_ClientList7785() throws InterruptedException {
	
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		CustomReporter.log("Enter valid email id and password");
		CustomReporter.log("Click on login to Idiom button");		
		//Reading email and password value from property file
		String emailid = property.getProperty("email");
		String clientName = property.getProperty("clientName");
		String password = property.getProperty("password");
		ln.func_LoginToIDIOM(emailid, password);	
		
		CustomReporter.log("User should land in Client List page");
		ClientList_Page CL = new ClientList_Page(driver);
		
		rm.waitTime(3000);
		
		CustomReporter.log("Select Client:"+clientName+" from Client list page");
		CL.func_SelectClient(clientName);
		
		rm.waitTime(3000);
		
		CustomReporter.log("Manage Idiom page is displayed");
		
		ManageIdiom_Page mi = new ManageIdiom_Page(driver);
		Thread.sleep(3000);
		
		
		Thread.sleep(3000);
		
		
		
		if ((mi.func_TopClientElementExists("Manage")==false) || (mi.func_TopClientElementExists("Save")==false) || (mi.func_TopClientElementExists("Rename")==false))
		{
			
			CustomReporter.log("Manage, Save and Rename options should not be present. And they are not present on the page");
		}	
		else{
			BaseClass.testCaseStatus=false;
			CustomReporter.errorLog("Manage, Save and Rename options should not be present on the page under Client Logo");
		}
		
		if (mi.func_TopMenuElementExists("Logout") && mi.func_TopClientElementExists("ChangeClient"))
		{
			CustomReporter.log("'Logout' and 'Change Client' are existing under client icon at right top");
		}
		else
		{
			BaseClass.testCaseStatus=false;
						
		}
		
		
		rm.waitTime(3000);
		CustomReporter.log("Click on Logout link");
		mi.func_PerformClickAction("right_Logout");
		rm.waitTime(3000);
		
		if(BaseClass.testCaseStatus==false)
		{
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
			  }
		}
		
	}


