/********************************************************************
Test Case Name:*573_Summary_HighestValue_Unknown
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8223.aspx
Objective:Verify that second highest value is getting populated in Summary table in case of highest value coming for "Unknown"
Module: Analyse>Profile
Created By: Amrutha Nair
Date: 16 September 2015
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

public class Profile8223 extends BaseClass {
	
	
	@Test
	public void test_Profile8223() throws Exception{
	//****************Variables declaration and Initialization****************************
		boolean bProjectCreate = false;
		ClientList_Page cl=null;
		String strProjectName="";
		String strMsg="";	
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");			
		String clientName=property.getProperty("clientName");
		String idiomName=null;
		
		
	//****************Test step starts here************************************************
		//Step1: Open URL
		try{
				CustomReporter.log("Launched Browser and Enter URL");
				Login_Page ln = new Login_Page(driver);
				
				
				//Step2:Enter valid username & password and click on login button
				Thread.sleep(3000);
		        ln.func_LoginToIDIOM(emailid, password);	
			    CustomReporter.log("Logged in successfully");
			    
			    
			    //Step3:Click on any client card and Open any existing idiom or create new
			    cl = new ClientList_Page(driver);
				cl.selectClient(clientName);
				CustomReporter.log("The client "+clientName+" has been opened");
				Thread.sleep(3000);
				ManageIdiom_Page mi = new ManageIdiom_Page(driver);
				
				//added by Sunil 01 June 2016
				int totalProjects = cl.totalProject();		
				System.out.println("Total projects: "+totalProjects);
				
				strProjectName = cl.createNewProject("");
				cl.func_PerformAction("Launch Project");
				bProjectCreate = true;
				CustomReporter.log("The idiom '"+strProjectName+"' is created.");
				
				ProjectDashboardPage pdp=new ProjectDashboardPage(driver);
				pdp.navigateTo("Profile");		
				rm.webElementSync("idiomspinningcomplete");
				CustomReporter.log("The user has navigated to the profile page");
				Thread.sleep(5000);
				
		//Step4: Verify that summary table is Not getting updated with data for 'Unkown'
		Analyse_Profile_Page an= new Analyse_Profile_Page(driver);
		if(an.func_VerifyProfileGraphValues("Unknown", "none")){
			CustomReporter.log("The Summary table is not getting updated with data for 'Unknown' ");
			
		}
		else{
			CustomReporter.errorLog("The summary table is getting updated with data for 'Unknown'");
		}
		
		}catch(Exception e)
		{
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("verifysummaryvalues", "fail");
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
		