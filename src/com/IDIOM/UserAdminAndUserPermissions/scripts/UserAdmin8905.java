/********************************************************************
Test Case Name: **Create User: Verify multiple clicks on create user button
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8905.aspx
Objective:This test case is for checking whether multiple clicks on create user button throws up error or not. 
Module: UserAdminAndUserPermissions
Created By:Sunil Nair
Date: 04 May 2016
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;


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

public class UserAdmin8905 extends BaseClass {
	@FindBy(xpath="//*[@class='overlay-modal user-modal ng-scope']//*[@class='layout horizontal']//input[@id='name']")
	private WebElement Adduser_Name;
    
    @FindBy(xpath="//*[@class='overlay-modal user-modal ng-scope']//*[@class='layout horizontal']//input[@id='emailAddress']")
	private WebElement Adduser_Email;
    
    @FindBy(xpath="//*[@class='overlay-modal user-modal ng-scope']//*[text()='Create']")
	private WebElement Adduser_Create;
		
	/*public void func_enteruserdetails(String user,String email) throws Exception{
		   
		System.out.println("The fn_parameter user name is "+user+" and the email is "+email);
		 Adduser_Name.click();
		 Adduser_Name.sendKeys(user);
		 Adduser_Email.click();
		 Adduser_Email.sendKeys(email);
		 for (int i=1;i<=5;i++)
		 {
			 Adduser_Create.click();
			 Thread.sleep(200);
		 }
		 }*/
		 
	@Test
	public void verifymultipleclicksoncreateuser() throws Exception{
	//initialize the variables
			
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		
		
	//****************Test step starts here************************************************
		//Load the URL and log in with valid Super User Admin Credentials
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		Thread.sleep(3000);
		
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	   	    
	    PageHeader ph=new PageHeader(driver);
	    Thread.sleep(5000);
	    //check for the visibility of elements before clicking on admin access
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");

        //click on admin access
	    ph.performAction("adminaccess");
	    CustomReporter.log("Admin access page opened");
	    //wait for the page to load
	    rm.webElementSync("idiomspinningcomplete");



	    UserAdmin_UserPermissions_Page useradmin=  new UserAdmin_UserPermissions_Page(driver);	    
	   /* useradmin.func_ClickElement("AdminAccess");
	    CustomReporter.log("Clicked on Admin access");*/
	    Thread.sleep(5000);
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        if(useradmin.isVisible("createuserbutton"))
        		{
        		CustomReporter.log("Create user button visible");
        		}
        else
        {
        	BaseClass.testCaseStatus =false;
        }
	    if(useradmin.func_ElementExist("Users List")){
	    	
	    	CustomReporter.log("The user listing is displayed");
	    }	
	    useradmin.func_PerformAction("AddUserClick");
	    CustomReporter.log("Create user button clicked");
	    Thread.sleep(3000);
	    //Check for the availability of elements in create user dialog box.
	    
	    useradmin.func_PerformAction("labelCreateUserExists");	 
	    useradmin.func_PerformAction("AddUserNameExists");
	    useradmin.func_PerformAction("AddUserEmailExists");
	    useradmin.func_PerformAction("AddUserCreateButtonExists");
	    useradmin.func_PerformAction("AddUserCancelButtonExists");
	    CustomReporter.log("All elements exist in create user window");
	    
	    String[] userdetails=useradmin.func_CreateUserDetails("sunil");
	    System.out.println("username is "+userdetails[0]+" and user email is "+userdetails[1] );
	    
	    Thread.sleep(10000);
	      
	    useradmin.func_enteruserdetails(userdetails[0],userdetails[1],3);
	    CustomReporter.log("user name password entered and clicked create multiple times");
	    //Log out from the website
	    Thread.sleep(5000);
		 //ph.performAction("Logout");
		 //CustomReporter.log("The user has been logged out");
		    
		
		//****************Test step ends here************************************************
				if(BaseClass.testCaseStatus == false){
						CustomReporter.errorLog("Test case has failed");
						Assert.assertTrue(false);
				}	
	}
	}