/********************************************************************
Test Case Name:*1211_Super Admin is able to provide client admin rights
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8527.aspx
Objective: Verify Super Admin is able to provide client admin rights
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 28 December 2015
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;


import org.testng.Assert;
import org.testng.annotations.Test;


import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.reports.CustomReporter;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;

import common.BaseClass;

public class UserAdmin8527 extends BaseClass {
	
	@Test
	public void verifyassignclientadminrights() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		
		
	//****************Test step starts here************************************************
		//Step1: Load the URL 
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		Thread.sleep(3000);
		
		//Step 2:	Login with valid super admin creds
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    //Step 3:Hover on menu icon and click on admin access
	    //ClientList_Page cl= new ClientList_Page(driver);
	    PageHeader ph=new PageHeader(driver);
	    Thread.sleep(3000);
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
	    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
	    ph.performAction("adminaccess");
	    //commented by Sunil 13 May 2016
	    //cl.func_PerformAction("AdminAccess");
	    CustomReporter.log("The user has clicked on 'Admin Access'");	    	    		    
	    if(up.func_ElementExist("Users List")){
	    	
	    	CustomReporter.log("The user listing is displayed");
	    	
	    	
	    //Step 4:Click on edit icon for any app user
	    //Step 5:Check key icon for any assigned clients or assign some clients and click on key icon
	    //Step 6:Click on save button
	    	if(up.func_CheckClientAdminFunctionality(1,"Client Assignment And Save")){
	    	 CustomReporter.log("The client assignment is completed properly");	
	    	}
	    	else{
	    		CustomReporter.errorLog("The client assignment is not proper");
	    		BaseClass.testCaseStatus=false;
	    	}
	    }
	    	 else{
	 		    CustomReporter.errorLog("The user listing is Not displayed");
	 	    	BaseClass.testCaseStatus=false;
	 		 }
	 		    
	 		//Step 4: Log out from the website
	    ph.performAction("Logout");
	    CustomReporter.log("The user has been logged out");
	 		    
	 		
	 		//****************Test step ends here************************************************
	 				if(BaseClass.testCaseStatus == false){
	 						CustomReporter.errorLog("Test case has failed");
	 						Assert.assertTrue(false);
	 				}	
	 	}
	 	}