/********************************************************************
Test Case Name: **Super User Admin - Super User Admin Header - Verify Super User Admin Header Return to IDIOM link Click
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8283.aspx
Objective: This test case is meant for checking Return to IDIOM link Click in Header
Module: UserAdminAndUserPermissions
Created By:Amrutha Nair
Date: 2 December 2015
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
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;
import com.msat.frameworks.data_driven.base.Base;
import com.reports.CustomReporter;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;

import common.BaseClass;
import common.IDIOMException;
import common.ReusableMethods;

public class UserAdmin8283 extends BaseClass {
		
	@Test
	public void verifyreturntoidiombtn() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		//****************Variables declaration and Initialization****************************
		
				String emailid = property.getProperty("SuperAdminUser");
				String password = property.getProperty("SuperAdminpassword");			
				String clientName="Beauty";//property.getProperty("clientName");
				
			//****************Test step starts here************************************************
				//Step1: Launch browser and enter IDIOM Url
				CustomReporter.log("Launched Browser and Enter URL");
				Login_Page ln = new Login_Page(driver);
				
							
				//Step2: Login with valid Super Admin user 
				
		        ln.func_LoginToIDIOM(emailid, password);	
			    CustomReporter.log("Logged in successfully");
			    			    
			    //Step 3: Click on menu icon at the top and click on Admin Access
			    ClientList_Page cl= new ClientList_Page(driver);
			    PageHeader ph=new PageHeader(driver);
			    
			    //rm.webElementSync("pageload");
		        //rm.webElementSync("jqueryload");
		        //rm.webElementSync(pageHeader.personMenu, "visibility");
			    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
			    //Thread.sleep(5000);
			    ph.performAction("adminaccess");
			    Thread.sleep(15000);
			    /*rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        rm.webElementSync(pageHeader.personMenu, "visibility");*/
				CustomReporter.log("The user has clicked on 'Admin Access'");	    
				//Thread.sleep(7000);				    		    				   				    
				//Step4: Go to project dashboard page and open Success Metrics page
				up.func_ClickElement("Return To Idiom");
				rm.webElementSync("pageload");
			    rm.webElementSync("jqueryload");
			    Thread.sleep(5000);
			    cl.selectClient(clientName);
			    //cl.func_SelectClient(clientName);--commented by Sunil 09 May 2016
				CustomReporter.log("The client "+clientName+" has been opened");
				Thread.sleep(5000);
				
				ManageIdiom_Page mi = new ManageIdiom_Page(driver);							
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
					int selectProjectId = totalProjects/totalProjects;
					cl.clickProjectById(selectProjectId);
					CustomReporter.log("Selected a project");
					Thread.sleep(5000);
				}
				ProjectDashboardPage pdp=new ProjectDashboardPage(driver);
			    pdp.navigateTo("Success Metrics");
			    CustomReporter.log("Navigated to success metrics page");
			    rm.webElementSync("pageload");
			    rm.webElementSync("jqueryload");
			    Thread.sleep(5000);
			    //Step 5:Click on menu icon at the top and click on Admin Access 
				ph.performAction("adminaccess");
				rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        rm.webElementSync(pageHeader.personMenu, "visibility");
		        CustomReporter.log("Admin access page has opened");
			    Thread.sleep(3000);
		        
		        //Step 6:Click on Return to IDIOM link. 
			    AudienceBuilderPage ab=new AudienceBuilderPage(driver);
		        up.func_ClickElement("Return To Idiom");
		        rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        rm.webElementSync(pageHeader.personMenu, "visibility");
		        Thread.sleep(5000);
		        ab.isVisible("nosuccessmetrictext", "");
		        CustomReporter.log("Back to Success Metrics page");
		        System.out.println("Now in success metrics page");
		        Thread.sleep(5000);
		        
				//Step7: Go to project dashboard page and open Audience Definition page 
		        ph.megaMenuLinksClick("Audience Definition");
		        rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
				CustomReporter.log("Audience definition page has opened");
				Thread.sleep(5000);
				//Step8: Click on menu icon at the top and click on Admin Access 
   				ph.performAction("adminaccess");   			
   				rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        rm.webElementSync(pageHeader.personMenu, "visibility");
		        CustomReporter.log("Admin access page has opened");
		        Thread.sleep(10000);
		        //Step9:Click on Return to IDIOM link
		        up.func_ClickElement("Return To Idiom");
		        rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        ab.isVisible("nosuccessmetrictext", "");
		        CustomReporter.log("Back to Audience definition page");
		        
				//Step10:Go to project dashboard page and open Profile page 
								
			    if (ph.isVisible("header_megamenu_icon"))
			    {
			    	ph.megaMenuLinksClick("Profile");
			    	
			    	rm.webElementSync("pageload");
			    	rm.webElementSync("jqueryload");
			    	Thread.sleep(5000);
			        CustomReporter.log("Landed on profile page");
			    }
		        //pdp.navigateTo("Profile");
				else{	
				    CustomReporter.log("Mega menu icon not visible");	
				}
			    
			    //Step11: Click on menu icon at the top and click on Admin Access
			    Thread.sleep(10000);
			    ph.performAction("adminaccess");
   				rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        rm.webElementSync(pageHeader.personMenu, "visibility");
		        CustomReporter.log("Admin access page has opened");
		        Thread.sleep(5000);
		        
		        //Step12: Click on Return to IDIOM link.
		        Analyse_Profile_Page ap=new Analyse_Profile_Page(driver);
		        up.func_ClickElement("Return To Idiom");		        
		        rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        Thread.sleep(5000);
		        ap.isVisible("summary");
		        ap.isVisible("interest");
		        ap.isVisible("demographics");
		        ap.isVisible("local market");
		        CustomReporter.log("Back to profile page");
		        
		        //Step13: Go to project dashboard page and open HVA page
			    
		        if (ph.isVisible("header_megamenu_icon"))
			    {
			    	ph.megaMenuLinksClick("HVA");					
					rm.webElementSync("pageload");
			        rm.webElementSync("jqueryload");
			        Thread.sleep(5000);
			        CustomReporter.log("Landed on HVA page");
			    }				 
				else{	
				    CustomReporter.log("Mega menu icon not visible");	
				}
		        
		        
		        //Step14: Click on menu icon at the top and click on Admin Access
		        ph.performAction("adminaccess");
   				rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        rm.webElementSync(pageHeader.personMenu, "visibility");
		        CustomReporter.log("Admin access page has opened");
		        
		        //Step15: Click on Return to IDIOM link.
		        Thread.sleep(3000);
		        HVA_Page hva=new HVA_Page(driver);
		        up.func_ClickElement("Return To Idiom");
		        CustomReporter.log("Clicked on 'Return to Idiom'");
		        rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        Thread.sleep(5000);
		        hva.isVisible("hva_chart");
		        CustomReporter.log("Back to HVA page");
		        
		        //Step16: Go to project dashboard page and open Webnet page
			    
		        if (ph.isVisible("header_megamenu_icon"))
			    {
			    	ph.megaMenuLinksClick("Webnet");					
					rm.webElementSync("pageload");
			        rm.webElementSync("jqueryload");
			        Thread.sleep(5000);
			        CustomReporter.log("Landed on Webnet page");
			    }				 
				else{	
				    CustomReporter.log("Mega menu icon not visible");	
				}
		        
		        
		        //Step17: Click on menu icon at the top and click on Admin Access
		        ph.performAction("adminaccess");
		        Thread.sleep(5000);
   				rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        rm.webElementSync(pageHeader.personMenu, "visibility");
		        CustomReporter.log("Admin access page has opened");
		        
		        //Step18: Click on Return to IDIOM link.
		        Analyse_Webnet_Page aw=new Analyse_Webnet_Page(driver);
		        up.func_ClickElement("Return To Idiom");
		        CustomReporter.log("Clicked on 'Return to Idiom'");
		        rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        Thread.sleep(5000);
		        aw.isVisible("webnetimage");
		        CustomReporter.log("Back to webnet page");

		        
		        //Step19: Go to project dashboard page and open Pathing page
		        Analyse_Pathing_Page path=new Analyse_Pathing_Page(driver);
			    
		        if (ph.isVisible("header_megamenu_icon"))
			    {
		        	Thread.sleep(3000);
			    	ph.megaMenuLinksClick("Pathing");					
					rm.webElementSync("pageload");
			        rm.webElementSync("jqueryload");
			        path.isVisible("pathing_wheel");
			        Thread.sleep(5000);
			        CustomReporter.log("Landed on Pathing page");
			    }				 
				else{	
				    CustomReporter.log("Mega menu icon not visible");	
				}
		        
		        
		        //Step20: Click on menu icon at the top and click on Admin Access
		        ph.performAction("adminaccess");
		        Thread.sleep(3000);
   				rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        rm.webElementSync(pageHeader.personMenu, "visibility");
		        CustomReporter.log("Admin access page has opened");
		        
		        //Step21: Click on Return to IDIOM link.
		       
		        up.func_ClickElement("Return To Idiom");
		        CustomReporter.log("Clicked on 'Return to Idiom'");
		        rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        Thread.sleep(5000);
		        path.isVisible("pathing_wheel");
		        CustomReporter.log("Back to pathing page");

