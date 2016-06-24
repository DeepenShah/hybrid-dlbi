package com.IDIOM.Analyse.Pathing.scripts;

import common.BaseClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;

import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/**
 * <p> <b>Test Case Name:</b> *Pathing_VerifyDefaultAudienceSelectedIn DropDown </p>
 * <p> <b>Objective:</b> verify the default audience selected in audience drop down and verify that all the audiences present in create project/edit project overlay is present </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/9040.aspx </p>
 * <p> <b>Module:</b> Pathing </p>
 * @author Rohan Macwan
 * @since 01 June 2016
 *
 */
public class Pathing9040 extends BaseClass {

	@Test
	
	public void	verifyAudienceSelectedInHeader(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName="";
		String strDetails ="";
		String strAudienceName="";
	
		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			
					
			String strClientName=property.getProperty("clientName");
			
			//****************Test step starts here************************************************
			
			clientListPage = new ClientList_Page(driver);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			Login_Page ln = new Login_Page(driver);
			
			//Step1: Launch browser and enter IDIOM Url
			//Step2: Enter valid username and password Click on 'login to idiom' button
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			//Step 3- Select any client from client drop down
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			
			//Step 4 - Click on new project button
			//Step 5 - Enter valid project name and description and click on save button 
			strProjectName = clientListPage.createNewProject("");
			bProjectCreate=true;
			
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			rm.webElementSync("pageload");

			
			clientListPage.performActionOnProject("edit", strProjectName);
			
			ArrayList<String> audiecnesInEditOverlay=clientListPage.func_getListOfAudiencesForEditedProject();
			
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			if(!clientListPage.isVisible("newaudiencebtn"))
				throw new IDIOMException("Audience Tab seems to be not properly loaded and there might be issues present on it###9040-AudienceTabIssue");
			
			strAudienceName= clientListPage.createNewAudience("");
			
			clientListPage.func_PerformAction("Close Project");
			
			//Step 6 - Now click on launch project
			CustomReporter.log("Launch Project");
			clientListPage.launchProject(strProjectName);
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
								
			//Step 7 - Navigate to Pathing page
			pageHeader.megaMenuLinksClick(property.getProperty("pathing"));
			try{
				rm.webElementSync("idiomspinningcomplete");
			}catch(Exception e){
				System.out.println("spinning bar not found");
			}
			
			Analyse_Pathing_Page paThing = new Analyse_Pathing_Page(driver);
			
			if(!rm.webElementSync(paThing.pathingWheel,"visibility"))
				throw new IDIOMException("Pathing page has not loaded successfully. There might be some issues present on it. ###9040_PathingWheelLoadingIssue");
						
			CustomReporter.log("Pathing page has loaded successfully");
			
			//Step 8 - Check the default audience selected in Pathing page 
			CustomReporter.log("Check whether Audience '" + strAudienceName + "' is present in Header Dropdown on Pathing Page");
			
			if(!pageHeader.verifySelectedAudienceInDropDown(strAudienceName))
				throw new IDIOMException("Default Audience does not match with the Audience '" + strAudienceName + "' which was created and selected. ###9040_BehaviorsDropdownNotVisible");
			
			CustomReporter.log("Audience '" + strAudienceName + "' created on Client Home page is correctly selected in Header Dropdown on Pathing Page");
			
			//Step 9 - Click on audience drop down and verify the audiences present
			pageHeader.audienceDropDownLink.click();
			CustomReporter.log("Click on audience drop down and verify the audiences present");
			ArrayList<String> audiecnesInPathing=pageHeader.returnAudiencesInDropDown();

			if(!rm.compareArray(audiecnesInEditOverlay, audiecnesInPathing))
				throw new IDIOMException("Audiences listed in Edit project overlay and which are there in dropdown in header on Pathing page do not match. ###9040_BehaviorsDropdownNotVisible_1");
			
			
			
			CustomReporter.log("Audiences listed in Edit project overlay and which are there in dropdown in header on Pathing page match.");
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("" + strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("9040", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the Project");
				}
				
				//Step 10 - Click on logout
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
		//****************Test step ends here************************************************
		
		if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}
	}
}
