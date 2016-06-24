/********************************************************************
Test Case Name: *958: User Admin Section - Search Bar - Typing Search Text box moves whole Search Grid.
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8483.aspx
Objective: Search result grid should be fixed while searching any records
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 13 January 2016
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import java.text.ParseException;

import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class UserAdmin8483 extends BaseClass{
	
	//InterruptedException, ParseException
	@Test	
	public void verifysearchgridcoordinates() throws Exception  {
		
	//****************Variables declaration and Initialization****************************	
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		
	//****************Test step starts here************************************************
		
		//Step1: Open URL: http://172.19.50.32:8080/idiom/#/login 
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		Thread.sleep(3000);
		
		
		//Step 2:Login with valid Client/Super admin user
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    ClientList_Page cl= new ClientList_Page(driver);
	    Thread.sleep(10000);
	   
	    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
	    
	    //Step 3 - Click on "Admin Access" link from sandwich icon of header
	    PageHeader ph=new PageHeader(driver);
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
        ph.performAction("adminaccess");
    	//cl.func_PerformAction("AdminAccess");
	    CustomReporter.log("The user has clicked on 'Admin Access'");	    
	 
	    Thread.sleep(5000);
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
	    Point SearchBar=null;
	    SearchBar=up.func_GetCoOrdinates("searchbar");
	    System.out.println("Search bar co-ordinates:"+SearchBar);
	    Thread.sleep(5000);
	    Point ScrollBox=null;
	    ScrollBox=up.func_GetCoOrdinates("scrollbox");
	    
	    //Step 4 - Now type email/user name to search records into user listing 
	    up.func_TypeSearchCriteria("rohan", "User");
	    
	    Thread.sleep(5000);
	    
	    Point SearchBarAfter=null;
	    SearchBarAfter=up.func_GetCoOrdinates("searchbar");
	    Thread.sleep(5000);
	    Point ScrollBoxAfter=null;
	    ScrollBoxAfter=up.func_GetCoOrdinates("scrollbox");
	    
	    Thread.sleep(5000);
	    
	    if ((SearchBar.x==SearchBarAfter.x) && (SearchBar.y==SearchBarAfter.y)){
	    	CustomReporter.log("Search Bar is not getting shifted/moved once search criteria is typed in Search textbox");
	    }
	    else
	    {
	    	CustomReporter.errorLog("Search Bar is getting shifted/moved once search criteria is typed in Search textbox");
	    	BaseClass.testCaseStatus=false;
	    }
	    
	    Thread.sleep(5000);
	    
	    if ((ScrollBox.x==ScrollBoxAfter.x) && (ScrollBox.y==ScrollBoxAfter.y)){
	    	CustomReporter.log("User List box (Scroll Box) is not getting shifted/moved once search criteria is typed in Search textbox");
	    }
	    else
	    {
	    	CustomReporter.errorLog("User List box (Scroll Box) is getting shifted/moved once search criteria is typed in Search textbox");
	    	BaseClass.testCaseStatus=false;
	    }
	    
	    //Step 5 - Click on log out link
	    Thread.sleep(3000);
	    ph.performAction("Logout");
	    CustomReporter.log("The user has been logged out");
		
		
		//****************Test step ends here************************************************
		
		if(BaseClass.testCaseStatus==false){
			   CustomReporter.errorLog("Test case has failed");
			   Assert.assertTrue(false);   
		}
	}

}