//-------------------------------------------------------------------------
		        
		        //Step22: Go to project dashboard page and open Digital Ranker page
			    
		        if (ph.isVisible("header_megamenu_icon"))
			    {
			    	ph.megaMenuLinksClick("Digital Ranker");
					rm.webElementSync("pageload");
			        rm.webElementSync("jqueryload");
			        CustomReporter.log("Landed on Digital Ranker page");
			    }				 
				else{	
				    CustomReporter.log("Mega menu icon not visible");	
				}
		        
		        
		        //Step23: Click on menu icon at the top and click on Admin Access
		        Thread.sleep(7000);
		        ph.performAction("adminaccess");
		        Thread.sleep(5000);
   				rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        rm.webElementSync(pageHeader.personMenu, "visibility");
		        CustomReporter.log("Admin access page has opened");
		        
		        //Step24: Click on Return to IDIOM link.
		        
		        up.func_ClickElement("Return To Idiom");
		        CustomReporter.log("Clicked on 'Return to Idiom'");
		        rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        Thread.sleep(7000);
		        ArchitectMediaRankerPage mr = new ArchitectMediaRankerPage(driver);
				if((mr.func_VerifyElementExist("metricbubbleplot")) && mr.func_VerifyElementExist("mediarankertextinmenu") && mr.func_VerifyElementExist("categorylist"))
				{
					CustomReporter.log("Back to Digital Ranker Page");
				}
		        Thread.sleep(5000);

