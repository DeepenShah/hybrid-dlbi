/********************************************************************
Test Case Name: *GenaralUser_Verify_AdminAccess_Absence
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/8289.aspx
Objective: The Admin Access option should not be present for General User
Module: UserAdmin And UserPermissions
Created By: ABHISHEK DESHPANDE
Date: 6th January 2016
**********************************************************************/
package com.IDIOM.UserAdminAndUserPermissions.scripts;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class UserAdmin8289 extends BaseClass{
		
	@Test
	public void test_UserAdmin8289() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("GeneralUserEmail");
		String password = property.getProperty("GeneralUserPassword");			
		
		
	//****************Test step starts here************************************************
		//Step1: Load the URL and log in with valid General User Credentials
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		Thread.sleep(3000);
		
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    //Step 2:Click on menu icon at the top and verify that Admin Access option is not available
	    ClientList_Page cl= new ClientList_Page(driver);
	    Thread.sleep(3000);
	    if(cl.func_ClientListPageElementExist("Admin Access Present")){
	    	CustomReporter.errorLog("Admin access is present for General User");
	    	BaseClass.testCaseStatus =false;
	    }else{
	    	CustomReporter.log("Admin access is not present for General User");
	    }    
	   
		    
		//Step 4: Log out from the website
		 cl.func_PerformAction("Logout");
		 CustomReporter.log("The user has been logged out");
		    
		
		//****************Test step ends here************************************************
				if(BaseClass.testCaseStatus == false){
						CustomReporter.errorLog("Test case has failed");
						Assert.assertTrue(false);
				}	
	}

}
