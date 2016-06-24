/********************************************************************
Test Case Name:*475_Profile Page_Verify nodes in local market appearing correctly
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8234.aspx
Objective: Verify nodes in local market appearing correctly
Module: Analyse>Profile
Created By: Amrutha Nair
Date: 18 September 2015
**********************************************************************/

package com.IDIOM.Analyse.Profile.scripts;

import java.util.Hashtable;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class Profile8234 extends BaseClass {
	
	
	@Test
	public void test_Profile8234() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");			
		String clientName=property.getProperty("defaultClient");
		String idiomName=null;
		//String Filter1="Age_18-24:Household Size_4:Has Child_Yes:Household Income_<25k:Highest Education_High School diploma or equivalent:Location_U.S. Home";
		String Filter1=property.getProperty("FilterCriteria4");
		String Filter2=property.getProperty("FiterCriteria1");
		Hashtable<String, String> nodeValues1 = new Hashtable<String, String>();
		Hashtable<String, String> nodeValues2 = new Hashtable<String, String>();
		Hashtable<String, String> nodeValues3 = new Hashtable<String, String>();
		
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
		
		
		//Step5:Select one card from every filter
		Audience_Page ad= new Audience_Page(driver);
		ad.func_SelectFilters(Filter1);
		CustomReporter.log("One card is selected in all filters");
		
		
	   //Step6: Click on analyze audience button
		ad.func_ClickElement("AnalyzeAudience");
		CustomReporter.log("The user has clicked on Analyse button");
		Thread.sleep(5000);
		
		Analyse_Profile_Page ap= new Analyse_Profile_Page(driver);
		if(ap.func_ElementExist("ProfilePage")){
			CustomReporter.log("The profile page is getting opened");
			
			
		//Step7:Verify correct nodes appearing in map & note down
			if(ap.func_ElementExist("Local Market Map Icon")){
				CustomReporter.log("The map nodes  are existing in local market");
				
				//click on one of the node to capture values
				nodeValues1=ap.RetrievelocalmarketData();
				CustomReporter.log("The node data has been captured from each node");
				
				
		//Step8:Click on audience in main nav
				ap.func_ClickElement("Audience");
				Thread.sleep(3000);
				CustomReporter.log("The user has clicked on 'audience' tab");
			}
			else{
				CustomReporter.errorLog("The map nodes  are not present in local market");
				BaseClass.testCaseStatus=false;
			}
		}
		else{
			CustomReporter.errorLog("The profile page is NOT getting opened on clicking on 'Analyse icon'");
			BaseClass.testCaseStatus=false;
		}
		
		//Step9:Click on select all 
		ad.func_ClickElement("SelectAll");
		
		CustomReporter.log("All filters are selected for filter");
		
		//Step10:  Click on analyze audience button
		ad.func_ClickElement("AnalyzeAudience");
		CustomReporter.log("The user has clicked on Analyse button");
		
		Thread.sleep(3000);
		if(ap.func_ElementExist("ProfilePage")){
			CustomReporter.log("The profile page is getting opened");
		
	    //Step11:Verify nodes changes in map as per selection
			if(ap.func_ElementExist("Local Market Map Icon")){
				CustomReporter.log("The map nodes  are existing in local market");
				
				//click on one of the node to capture values
				nodeValues2=ap.RetrievelocalmarketData();				
				
				CustomReporter.log("The node data has been captures from each node");
				
				//Check whether the node values changes as per the user selects new filters
				if(rm.CompareHashTables(nodeValues2, nodeValues1)){
					CustomReporter.errorLog("The node values are NOt getting updated in Local Market secton on changing filters");
					BaseClass.testCaseStatus=false;
				}
				else{
					CustomReporter.log("The local market nodes are getting updated in local market section on changing nodes");
				}
				
				//Step 12:Click on audience in main nav
				ap.func_ClickElement("Audience");
				CustomReporter.log("The use has clicked on 'audience' tab");
			}
			else{
				
				CustomReporter.errorLog("The map nodes are not existing in local market");
				BaseClass.testCaseStatus=false;
				}
			}
		else{
			CustomReporter.errorLog("The profile page is NOT getting opened on clicking on 'Analyse icon'");
			BaseClass.testCaseStatus=false;
		}
		
		
		//Step13:Select cards as selected in step 5
		//deslect all cards
		ad.func_ClickElement("DeselectAll");
		
		ad.func_SelectFilters(Filter1);
		CustomReporter.log("The cards are selected as per step 5");
		
		
	   //Step14: Click on analyze audience button
		ad.func_ClickElement("AnalyzeAudience");
		CustomReporter.log("The user has clicked on Analyse button");
		Thread.sleep(3000);
		
		//Step 15:Verify nodes in map appears as noted in step 6
		if(ap.func_ElementExist("ProfilePage")){
			CustomReporter.log("The profile page is getting opened");
		
			if(ap.func_ElementExist("Local Market Map Icon")){
				CustomReporter.log("The map nodes  are existing in local market");
				
				//click on one of the node to capture values
				nodeValues3=ap.RetrievelocalmarketData();				
				
				CustomReporter.log("The node data has been captures from each node");
				
				//Check whether the node values changes as  noted in step 6
				if(rm.CompareHashTables(nodeValues3, nodeValues1)){
					CustomReporter.log("The node values are as coming in step 6");
				}
				else{
					CustomReporter.errorLog("The node values are not same as the step 6 eventhough the suer selects same filters as in step6");
					BaseClass.testCaseStatus=false;
				}
			}
			else{
				CustomReporter.errorLog("The map nodes are not existing in local market");
				BaseClass.testCaseStatus=false;
			}
			
			//Click on audience in main nav
			ap.func_ClickElement("Audience");
			CustomReporter.log("The use has clicked on 'audience' tab");
		}
		else
		{
			CustomReporter.errorLog("The profile page is NOT getting opened on clicking on 'Analyse icon'");
			BaseClass.testCaseStatus=false;
		}
		Thread.sleep(3000);
		//Go to manage Idiom page and delete the created idiom
		ad.func_ClickElement("Manage");
		Thread.sleep(3000);
		
		mi.func_PerformClickAction("Delete");
		Thread.sleep(1000);
		
		//Step16:Logout from the application
		mi.func_PerformClickAction("Logout");
		CustomReporter.log("The user has been logged out successfully");
		
		//****************Test step ends here************************************************
		if(BaseClass.testCaseStatus == false){
				CustomReporter.errorLog("Test case has failed");
				Assert.assertTrue(false);
		}				
				
				
		
}
}
