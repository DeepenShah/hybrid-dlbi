/** *******************************************************************
Test Case Name: *Create user-Super Admin_Client not assigned Manually
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8368.aspx
Objective: No client should appear when not assigned any
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 20 January 2016
Modified By:Sunil Nair
Date: 18 May 2016
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

public class UserAdmin8368 extends BaseClass {

	@Test	
	public void verifynoclientassignment() throws Exception {
		
	//****************Variables declaration and Initialization****************************	
		try{
		String emailid = property.getProperty("email1");
		String password = property.getProperty("password1");
		String ClientName = property.getProperty("clientName");
		String ClientName1 = property.getProperty("clientName3");
		
	//****************Test steps starts here************************************************
		
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
		UA.func_CreateUser(UsrDetails[0], UsrDetails[1], 2, "Add New User Click Cancel Button Instead of Assign");
		
		Thread.sleep(5000);
		
		driver.navigate().refresh();
		Thread.sleep(5000);
		rm.webElementSync("pageload");
		rm.webElementSync("jqueryload");
		
		CustomReporter.log("Search newly created User " + UsrDetails[1] + " and click on Edit for it to check whether any assignment of Clients is noticed");
		
		rm.waitTime(5000);
		
		UA.func_ClickEditUserByEmailId(UsrDetails[1]);
		
		
		
		
		if (UA.func_ElementExist("AssignedClients"))
		{
			BaseClass.testCaseStatus=false;
			CustomReporter.errorLog("There is/are single/few/all Clients assigned");
		}
		else
		{
			CustomReporter.log("There is no Client assigned");
		}
		
		//Step 10 - Click on log out link
		rm.waitTime(3000);
		//ph.performAction("logout");
		CustomReporter.log("The user has been logged out");
		//UA.func_ClickElement ("Logout");
		}catch(Exception e){
			e.printStackTrace();
		}
		//****************Test step ends here************************************************
		
		if(BaseClass.testCaseStatus==false){
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
		}
	}
	
}
