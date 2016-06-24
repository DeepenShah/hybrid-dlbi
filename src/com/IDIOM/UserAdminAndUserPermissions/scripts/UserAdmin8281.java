/********************************************************************
Test Case Name:*Super User Admin - Super User Admin Header - Verify Super User Admin Header "Manage Team" text
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8281.aspx
Objective:*Super User Admin - Super User Admin Header - Verify Super User Admin Header "Manage Team" text
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 30 November 2015
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;

import common.BaseClass;

public class UserAdmin8281 extends BaseClass {
		
	@Test
	public void test_UserAdmin8281() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		
		
	//****************Test step starts here************************************************
		//Step1: Load the URL and log in with valid Super User Admin Credentials 
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		Thread.sleep(3000);
		
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    //Step 2:Click on menu icon at the top and click on Admin Access
	    ClientList_Page cl= new ClientList_Page(driver);
	    Thread.sleep(3000);
	    cl.func_PerformAction("AdminAccess");
	    CustomReporter.log("The user has clicked on 'Admin Access'");
	    
	    
	    
	    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
	    Thread.sleep(3000);
	    
	    
	    if(up.func_ElementExist("Users List")){	    	
	    	CustomReporter.log("The user listing is displayed");
	    	
	    //sTEP 3:Check whether mANAGE idIOM text is present beside Idiom Logo.
	    	if (up.func_ElementExist("Manage Team")){
	    		CustomReporter.log("'Manage Team ' is present in header");
	    	}
	    	else{
	    		CustomReporter.errorLog("'Manage Team' is not present in header");
	    		BaseClass.testCaseStatus=false;
	    	}
	    }
	    else{
	    	CustomReporter.errorLog("The user listing is NOT displayed");
    		BaseClass.testCaseStatus=false;
	    }
	    
	    //Step 4: Log out from the website
	    up.func_ClickElement("Logout");
		CustomReporter.log("The user has been logged out");
		    
		
		//****************Test step ends here************************************************
		if(BaseClass.testCaseStatus == false){
				CustomReporter.errorLog("Test case has failed");
				Assert.assertTrue(false);
			}	
	}
	}
	 
	    	