/********************************************************************
Test Case Name: *User Admin_Search and Filtering_Bulk Client Assignment Menu_4.2.2(2). Verify drop down menu with a list of all clients in alphabetical order
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8255.aspx
Objective:Verify drop down menu with a list of all clients in alphabetical order
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 26 November 2015
Modified By:Sunil Nair
Date: 16 May 2016
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

public class UserAdmin8255 extends BaseClass{

	@Test	
	public void verifyclientsort() throws Exception {
		
	//****************Variables declaration and Initialization****************************
		try{
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
		rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        Thread.sleep(7000);
				
		//Step 3 - Click on "Admin Access" link from header sandwich icon 
        //modified by Sunil 10 May 2016
		PageHeader ph=new PageHeader(driver);
		UserAdmin_UserPermissions_Page up = new UserAdmin_UserPermissions_Page(driver);	
		ph.performAction("adminaccess");	    	
	    CustomReporter.log("The user has clicked on 'Admin Access'");	
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
		//Step 4 - Select more than one users
		
		up.func_selectUsers(3);
		CustomReporter.log("Selected Users");
		
		//Step 5 - Click on Bulk Assignment menu button from left top of user admin listing
		CustomReporter.log("Click on Bulk Assignment menu button from left top of user admin listing");
		if (up.func_ElementExist("Bulk Client Icon")){
			up.func_ClickElement("Bulk Icon Click");
			rm.waitTime(5000);
			if(up.func_SortClients("Clients In Bulk Assignment Call Out")){
				CustomReporter.log("The Clients are sorted alphabetically");
			}else{
				CustomReporter.errorLog("The Clients are not sorted alphabetically");
				BaseClass.testCaseStatus=false;
			}
			
		}
		rm.waitTime(5000);
				
		//Step 6 - Click on log out link--modified by Sunil 10 May 2016
		}catch(Exception e){
			e.printStackTrace();
		      BaseClass.testCaseStatus = false;
		      e.printStackTrace(System.out);
		      CustomReporter.errorLog("Some exception is generated, " + e.getMessage()
		          + ". Please check the log for more details");
		      rm.captureScreenShot("Clientlistsort", "fail");
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
		
		if(BaseClass.testCaseStatus==false){
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
		}
	}
	
}
