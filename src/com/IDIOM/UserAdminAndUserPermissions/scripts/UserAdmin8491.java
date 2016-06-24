/********************************************************************
Test Case Name: *1010_Super_User_Admin_Verify_Count_For_The_First_Time_After_Assigning_Clients
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8491.aspx
Objective: Objective: Verify Count For The First Time After Assigning Clients
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

public class UserAdmin8491 extends BaseClass {
	
	@Test
	public void verifyclientcount() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		
		
	//****************Test step starts here************************************************
		//Step1: Open URL
		try{
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		Thread.sleep(3000);
		//Step2: Log in as a Super Admin User User
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    //Step 3:Click on Admin Access from Menu
	    ClientList_Page cl= new ClientList_Page(driver);
	    PageHeader ph=new PageHeader(driver);
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
	    ph.performAction("adminaccess");
	    CustomReporter.log("clicked on admin access");
	    
	    
	    
	    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
	    Thread.sleep(3000);
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        Thread.sleep(7000);
	    if(up.func_ElementExist("Users List")){
	    	
	    	CustomReporter.log("The user listing is displayed");
	    
	    //Step4:Edit any user and assign one more client and save
	    	
	    	
	    	if(up.func_AssignClients(0,2,"Specific Number of Clients", null))
	    	{
	    		CustomReporter.log("The user has clicked on edit button and selected 2 clients and saved the same");
	    	
	    		CustomReporter.log(" Count in User list grid is updated correctly");
	    	}
	    	else{
	    		CustomReporter.log("The user has clicked on edit button and selected 2 clients and saved the same");
		    	
	    		CustomReporter.errorLog(" Count in User list grid is not updated correctly");
	    		
	    		BaseClass.testCaseStatus=false;
	    	}
	    	
	    	Thread.sleep(3000);
	    //Step 5:Now once again edit the user and just simply save the button
	    	
	    	if(up.func_AssignClients(0,2,"Only Save", null)){
	    		CustomReporter.log("The user has clicked on edit button and  saved the same without selecting any clients");
		    	
	    		CustomReporter.log(" Count in User list grid is not changed since we didnt select any clients");
	    	}
	    	
	    	else{
	    		CustomReporter.log("The user has clicked on edit button and  saved the same without selecting any clients");
		    	
	    		CustomReporter.errorLog(" Count in User list grid is  changed eventhough we didnt select any clients");
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
		      rm.captureScreenShot("clientcount", "fail");
		}
	    
		finally {
		      try {
		        pageHeader.performAction("logout");
		        CustomReporter.log("Logged out successfully");
		      } catch (Exception ee) {
		        ee.printStackTrace();
		      }
	    }

	    
	Thread.sleep(5000);
	//****************Test step ends here************************************************
			if(BaseClass.testCaseStatus == false){
					CustomReporter.errorLog("Test case has failed");
					Assert.assertTrue(false);
			}	
}
}
	    	
	  
	    	
	    	
	    	
 	
	    	
	    	