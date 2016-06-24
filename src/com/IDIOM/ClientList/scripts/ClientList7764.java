/********************************************************************
Test Case Name: Client List_Verify help icon in top bar
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/7764.aspx
Module: Client List
Created By: Abhishek Deshpande
Date: 04th AUG 2015
Modified By:Amrutha Nair
Date:12Th Aug 2015
**********************************************************************/

package com.IDIOM.ClientList.scripts;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;
//import com.msat.frameworks.data_driven.generic.reusables.GenericComponents;

import common.BaseClass;

public class ClientList7764 extends BaseClass {
	
	@Test
	public void test_ClientList7764() throws InterruptedException {
		
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		CustomReporter.log("Enter valid email id and password");		
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");
		ln.func_LoginToIDIOM(emailid, password);	
		
		CustomReporter.log("User should land in Client List page");
		ClientList_Page CL = new ClientList_Page(driver);		
	
		
		CustomReporter.log("Verify Help Icon is displayed");
		if(CL.func_ClientListPageElementExist("Help Icon"))
		{
			CustomReporter.log("The help icon is existing. Mouse over on help icon");
			CL.func_PerformAction("Help");
			
			if(CL.func_ClientListPageElementExist("Help Content")){
				CustomReporter.log("The help content is visible");
			}
			else{
				CustomReporter.errorLog("The help content is NOT visible");
				BaseClass.testCaseStatus=false;
			}
			
			
		}
		else{
			CustomReporter.errorLog("The help icon is NOT present");
			BaseClass.testCaseStatus=false;
		}
		
		if(CL.func_ClientListPageElementExist("Logout"))
		{
			CustomReporter.log("The Logout icon is existing. Click on that");
			CL.func_PerformAction("Logout");
		}
		else{
			CustomReporter.errorLog("The Logout is NOT Present");
			BaseClass.testCaseStatus=false;
		}
		
		
		if(BaseClass.testCaseStatus==false){
			CustomReporter.errorLog("Test case has failed");
			Assert.assertTrue(false);
		}
		
				
	}

}

