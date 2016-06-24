/********************************************************************
Test Case Name: *1. a - Profile Headers and Menus- Verify Sticky Nav
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8035.aspx
Objective: This test case verifies the sticky navigation in Analysis>Profile page
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
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;

public class Profile8035 extends BaseClass {
	
	
	@Test
	public void test_Profile8035() throws Exception{
	//****************Variables declaration and Initialization****************************
		boolean bProjectCreate = false;
		ClientList_Page cl=null;
		String strProjectName="";
		String strMsg="";
		try{
			
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");			
		String clientName=property.getProperty("clientName");
		String idiomName=null;
		String Filter=property.getProperty("FilterCriteria4");
		
	//****************Test step starts here************************************************	
		//Step1: Open URL
		CustomReporter.log("Launched Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);
		
		
		//Step2:Enter valid username & password and click on login button
		Thread.sleep(3000);
        ln.func_LoginToIDIOM(emailid, password);	
	    CustomReporter.log("Logged in successfully");
	    
	    
	    //Step3:Click on any client card and Open any existing idiom or create new
	    cl = new ClientList_Page(driver);
		//cl.func_SelectClient(clientName); commented by Sunil 01 Jun 2016
		cl.selectClient(clientName);
		CustomReporter.log("The client "+clientName+" has been opened");
		Thread.sleep(3000);
		
		ManageIdiom_Page mi = new ManageIdiom_Page(driver);
		/*Thread.sleep(8000);
		idiomName=mi.func_CreateIdiomName("Idiom","New");
		mi.func_CreateNewIdiomOrRename(idiomName, "NotBlank"); //commented by Sunil 01 Jun 2016*/
		
		//added by Sunil 01 June 2016
		int totalProjects = cl.totalProject();		
		System.out.println("Total projects: "+totalProjects);
		
		//String strProjectName;
		//Boolean bProjectCreate;
		strProjectName = cl.createNewProject("");
		cl.func_PerformAction("Launch Project");
		bProjectCreate = true;
		CustomReporter.log("The idiom '"+strProjectName+"' is created.");
		
		ProjectDashboardPage pdp=new ProjectDashboardPage(driver);
		pdp.navigateTo("Profile");		
		rm.webElementSync("idiomspinningcomplete");
		CustomReporter.log("The user has navigated to the profile page");
		Thread.sleep(5000);
		
		//Step4:Select/deselect any filters & click on analyze audience button
		Audience_Page ad= new Audience_Page(driver);
		/*ad.func_SelectFilters(Filter);
		CustomReporter.log("The filters are selected");
		
		ad.func_ClickElement("AnalyzeAudience");
		CustomReporter.log("The user has clicked on Analyse button");
		Thread.sleep(5000);*/
		
		
		//Step5:Scroll the page and verify each section on the page have a sticky header
		
		Analyse_Profile_Page ap= new Analyse_Profile_Page(driver);
		
		//Checking demographics
		if(ap.func_VerifyStickyheaderOnScroll("Demographics"))
		{
			CustomReporter.log("On scroll, page focus has been moved to 'Demographics'section");
		}
		else{
			CustomReporter.errorLog("On scroll, page focus has NOT been moved to 'Demographics'section");
			BaseClass.testCaseStatus=false;
		}
		
		if(ap.func_VerifyStickyheaderOnScroll("Local Markets"))
		{
			CustomReporter.log("On scroll, page focus has been moved to Local Markets section");
		}
		else{
			CustomReporter.errorLog("On scroll, page focus has NOT been moved to 'Local Markets ");
			BaseClass.testCaseStatus=false;
		}
		
		//Step 6:Verify the page the header description should remain at the top of the screen until user has entered the next section 
		//Cannot be automated
		
		/*Thread.sleep(3000);
		//Go to manage Idiom page and delete the created idiom
		ad.func_ClickElement("Manage");
		Thread.sleep(3000);
		
		mi.func_PerformClickAction("Delete");
		Thread.sleep(1000);
		
		//Step8:Logout from the application
		//mi.func_PerformClickAction("Logout",driver);
		CustomReporter.log("The user has been logged out successfully");*/
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("verifystickyheader", "fail");
		}
		finally{
			try{
				
				//Step 13: Deleting newly created project
				if(bProjectCreate){
					pageHeader.navigateTo("projecthomepage");
					rm.webElementSync(pageHeader.personMenu,"visibility");
					
					cl.func_PerformAction("Close Project");
					
					cl.performActionOnProject("delete", strProjectName);
					cl.performActionOnProject("yes","");
					Thread.sleep(2000);
					CustomReporter.log("Deleted the project");
				}
				
				
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
		
		
		//****************Test step ends here************************************************
		if(BaseClass.testCaseStatus == false){
				CustomReporter.errorLog("Test case has failed");
				Assert.assertTrue(false);
		}	
		}
		}
		
				