/********************************************************************
Test Case Name:*587_Verify_Axis Values for Graphs
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8227.aspx
Objective:Verify that X axis and Y axis values are coming for each graph in Analyse >Profile page
Module: Analyse>Profile
Created By: Amrutha Nair
Date: 21 September 2015
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

public class Profile8227 extends BaseClass {
	
	@Test
	public void test_Profile8227() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");			
		String clientName=property.getProperty("defaultClient");
		String idiomName=null;
		String Filter=property.getProperty("FilterCriteria4");
	//****************Test step starts here************************************************
		
		//Step1:Open URL and login
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		Thread.sleep(3000);
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
		
		//Step2:Select a client and create an idiom	
	    ClientList_Page cl = new ClientList_Page(driver);
	  	cl.func_SelectClient(clientName);
	  	CustomReporter.log("The client "+clientName+" has been opened");
	  	Thread.sleep(3000);
	  	
	  	ManageIdiom_Page mi = new ManageIdiom_Page(driver);
		Thread.sleep(8000);
		idiomName=mi.func_CreateIdiomName("Idiom","New");
		mi.func_CreateNewIdiomOrRename(idiomName, "NotBlank");
		CustomReporter.log("The idiom '"+idiomName+"' is created.");
		
		
		//Step3:Select some filters and click on Analyse
		Audience_Page ad= new Audience_Page(driver);
		ad.func_SelectFilters(Filter);
		CustomReporter.log("The filters are selected");
		
		ad.func_ClickElement("AnalyzeAudience");
		CustomReporter.log("The user has clicked on Analyse button");
		Thread.sleep(3000);
		
		
		//Step4:Check  the graphs in Demographics Analyse >Profile page
		Analyse_Profile_Page ap = new Analyse_Profile_Page(driver);
		
		if(ap.func_VerifyAxisValues(Filter)){
			CustomReporter.log("The graph is proper with values in x and y axis");
			
		}
		else{
			CustomReporter.errorLog("The graph is not proper with values in X and Y axis");
			BaseClass.testCaseStatus=false;
		}
		
		Thread.sleep(3000);
		//Go to manage Idiom page and delete the created idiom
		ad.func_ClickElement("Manage");
		Thread.sleep(3000);
		
		mi.func_PerformClickAction("Delete");
		Thread.sleep(1000);
		
		//Step5:Logout from the application
		mi.func_PerformClickAction("Logout");
		CustomReporter.log("The user has been logged out successfully");
		
		//****************Test step ends here************************************************
		if(BaseClass.testCaseStatus == false){
				CustomReporter.errorLog("Test case has failed");
				Assert.assertTrue(false);
		}	
		
	}
}
		
				