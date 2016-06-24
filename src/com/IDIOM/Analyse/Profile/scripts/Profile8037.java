/********************************************************************
Test Case Name: *2.b - Profile Headers and Menus - Verify menu
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/8037.aspx
Objective: Objective:This test case verifies the menu items in Analysis>Profile page
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
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.ReusableMethods;

public class Profile8037 extends BaseClass {
	
	
	@Test
	public void test_Profile8037() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");			
		String clientName=property.getProperty("clientName");
		String idiomName=null;
		ArrayList<String> dropdownlistvalues;
		String profileDrpDown="Summary,Demographics,Local Markets";
		boolean bProjectCreate = false;
		ClientList_Page cl=null;
		String strProjectName="";
		String strMsg="";
		
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
			
			
			//Step5: Click on "v" on header menu
			Thread.sleep(6000);
			an.func_ClickElement("SummaryDownArrow");
			Thread.sleep(3000);
			if(an.func_ElementExist("DropDownOpen")){
				CustomReporter.log("The drop down is getting opened on clicking on the down arrow");
				
				
				dropdownlistvalues=an.func_getSummaryvalues();
				System.out.println("Displaying array values");
				System.out.println(dropdownlistvalues.toString());
				Thread.sleep(5000);
				//Step6: Verify the options present in Menu
				if(an.func_VerifyProfileDropDown(profileDrpDown)){
					CustomReporter.log("The drop down options presnt is menu are expected ones. Those are :"+dropdownlistvalues);
				    
					
					//Step7:Click on any option
					an.func_ClickElement("Demographics");
					CustomReporter.log("The user has selected 'Demographics' from menu options");
					if(an.func_ElementExist("Demographics")){
						CustomReporter.log("The user has been navigated to demographics section in analyse>Profile page");
					}
					else{
						CustomReporter.errorLog("The user has NOT been navigated to demographics section in analyse>Profile page");
						BaseClass.testCaseStatus=false;
					}
					
				
				}
				else{
					CustomReporter.errorLog("The drop down options are not expected");
					BaseClass.testCaseStatus=false;
				}
			}
			else{
				CustomReporter.errorLog("The drop down is not getting opened on clicking on down arrow");
				BaseClass.testCaseStatus=false;
			}
		}
		else{
		   CustomReporter.errorLog("Analyse_Profile page didn't get opened succssfully");
		   BaseClass.testCaseStatus=false;
		}
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("verifydropdown", "fail");
			
		}finally{
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
