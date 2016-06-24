/********************************************************************
Test Case Name: Profile page - Verify summary & demographics sections
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8093.aspx
Objective: This test case verifies the summary and demographics sections on selecting filters
Module: Analyse>Profile
Created By: Amrutha Nair
Date: 15 September 2015
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

public class Profile8093 extends BaseClass {
		
	@Test
	public void test_Profile8093() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");			
		String clientName=property.getProperty("defaultClient");
		String idiomName=null;
		String Filter=property.getProperty("FilterCriteria6");
		//String Filter="Age_18-24:Household Size_4";
		
	//****************Test step starts here************************************************	
		
	try{
		//Step 1: Login in application
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		Thread.sleep(3000);
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    
	    //Step 2:Click on any client card
	    ClientList_Page cl = new ClientList_Page(driver);
		cl.func_SelectClient(clientName);
		CustomReporter.log("The client "+clientName+" has been opened");
		Thread.sleep(3000);
		
		
		//Step 3:Click on any existing idiom/Create new one
		ManageIdiom_Page mi = new ManageIdiom_Page(driver);
		Thread.sleep(8000);
		idiomName=mi.func_CreateIdiomName("Idiom","New");
		mi.func_CreateNewIdiomOrRename(idiomName, "NotBlank");
		CustomReporter.log("The idiom '"+idiomName+"' is created.");
		Thread.sleep(8000);
		
		//Step 4: Select some filters
		Audience_Page ad= new Audience_Page(driver);
		ad.func_SelectFilters(Filter);
		CustomReporter.log("The user has selected filter cards");
		
		
		//Step5:Click on analyze button from main nav
		ad.func_ClickElement("AnalyzeAudience");
		CustomReporter.log("The user has clicked on Analyse button");
		Thread.sleep(3000);
		
		//Step 6:Verify values in filters appears as selected in step 4 in summary sections
		Analyse_Profile_Page an= new Analyse_Profile_Page(driver);
		if(an.func_VerifyProfileGraphValues("Summary", Filter)){
			CustomReporter.log("Values in filters appears as selected in step 4 in summary sections ");
		}
		else{
			CustomReporter.errorLog("Values in Summary table are not matching with filters selected in Audience page");
			BaseClass.testCaseStatus=false;
		}
		
		//Check whether largest values are coming in 'Summary secion
		if(an.func_VerifySummaryWithBiggestValue(Filter))
		{
			CustomReporter.log("The summary table is getting upated with largest values for selected filters");
		}
		else{
			CustomReporter.errorLog("The summary table is NOT getting upated with largest values for all selected filters");
		}
		//Step7: Verify values in charts appears in demographic section is as per filters selected in step 4
		
		
		if(an.func_VerifyProfileGraphValues("Demographics", Filter)){
			CustomReporter.log("Values in charts appears in demographic section is as per filters selected in step 4");
		}
		else{
			CustomReporter.errorLog("Values in Demographics are not matching with filters selected in Audience page");
			BaseClass.testCaseStatus=false;
		}
			
		//Go back to 'Manage Idiom' page and delete the idiom created
		an.func_ClickElement("Manage");
		Thread.sleep(3000);
		
		mi.func_PerformClickAction("Delete");
		Thread.sleep(1000);
		
		//Step8:Logout from the application
		mi.func_PerformClickAction("Logout");
		CustomReporter.log("The user has been logged out successfully");
	}
	
	catch(Exception e){
		BaseClass.testCaseStatus = false;
		CustomReporter.errorLog(" The exception is generated, " + e.getMessage() + ". ");
		e.printStackTrace(System.out);
		rm.captureScreenShot("Profile8093", "fail");
	}	
	
		//****************Test step ends here************************************************
				if(BaseClass.testCaseStatus == false){
					CustomReporter.errorLog("Test case has failed");
					Assert.assertTrue(false);
				}
		

	}
	
}
