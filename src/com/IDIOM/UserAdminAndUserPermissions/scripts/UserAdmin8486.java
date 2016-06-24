/********************************************************************
Test Case Name: *1006_Super_User_Admin_Verify_Key_Icon_Removal_After_Revoking_Rights
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8486.aspx
Objective: Verify whether key icon is getting removed in User List Grid after rights are revoked
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 22 December 2015
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

public class UserAdmin8486 extends BaseClass {
	
	@Test
	public void verifykeyiconremoval() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		
		
	//****************Test step starts here************************************************
		try{
			//Step 1:Open URL 
			CustomReporter.log("Launched Browser and Enter URL");
			Login_Page ln = new Login_Page(driver);
			
			Thread.sleep(3000);
			
			//Step2:Log in as a Super Admin User User
	        ln.func_LoginToIDIOM(emailid, password);	
		    CustomReporter.log("Logged in successfully");
		    
		    
		    //Step 3:Click on Admin Access from Menu
		    ClientList_Page cl= new ClientList_Page(driver);
		    PageHeader ph=new PageHeader(driver);
		    rm.webElementSync("pageload");
	        rm.webElementSync("jqueryload");
	        rm.webElementSync(pageHeader.personMenu, "visibility");	    
		    ph.performAction("adminaccess");
		    CustomReporter.log("The user has clicked on 'Admin Access'");
		    
		    
		    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
		    rm.webElementSync("pageload");
	        rm.webElementSync("jqueryload");
		    if(up.func_ElementExist("Users List")){
		    	
		    	CustomReporter.log("The user listing is displayed");
		    	
		    //Step4:Search user as 'lorem' (this is a Client Admin user)
		    //Step5:Edit it and revoke all rights from this user and save
		    //Step6:Now check the user list grid
		    	if(up.func_CheckClientAdminFunctionality(0,"Client Icon Removal")){
		    		CustomReporter.log("Client rights are revoked succesfully and key admin icon is not present with the user");
		    	}
		    	else{
		    		CustomReporter.errorLog("The client rights are not successfully revoked");
		    		BaseClass.testCaseStatus=false;
		    	}
		    }
		    else{
		    	CustomReporter.errorLog("The Users are not present");
	    		BaseClass.testCaseStatus=true;
		    }
		  //Step 7: Log out from the website
		    ph.performAction("logout");
		    CustomReporter.log("The user has been logged out");
		}
		catch (Exception e){
			CustomReporter.errorLog("We are getting un expected error . Please find error details : "+e);
			BaseClass.testCaseStatus=false;
		}
	//****************Test step ends here************************************************
		if(BaseClass.testCaseStatus == false){
				CustomReporter.errorLog("Test case has failed");
				Assert.assertTrue(false);
		}	


}
	
}
	