/********************************************************************
Test Case Name:*596_Profile page_Verify labels for circle chart in demographics section
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8245.aspx
Objective:Verify the labels in doughnut graph
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

public class Profile8245 extends BaseClass {
	
	
	@Test
	public void test_Profile8245() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");			
		String clientName=property.getProperty("defaultClient");
		String idiomName=null;
		String Filter=property.getProperty("FilterCriteria5");
	//****************Test step starts here************************************************
		
		//Step1:Open URL
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		Thread.sleep(3000);
		
		//Step2:Login with valid creds
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    
	    //Step3:Click on any client
	    ClientList_Page cl = new ClientList_Page(driver);
	  	cl.func_SelectClient(clientName);
	  	CustomReporter.log("The client "+clientName+" has been opened");
	  	Thread.sleep(3000);
	  	
	  	
	  	//Step4:Open any existing idiom/create new
	  	ManageIdiom_Page mi = new ManageIdiom_Page(driver);
		Thread.sleep(8000);
		idiomName=mi.func_CreateIdiomName("Idiom","New");
		mi.func_CreateNewIdiomOrRename(idiomName, "NotBlank");
		CustomReporter.log("The idiom '"+idiomName+"' is created.");
		
		
		//Step5:Select some filters and click on analyze button
		Audience_Page ad= new Audience_Page(driver);
		ad.func_SelectFilters(Filter);
		CustomReporter.log("The filters are selected");
		
		ad.func_ClickElement("AnalyzeAudience");
		CustomReporter.log("The user has clicked on Analyse button");
		Thread.sleep(5000);
		
		
		//Step6:Go to demographics section and click on labels present for circle chart
		//and
		//Step7:Click on both labels for chart, if two labels are present
		Analyse_Profile_Page ap= new Analyse_Profile_Page(driver);
		if(ap.func_Verify_HighLightElement(Filter)){
			CustomReporter.log("On selecting label, the selcted label is getting highlighted and other is NOT");
			
		}
		else{
			
			CustomReporter.errorLog("The highlighting the chart on clicking n label is NOt functioning well");
			BaseClass.testCaseStatus=false;
		}
		
		Thread.sleep(3000);
		//Go to manage Idiom page and delete the created idiom
		ad.func_ClickElement("Manage");
		Thread.sleep(3000);
		
		mi.func_PerformClickAction("Delete");
		Thread.sleep(1000);
		
		//Step8:Logout from the application
		//mi.func_PerformClickAction("Logout",driver);
		CustomReporter.log("The user has been logged out successfully");
		
		//****************Test step ends here************************************************
		if(BaseClass.testCaseStatus == false){
				CustomReporter.errorLog("Test case has failed");
				Assert.assertTrue(false);
		}	
		
	}
	}
	    