//------------------------------------------------
		        //Step25: Go to project dashboard page and open TV Ranker page
			    
		        if (ph.isVisible("header_megamenu_icon"))
			    {
			    	ph.megaMenuLinksClick("TV Ranker");					
					rm.webElementSync("pageload");
			        rm.webElementSync("jqueryload");
			        CustomReporter.log("Landed on TV Ranker page");			        
			        Thread.sleep(5000);
			    }				 
				else{	
				    CustomReporter.log("Mega menu icon not visible");	
				}
		        
		        
		        //Step26: Click on menu icon at the top and click on Admin Access
		        Thread.sleep(5000);
		        ph.performAction("adminaccess");
   				rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        rm.webElementSync(pageHeader.personMenu, "visibility");
		        CustomReporter.log("Admin access page has opened");
		        
		        //Step27: Click on Return to IDIOM link.
		        Thread.sleep(5000);
		        up.func_ClickElement("Return To Idiom");
		        CustomReporter.log("Clicked on 'Return to Idiom'");
		        rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        Thread.sleep(7000);
				if((mr.func_VerifyVisibilityOfElement("scatteredplot")))
				{
					CustomReporter.log("Back to TV Ranker Page");
				}
		        Thread.sleep(7000);
		        
				//Step28:Log out from the application
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
	    