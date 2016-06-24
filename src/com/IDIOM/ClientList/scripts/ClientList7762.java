
/********************************************************************
Test Case Name: Client List_Verify_Going_Back_To_Client List 
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/7762.aspx 
Module: Client List
Created By: Abhishek Deshpande
Date: 03 Aug 2015
Modified By:Amrutha Nair
date:12 th Aug 2015
**********************************************************************/

package com.IDIOM.ClientList.scripts;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.reports.CustomReporter;
//import com.msat.frameworks.data_driven.generic.reusables.GenericComponents;

import common.BaseClass;

public class ClientList7762 extends BaseClass {
			
	@Test
	public void test_ClientList7762() throws InterruptedException {
		BaseClass.testCaseStatus=true;
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		CustomReporter.log("Enter valid email id and password");		
		//Reading email and password value from property file
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");
		String clientName = property.getProperty("clientName");
		ln.func_LoginToIDIOM(emailid, password);	
		
		CustomReporter.log("User should land in Client List page");
		ClientList_Page CL = new ClientList_Page(driver);
		
		rm.waitTime(3000);
		
		CustomReporter.log("Verify IDIOM logo is displayed");		
		if(CL.func_ClientListPageElementExist("IDIOM Logo"))
		{
			CustomReporter.log(" The Idiom logo is existing");
		}else{
			CustomReporter.errorLog("The idiom logo is NOT existing");
			BaseClass.testCaseStatus=false;
		}
		
		/*CustomReporter.log("Click on Industry vertical client");
		CL.selectClient("Industry Vertical");
		
		CustomReporter.log("Click on Ecommerce client");
		CL.selectClient("ECommerce");
		
		CustomReporter.log("Click on Client logo");
		CL.clickElement("ClientLogo");
		
		CustomReporter.log("Click on Change Client button");
		CL.clickElement("ChangeClient");
		
		CustomReporter.log("Click on Delta client");
		CL.selectClient("Delta");
		
		CustomReporter.log("Click on Client logo");
		CL.clickElement("ClientLogo");
		
		CustomReporter.log("Click on Change Client button");
		CL.clickElement("ChangeClient");
		*/
		CustomReporter.log("Select  client:"+clientName+" from client list page");
		CL.func_SelectClient(clientName);
		
		Thread.sleep(3000);
		ManageIdiom_Page mi = new ManageIdiom_Page(driver);
		
		if(mi.func_TopClientElementExists("ChangeClient")){
			CustomReporter.log("The user has navigated to manage idiom page of respective client");
			CustomReporter.log("Click on 'Change Client'");
			
			mi.func_PerformClickAction("ChangeClient");
			
			if(CL.func_ClientListPageElementExist("Select a Client")){
				CustomReporter.log("The user has been navigated back to 'Client list' page");
				CustomReporter.log("Click on client ");
				CL.func_SelectClient(clientName);

			}
			else{
				CustomReporter.errorLog(" The user has not been navigated back to 'Client list' page");
				BaseClass.testCaseStatus=false;
			}			
			
		}
		else{
			CustomReporter.errorLog("The user has NOT navigated to manage idiom page of respective client");
			BaseClass.testCaseStatus=false;
		}
		CustomReporter.log("Click on Logout link");
		mi.func_PerformClickAction("Logout");			
		if(BaseClass.testCaseStatus==false){
			CustomReporter.errorLog("Test case has failed");
			Assert.assertTrue(false);
		}
		
				
	}

}
