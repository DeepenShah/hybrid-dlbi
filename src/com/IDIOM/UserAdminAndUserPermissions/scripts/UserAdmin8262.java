/********************************************************************
Test Case Name: User Admin_Search and Filtering_Bulk Client Assignment Menu_4.2.2(1). Verify bulk client button should appear when more than one user is selected in the list
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8262.aspx
Objective: Verify bulk client button should appear when more than one user is selected in the list
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 24 November 2015
Modified on:06 May 2016
Modified By:Sunil 
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

public class UserAdmin8262 extends BaseClass {
		
	@Test
	public void verifybulkclientbtnpresent() throws Exception{
	//****************Variables declaration and Initialization****************************
		try{	
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		
		
	//****************Test step starts here************************************************
		//Step1: Open URL 
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        
		//Step2: Login with valid credentials
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    PageHeader ph=new PageHeader(driver);
	    //Step 3:Click on User Admin from Left Top corner	  
	    //modified by Sunil May 06 2016
	    Thread.sleep(3000);
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
	    ph.performAction("adminaccess");
	    CustomReporter.log("clicked on admin access");
	    //cl.func_PerformAction("AdminAccess");
	    //CustomReporter.log("The user has clicked on 'Admin Access'");
	    
	    
	    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
	    Thread.sleep(3000);
	    
	    
	    if(up.func_ElementExist("Users List")){
	    	
	    	CustomReporter.log("The user listing is displayed");
	    	
	    	//Step 4:Select more than 1 user from user listing
	    	up.func_selectUsers(2);
	    	CustomReporter.log("2 users are selected in user admin page");
	    
	    	if(up.func_ElementExist("Bulk Client Icon")){
	    		CustomReporter.log("Bulk client icon is getting populated on selecting one or more users");
	    	
	    	}
	    	else {
	    		CustomReporter.errorLog("Bulk client icon is not getting populated on selecting one or more users");
	    		BaseClass.testCaseStatus=false;
	    	}
	    
	    }
	    else{
	    	CustomReporter.errorLog("The user listing is Not displayed");
    		BaseClass.testCaseStatus=false;
	    }
		}catch(Exception e){
			e.printStackTrace();
		      BaseClass.testCaseStatus = false;
		      e.printStackTrace(System.out);
		      CustomReporter.errorLog("Some exception is generated, " + e.getMessage()
		          + ". Please check the log for more details");
		      rm.captureScreenShot("Bulkassignment", "fail");
		}
	    //Step 5: Log out from the website
		finally {
		      // Step 6: Logout from application.
		      try {
		        pageHeader.performAction("logout");
		        CustomReporter.log("Logged out successfully");
		      } catch (Exception ee) {
		        ee.printStackTrace();
		      }
	    }
	//****************Test step ends here************************************************
			if(BaseClass.testCaseStatus == false){
					CustomReporter.errorLog("Test case has failed");
					Assert.assertTrue(false);
			}	
}
}
	   
	    
	    