/********************************************************************
Test Case Name: **Super User Admin - User Admin Screen Overview - Verify Components/Sections
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8252.aspx
Objective:This test case is for verifying whether all components of User Admin Screen are present on this page.
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 26 November 2015
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;


import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;

import common.BaseClass;

public class UserAdmin8407 extends BaseClass {
	
	@Test
	public void test_UserAdmin8407() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		String HelpContent=property.getProperty("HelpContent_UserAdminPage");
	//****************Test step starts here************************************************
		
	try{
		//Step1: Login idiom application with valid super user admin id and password
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		Thread.sleep(3000);
		
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    //Step 2:	Click on "Admin Access" from client list page>Menu drop down
	    ClientList_Page cl= new ClientList_Page(driver);
	    Thread.sleep(3000);
	    cl.func_PerformAction("AdminAccess");
	    CustomReporter.log("The user has clicked on 'Admin Access'");
	    
	    
	    
	    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
	    Thread.sleep(3000);
	    
	    //Step 3:Mouse over the Help icon at the header
	    if(up.func_ElementExist("Mouseover On Help")){
	    	CustomReporter.log("The Help drop down content is getting opene on mouse over on the same");
	    }
	    
	    else{
	    	CustomReporter.errorLog("The help drop down is not getting opened on mouse over");
	    	BaseClass.testCaseStatus=false;
	    }
	    
	    
	    //Step 4:Check the content in help box
	    if(up.func_Verify_HelpContent(HelpContent)){
    		CustomReporter.log(" The help box is proper in User Admin PAge. And help content is also proper");
    	}
	    else{
	    	
	    	CustomReporter.errorLog("The help box is not proper");
	    	BaseClass.testCaseStatus=false;
	    }
	    
	    
	    //Step 5:	Click on "Logout"
	    
	    up.func_ClickElement("Logout");
	    CustomReporter.log("The user has been clicked on logout");
	    
	}
	
	catch(Exception e){
		BaseClass.testCaseStatus = false;
		CustomReporter.errorLog(" The exception is generated, " + e.getMessage() + ". ");
		e.printStackTrace(System.out);
		rm.captureScreenShot(rm.getScreenShotName(this.getClass().getName()), "fail");
	}	
	  //****************Test step ends here************************************************
		
	  		if(BaseClass.testCaseStatus==false){
	  			   CustomReporter.errorLog("Test case has failed");
	  			   Assert.assertTrue(false);   
	  		}
	  	}

	  }
