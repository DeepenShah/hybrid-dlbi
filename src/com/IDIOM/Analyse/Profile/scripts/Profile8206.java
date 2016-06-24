/********************************************************************
Test Case Name: 543_Verify_Dropdown_profilePage
Objective:This case checks whether the drop down is proper for profile page
Test Case ID: http://qa.digitas.com/SpiraTest/523/TestCase/8206.aspx
Module: Audience Page
Created By: Rohan Macwan
Creation Date: 14th September 2015
**********************************************************************/
package com.IDIOM.Analyse.Profile.scripts;

import java.util.ArrayList;

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

public class Profile8206 extends BaseClass{
	
	@Test
	
	public void test_Profile8206() throws InterruptedException {

	//****************Variables declaration and Initialization****************************
		boolean bProjectCreate = false;
		ClientList_Page cl=null;
		String strProjectName="";
		String strMsg="";
		String clientName=property.getProperty("clientName");
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");
		String client1 = property.getProperty("clientName");
		boolean rst = true;
	
	//****************Test step starts here************************************************
	try{	
		//Step1: Login in application
				CustomReporter.log("Launched Browser and Enter URL");
				Login_Page ln = new Login_Page(driver);	
				
				Thread.sleep(3000);
		        ln.func_LoginToIDIOM(emailid, password);	
			    CustomReporter.log("Logged in successfully");
			    
			    //Step2: Click on any client card
			    cl = new ClientList_Page(driver);
			    cl.selectClient(clientName);
			    //cl.func_SelectClient(clientName);--commented by Sunil 09 May 2016
				CustomReporter.log("The client "+clientName+" has been opened");
				Thread.sleep(3000);
				
				
				int totalProjects = cl.totalProject();
				System.out.println("Total projects: "+totalProjects);
				
				//Step 3: Create/Select project and launch the same 

					strProjectName = cl.createNewProject("");
					cl.func_PerformAction("Launch Project");
					bProjectCreate = true;	
				
				//Step3:Click on any idiom/create new
				ManageIdiom_Page mi = new ManageIdiom_Page(driver);		
				Thread.sleep(5000);
				//Step4: Click on analyze audience button
				ProjectDashboardPage pdp=new ProjectDashboardPage(driver);
				Audience_Page ad= new Audience_Page(driver);
				pdp.navigateTo("Profile");
				
				/*ad.func_ClickElement("AnalyzeAudience");*/
				Analyse_Profile_Page an=new Analyse_Profile_Page(driver);
				if(an.func_ElementExist("ProfilePage")){
					CustomReporter.log("Analyse _Profile page opened successfully"); //commented by Sunil 01 Jun 2016*/
				}	
		
		Thread.sleep(3000);
		//Check the drop downs in profile page whether it has following 3 values
			//1. Summary
			//2. Demographics
			//3.Local markets
		//Analyse_Profile_Page an=new Analyse_Profile_Page(driver);
		
		//an.func_ClickElement("SummaryDownArrow");

		Thread.sleep(10000);
		ArrayList<String> listSummary = an.func_CaptureList("getSummaryList");
		
		int totalValuesInSummaryDropDown = listSummary.size(); 
		
		Thread.sleep(3000);
		for(int startVal = 0; startVal <= totalValuesInSummaryDropDown -1; startVal++){
			
			String summaryTableValue = listSummary.get(startVal);
			
			if(summaryTableValue.contentEquals("SUMMARY") || summaryTableValue.contentEquals("DEMOGRAPHICS") || summaryTableValue.contentEquals("LOCAL MARKETS")||summaryTableValue.contentEquals("INTERESTS")){
				CustomReporter.log("Summary Table has expected Values: "+summaryTableValue);
			}else{
				CustomReporter.errorLog("Summary table does not contain expected value: "+summaryTableValue);
				BaseClass.testCaseStatus = false;
			}
		}
				
	}
	
	catch(Exception e){
		BaseClass.testCaseStatus = false;
		CustomReporter.errorLog(" The exception is generated, " + e.getMessage() + ". ");
		e.printStackTrace(System.out);
		rm.captureScreenShot("Profile8206", "fail");
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
