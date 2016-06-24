/********************************************************************
Test Case Name: Super User Admin - Super User Admin Header - Verify Super User Admin Header Idiom Logo Click
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8256.aspx
Objective:This test case is meant for checking Idiom Logo Click in Header
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 30 November 2015
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

public class UserAdmin8256 extends BaseClass {
	
	@Test
	public void verifyidiomlogoclick() throws Exception{
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
	    //modified by Sunil 10 May 2016
	    PageHeader ph=new PageHeader(driver);		
		rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
	    ph.performAction("adminaccess");
	    CustomReporter.log("clicked on admin access");
	    /*ClientList_Page cl= new ClientList_Page(driver);
	    Thread.sleep(3000);
	    cl.func_PerformAction("AdminAccess");
	    CustomReporter.log("The user has clicked on 'Admin Access'");*/
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
	    
	    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
	    Thread.sleep(3000);
	    
	    
	    if(up.func_ElementExist("Users List")){	    	
	    	CustomReporter.log("The user listing is displayed");
	    	

	    //Step3 :Verify Super User Admin Header - Idiom Logo Click
	    	ph.performAction("idiomlogo");//added by Sunil 10 May 2016
	    	//up.func_ClickElement("Idiom Logo");
	    	CustomReporter.log("The user has clicked on 'Idiom Logo ' from user admin page");
	    	Thread.sleep(5000);
	    	rm.webElementSync("pageload");
	        rm.webElementSync("jqueryload");
	        //rm.webElementSync(pageHeader.personMenu, "visibility");
	        ClientList_Page cl = new ClientList_Page(driver);
	        rm.webElementSync("pageload");
	        rm.webElementSync("jqueryload");
	    	if(cl.func_ClientListPageElementExist("Clients")){
	    		CustomReporter.log("The user has landed in Client List page");
	    	}
	    	else {
	    		CustomReporter.errorLog("The user has not landed in Client list page");
	    		BaseClass.testCaseStatus=false;
	    	}
	    }
	    else{
	    	CustomReporter.errorLog("The user listing is NOT displayed");
	    	BaseClass.testCaseStatus=false;
	    }
	    
	    //Step 4: Log out from the website
	    ph.performAction("Logout");
	    CustomReporter.log("The user has been logged out");
	    /*cl.func_PerformAction("Logout");
	    CustomReporter.log("The user has been logged out");*/
	    
		
		//****************Test step ends here************************************************
		if(BaseClass.testCaseStatus == false){
				CustomReporter.errorLog("Test case has failed");
				Assert.assertTrue(false);
		}	
	}
	}