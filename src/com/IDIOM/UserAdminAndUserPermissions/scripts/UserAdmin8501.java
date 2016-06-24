/********************************************************************
Test Case Name: *1141: QA>User Admin Page :On click on "X" icon, the selected users are not getiing deselected
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8501.aspx
Objective:Selected users should reset on cliking "X" sign 
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 24 November 2015
Modified By: Sunil Nair
Date:16 May 2016
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class UserAdmin8501 extends BaseClass{
	
	@Test	
	public void verifyuserresetclose() throws Exception {
		
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
		rm.waitTime(3000);
				
		//Step 3 - Click on "Admin Access" link from header sandwich icon
		/*-----------modified by sunil 06 May 2016-------------*/
		PageHeader ph=new PageHeader(driver);
		ClientList_Page CL = new ClientList_Page(driver);
		rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
	    ph.performAction("adminaccess");
	    CustomReporter.log("clicked on admin access");
		
		//Step 4 - Select 2-3 users from user listing
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        
		UserAdmin_UserPermissions_Page UA = new UserAdmin_UserPermissions_Page(driver);
		CustomReporter.log("Select Users");
		if(UA.func_ElementExist("Users List")){
		UA.func_selectUsers(3);
		Thread.sleep(3000);
		//Step 5 - Click on "X' sign
		CustomReporter.log("Click on Close icon from Bulk Assignment bar");		
		UA.func_ClickElement("Bulk Close Icon");
		}
		
		if(UA.func_ElementExist ("Selected User Count"))
		{
			CustomReporter.errorLog("User count is still not getting deselected");
			BaseClass.testCaseStatus=false;
		}
		else
		{
			CustomReporter.log("All selected users have been deselected after close icon is clicked from Bulk assignment bar");
			BaseClass.testCaseStatus=true;
		}
		}catch(Exception e){
			e.printStackTrace();
		      BaseClass.testCaseStatus = false;
		      e.printStackTrace(System.out);
		      CustomReporter.errorLog("Some exception is generated, " + e.getMessage()
		          + ". Please check the log for more details");
		      rm.captureScreenShot("Bulkassignment", "fail");
		}
	    //Step 5: Log out from the website
		finally {
		      // Step 6: Logout from application.
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
