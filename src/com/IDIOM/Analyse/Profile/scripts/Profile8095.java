/********************************************************************
Test Case Name: Profile page - Verify hover state in demographics
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/8095.aspx
Module: Analyse Page
Created By: Rohan Macwan
Creation Date: 18th September 2015
**********************************************************************/

package com.IDIOM.Analyse.Profile.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Profile_Page ;
import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class Profile8095 extends BaseClass{
	
	
	@Test
	
	public void test_Profile8095() throws InterruptedException {

	//****************Variables declaration and Initialization****************************
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");
		String client1 = property.getProperty("clientName");
		boolean rst = true;
		
		boolean bProjectCreate = false;
		ClientList_Page cl=null;
		String strProjectName="";
		String strMsg="";
		
		String Age_SubFilter=null;
		String HasChild_SubFilter=null;
		String HHIncome_SubFilter=null;
		String HHSize_SubFilter=null;
		String Location_SunFilter=null;
		String HighestEdu_SubFilter=null;
	
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
		Thread.sleep(8000);
		
		String Name="";
		
		Name= mi.func_CreateIdiomName("Rohan", "New");
		Thread.sleep(3000);
		
		mi.func_CreateNewIdiomOrRename(Name, "NotBlank");
		Thread.sleep(3000);
		CustomReporter.log("New Idiom - "+ Name + " Created");
		Thread.sleep(3000);
		
		/*mi.func_SelectExistingIdiomByNumber(1, driver);
		Thread.sleep(3000);
		CustomReporter.log("Idiom Selected");
		Thread.sleep(3000);*/
		
		//Step 4 - Add filter and click on Analyse
		Audience_Page Ad = new Audience_Page(driver);
		//Ad.func_selectFilterAndPreserveNames();
		
		//Capture subfilter present for each filter
		Age_SubFilter =Ad.func_capture_IndividualFilter("Age");
	
		HasChild_SubFilter=Ad.func_capture_IndividualFilter("Has Child");
	
		HHIncome_SubFilter=Ad.func_capture_IndividualFilter("Household Income");
	
		HHSize_SubFilter=Ad.func_capture_IndividualFilter("Household Size");
	
		Location_SunFilter=Ad.func_capture_IndividualFilter("Location");
		
		HighestEdu_SubFilter=Ad.func_capture_IndividualFilter("Highest Education");
	
		String Filters=Age_SubFilter+":"+HasChild_SubFilter+":"+HHIncome_SubFilter+":"+HHSize_SubFilter+":"+HighestEdu_SubFilter;
	

		Thread.sleep(3000);
		Ad.func_ClickElement("AnalyzeAudience");
		
		Thread.sleep(3000);
		//Check the drop downs in profile page whether it has following 3 values
			//1. Summary
			//2. Demographics
			//3.Local markets
		Analyse_Profile_Page  an=new Analyse_Profile_Page (driver);
		
		Thread.sleep(3000);
		
		an.func_barChartSelector();
		//an.func_ChartTitles();
		
		
		//Go to manage Idiom page and delete the created idiom
		an.func_ClickElement("Manage");
		Thread.sleep(3000);
		  
		mi.func_PerformClickAction("Delete");
		Thread.sleep(1000);
		
		
		Thread.sleep(3000);
		
		//an.func_ClickElement("Logout");
		mi.func_PerformClickAction("Logout");
		CustomReporter.log("The user has been logged out");
		
		//****************Test step ends here************************************************
		if(BaseClass.testCaseStatus == false){
			CustomReporter.errorLog("Test case has failed");
			Assert.assertTrue(false);
		}
	}
}
