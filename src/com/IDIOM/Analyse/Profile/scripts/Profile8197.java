/********************************************************************
Test Case Name: *535_Profile_Page_Verify_No_Space_at_Bottom
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8197.aspx
Objective: Objective:This test case is meant to check the unnecessary space present in it.
Module: Analyse>Profile
Created By: Rohan Macwan
Date: 16 September 2015
**********************************************************************/
package com.IDIOM.Analyse.Profile.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class Profile8197 extends BaseClass{

	@Test	
	public void test_Profile8197() throws InterruptedException {

	//****************Variables declaration and Initialization****************************
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");
		String client1 = property.getProperty("defaultClient");
		boolean rst = true;
	
	//****************Test step starts here************************************************
		
		//Step1 - Login to url http://172.19.50.32:8080/idiom
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		CustomReporter.log("Enter valid email id and password");
		CustomReporter.log("Click on login to Idiom button");		
		//Reading email and password value from property file
				
		ln.func_LoginToIDIOM(emailid, password);	
		
		//Step 2 - Select a client
		CustomReporter.log("User should land in Client List page");
		ClientList_Page CL = new ClientList_Page(driver);
		
		rm.waitTime(3000);
		
		CustomReporter.log("Select Client - " + client1 +  " from Client list page");
		
		CL.func_SelectClient(client1);
		
		rm.waitTime(3000);
		
		//Step 3 - Create an idiom or click on already created one
		CustomReporter.log("Manage Idiom page is displayed");
		
		ManageIdiom_Page mi = new ManageIdiom_Page(driver);
		Thread.sleep(3000);
		
		String Name="";
		
		Name= mi.func_CreateIdiomName("Rohan", "New");
		Thread.sleep(3000);
		
		/*mi.func_CreateNewIdiomOrRename(Name, "NotBlank");
		Thread.sleep(3000);
		CustomReporter.log("New Idiom - "+ Name + " Created");
		Thread.sleep(3000);*/
		
		mi.func_SelectExistingIdiomByNumber(1);
		Thread.sleep(3000);
		CustomReporter.log("Idiom Selected");
		Thread.sleep(3000);
		
		//Step 4 - Add filter and click on Analyse
		Audience_Page Ad = new Audience_Page(driver);
		//Ad.func_selectFilterAndPreserveNames();
		
		Thread.sleep(3000);
		Ad.func_ClickElement("AnalyzeAudience");
		
		Thread.sleep(3000);
		//Check the drop downs in profile page whether it has following 3 values
			//1. Summary
			//2. Demographics
			//3.Local markets
		Analyse_Profile_Page  an=new Analyse_Profile_Page (driver);
		if (an.func_ElementExist("FooterSpace")){
			an.func_PrintSpaceMeasurementAtTheBottom();
			CustomReporter.errorLog("Space is quite more in the bottom");
			BaseClass.testCaseStatus = false;
		}
		else
		{
			CustomReporter.log("Space is properly set in the bottom");
		}
		
		
		
		Thread.sleep(3000);
		//Logout from the application
		//Step 6 - Click on 'Logout' 
		an.func_ClickElement("Logout");
		CustomReporter.log("The user has been logged out");
		
		//****************Test step ends here************************************************
		if(BaseClass.testCaseStatus == false){
			CustomReporter.errorLog("Test case has failed");
			Assert.assertTrue(false);
		}
	}
}
