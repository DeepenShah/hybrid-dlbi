/********************************************************************
Test Case Name:  Super User Admin_Search and Filtering_Client Search and Assignment List_Verify client search and assignment for any user 
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8258.aspx
Objective:Verify client search and assignment for any user
Module: User Admin Functionality
Created By: Sunil Nair
Date: 24 May 2016
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

public class UserAdmin8258 extends BaseClass {

	@Test	
	public void verifyassignedclientlist() throws Exception {
		
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
				
		//Step 2 - verify adminaccess opetion is present in the list
		CustomReporter.log("Check whether Admin Access is available in Call Out/Menu");
		PageHeader ph=new PageHeader(driver);
	    
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
	    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
	    String emailID1="vizualizetest@gmail.com";
	    Thread.sleep(5000);
	    ph.performAction("adminaccesspresent");
	    Thread.sleep(7000);
	    if (ph.isVisible("adminaccess")){
	    	CustomReporter.log("Admin access option is present in the list");	  
	    //Step 3:click on adminaccess option
	    	ph.performAction("adminaccess");	    	
		    CustomReporter.log("The user has clicked on 'Admin Access'");	    
		    
		    rm.webElementSync("pageload");
	        rm.webElementSync("jqueryload");
	        Thread.sleep(10000);
	   //Step 4:Click on edit for any user..		        
	    	up.func_ClickElement("FirstEditButton");
	    	CustomReporter.log("The user has clicked on 'Edit'");
	    	Thread.sleep(3000);
	    	if(up.func_ElementExist("EDit Panel for First User")){
	    		CustomReporter.log("Edit panel exists for the searched user");
	    	}
	    	else{
	    		CustomReporter.errorLog("Edit panel doesn't exist for the searched user");
	    		BaseClass.testCaseStatus = false;
	    	}
	    	Thread.sleep(5000);
	    	if(up.func_ElementExist("ClientsInEditPAnel")){
	    		CustomReporter.log("The client edit panel appears for the selected user");
	    	}
	    	else{
	    		CustomReporter.errorLog("The client edit panel is not available for the selected user");
	    		BaseClass.testCaseStatus = false;
	    	}
	    	if(up.func_ElementExist("Search clients")){
	    		CustomReporter.log("The search panel appears for the selected user");
	    	}
	    	else{
	    		CustomReporter.errorLog("The client search panel is not available for the selected user");
	    		BaseClass.testCaseStatus = false;
	    	}
	    	if(up.func_SortClients("Clients in Edit user call out")){
	    		CustomReporter.log("The client list is in sorted order");
	    	}
	    	else{
	    		CustomReporter.errorLog("The client list is not in sorted order");
	    		BaseClass.testCaseStatus=false;
	    	}
	    	if(up.func_ElementExist("Scroll box exist")){
	    		CustomReporter.log("Scroll box exists to view client assignment");
	    	}
	    	else{
	    		CustomReporter.errorLog("Scroll box doesn't exist");
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
