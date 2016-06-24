/********************************************************************
Test Case Name: *494_Profile page_Verify sorting in graph
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8235.aspx
Objective:Verify sorting of graph in demographics section
Module: Analyse>Profile
Created By: Amrutha Nair
Date: 16 September 2015
**********************************************************************/

package com.IDIOM.Analyse.Profile.scripts;

import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class Profile8235 extends BaseClass {
		
	@Test
	public void test_Profile8235() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");			
		String clientName=property.getProperty("defaultClient");
		String idiomName=null;
		
		
	//****************Test step starts here************************************************	
		
		//Step1: Log into application  with valid creds
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		
		Thread.sleep(3000);
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    
	    //Step2:Click on any client
	    ClientList_Page cl = new ClientList_Page(driver);
		cl.func_SelectClient(clientName);
		CustomReporter.log("The client "+clientName+" has been opened");
		Thread.sleep(3000);
		
		//Step3: Create new/Open existing idiom
		ManageIdiom_Page mi = new ManageIdiom_Page(driver);
		Thread.sleep(8000);
		idiomName=mi.func_CreateIdiomName("Idiom","New");
		mi.func_CreateNewIdiomOrRename(idiomName, "NotBlank");
		CustomReporter.log("The idiom '"+idiomName+"' is created.");
		
		
		//
		
	}

}
