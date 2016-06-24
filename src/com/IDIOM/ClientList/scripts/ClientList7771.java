/********************************************************************
Test Case Name: Client List_Verify "Missing a Client" Click 
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/7771.aspx 
Module: Client List
Created By: Rohan Macwan
Date: 07 Aug 2015
**********************************************************************/

package com.IDIOM.ClientList.scripts;

import java.awt.AWTException;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;

import common.BaseClass;
import common.ReusableMethods;


public class ClientList7771 extends BaseClass {
	
	@Test
	public void test_ClientList7771() throws InterruptedException {
		//****************Variables declaration and Initialization****************************
			String emailid = property.getProperty("email");
			String password = property.getProperty("password");
			String client1 = property.getProperty("clientName");
		
		//****************Test step starts here************************************************
		try{
			CustomReporter.log("Launch Browser and Enter URL");
			Login_Page ln = new Login_Page(driver);	
			
			CustomReporter.log("Enter valid email id and password");
			CustomReporter.log("Click on login to Idiom button");		
			
			
			ln.func_LoginToIDIOM(emailid, password);	
			
			CustomReporter.log("User should land in Client List page");
			ClientList_Page CL = new ClientList_Page(driver);
			
			rm.waitTime(3000);
			
			if(CL.func_ClientListPageElementExist("Missing a client?")){
				CustomReporter.log("'Missing a client?' element is found");
				CustomReporter.log("Click on 'Missing a client?' element");
				CL.func_PerformAction("Missing a client?");	
				
				try {
					if(CL.func_CheckEmailClient("IDIOM Client Access Request","stephanie.shaw@digitaslbi.com",true))
					{
						CustomReporter.log("Subject line and Email Id both are correct");
					}
					else
					{
						BaseClass.testCaseStatus=false;
					}
				}
					
					//CL.getClipboard("IDIOM Client Access Request");
				 catch (InterruptedException e) {
					e.printStackTrace();	
					
				} catch (AWTException e) {
					e.printStackTrace();
						
			}
				
			}
			rm.waitTime(3000);
			CustomReporter.log("Click on Logout link");
			CL.func_PerformAction("Logout");			
			
		
	}
	
	catch(Exception e){
		BaseClass.testCaseStatus = false;
		CustomReporter.errorLog(" The exception is generated, " + e.getMessage() + ". ");
		e.printStackTrace(System.out);
		rm.captureScreenShot("ClientList7771", "fail");
	}
//****************Test step ends here************************************************
	
	if(BaseClass.testCaseStatus==false){
		   CustomReporter.errorLog("Test case has failed");
		   Assert.assertTrue(false);   
	}
			
	
}

}
		
