/********************************************************************
Test Case Name:**1191_Verify client appears after assigning of only child client
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8537.aspx
*1191_Verify client appears after assigning of only child client
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 23 December 2015
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;



import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.reports.CustomReporter;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;

import common.BaseClass;

public class UserAdmin8537 extends BaseClass {
	
	@Test
	public void clientlistappearance() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		String emailID1=property.getProperty("GeneralUserEmail");
		String password1=property.getProperty("GeneralUserPassword");
	//****************Test step starts here************************************************
		
		//Step1: Open URL 
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		Thread.sleep(3000);
		
		
		//Step 2:Login with super/client admin creds
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    //Step 3:Hover on menu and click on admin access
	    PageHeader ph=new PageHeader(driver);
	    Thread.sleep(3000);
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
	    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
	    ph.performAction("adminaccess");
	    ClientList_Page cl= new ClientList_Page(driver);
	    //Thread.sleep(10000);
	    
    	
    	/*cl.func_PerformAction("AdminAccess");*/
	    CustomReporter.log("The user has clicked on 'Admin Access'");	    
	    		    
	 
	    Thread.sleep(3000);
	    		    
	    if(up.func_ElementExist("Users List")){		    	
	    	CustomReporter.log(" User Admin page Got landed.The user listing is displayed");
	    	
	    //Step 4:Search for any user say A, click on edit button
	    //	up.func_ClearText("SearchTextBox");
	    	Thread.sleep(10000);
	    	up.func_TypeSearchCriteria(emailID1, "User");
	    	CustomReporter.log("The user has searched for "+emailID1+ " in seach text box");
	    	
	    	Thread.sleep(3000);
	    	
	    	if(up.func_ElementExist("Users List")){		
	    		CustomReporter.log("The user with id "+emailID1+" is existing");
	    //Step5:Assign only child/sub client to user
	    //Note: Do not assign parent client
	    		up.func_AssignClients(0,1, "Specific Number of Clients", null);
	    		
	    		CustomReporter.log("The user has assigned one client for the mentioned new user");
	    		
	    		//Get the assigned client name
	    		String [] clients1=up.func_GetAssignedClients();
	    		
	    //Step6:Logout and login with above user, say A
	    		
	    		CustomReporter.log("Click on Logout link");
	    		ph.performAction("logout");
	    		
	    		CustomReporter.log("The admin has logged out");
	    		
	    		ln.func_LoginToIDIOM(emailID1, password1);	
	    	    CustomReporter.log("The user "+emailID1+" is logged in successfully");
	    	    
	    	    Thread.sleep(3000);
	    	    
	    	    String [] client2=cl.func_getClientsPresent();
	    	    
	    	    if (Arrays.equals(clients1, client2)){
	    	    	CustomReporter.log("The clients assigned are present for the user "+emailID1);
	    	    }
	    	    else{
	    	    	CustomReporter.errorLog("The client assigned are not present for the user "+emailID1);
	    	    	BaseClass.testCaseStatus=false;
	    	    }
	    	    
	    	    CustomReporter.log("The user "+emailID1+" has been logged out");
	    	    
	    	}
	    	else{
	    		CustomReporter.errorLog("The user with id "+emailID1+" is NOT existing");
	    		BaseClass.testCaseStatus=false;
	    	}
	    }
	    else{
	    	CustomReporter.errorLog("The user list is absent");
    		BaseClass.testCaseStatus=false;
    		ph.performAction("logout");
    		CustomReporter.log("The user "+emailid+" is logged out");
	    }
	    	    
	   //Step 7:Verify client assigned in step 5 should appear in client list page
	    	    
	    	  //****************Test step ends here************************************************
	    		
	    		if(BaseClass.testCaseStatus==false){
	    			   CustomReporter.errorLog("Test case has failed");
	    			   Assert.assertTrue(false);   
	    		}
	    	}
	    	
	    	
	    }
	    		
	    		
	    	
