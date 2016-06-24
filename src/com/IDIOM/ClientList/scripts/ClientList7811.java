/********************************************************************
Test Case Name: Client List_Verify Analize option
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/7811.aspx
Module: Client List
Created By: Abhishek Deshpande
Date: 04th AUG 2015
**********************************************************************/

package com.IDIOM.ClientList.scripts;
import org.testng.Assert;
import org.testng.annotations.Test;


import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.reports.CustomReporter;
//import com.msat.frameworks.data_driven.generic.reusables.GenericComponents;

import common.BaseClass;

public class ClientList7811 extends BaseClass {
		
	@Test
	public void test_ClientList7811() throws InterruptedException {
		
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		CustomReporter.log("Enter valid email id and password");
		CustomReporter.log("Click on login to Idiom button");
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");
		String Client=property.getProperty("clientName");
		ln.func_LoginToIDIOM(emailid, password);	
		
		CustomReporter.log("User should land in Client List page");
		ClientList_Page CL = new ClientList_Page(driver);	
		
		CustomReporter.log("Verify Help Icon is displayed");
		CL.func_ClientListPageElementExist("Help Icon");		
		
		CustomReporter.log("Select "+Client+" from client list page");
		CL.func_SelectClient(Client);
		
		rm.waitTime(5000);
		
		CustomReporter.log("Select any existing Idiom");
		ManageIdiom_Page MI = new ManageIdiom_Page(driver);
		if(MI.func_ElementExist("CreateNewIdiom")){
			CustomReporter.log("'Create New Idiom' button exists");
		}else{
			CustomReporter.errorLog("'Create New Idiom' button does NOT exist");
			BaseClass.testCaseStatus= false;
		}
		int num=2;
		MI.func_SelectExistingIdiomByNumber(num);
		
		CustomReporter.log("User is navigated to Audience page");
		Audience_Page AP = new Audience_Page(driver);
		
		CustomReporter.log("Top menu should contain items: Audience, Analyze, Architect, Activate");
		if(AP.func_AudiencePageElementExist("AudienceLabel")){
			CustomReporter.log("AudienceLabel is exising");
		}
		else{
			CustomReporter.errorLog("AudienceLabel is NOT exising");
			BaseClass.testCaseStatus= false;
		}
		if(AP.func_AudiencePageElementExist("AnalyzeLabel")){
			CustomReporter.log("AnalyzeLabel is exising");
		}
		else{
			CustomReporter.errorLog("AnalyzeLabel is NOT exising");
			BaseClass.testCaseStatus= false;
		}
		
		if(AP.func_AudiencePageElementExist("ArchitectLabel")){
			CustomReporter.log("ArchitectLabel is exising");
		}
		else{
		CustomReporter.errorLog("ArchitectLabel is NOT exising");
		BaseClass.testCaseStatus= false;
		}
		if(AP.func_AudiencePageElementExist("ActivateLabel")){
			CustomReporter.log("ActivateLabel is exising");
		}
		else{
		CustomReporter.errorLog("ActivateLabel is NOT exising");
		BaseClass.testCaseStatus= false;
		}
		if(AP.func_AudiencePageElementExist("HelpIcon")){
			
			CustomReporter.log("HelpIcon is exising");
		}
		else{
		CustomReporter.errorLog("HelpIcon is NOT exising");
		BaseClass.testCaseStatus= false;
		}
		
		CustomReporter.log("Click on Analyze label");
		AP.func_ClickElement("AnalyzeLabel");
		
		rm.waitTime(3000);
		
		
		Analyse_Profile_Page An = new Analyse_Profile_Page(driver);
		if(An.func_ElementExist("AnalysePageExists")){
			CustomReporter.log("The Analyze page is opened");
			CustomReporter.log("Click on Logout link");
			rm.waitTime(3000);
			An.func_ClickElement("Logout");
		}
		else{
			CustomReporter.errorLog("Analyze is NOT opened");
			BaseClass.testCaseStatus= false;
		}		
		
		
		
		if(BaseClass.testCaseStatus==false){
			CustomReporter.errorLog("Test case has failed");
			Assert.assertTrue(false);
		}
		
				
	}

}

