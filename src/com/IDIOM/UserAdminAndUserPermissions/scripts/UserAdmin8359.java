/********************************************************************
Test Case Name: **Super User Admin - Accessing Admin - Verify Red Admin Link
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8259.aspx
Objective: Verify that the app users are getting changed to client admin on key icon.
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 27 November 2015
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;
import com.msat.frameworks.data_driven.base.Base;
import com.reports.CustomReporter;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;

import common.BaseClass;
import common.ReusableMethods;

public class UserAdmin8359 extends BaseClass {
	
	
	@Test
	public void verifyadminlinkvisibility() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword");			
		String clientName=property.getProperty("clientName");
		
	//****************Test step starts here************************************************
		//Step1: LoLoad the URL and log in with valid Super User Admin Credentials
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		Thread.sleep(3000);
		
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    //Step 2:Verify all pages to check Admin link's color and its present
	    //And
	    //Step 3: Click on Red Admin link
	    ClientList_Page cl= new ClientList_Page(driver);
	    PageHeader ph=new PageHeader(driver);
	    Thread.sleep(5000);
	    rm.webElementSync("pageload");
        rm.webElementSync("jqueryload");
        rm.webElementSync(pageHeader.personMenu, "visibility");
	    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
	    ph.performAction("adminaccesspresent");
	    if (ph.isVisible("adminaccess")){
	    	CustomReporter.log("'Admin Access' link is present in Client List page");	   
	    /*if(cl.func_ClientListPageElementExist("Admin Access Present")){
	    	CustomReporter.log("'Admin Access' link is present in Client List page");--commented by Sunil 09 May 2016*/
            Thread.sleep(5000);
	    	//Click on Admin Access
	    	ph.performAction("adminaccess");//modified by Sunil 09 May 2016
	    	//cl.func_PerformAction("AdminAccess");
		    CustomReporter.log("The user has clicked on 'Admin Access'");	    
		    		    
		 
		    Thread.sleep(3000);
		    		    
		    if(up.func_ElementExist("Users List")){		    	
		    	CustomReporter.log("The user listing is displayed");
		    }
		    else{
		    	CustomReporter.errorLog("The user listing is Not displayed");
	    		BaseClass.testCaseStatus=false;
		    }
		    
		    //Click on 'Return to Idiom'
		    up.func_ClickElement("Return To Idiom");
		    Thread.sleep(3000);
	    }
	    else
	    {
	    	CustomReporter.errorLog("The Admin access is not coming in client list page Menu");
    		BaseClass.testCaseStatus=false;
	    }
	    
	    
	    cl.selectClient(clientName);
	    //cl.func_SelectClient(clientName);--commented by Sunil 09 May 2016
		CustomReporter.log("The client "+clientName+" has been opened");
		Thread.sleep(3000);
		
		ManageIdiom_Page mi = new ManageIdiom_Page(driver);
		 Thread.sleep(5000);
		/* if(mi.func_ElementExist("Admin Access Present")){
		    	CustomReporter.log("'Admin Access' link is present in ManageIdiom page");
		    	
		    	//Click on Admin Access
		    	mi.func_PerformClickAction("AdminAccess");
			    CustomReporter.log("The user has clicked on 'Admin Access'");	    
			    		    
			 
			    Thread.sleep(3000);
			    		    
			    if(up.func_ElementExist("Users List")){		    	
			    	CustomReporter.log("The user listing is displayed");
			    }
			    else{
			    	CustomReporter.errorLog("The user listing is Not displayed");
		    		BaseClass.testCaseStatus=false;
			    }
			    
			    //Click on 'Return to Idiom'
			    up.func_ClickElement("Return To Idiom");
			    Thread.sleep(3000);
		    }
	    else
	    {
	    	CustomReporter.errorLog("The Admin access is not coming in ManageIdiom page Menu");
    		BaseClass.testCaseStatus=false;
	    }*/
		 
		/*mi.func_PerformClickAction("SelectFirstIdiom");
		CustomReporter.log("The user has clicked on existing idiom");
		Thread.sleep(8000);--commented by Sunil 09 May 2016*/
		//clientListPage cl=new clientListPage(driver);
		int totalProjects = cl.totalProject();
		System.out.println("Total projects: "+totalProjects);
		
		String strProjectName;
		Boolean bProjectCreate;
		//Step 3: Create/Select project and launch the same 
		if(totalProjects == 0){
			CustomReporter.log("Creating new project as no project for this client");
			System.out.println("Creating new project as no project for this client");
			strProjectName = cl.createNewProject("");
			cl.func_PerformAction("Launch Project");
			bProjectCreate = true;
			
		}else{
			CustomReporter.log("Selecting existing project");
			System.out.println("Selecting existing project");
			int selectProjectId = totalProjects;
			cl.clickProjectById(selectProjectId);
			CustomReporter.log("Selected a project");
			Thread.sleep(5000);
		}
						
		ph.performAction("adminaccesspresent");
	    if (ph.isVisible("adminaccess")){
	    	CustomReporter.log("'Admin Access' link is present in Manage idiom page");
	    	   
	    /*ph.performAction("adminaccess");//modified by Sunil 09 May 2016	    	
		CustomReporter.log("The user has clicked on 'Admin Access'");*/
	    }
	    else
	    {	    	
	    	CustomReporter.errorLog("The Admin access is not appearing in Audience page Menu");
			BaseClass.testCaseStatus=false;
	    }
	    ProjectDashboardPage pdp=new ProjectDashboardPage(driver);
	    pdp.navigateTo("Success Metrics");
	    Thread.sleep(3000);
	    
	    //Check whether admin access is available in Success Metrics page
			    	
	    ph.performAction("adminaccesspresent");
	    if (ph.isVisible("adminaccess")){
	    	CustomReporter.log("'Admin Access' link is present in Success Metrics page");
	    	   
	   /* ph.performAction("adminaccess");//modified by Sunil 09 May 2016	    	
		CustomReporter.log("The user has clicked on 'Admin Access'");*/
	    }
	    else
	    {	    	
	    	CustomReporter.errorLog("The Admin access is not appearing in Success Metrics page Menu");
			BaseClass.testCaseStatus=false;
	    }
	    
		Thread.sleep(4000);
		System.out.println("Now in success metrics page");
		//Return to Idiom main page
		//Navigate back to manage idiom page and select an existing project 
		//-------------------
		ph.megaMenuLinksClick("Audience Definition");
		CustomReporter.log("clicked on Audience Definition");
						
		ph.performAction("adminaccesspresent");
	    if (ph.isVisible("adminaccess")){
	    	CustomReporter.log("'Admin Access' link is present in Audience definition page");
	    }
	    else
	    {	    	
	    	CustomReporter.errorLog("The Admin access is not appearing in Audience definition page Menu");
			BaseClass.testCaseStatus=false;
	    }
	    Thread.sleep(3000);
		//-----------------------
	    if (ph.isVisible("header_megamenu_icon"))
	    {
	    	ph.megaMenuLinksClick("Profile");
			CustomReporter.log("clicked on Profile");
			rm.webElementSync("pageload");
	        rm.webElementSync("jqueryload");
	        rm.webElementSync(pageHeader.personMenu, "visibility");
			/*CustomReporter.log("Back to Audience page");*/				
			ph.performAction("adminaccesspresent");
		    if (ph.isVisible("adminaccess")){
		    	CustomReporter.log("'Admin Access' link is present in Profile page");
		    	}
		    else{
		    	CustomReporter.log("Admin access is not visible in Profile page");
		    }
		 }
		else
		{
		    CustomReporter.log("Mega menu icon not visible");	
		    }
	    
	    Thread.sleep(5000);
	    
	    if (ph.isVisible("header_megamenu_icon"))
	    {
	    	ph.megaMenuLinksClick("Webnet");
			CustomReporter.log("clicked on Webnet");
			rm.webElementSync("pageload");
	        rm.webElementSync("jqueryload");
	        rm.webElementSync(pageHeader.personMenu, "visibility");
			/*CustomReporter.log("Back to Audience page");*/				
			ph.performAction("adminaccesspresent");
		    if (ph.isVisible("adminaccess")){
		    	CustomReporter.log("'Admin Access' link is present in Webnet page");
		    	}
		    else{
		    	CustomReporter.log("Admin access is not visible in Webnet page");
		    }
		 }
		else
		{
		    CustomReporter.log("Mega menu icon not visible");	
		    }
	    //Navigating to HVA Page
	    Thread.sleep(5000);

	    if (ph.isVisible("header_megamenu_icon"))
	    {
	    	ph.megaMenuLinksClick("HVA");
			CustomReporter.log("clicked on HVA");
			rm.webElementSync("pageload");
	        rm.webElementSync("jqueryload");
	        rm.webElementSync(pageHeader.personMenu, "visibility");
			/*CustomReporter.log("Back to Audience page");*/				
			ph.performAction("adminaccesspresent");
		    if (ph.isVisible("adminaccess")){
		    	CustomReporter.log("'Admin Access' link is present in HVA page");
		    	}
		    else{
		    	CustomReporter.log("Admin access is not visible in HVA page");
		    }
		 }
		else
		{
		    CustomReporter.log("Mega menu icon not visible");	
		    }
	    //Navigating to Pathing page
	    Thread.sleep(5000);

	    if (ph.isVisible("header_megamenu_icon"))
	    {
	    	ph.megaMenuLinksClick("Pathing");
			CustomReporter.log("clicked on Pathing");
			rm.webElementSync("pageload");
	        rm.webElementSync("jqueryload");
	        rm.webElementSync(pageHeader.personMenu, "visibility");
			/*CustomReporter.log("Back to Audience page");*/				
			ph.performAction("adminaccesspresent");
		    if (ph.isVisible("adminaccess")){
		    	CustomReporter.log("'Admin Access' link is present in Pathing page");
		    	}
		    else{
		    	CustomReporter.log("Admin access is not visible in Pathing page");
		    }
		 }
		else
		{
		    CustomReporter.log("Mega menu icon not visible");	
		    }
	    //Navigating to Digital Ranker Page
	    Thread.sleep(5000);

	    if (ph.isVisible("header_megamenu_icon"))
	    {
	    	ph.megaMenuLinksClick("Digital Ranker");
			CustomReporter.log("clicked on Digital Ranker");
			rm.webElementSync("pageload");
	        rm.webElementSync("jqueryload");
	        rm.webElementSync(pageHeader.personMenu, "visibility");
			/*CustomReporter.log("Back to Audience page");*/				
			ph.performAction("adminaccesspresent");
		    if (ph.isVisible("adminaccess")){
		    	CustomReporter.log("'Admin Access' link is present in Digital Ranker page");
		    	}
		    else{
		    	CustomReporter.log("Admin access is not visible in Digital Ranker page");
		    }
		 }
		else
		{
		    CustomReporter.log("Mega menu icon not visible");	
		    }

	    //Navigating to TV Ranker Page
	    Thread.sleep(10000);
	    ArchitectMediaRankerPage mr = new ArchitectMediaRankerPage(driver);
	    if (ph.isVisible("header_megamenu_icon"))
	    {
	    	ph.megaMenuLinksClick("TV Ranker");
			CustomReporter.log("clicked on TV Ranker");
			Thread.sleep(15000);
			rm.webElementSync("pageload");
	        rm.webElementSync("jqueryload");
	        rm.webElementSync(pageHeader.personMenu, "visibility");
	        mr.func_VerifyVisibilityOfElement("scatteredplot");
	        
			/*CustomReporter.log("Back to Audience page");*/				
			ph.performAction("adminaccesspresent");
		    if (ph.isVisible("adminaccess")){
		    	CustomReporter.log("'Admin Access' link is present in TV Ranker page");
		    	}
		    else{
		    	CustomReporter.log("Admin access is not visible in TV Ranker page");
		    }
		 }
		else
		{
		    CustomReporter.log("Mega menu icon not visible");	
		    }

		//Step 4:Log out from the application
	    /*ph.performAction("logout");
	    CustomReporter.log("The user has been logged out");*/
		 //up.func_ClickElement("logout");
		 //CustomReporter.log("The user has been logged out");
		//****************Test step ends here************************************************
		if(BaseClass.testCaseStatus == false){
				CustomReporter.errorLog("Test case has failed");
				Assert.assertTrue(false);
		}	
}	
}
	    