/********************************************************************
Test Case Name: **Verifiy assign client is working as expected
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8898.aspx
Objective:This test case is for checking whether the client assignment functionality is working fine or not. 
Module: UserAdminAndUserPermissions
Created By:Sunil Nair
Date: 04 May 2016
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.reports.CustomReporter;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;

import common.BaseClass;

public class UserAdmin8898 extends BaseClass {
	
		
		 
	@Test
	public void verifyassignclient() throws Exception{
	//initialize the variables
			
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		
		
	//****************Test step starts here************************************************
		//Step 1 - Open URL: http://172.19.50.32:8080/idiom/#/login
				CustomReporter.log("Launch Browser and Enter URL");
				Login_Page ln = new Login_Page(driver);	
				
				//Step 2 - Login with super user admin creds
				CustomReporter.log("Enter valid email id and password");
				CustomReporter.log("Click on login to Idiom button");		
				ln.func_LoginToIDIOM(emailid, password);	
				CustomReporter.log("User should land in Client List page");
				Thread.sleep(5000);
				//rm.webElementSync("pageload");
		        //rm.webElementSync("jqueryload");
		        //rm.webElementSync(pageHeader.personMenu, "visibility");
						
				//Step 3 - Click on "Admin Access" link from header sandwich icon
				ClientList_Page CL = new ClientList_Page(driver);
				PageHeader ph=new PageHeader(driver);
				ph.performAction("adminaccess");
				
				//rm.webElementSync("pageload");
				//rm.webElementSync("jqueryload");
				Thread.sleep(10000);

				//Step 4 - Verify create user button is available in bottom right corner
				UserAdmin_UserPermissions_Page UA = new UserAdmin_UserPermissions_Page(driver);
				
				
				// Step - 5 - Click on create new user button
				// Step - 6 - Verify following is present in create user:- Name- Email Address- Create Button - Cancel Button
				// Step - 7 - Enter valid first, last name and email address
				// Step - 8 - Click on create button
				// Step - 9 - Verify the name present in Assign clients page
				String[] UsrDetails=UA.func_CreateUserDetails("Sunil");
				
				System.out.println(UsrDetails[0]+ " " + UsrDetails[1]);
				Thread.sleep(10000);
				UA.func_CreateUser(UsrDetails[0], UsrDetails[1], 2, "Assign and unassign clients");
				//Step 4:Search for a user and verify whether the user appears in the list..	
		        //UA.func_TypeSearchCriteria("vizualizetest@gmail.com", "User");
				CustomReporter.log("User has been created and one client assigned successfully");
				Thread.sleep(10000);
		        UA.func_TypeSearchCriteria(UsrDetails[1],"User");
		    	if(UA.func_ElementExist("Users List")){
		    		CustomReporter.log("The user '"+UsrDetails[1]+"' is existing");
		    		
		    //Step 5:Click on edit
		    		UA.func_ClickElement("FirstEditButton");
		    		CustomReporter.log("The user has clicked on 'Edit' for "+UsrDetails[1]);
		    		 Thread.sleep(3000);
		    	if(UA.func_ElementExist("EDit Panel for First User")){
		    		CustomReporter.log("Edit panel exists for the searched user");
		    	}
		    	else{
		    		CustomReporter.errorLog("Edit panel doesn't exist for the searched user");
		    		BaseClass.testCaseStatus = false;
		    	}
		    	if(UA.func_ElementExist("AssignedClients")){
		    		CustomReporter.log("Assigned client list is appearing for the selected user");
		    	}
		    	else{
		    		CustomReporter.errorLog("Assigned client list is not present for the selected user");
		    		BaseClass.testCaseStatus = false;
		    	}

		    	}	
   
		    	int clientcount=UA.func_GetAssignedClientscount();
		    	System.out.println("Client count is "+clientcount);
		    	if (clientcount==1){
		    		CustomReporter.log("Only one client is assigned to the user");
		    		}
		    	else{
		    		CustomReporter.errorLog("More than one client exists for the user");
		    		BaseClass.testCaseStatus = false;
		    	}
		Thread.sleep(10000);    
		ph.performAction("Logout");
		CustomReporter.log("User has been logged out");
		//****************Test step ends here************************************************
				if(BaseClass.testCaseStatus == false){
						CustomReporter.errorLog("Test case has failed");
						Assert.assertTrue(false);
				}	
	}
	}