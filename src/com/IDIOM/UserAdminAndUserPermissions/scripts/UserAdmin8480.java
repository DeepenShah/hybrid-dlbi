/********************************************************************
Test Case Name:*1193_Verify user is not able to select reset password & disable user at same time
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8480.aspx
Objective: Verify user is not able to select reset password & disable user at same time
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 29 December 2015
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

public class UserAdmin8480 extends BaseClass {
		
	@Test
	public void verifyresetpwdanddisable() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		String emailID1=property.getProperty("GeneralUserEmail");
		
	//****************Test step starts here************************************************
		
		//Step1: Open URL 
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		Thread.sleep(3000);
		
		
		//Step 2:	Login with super admin creds
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    //Step 3:Hover on menu icon and click on Admin access
	    ClientList_Page cl= new ClientList_Page(driver);
	    Thread.sleep(10000);
	   
	    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
	    
	    PageHeader ph=new PageHeader(driver);
	    Thread.sleep(5000);
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");	    
	    ph.performAction("adminaccess");
    	/*cl.func_PerformAction("AdminAccess");
	    CustomReporter.log("The user has clicked on 'Admin Access'");*/	    
	    		    
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
	    Thread.sleep(3000);
	    		    
	    if(up.func_ElementExist("Users List")){		    	
	    	CustomReporter.log(" User Admin page Got landed.The user listing is displayed");
	    	
	    //Step 4:Search for any user, say
	    	up.func_TypeSearchCriteria(emailID1,"User");
	    	if(up.func_ElementExist("Users List")){
	    		CustomReporter.log("The user '"+emailID1+"' is existing");
	    		
	    //Step 5:Click on edit
	    		up.func_ClickElement("FirstEditButton");
	    		CustomReporter.log("The user has clicked on 'Edit' for "+emailID1);
	    		 Thread.sleep(3000);
	    //Step 6:Click on disable account button
	    		up.func_ClickElement("DisableButton");
	    		CustomReporter.log("The user has clicked on 'DisableButton' for "+emailID1);
	    		 Thread.sleep(3000);
	    //Step 7:Check reset password button
	    		if(up.func_ElementExist("Reset Password Disabled")){
	    			CustomReporter.log("The Reset Password button is getting disabled on clicking on Disable Account");
	    		}
	    		else{
	    			CustomReporter.errorLog("The Reset Password button is not getting disabled on clicking on Disable Account");
	    			BaseClass.testCaseStatus=false;
	    		}
	    
	    //Step 8:Click on disable account button again
	    		up.func_ClickElement("DisableButton");
	    		 Thread.sleep(3000);
	    		CustomReporter.log("The user has clicked on 'DisableButton' for "+emailID1);
	    		
		//Step 9:Check reset password button
				if(!up.func_ElementExist("Reset Password Disabled")){
					CustomReporter.log("The Reset Password button is getting enabled on clicking on Disable Account again");
				}
				else{
					CustomReporter.errorLog("The Reset Password button is not getting enabled on clicking on Disable Account again");
					BaseClass.testCaseStatus=false;
				}
					
		//Step 10:Click reset password button
				up.func_ClickElement("Reset Password");
				 Thread.sleep(3000);
	    		CustomReporter.log("The user has clicked on 'Reset Password button' for "+emailID1);
	    		
	    //Step 11:Click on disable account button
	    		if(up.func_ElementExist("Disable Account Button Disabled?")){
					CustomReporter.log("The Disable Account button is getting disabled on clicking on Reset Password button");
				}
				else{
					CustomReporter.errorLog("The Disable Account button is not getting disabled on clicking on Reset Password button");
					BaseClass.testCaseStatus=false;
				}
	    //Step 12:Click on reset password button again
	    		up.func_ClickElement("Reset Password");
	    		 Thread.sleep(3000);
	    		CustomReporter.log("The user has clicked on 'Reset Password button' for "+emailID1);
	    		
	    		if(!up.func_ElementExist("Disable Account Button Disabled?")){
					CustomReporter.log("The Disable Account button is getting enabled on clicking on Reset Password button again");
				}
				else{
					CustomReporter.errorLog("The Disable Account button is not getting enabled on clicking on Reset Password button again");
					BaseClass.testCaseStatus=false;
				}
	    		
	    	}
	    	
	    	else{
	    		CustomReporter.errorLog("The user '"+emailID1+"' is not existing");
	    		BaseClass.testCaseStatus=false;
	    	}
	    	
	    }else{
		    CustomReporter.errorLog("The user listing is Not displayed");
	    	BaseClass.testCaseStatus=false;
		 }
		    
		//Step 13: Log out from the website
	    ph.performAction("logout");
	    CustomReporter.log("The user has been logged out");
		    
		
		//****************Test step ends here************************************************
				if(BaseClass.testCaseStatus == false){
						CustomReporter.errorLog("Test case has failed");
						Assert.assertTrue(false);
				}	
	}
	
	    	
	    	
	    	
}  	