/********************************************************************
Test Case Name: *962_Verify Changing App Users As ClientAdmin
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8522.aspx
Objective: Verify that the app users are getting changed to client admin on key icon.
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 27 November 2015
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

public class UserAdmin8522 extends BaseClass {
	
	@Test
	public void verifyclientadmin() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		
		
	//****************Test step starts here************************************************
		try{
		//Step1: Login with valid supr admin credential
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		//Thread.sleep(3000);
		ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    //Step 2:Click on Admin Access
	    ClientList_Page cl= new ClientList_Page(driver);
	    PageHeader ph=new PageHeader(driver);
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
	    ph.performAction("adminaccess");//added by Sunil 09 May 2016
	    CustomReporter.log("The user has clicked on 'Admin Access'");
	    	    
	    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
	    Thread.sleep(3000);
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
	    if(up.func_ElementExist("Users List")){
	    	
	    	CustomReporter.log("The user listing is displayed");	    		    	
	    	
	    //Step 3: Click on edit panel for an app user present	
	   //Step 4: Click on key icons for the clients and click on save
	    	 if(up.func_CheckClientAdminFunctionality(1, "Client Assignment And Save")){
	    		
	    		 CustomReporter.log("The user is getting changed to client admin with key icon on selecting key icon from Edit panel and save the same");
	    	 	}
	    	 else{
	    		 CustomReporter.errorLog("The user is NOT getiing changed to client admin with key icon on selecting key icon from Edit panel and save the same");
	    		 BaseClass.testCaseStatus= false;
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
		      rm.captureScreenShot("Clientadminkeyassignment", "fail");
		}
	    //Step 5: Log out from the website
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