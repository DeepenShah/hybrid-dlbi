package com.IDIOM.Analyse.HVA.scripts;

import common.BaseClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/**
 * <p> <b>Test Case Name:</b> *HVA_Verify Audience drop down when navigating from Another Page[After switching audience from both the pages </p>
 * <p> <b>Objective:</b> Verify that proper auidence is selected in audience drop down after switching audiences from both the pages </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/9066.aspx </p>
 * <p> <b>Module:</b> HVA </p>
 * @author Rohan Macwan
 * @since 24 May 2016
 *
 */
public class HVA9066 extends BaseClass{

@Test
	
	public void	verifySwitchingOfAudiencesOnBothPages(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName="";
		String strDetails ="";
		String strAudienceName="";
		String strAudienceName2="";
		
	
		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
					
			String strClientName=property.getProperty("clientName");
			
			//****************Test step starts here************************************************
			
			clientListPage = new ClientList_Page(driver);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			Login_Page ln = new Login_Page(driver);
			
			//Step1: Launch browser and enter IDIOM Url. Enter valid username and password Click on 'login to idiom' button
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			//Step 2- Select any client from client drop down
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			
			//Step 3 - Click on new project button
			//Step 4 - Enter valid project name and description and click on save button 
			strProjectName = clientListPage.createNewProject("");
			bProjectCreate=true;
			
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			rm.webElementSync("pageload");

			//Step 5 - Click on new audience link,provide name and click on 'create and save'[Say Audience A]
			clientListPage.performActionOnProject("edit", strProjectName);
			
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			if(!clientListPage.isVisible("newaudiencebtn"))
				throw new IDIOMException("Audience Tab seems to be not properly loaded and there might be issues present on it###9066-AudienceTabIssue");
			
			strAudienceName= clientListPage.createNewAudience("");
			
			CustomReporter.log("Created Audience and currently now selected Audience is - '" + strAudienceName+"'");

			Thread.sleep(3000);
			
			//Step 6 - Create one more audience here(Say Audience B
			strAudienceName2= clientListPage.createNewAudience("");
			
			CustomReporter.log("Created Audience and currently now selected Audience is - '" + strAudienceName2+"'");
						
			clientListPage.func_PerformAction("Close Project");
			
			//Step 7 - Now click on launch project
			CustomReporter.log("Launch Project");
			clientListPage.launchProject(strProjectName);
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
								
			//Step 8 - Navigate to HVA page
			CustomReporter.log("Navigate to HVA page");
			pageHeader.megaMenuLinksClick(property.getProperty("hva"));
			
			
			HVA_Page hva = new HVA_Page(driver);
			
			hva.isVisible("slowdown");
						
			if(!rm.webElementSync(hva.behaviour,"visibility"))
				throw new IDIOMException("Behavior dropdown is not available or there might be other issues on the page. ###9066_BehaviorsDropdownNotVisible");
			
			CustomReporter.log("Audience '" + strAudienceName2 + "' is currently selected on HVA Page");
			
			//Step 9 - Switch the audience from drop down[Say Select Audience A here]
			CustomReporter.log("Change the Audience to - '" + strAudienceName +"'");
			pageHeader.selectAudienceFromDropDown(strAudienceName);
			
			hva.isVisible("slowdown");
			
			if(!rm.webElementSync(hva.behaviour,"visibility"))
				throw new IDIOMException("Behavior dropdown is not available or there might be other issues on the page. ###9066_BehaviorsDropdownNotVisible_1");
			
			if(!pageHeader.verifySelectedAudienceInDropDown(strAudienceName))
				throw new IDIOMException("Currently selected Audience is not '" + strAudienceName + "'. ###9066_BehaviorsDropdownNotVisible_2");
			
			CustomReporter.log("Selected Audience is '" + strAudienceName + "'");
			
			hva.isVisible("slowdown");
			
			if(!rm.webElementSync(hva.behaviour,"visibility"))
				throw new IDIOMException("Behavior dropdown is not available or there might be other issues on the page. ###9066_BehaviorsDropdownNotVisible_3");
			
			rm.webElementSync("pageload");
			rm.webElementSync("clickable");
			
			Thread.sleep(6000);
			//Step 10 - Navigate to any other page like ;Webnet or Pathing " from mega menu
			CustomReporter.log("Navigate to Pathing page");
			pageHeader.megaMenuLinksClick(property.getProperty("pathing"));
			try{
				rm.webElementSync("idiomspinningcomplete");
			}catch(Exception e){
				System.out.println("spinning bar not found");
			}
			
			Thread.sleep(3000);
			
			Analyse_Pathing_Page pathing = new Analyse_Pathing_Page(driver);
			
			if(!rm.webElementSync(pathing.pathingWheel,"visibility"))
				throw new IDIOMException("Pathing page has not loaded successfully. There might be some issues present on it. ###9066_PathingWheelLoadingIssue");
			
			//Step 9 - Switch the audience from drop down
			CustomReporter.log("Change the Audience to - '" + strAudienceName2+"'");
			pageHeader.selectAudienceFromDropDown(strAudienceName2);
			
			//CustomReporter.log("Navigate to Pathing page");
			//pageHeader.megaMenuLinksClick(property.getProperty("pathing"));
			try{
				rm.webElementSync("idiomspinningcomplete");
			}catch(Exception e){
				System.out.println("spinning bar not found");
			}
			
			Thread.sleep(3000);
			
			if(!rm.webElementSync(pathing.pathingWheel,"visibility"))
				throw new IDIOMException("Pathing page has not loaded successfully. There might be some issues present on it. ###9066_PathingWheelLoadingIssue_1");
			
			if(!pageHeader.verifySelectedAudienceInDropDown(strAudienceName2))
				throw new IDIOMException("Currently selected Audience is not '" + strAudienceName2 + "'. ###9066_BehaviorsDropdownNotVisible_4");
			
			CustomReporter.log("Selected Audience is '" + strAudienceName2 + "'");
			
			try{
				rm.webElementSync("idiomspinningcomplete");
			}catch(Exception e){
				System.out.println("spinning bar not found");
			}
			
			Thread.sleep(3000);
			
			if(!rm.webElementSync(pathing.pathingWheel,"visibility"))
				throw new IDIOMException("Pathing page has not loaded successfully. There might be some issues present on it. ###9066_PathingWheelLoadingIssue_2");
			
			//Step 12 - Navigate to HVA page
			CustomReporter.log("Navigate to HVA page");
			pageHeader.megaMenuLinksClick(property.getProperty("hva"));
			
			hva.isVisible("slowdown");
			
			if(!rm.webElementSync(hva.behaviour,"visibility"))
				throw new IDIOMException("Behavior dropdown is not available or there might be other issues on the page. ###9066_BehaviorsDropdownNotVisible_5");
			
			//Step 13 - Check the audience selected by default on HVA page 
			
			CustomReporter.log("Check the audience selected by default on HVA page");
			
			if(!pageHeader.verifySelectedAudienceInDropDown(strAudienceName2))
				throw new IDIOMException("Currently selected Audience is not '" + strAudienceName2 + "'. ###9066_BehaviorsDropdownNotVisible_6");
			
			CustomReporter.log("Selected Audience is '" + strAudienceName2 + "'");
		
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
			rm.captureScreenShot("9066", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the Project");
				}
				
				//Step 12 - Click on logout
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
