/********************************************************************
Test Case Name: *Super User Admin - Accessing the Admin - Verify Users can access the user admin via the page header.
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8253.aspx
Objective:This test case is meant to verify whether Super Admin User has the option Access Admin available in menu and clicking on the same redirects the user to User Admin Module/page successfully 
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 26 November 2015
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

public class UserAdmin8253 extends BaseClass {

	@Test	
	public void verifyadminaccess() throws Exception {
		
	//****************Variables declaration and Initialization****************************
		try{
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");
		
	//****************Test steps starts here************************************************
		
		//Step 1 - Open URL: http://172.19.50.32:8080/idiom/#/login and log in with valid Super User Admin Credentials
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		CustomReporter.log("Enter valid Super User email id and password");
		CustomReporter.log("Click on login to Idiom button");		
		ln.func_LoginToIDIOM(emailid, password);	
		CustomReporter.log("User should land in Client List page");
		rm.waitTime(3000);
				
		//Step 2 - Click on menu icon at the top
		//Step 3 - Check whether Admin Access is available in Call Out/Menu
		//ClientList_Page CL = new ClientList_Page(driver);
		CustomReporter.log("Check whether Admin Access is available in Call Out/Menu");
		PageHeader ph=new PageHeader(driver);
	    Thread.sleep(5000);
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
	    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
	    
	    ph.performAction("adminaccesspresent");
	    Thread.sleep(5000);
	    if (ph.isVisible("adminaccess")){
	    	CustomReporter.log("'Admin Access' link is present in Client List page");	   	    
	    	ph.performAction("adminaccess");	    	
		    CustomReporter.log("The user has clicked on 'Admin Access'");	    
		    Thread.sleep(3000);
		    rm.webElementSync("pageload");
	        rm.webElementSync("jqueryload");
	        up.isVisible("createuserbutton");
		    if(up.func_ElementExist("Users List")){		    	
		    	CustomReporter.log("The user listing is displayed");
		    }
		    else{
		    	CustomReporter.errorLog("The user listing is Not displayed");
	    		BaseClass.testCaseStatus=false;
		    	}
		    if (up.func_ElementExist("Create User Button"))
			{
				CustomReporter.log("Admin page for Super User Admin is correctly displayed");
				BaseClass.testCaseStatus=true;
			}
		    else{
		    	CustomReporter.errorLog("There are some problems either in Super Admin User page or some other details are displayed");
				BaseClass.testCaseStatus=false;
		    }
			
			
		}
			else{
			CustomReporter.errorLog("Admin Access Menu option not found");
			BaseClass.testCaseStatus=false;
			}
		}catch(Exception e){
			e.printStackTrace();
		      BaseClass.testCaseStatus = false;
		      e.printStackTrace(System.out);
		      CustomReporter.errorLog("Some exception is generated, " + e.getMessage()
		          + ". Please check the log for more details");
		      rm.captureScreenShot("adminaccess", "fail");
		}
	    
		finally {
		      try {
		        pageHeader.performAction("logout");
		        CustomReporter.log("Logged out successfully");
		      } catch (Exception ee) {
		        ee.printStackTrace();
		      }
	    }
		
		//****************Test step ends here************************************************
		
		if(BaseClass.testCaseStatus==false){
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
		}
	}
	
}
