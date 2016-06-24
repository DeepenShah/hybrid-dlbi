/********************************************************************
Test Case Name: **Super User Admin - User Admin Screen Overview - Verify Components/Sections
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8252.aspx
Objective:This test case is for verifying whether all components of User Admin Screen are present on this page.
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 26 November 2015
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

public class UserAdmin8252 extends BaseClass {
		
	@Test
	public void verifyadminscreencomponents() throws Exception{
	//****************Variables declaration and Initialization****************************
		try{	
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		
		
	//****************Test step starts here************************************************
		//Step1: Load the URL and log in with valid Super User Admin Credentials
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);			
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    //Step 2:Click on menu icon at the top and click on Admin Access

	    PageHeader ph=new PageHeader(driver);	    
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
	    
        ph.performAction("adminaccess");	    	
	    CustomReporter.log("The user has clicked on 'Admin Access'");
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
	    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
	    Thread.sleep(3000);
	    
	    if(up.func_ElementExist("Users List")){
	    	
	    	CustomReporter.log("The user listing is displayed");
	    	
	    //Step 3:Verify Components of the page
	    	if(up.func_VerifyPageComponents("Super Admin User")){
	    		if(up.func_ElementExist("Search Icon")){
	    			CustomReporter.log("search icon found");
	    		}
	    		else{
	    			CustomReporter.errorLog("search icon not found");
	    		}
	    		if(up.func_ElementExist("Search TextBox")){
	    			CustomReporter.log("search textbox found");
	    		}
	    		else{
	    			CustomReporter.errorLog("search textbox not found");
	    		}
	    		if(up.func_ElementExist("ListEditButtons")){
	    			CustomReporter.log("Edit buttons are visible");
	    		}
	    		else{
	    			CustomReporter.errorLog("Edit buttons are not visible");
	    		}
	    		 CustomReporter.log("All components are present in user admin page");
	    	}
	    	else{
	    		CustomReporter.errorLog("All components are not present in User Admin page");
	    		BaseClass.testCaseStatus =false;
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
		      rm.captureScreenShot("adminscreen", "fail");
		}
	    //Step 4: Log out from the website
		finally {
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