/********************************************************************
Test Case Name:**553_Profile page_Verify map appears correctly
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8220.aspx
Objective: Verify map appears correctly
Module: Analyse>Profile
Created By: Amrutha Nair
Date: 18 September 2015
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

public class Profile8220 extends BaseClass {
		
	@Test
	public void test_Profile8220() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");			
		String clientName=property.getProperty("defaultClient");
		String idiomName=null;
		String Filter1=property.getProperty("FilterCriteria3");

	//****************Test step starts here************************************************
		
		//Step1:Open URL
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		//Step2:Login with valid creds
		Thread.sleep(3000);
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    //Step3:Click on any client card
	    ClientList_Page cl = new ClientList_Page(driver);
		cl.func_SelectClient(clientName);
		CustomReporter.log("The client "+clientName+" has been opened");
		Thread.sleep(3000);
		
		
		//Step4:Create an idiom/select any
		ManageIdiom_Page mi = new ManageIdiom_Page(driver);
		Thread.sleep(8000);
		idiomName=mi.func_CreateIdiomName("Idiom","New");
		mi.func_CreateNewIdiomOrRename(idiomName, "NotBlank");
		CustomReporter.log("The idiom '"+idiomName+"' is created.");
		
		
		//Step5:Select filter as mentioned in test case 
		Audience_Page ad= new Audience_Page(driver);
		ad.func_SelectFilters(Filter1);
		CustomReporter.log("One card is selected in all filters");
		
		//Step6: Click on analyze audience button
		ad.func_ClickElement("AnalyzeAudience");
		CustomReporter.log("The user has clicked on Analyse button");
		Thread.sleep(3000);
		
		Analyse_Profile_Page ap= new Analyse_Profile_Page(driver);
		if(ap.func_ElementExist("ProfilePage")){
			CustomReporter.log("The profile page is getting opened");
			
			
		//Step7: Check local market section
			if(ap.func_ElementExist("Local Market Map Icon")){
				CustomReporter.log("The map nodes  are existing in local market");
			}
			else{
				CustomReporter.errorLog("The map nodes are not getting populated in local market section");
				BaseClass.testCaseStatus=false;
			}
			//Go back to audience page
			ap.func_ClickElement("Audience");
			Thread.sleep(2000);
		}
		else{
			CustomReporter.errorLog("The profile page is NOT getting opened");
			BaseClass.testCaseStatus=false;
		}
		
		//Go to 'manage idiom page and delete the created idiom
		ad.func_ClickElement("Manage");
		Thread.sleep(3000);
		
		mi.func_PerformClickAction("Delete");
		Thread.sleep(1000);
		
		//Step 8:Logout of application
		mi.func_PerformClickAction("Logout");
		CustomReporter.log("The user has been logged out successfully");
		
		//****************Test step ends here************************************************
		if(BaseClass.testCaseStatus == false){
				CustomReporter.errorLog("Test case has failed");
				Assert.assertTrue(false);
		}	
		}
	}
		