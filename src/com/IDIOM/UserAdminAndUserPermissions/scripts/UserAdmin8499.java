/********************************************************************
Test Case Name: *1148: QA>User Admin:With blank First name and last name, user row is getting saved
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8499.aspx
Objective:Verify that edit profile should not allow to update with blank Name 
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 24 November 2015
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class UserAdmin8499 extends BaseClass{

	
	@Test	
	public void verifyedituserblankname() throws Exception {
		
	//****************Variables declaration and Initialization****************************	
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");
		
	//****************Test steps starts here************************************************
		
		//Step 1 - Open URL: http://172.19.50.32:8080/idiom/#/login
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		//Step 2 - Login with valid super Admin user
		CustomReporter.log("Enter valid email id and password");
		CustomReporter.log("Click on login to Idiom button");		
		ln.func_LoginToIDIOM(emailid, password);	
		CustomReporter.log("User should land in Client List page");
		rm.waitTime(3000);
				
		//Step 3 - Click on "Admin Access" link from header sandwich icon
		ClientList_Page CL = new ClientList_Page(driver);
		PageHeader ph=new PageHeader(driver);
		/*CustomReporter.log("Click on Admin Access menu option");
		CL.func_PerformAction("AdminAccess");
		rm.waitTime(15000);*/
		/*-----added by Sunil 06 May 2016------------*/
		rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
	    ph.performAction("adminaccess");
	    CustomReporter.log("clicked on admin access");
	    /*-----added by Sunil 06 May 2016------------*/
		
	    //rm.wait(10000);
		//Step 4 - Select any active user and click on Edit link to change that user information
		UserAdmin_UserPermissions_Page UA = new UserAdmin_UserPermissions_Page(driver);
		rm.webElementSync("idiomspinningcomplete");
		rm.webElementSync("pageload");
		Thread.sleep(5000);
		if(UA.func_ElementExist("Users List")){
	    	
		    CustomReporter.log("The user listing is displayed");}
		    	
		    UA.func_ClickElement ("FirstEditButton");
		    CustomReporter.log("Click on Edit button of the selected user");
		
		//Step5 - Remove "Name" to update user information with blank name and click on "Save" button
		//rm.waitTime(10000);
		Thread.sleep(10000);
		CustomReporter.log("Click on Name Textbox");
		UA.func_ClickElement ("FirstNameTextBox");
		//rm.waitTime(7000);
		Thread.sleep(7000);
		CustomReporter.log("Clear the text of the Name Textbox");
		UA.func_ClearText("FirstNameTextBox");
		//rm.waitTime(3000);
		Thread.sleep(3000);
		UA.func_ClickElement ("FirstSaveButton");
		//rm.waitTime(3000);
		Thread.sleep(3000);
		if(UA.func_ElementExist ("Validation Message For BlankName"))
		{
			BaseClass.testCaseStatus=true;
		}
		else
		{
			CustomReporter.errorLog("No error message is conveyed to the user and it is allowing blank name to be saved");
			BaseClass.testCaseStatus=false;
		}
		
		
		//Step 6 - Click on log out link
		rm.waitTime(3000);
		
		ph.performAction("Logout");//added by Sunil 06 May 2016		
		CustomReporter.log("The user has been logged out");
		
		//****************Test step ends here************************************************
		
		if(BaseClass.testCaseStatus==false){
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
		}
	}
	
	
}
