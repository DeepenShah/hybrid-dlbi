/** *******************************************************************
Test Case Name: *980_SuperUserAdmin_Verify Last Client Detail In Edit User
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8482.aspx
Objective: Verify last client detail while editing User
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 18 January 2016
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class UserAdmin8482 extends BaseClass {
	
	@Test	
	public void verifylastclientvisibility() throws Exception {
		
	//****************Variables declaration and Initialization****************************	
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		
	//****************Test step starts here************************************************
		
		//Step1: Open URL - http://172.19.50.32:8080/idiom/ 
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		Thread.sleep(3000);
		
		
		//Step 2: Log in as a Super Admin User User 
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    ClientList_Page cl= new ClientList_Page(driver);
	   // Thread.sleep(10000);
	   
	    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
	    
	    //Step 3: Click on Admin Access from Menu 
	    PageHeader ph=new PageHeader(driver);
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
	    ph.performAction("adminaccess");
	    CustomReporter.log("clicked on admin access");    
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");		    
	 
	    //Thread.sleep(5000);
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
    		CustomReporter.log("The client list is visible in the edit user grid");
    	}
    	
    	else{
    		CustomReporter.errorLog("The client list is visible in the edit user grid");
    		BaseClass.testCaseStatus = false;
    	}
    	
    	/*String[] clientlist=up.func_GetAssignedClients();
    	for (String str:clientlist){
    		System.out.println(str);
    	}*/
    	//up.func_GetClientListByUserNumber(0);
	    
	    //Step 4, 5: Edit any user and Scroll through the Client list to check the last client detail 
	    
	   if (up.func_CheckTheVisibilityOfLastClientForEditedUser()){
	    	CustomReporter.log("Last Client is visible for the edited user");
	    }
	    else
	    {
	    	CustomReporter.errorLog("Last Client is not visible for the edited user");
	    	BaseClass.testCaseStatus=false;
	    }
	    
	    //Step 6 - Click on log out link
	    pageHeader.performAction("logout");
        CustomReporter.log("Logged out successfully");
		
		
		//****************Test step ends here************************************************
		
		if(BaseClass.testCaseStatus==false){
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
		}
	}
}
