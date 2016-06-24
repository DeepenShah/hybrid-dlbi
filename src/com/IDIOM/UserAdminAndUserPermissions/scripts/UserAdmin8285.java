/********************************************************************
Test Case Name: *Super User Admin - Super User Admin Header - Verify Super User Admin Header - Mouse hover Action Result on help icon
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8285.aspx
Objective:This test case is meant for checking Mouse hover Action Result on help icon in Header
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 1 December 2015
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;



import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;

import common.BaseClass;

public class UserAdmin8285 extends BaseClass {
		
	@Test
	public void test_UserAdmin8285() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		String HelpContent=property.getProperty("HelpContent_UserAdminPage");
		
	//****************Test step starts here************************************************
		//Step1: Load the URL and log in with valid Super User Admin Credentials
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		Thread.sleep(3000);
		
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    //Step 2:Click on menu icon at the top and click on Admin Access
	    ClientList_Page cl= new ClientList_Page(driver);
	    Thread.sleep(5000);
	    
	    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
	    
    	
    	cl.func_PerformAction("AdminAccess");
	    CustomReporter.log("The user has clicked on 'Admin Access'");	    
	    		    
	 
	    Thread.sleep(3000);
	    		    
	    if(up.func_ElementExist("Users List")){		    	
	    	CustomReporter.log(" User Admin page Got landed.The user listing is displayed");
	    	
	    //Step 3: Mouse hover on help icon
	    	if(up.func_Verify_HelpContent(HelpContent)){
	    		CustomReporter.log(" The help box is proper in User Admin PAge");
	    	}
	    	else{
	    		CustomReporter.errorLog("The Help box is NOT proper in User Admin Page");
	    		BaseClass.testCaseStatus=false;
	    	}
	    }
    	else{
		    CustomReporter.errorLog("The user listing is Not displayed");
	    	BaseClass.testCaseStatus=false;
		 }
		    
		//Step 4: Log out from the website
		 up.func_ClickElement("logout");
		 CustomReporter.log("The user has been logged out");
		    
		
		//****************Test step ends here************************************************
		if(BaseClass.testCaseStatus == false){
				CustomReporter.errorLog("Test case has failed");
				Assert.assertTrue(false);
		}	
		}
		}