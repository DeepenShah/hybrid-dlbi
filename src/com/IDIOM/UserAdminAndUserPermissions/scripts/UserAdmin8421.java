/** *******************************************************************
Test Case Name: 968: UserList: SuperAdmin> Full client row is not clickable
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8421.aspx
Objective: Verify that full client row is clickable 
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 20 January 2016
Modified By:Sunil Nair
Date: 20 May 2016
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

public class UserAdmin8421 extends BaseClass {

	@Test	
	public void verifycreateuserentirerowisclickable() throws Exception {
		
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
		Thread.sleep(10000);
		//Step 2 - Login with super user admin creds
		CustomReporter.log("Enter valid email id and password");
		CustomReporter.log("Click on login to Idiom button");		
		ln.func_LoginToIDIOM(emailid, password);	
		CustomReporter.log("User should land in Client List page");
		
		rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
        //Thread.sleep(10000);
				
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
		
		UA.func_CreateUser(UsrDetails[0], UsrDetails[1], 1, "Add New User Click anywhere in assign clients");	
		
		ph.performAction("Logout");
		CustomReporter.log("The user has been logged out");
		//UA.func_ClickElement ("Logout");*/
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
