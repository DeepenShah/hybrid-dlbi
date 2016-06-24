/********************************************************************
Test Case Name: *Super User Admin-Date Sort and Filter_Date
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8267.aspx
Objective:: This test case checks the sort order by Date functionality in User Admin page
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 1 December 2015
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

public class UserAdmin8267 extends BaseClass {
	
		
	@Test
	public void verifysortorder() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		String sortItems=property.getProperty("UserAdmin_Sort");
	//****************Test step starts here************************************************
		try{
		//Step1: Load the URL and log in with valid Super User Admin Credentials
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		Thread.sleep(3000);
		
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    //Step 2:Click on menu icon at the top and click on Admin Access
	    /*ClientList_Page cl= new ClientList_Page(driver);
	    Thread.sleep(5000);*/
	    
	    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
	    
    	
    	/*cl.func_PerformAction("AdminAccess");
	    CustomReporter.log("The user has clicked on 'Admin Access'");*/
	    PageHeader ph=new PageHeader(driver);	    
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
	    
        ph.performAction("adminaccess");	    	
	    CustomReporter.log("The user has clicked on 'Admin Access'");
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
	    		    
	 
	    Thread.sleep(3000);
	    		    
	    if(up.func_ElementExist("Users List")){		    	
	    	CustomReporter.log(" User Admin page Got landed.The user listing is displayed");
	    if(up.func_sortelement("Name Ascending")){
	    	CustomReporter.log("The name is sorted in ascending order");
	    }
	    else{
	    	CustomReporter.errorLog("The name is not sorted in ascending order");
	    	BaseClass.testCaseStatus =false;
	    }
	    up.func_ClickElement("Name");
	    Thread.sleep(3000);
	    if(up.func_sortelement("Name Descending")){
	    	CustomReporter.log("The name is sorted in descending order");
	    }
	    else{
	    	CustomReporter.errorLog("The name is not sorted in descending order");
	    	BaseClass.testCaseStatus =false;
	    }
	    Thread.sleep(3000);
	    up.func_ClickElement("Created Date");
	    Thread.sleep(3000);
	    if(up.func_sortelement("Date Ascending")){
	    	CustomReporter.log("The date is sorted in ascending order");
	    }
	    else{
	    	CustomReporter.errorLog("Created date is not sorted in ascending order");
	    	BaseClass.testCaseStatus =false;
	    }
	    Thread.sleep(3000);
	    up.func_ClickElement("Created Date");
	    Thread.sleep(3000);
	    if(up.func_sortelement("Date Descending")){
	    	CustomReporter.log("The date is sorted in descending order");
	    }
	    else{
	    	CustomReporter.errorLog("Created date is not sorted in descending order");
	    	BaseClass.testCaseStatus =false;
	    }
	    up.func_ClickElement("Access");
	    CustomReporter.log("Clicked on 'Access'");
	    CustomReporter.log("Results are displayed based on assigned roles");
	    Thread.sleep(3000);
	    up.func_ClickElement("Email");
	    CustomReporter.log("Clicked on 'Email'");
	    if(up.func_sortelement("Email Ascending")){
	    	CustomReporter.log("Email is sorted in ascending order");
	    }
	    else{
	    	CustomReporter.errorLog("Email is not sorted in ascending order");
	    	BaseClass.testCaseStatus =false;
	    }
	    Thread.sleep(3000);
	    up.func_ClickElement("Clients");
	    CustomReporter.log("Clicked on 'Clients'");
	    if(up.func_sortelement("Clients Ascending")){
	    	CustomReporter.log("Client is sorted in ascending order");
	    }
	    else{
	    	CustomReporter.errorLog("Client is not sorted in ascending order");
	    	BaseClass.testCaseStatus =false;
	    }
	    Thread.sleep(3000);
	    
	   /* if(up.func_sortelement("Name Ascending")){
	    	CustomReporter.log("The name is sorted in ascending order");
	    }
	    else{
	    	CustomReporter.errorLog("The name is not sorted in ascending order");
	    	BaseClass.testCaseStatus =false;
	    }*/
	    
    	Thread.sleep(3000);
	    ph.performAction("logout");
		 CustomReporter.log("The user has been logged out");
			    
		}
		}catch (Exception e){
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