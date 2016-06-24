/********************************************************************
	Test Case Name: *Client Assignment Admin_ScreenOverview
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8290.aspx
Objective:Verify the screen of Client assignment admin user. Verify that there is NO option for Adding user
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 27 November 2015
**********************************************************************/
package com.IDIOM.UserAdminAndUserPermissions.scripts;

import java.text.ParseException;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;

import common.BaseClass;


public class UserAdmin8290 extends BaseClass{
	
	@Test	
	public void verifyclientadminprivilege() throws Exception {
		
	//****************Variables declaration and Initialization****************************	
		String emailid = property.getProperty("ClientAdminEmail");
		String password = property.getProperty("ClientAdminPassword");
		
	//****************Test steps starts here************************************************
		
		//Step 1 - Open URL: http://172.19.50.32:8080/idiom/#/login
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		//Step 2 - Login with valid Client Admin user
		CustomReporter.log("Enter valid Client User Email id and Password");
		CustomReporter.log("Click on login to Idiom button");		
		ln.func_LoginToIDIOM(emailid, password);	
		CustomReporter.log("User should land in Client List page");
		rm.waitTime(3000);
				
		//Step 3 - Click on "Admin Access" link from header sandwich icon
		rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
		ClientList_Page CL = new ClientList_Page(driver);
		PageHeader ph=new PageHeader(driver);
		ph.performAction("adminaccess");//added by Sunil 09 May 2016
	    CustomReporter.log("The user has clicked on Admin Access");

		
		//Step 4 - Verify necessary components for Client Admin
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
		UserAdmin_UserPermissions_Page UA = new UserAdmin_UserPermissions_Page(driver);
		rm.waitTime(5000);
		
			
		//Step 5 - Check for 'Add a user Button" 
		boolean status =false;
		
		status=UA.func_VerifyPageComponents("Client Admin User");//modified by Sunil 09 May 2016
		if(status==false)
		{
			BaseClass.testCaseStatus=false;
		}
		
		
		//Step 6 - Click on log out link
		rm.waitTime(3000);
		ph.performAction("Logout");
		CustomReporter.log("The user has been logged out");
		
		//****************Test step ends here************************************************
		
		if(BaseClass.testCaseStatus==false){
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
		}
	}
	
}
