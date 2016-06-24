/********************************************************************
Test Case Name:**1210_User_Admin_Admin_Header_Verify_ReturnToIdiomLink_Click
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8427.aspx
Objective: Verify the click of Return to Idiom Link click 
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 31 December 2015
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;



import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.UserAdminMyAccount_Page;
import com.reports.CustomReporter;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;

import common.BaseClass;

public class UserAdmin8427 extends BaseClass {
		
	@Test
	public void test_UserAdmin8427() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		
	//****************Test step starts here************************************************
		
		//Step1:Open URL
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		Thread.sleep(3000);
		
		
		//Step 2:Login as a Super User Admin
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    //Step 3:Click on "My Account" from header
	    Thread.sleep(3000);
	    ClientList_Page cl= new ClientList_Page(driver);
	    cl.func_PerformAction("My Account");
	    CustomReporter.log("The user has clicked on My Account link from Menu list");
	    Thread.sleep(3000);
	    
	    
	    //Step 4:Now click on "Admin Access" from header
	    UserAdminMyAccount_Page ma=new UserAdminMyAccount_Page(driver);
	    if(ma.func_elementExist("My Account Page Exists")){
	    	CustomReporter.log("The my account page is getting landed");
	    	
	    	ma.func_click("Admin Access");
	    	CustomReporter.log("The user has clicked on 'Admin Access' from My account page");
	    	Thread.sleep(3000);
	    	 
	    	UserAdmin_UserPermissions_Page up= new UserAdmin_UserPermissions_Page(driver);
	    	if(up.func_ElementExist("User Admin Page Exists")){
	    		CustomReporter.log("The user admin page is getting opened");
	    		
	    //Step 5:Click on "Return to IDIOM" link from header
	    		up.func_ClickElement("Return To Idiom");
	    		Thread.sleep(3000);
	    		CustomReporter.log("The user has clicked on 'Return to Idiom' link");
	    		
	    		if(ma.func_elementExist("My Account Page Exists")){
	    	    	CustomReporter.log("The my account page is getting landed on clicking on 'Returhn to Idiom' from User Admin page");
	    //Step 6:Log out from the application
	    	    	ma.func_click("headericon");
	    	    	ma.func_click("logout");
	    	    	
	    	    	CustomReporter.log("The user has been logged out");
	    	    	
	    		}
	    		else{
	    			CustomReporter.errorLog("The my account page is NOT getting landed on clicking on 'Return to Idiom' from User Admin page");
	    			BaseClass.testCaseStatus=false;	 
	    		}
	    	}
	    	else{
	    		CustomReporter.errorLog("The user admin page is not getting landed");
		    	BaseClass.testCaseStatus=false;	    	
	    		
	    	}
	    	
	    }
	    else{
	    	
	    	CustomReporter.errorLog("The my Account page is not getting landed");
	    	BaseClass.testCaseStatus=false;
	    }

		//****************Test step ends here************************************************
				if(BaseClass.testCaseStatus == false){
						CustomReporter.errorLog("Test case has failed");
						Assert.assertTrue(false);
				}	
	}
	}