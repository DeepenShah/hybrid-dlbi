/********************************************************************
Test Case Name: *Profile page - Verify default filters
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8096.aspx
Objective:  This test  case verifies the filters present in audience and analyse without selecting any filters
Module: Analyse>Profile
Created By: Amrutha Nair
Date: 14 September 2015
**********************************************************************/

package com.IDIOM.Analyse.Profile.scripts;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.reports.CustomReporter;

import common.BaseClass;
import common.ReusableMethods;

public class Profile8096 extends BaseClass {
	
	
	@Test
	public void test_Profile8096() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");			
		String clientName=property.getProperty("defaultClient");
		String idiomName=null;
		ArrayList<String> filterCards = new ArrayList<String>();
		ArrayList<String> Analyse_ProfileFilters = new ArrayList<String>();
		String Age_SubFilter=null;
		String HasChild_SubFilter=null;
		String HHIncome_SubFilter=null;
		String HHSize_SubFilter=null;
		String Location_SunFilter=null;
		String HighestEdu_SubFilter=null;
		
		
		
	//****************Test step starts here************************************************	
		
		//Step1: Login in application
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		
		Thread.sleep(3000);
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
		
		//Step2: Click on any client card
	    ClientList_Page cl = new ClientList_Page(driver);
		cl.func_SelectClient(clientName);
		CustomReporter.log("The client "+clientName+" has been opened");
		Thread.sleep(3000);
		
		
		//Step 3:Open any existing idiom/create new
		ManageIdiom_Page mi = new ManageIdiom_Page(driver);
		Thread.sleep(8000);
		idiomName=mi.func_CreateIdiomName("Idiom","New");
		mi.func_CreateNewIdiomOrRename(idiomName, "NotBlank");
		CustomReporter.log("The idiom '"+idiomName+"' is created.");
		
		
		//Step4: Deselect all filters if selected
		Audience_Page ad= new Audience_Page(driver);
		ad.func_ClickElement("DeselectAll");
		CustomReporter.log("The user has deslected all the selected cards");
		
		//capture the filters present in audience page
		filterCards= ad.func_CaptureList("DropDownItems");
		
		//Capture subfilter present for each filter
		Age_SubFilter =ad.func_capture_IndividualFilter("Age");
	
		HasChild_SubFilter=ad.func_capture_IndividualFilter("Has Child");
	
		HHIncome_SubFilter=ad.func_capture_IndividualFilter("Household Income");
	
		HHSize_SubFilter=ad.func_capture_IndividualFilter("Household Size");
	
		Location_SunFilter=ad.func_capture_IndividualFilter("Location");
		
		HighestEdu_SubFilter=ad.func_capture_IndividualFilter("Highest Education");
	
		String Filters=Age_SubFilter+":"+HasChild_SubFilter+":"+HHIncome_SubFilter+":"+HHSize_SubFilter+":"+HighestEdu_SubFilter;
	
		//Step5:Click on analyze in main nav
		ad.func_ClickElement("AnalyzeAudience");
		CustomReporter.log("The user has clicked on 'Analyse' button");
		
		//Step6:Verify all filter data appears on every section by default
		//Check Summary section for all filter data
		Analyse_Profile_Page an= new Analyse_Profile_Page(driver);
		Analyse_ProfileFilters=an.func_CaptureList("getSummaryList");
		
		if(Analyse_ProfileFilters.isEmpty()){
			CustomReporter.errorLog("The analyse summary table doesn't contain any filter data");
			BaseClass.testCaseStatus=false;
		}
		else{
			
			if(rm.compareArray(Analyse_ProfileFilters,filterCards)){
				CustomReporter.log("The summary section contains all the filter data which is present in  audience page");
			}
			else{
				CustomReporter.errorLog("The summary section doesn't contain all the filter data which is present in  audience page");
				BaseClass.testCaseStatus=false;
			}
		}
		
		//Check Demographics section to verify all the filter data present here
	
		if(an.func_VerifyProfileGraphValues("Demographics", Filters)){
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
				
		//Step7:Logout from the application
		mi.func_PerformClickAction("Logout");
		CustomReporter.log("The user has been logged out successfully");
		//****************Test step ends here************************************************
		if(BaseClass.testCaseStatus == false){
			CustomReporter.errorLog("Test case has failed");
			Assert.assertTrue(false);
		}
	    
	}
}

		
		
		
		
		
		
		
		
		