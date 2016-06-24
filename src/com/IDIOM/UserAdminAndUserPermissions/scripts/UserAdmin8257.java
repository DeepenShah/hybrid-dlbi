/********************************************************************
Test Case Name: *User Admin_Search and Filtering_Bulk Client Assignment Menu_4.2.2(3). Verify search bar in bulk assignment drop down
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8257.aspx
Objective: Verify search bar in bulk assignment drop down
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 30 November 2015
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

public class UserAdmin8257 extends BaseClass{
	
	@Test	
	public void verifybulkassignmentsearch() throws Exception {
		
	//****************Variables declaration and Initialization****************************	
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");
		String ClientName = property.getProperty("clientName");
		String Query=null;
		String connection=property.getProperty("dbinstance");
		String DBUserName=property.getProperty("dbuser");
		String DBPassword=property.getProperty("dbpass");
	//****************Test steps starts here************************************************
		
		//Step 1 - Open URL: http://172.19.50.32:8080/idiom/#/login
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		//Step 2 - Login with valid super Admin user
		CustomReporter.log("Enter valid email id and password");
		CustomReporter.log("Click on login to Idiom button");		
		ln.func_LoginToIDIOM(emailid, password);	
		CustomReporter.log("User should land in Client List page");
		
				
		//Step 3 - Click on "Admin Access" link from header sandwich icon
		PageHeader ph=new PageHeader(driver);
	    Thread.sleep(2000);
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");	    
	    ph.performAction("adminaccess");
		/*ClientList_Page CL = new ClientList_Page(driver);
		CustomReporter.log("Click on Admin Access menu option");
		CL.func_PerformAction("AdminAccess");
		rm.waitTime(15000);*/
		
		//Step 4 - Select more than one users
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
		UserAdmin_UserPermissions_Page UA = new UserAdmin_UserPermissions_Page(driver);
		//rm.waitTime(5000);
		CustomReporter.log("Select Users");
		UA.func_selectUsers(3);
		
		//Step 5 - Click on Bulk Assignment menu button from left top of user admin listing
		CustomReporter.log("Click on Bulk Assignment menu button from left top of user admin listing");
		if (UA.func_ElementExist("Bulk Client Icon")){
			UA.func_ClickElement("Bulk Icon Click");
			//rm.waitTime(5000);
			// Query="Select name from Idiom.Client where Status = 'ACTIVE'";
			/* if(UA.func_VerifyApplicationDataWithDB("Check Clients from DB",Query,connection,DBUserName,DBPassword))
				
			//if (UA.func_CheckClientsFromDB())
			{
				//Stept 6 - Search for any client name say: Victoria from "Search Bar" 
				CustomReporter.log("Clients from the Database and on Client Assignment Call Out match");
				rm.waitTime(5000);*/
				UA.func_TypeSearchCriteria(ClientName.substring(0, 2),"Client");
				//UA.func_TypeSearchCriteria("ba");
				rm.waitTime(7000);
				if (UA.func_SearchClientsInClientAssignmentCallOut(ClientName.substring(0, 2)))
				//if (UA.func_SearchClientsInClientAssignmentCallOut("ba"))
				{
					CustomReporter.log("Searching Clients in Client Assignment Call Out functions properly");
				}
				else
				{
					CustomReporter.errorLog("Searching Clients in Client Assignment Call Out does not function properly");
					BaseClass.testCaseStatus=false;
				}
				
			}
			else
			{
				CustomReporter.errorLog("Clients from the Database and on Client Assignment Call Out do not match");
				BaseClass.testCaseStatus=false;
			}
			
		//}
		
				
		//Step 7 - Click on log out link
		Thread.sleep(5000);
		ph.performAction("Logout");
	    CustomReporter.log("The user has been logged out");
		
		//****************Test step ends here************************************************
		
		if(BaseClass.testCaseStatus==false){
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
		}
	}
}
