/********************************************************************
Test Case Name: *Super User Admin - Accessing Admin - Verify Red Admin Link when user logs in as a General User
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8360.aspx
Objective: This test case is for verifying that Admin link does not appear beside Logout link as a General User
Module: User Admin Functionality
Created By: Rohan Macwan
Date: 27 November 2015
**********************************************************************/

package com.IDIOM.UserAdminAndUserPermissions.scripts;

import java.text.ParseException;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;

import common.BaseClass;

public class UserAdmin8360 extends BaseClass{
	
	@Test	
	public void verifynormaluseradminvisibility() throws Exception {
		
		try{
		//****************Variables declaration and Initialization****************************
		
				String emailid = property.getProperty("GeneralUserEmail");
				String password = property.getProperty("GeneralUserPassword");			
				String clientName=property.getProperty("clientName");
				
			//****************Test step starts here************************************************
				//Step1: Launch browser and enter IDIOM URL
				CustomReporter.log("Launched Browser and Enter URL");
				Login_Page ln = new Login_Page(driver);
				
				Thread.sleep(3000);
				//Step2: Login with the credentials of application user
		        ln.func_LoginToIDIOM(emailid, password);	
			    CustomReporter.log("Logged in successfully");
			    
			    ClientList_Page cl= new ClientList_Page(driver);
			    PageHeader ph=new PageHeader(driver);
			    UserAdmin_UserPermissions_Page up=  new UserAdmin_UserPermissions_Page(driver);
			    ProjectDashboardPage pdp=new ProjectDashboardPage(driver);
			    //wait for the page to load
    			Thread.sleep(5000);
			    rm.webElementSync("pageload");
		        rm.webElementSync("jqueryload");
		        rm.webElementSync(pageHeader.personMenu, "visibility");
		        
			    //Step3: Check whether admin access link is visible from project dashboard
		        
			    ph.performAction("adminaccesspresent");
			    if  (ph.isVisible("adminaccess")==false){
			    	CustomReporter.log("'Admin Access' link is not present in Client List page");	   			        				   
			    }
			    else {
			    	CustomReporter.errorLog("The Admin access is visible for the user in client list page Menu");
		    		BaseClass.testCaseStatus=false;
			    }
			    
			    //back to idiom page
			    //up.func_ClickElement("Idiom Logo");
			    
			    //step4:Navigate to project dashboard and open success metrics page
			    
			    cl.selectClient(clientName);
				CustomReporter.log("The client "+clientName+" has been opened");
				Thread.sleep(3000);											 				 			
				int totalProjects = cl.totalProject();
				System.out.println("Total projects: "+totalProjects);
				
				String strProjectName;
				Boolean bProjectCreate;
				//Create/Select project and launch the same 
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
				}
				Thread.sleep(5000);
				rm.webElementSync("pageload");
			    rm.webElementSync("jqueryload");
				ph.performAction("adminaccesspresent");
			    pdp.navigateTo("Success Metrics");
			    rm.webElementSync("pageload");
			    rm.webElementSync("jqueryload");
			    
			    //Step5 :Click on Person icon from top right corner and verify Admin access link is available or not
					    	
			    ph.performAction("adminaccesspresent");
			    if (ph.isVisible("adminaccess")==false){
			    	CustomReporter.log("'Admin Access' link is not present in Success Metrics page");
			    }
			    else{	    	
			    	CustomReporter.errorLog("The Admin access is visible to user in Success Metrics page Menu");
					BaseClass.testCaseStatus=false;
			    }
			    //Step6 :Open Audience Definition page from megamenu
				ph.megaMenuLinksClick("Audience Definition");
				CustomReporter.log("clicked on Audience Definition");
				rm.webElementSync("pageload");
			    rm.webElementSync("jqueryload");
			    
				//Step7 :Click on Person icon from top right corner and verify Admin access link is available or not 
				ph.performAction("adminaccesspresent");
			    if (ph.isVisible("adminaccess")==false){
			    	CustomReporter.log("'Admin Access' link is not present in Audience definition page");
			    }
			    else{	    	
			    	CustomReporter.errorLog("The Admin access is visible to user in Audience definition page Menu");
					BaseClass.testCaseStatus=false;
			    }
			    Thread.sleep(3000);
			    
			    //Step8 :Go to project dashboard page and open Profile page
			    if (ph.isVisible("header_megamenu_icon"))
			    {
			    	ph.megaMenuLinksClick("Profile");
					CustomReporter.log("clicked on Profile");
					rm.webElementSync("pageload");
			        rm.webElementSync("jqueryload");
			        rm.webElementSync(pageHeader.personMenu, "visibility");
			        
				//Step9 :Click on Person icon from top right corner and verify Admin access link is available or not		
					ph.performAction("adminaccesspresent");
				    if (ph.isVisible("adminaccess")==false){
				    	CustomReporter.log("'Admin Access' link is not present in Profile page");
				    	}
				    else{
				    	CustomReporter.errorLog("Admin access is visible to user in Profile page");
				    }
				 }
				else
				{
				    CustomReporter.log("Mega menu icon not visible");	
				    }
			    Thread.sleep(2000);
			    
			    //Step 10: Go to project dashboard page and open WebNet page 
			    if (ph.isVisible("header_megamenu_icon"))
			    {
			    	ph.megaMenuLinksClick("Webnet");
					CustomReporter.log("clicked on Webnet");
					rm.webElementSync("pageload");
			        rm.webElementSync("jqueryload");
			        rm.webElementSync(pageHeader.personMenu, "visibility");
					
			    //Step 11: Click on Person icon from top right corner and verify Admin access link is available or not 
			        
					ph.performAction("adminaccesspresent");
				    if (ph.isVisible("adminaccess")==false){
				    	CustomReporter.log("'Admin Access' link is not present in Webnet page");
				    	}
				    else{
				    	CustomReporter.errorLog("Admin access is visible to user in Webnet page");
				    }
				 }
				else
				{
				    CustomReporter.log("Mega menu icon not visible");	
				    }			    			 
			    Thread.sleep(2000);
			    
			    //Step 12:Go to project dashboard page and open HVA page
			    if (ph.isVisible("header_megamenu_icon"))
			    {
			    	ph.megaMenuLinksClick("HVA");
					CustomReporter.log("clicked on HVA");
					rm.webElementSync("pageload");
			        rm.webElementSync("jqueryload");
			        rm.webElementSync(pageHeader.personMenu, "visibility");
			        
			    //Step 13:Click on Person icon from top right corner and verify Admin access link is available or not 
					ph.performAction("adminaccesspresent");
				    if (ph.isVisible("adminaccess")==false){
				    	CustomReporter.log("'Admin Access' link is not present in HVA page");
				    	}
				    else{
				    	CustomReporter.errorLog("Admin access is visible to user in HVA page");
				    }
				 }
				else
				{
				    CustomReporter.log("Mega menu icon not visible");	
				    }
			    //Step 14: Go to project dashboard page and open Pathing page 			    
			    if (ph.isVisible("header_megamenu_icon"))
			    {
			    	ph.megaMenuLinksClick("Pathing");
					CustomReporter.log("clicked on Pathing");
					rm.webElementSync("pageload");
			        rm.webElementSync("jqueryload");
			        rm.webElementSync(pageHeader.personMenu, "visibility");
			        
			    //Step 15: Click on Person icon from top right corner and verify Admin access link is available or not    
					ph.performAction("adminaccesspresent");
				    if (ph.isVisible("adminaccess")==false){
				    	CustomReporter.log("'Admin Access' link is not present in Pathing page");
				    	}
				    else{
				    	CustomReporter.errorLog("Admin access is visible to user in Pathing page");
				    }
				 }
				else
				{
				    CustomReporter.log("Mega menu icon not visible");	
				    }
			    //Step 16:Go to project dashboard page and open Digital Ranker page
			    
			    if (ph.isVisible("header_megamenu_icon"))
			    {
			    	ph.megaMenuLinksClick("Digital Ranker");
					CustomReporter.log("clicked on Digital Ranker");
					rm.webElementSync("pageload");
			        rm.webElementSync("jqueryload");
			        rm.webElementSync(pageHeader.personMenu, "visibility");
			        
			    //Step 17:Click on Person icon from top right corner and verify Admin access link is available or not 
					ph.performAction("adminaccesspresent");
				    if (ph.isVisible("adminaccess")==false){
				    	CustomReporter.log("'Admin Access' link is not present in Digital Ranker page");
				    	}
				    else{
				    	CustomReporter.errorLog("Admin access is visible to user in Digital Ranker page");
				    }
				 }
				else
				{
				    CustomReporter.log("Mega menu icon not visible");	
				    }

			    //Step 18: Go to project dashboard page and open TV Ranker page			    

			    if (ph.isVisible("header_megamenu_icon"))
			    {
			    	ph.megaMenuLinksClick("TV Ranker");
					CustomReporter.log("clicked on TV Ranker");
					rm.webElementSync("pageload");
			        rm.webElementSync("jqueryload");
			        rm.webElementSync(pageHeader.personMenu, "visibility");
			        Thread.sleep(7000);
			        
			   //Step 19:Click on Person icon from top right corner and verify Admin access link is available or not 
					ph.performAction("adminaccesspresent");
				    if (ph.isVisible("adminaccess")==false){
				    	CustomReporter.log("'Admin Access' link is not present in TV Ranker page");
				    	}
				    else{
				    	CustomReporter.errorLog("Admin access is not visible in TV Ranker page");
				    }
				 }
				else
				{
				    CustomReporter.log("Mega menu icon not visible");	
				    }
	
				//Step 20:Log out from the application
			    /*ph.performAction("logout");
			    CustomReporter.log("The user has been logged out");*/
		}catch(Exception ee){
					ee.printStackTrace();
				}
		
				//****************Test step ends here************************************************
				if(BaseClass.testCaseStatus == false){
						CustomReporter.errorLog("Test case has failed");
						Assert.assertTrue(false);
				}	
	}
	
}
