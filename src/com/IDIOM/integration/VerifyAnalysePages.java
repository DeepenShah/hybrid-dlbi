package com.IDIOM.integration;

/********************************************************************
Test Case Name: Verify Analyze pages (Profile, Pathing, HVA and Webnet)
Objective: Verify Analyze pages (Profile, Pathing, HVA and Webnet) should be accessible
Test Case ID: qa.digitas.com/SpiraTest/523/TestCase/8992.aspx
Module: User Analyze
@author: Shailesh Kava
@since: 27-April-2016
**********************************************************************/

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

public class VerifyAnalysePages extends BaseClass {
		
	@Test
	public void analysePagesVerification(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strProjectName=property.getProperty("projectName");
			String strProjectDescription=property.getProperty("projectDescription");
			//****************Test step starts here************************************************
			
			clientListPage = new ClientList_Page(driver);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			Login_Page ln = new Login_Page(driver);
			
			//Step1: Open URL
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			if(totalProjects == 0){
				System.out.println("No project available for this client, ceating new project");
				CustomReporter.log("No project available for this client, ceating new project");	
				clientListPage.func_PerformAction("New Project");
				rm.webElementSync(clientListPage.newProjectWindow, "visibility");
				strMsg = "Create New Project Window appeared successfully";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
				
				clientListPage.findAndSaveProjectWindow(true, "");
				
				clientListPage.fillProject(strProjectName,strProjectDescription);				
				clientListPage.func_PerformAction("Save Project");
				Thread.sleep(2000);
				clientListPage.func_PerformAction("Launch Project");
				
			}else{
				
				int selectProjectId = totalProjects;
				System.out.println("Clicking on project id: "+selectProjectId);
				CustomReporter.log("Clicking on project id: "+selectProjectId);
				clientListPage.clickProjectById(selectProjectId);
			}
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			System.out.println("Clicking on Profile link from Project dashboard page");
			CustomReporter.log("Clicking on Profile link from Project dashboard page");
			
			//Step4: Click on new profile page
			pdp.navigateTo("Profile");
			Analyse_Profile_Page analysePage = new Analyse_Profile_Page(driver);
			
			//Verify required sections in profile page
			if(analysePage.isVisible("summary") && analysePage.isVisible("interest") && analysePage.isVisible("demographics")
					&& analysePage.isVisible("local market")){
				
				CustomReporter.log("Summary,Interest,Demographics and Local Market are available");
				System.out.println("Summary,Interest,Demographics and Local Market are available");
			}else{
				rm.captureScreenShot("profilPageIssue", "fail");
				CustomReporter.errorLog("Summary or Interest or Demographics or Local Market is missing/problem, screenshot [profilPageIssue.png]");
				System.out.println("Summary or Interest or Demographics or Local Market is missing/problem, screenshot [profilPageIssue.png]");
			}
			
			
			if(!pageHeader.isVisible("header_megamenu_icon"))
				throw new IDIOMException("Mega menu is missing in header###megaMenu-missingMegaMenu");
			
			System.out.println("Mege menu is appear in header");
			CustomReporter.log("Mege menu is appear in header");
			
			pageHeader.megaMenuLinksClick("Webnet");
			rm.webElementSync("pageload");
			
			Analyse_Webnet_Page webNetPage = new Analyse_Webnet_Page(driver);
			if(webNetPage.isVisible("webnetimage")){
				System.out.println("WebNet page is open");
				CustomReporter.log("WebNet page is open");
			}else{
				rm.captureScreenShot("webnetPageIssue", "fail");
				CustomReporter.errorLog("Problem in webnet page, screenshot [webnetPageIssue.png]");
				System.out.println("Problem in webnet page, screenshot [webnetPageIssue.png]");
			}
				
			CustomReporter.log("Clicking on Pathing link from mega menu");
			System.out.println("Clicking on Pathing link from mega menu");
			
			pageHeader.megaMenuLinksClick(property.getProperty("pathing"));
			try{
				rm.webElementSync("idiomspinningcomplete");
			}catch(Exception e){
				System.out.println("spinning bar not found");
			}
			
			Thread.sleep(3000);
			Analyse_Pathing_Page pathingPage = new Analyse_Pathing_Page(driver);
			
			if(pathingPage.isVisible("pathing_wheel")){
				CustomReporter.log("Pathing page is open");
				System.out.println("Pathing page is open");
			}else{
				rm.captureScreenShot("pathingPageIssue", "fail");
				CustomReporter.errorLog("Problem in pathing page, screenshot [pathingPageIssue.png]");
				System.out.println("Problem in pathing page, screenshot [pathingPageIssue.png]");
			}
				
			CustomReporter.log("Clicking on HVA link from mega menu");
			System.out.println("Clicking on HVA link from mega menu");
			
			pageHeader.megaMenuLinksClick(property.getProperty("hva"));
			rm.webElementSync("pageload");
			Thread.sleep(3000);
			HVA_Page hvaPage = new HVA_Page(driver);
			
			if(hvaPage.isVisible("hva_chart")){
				CustomReporter.log("HVA page is open");
				System.out.println("HVA page is open");
			}else
				throw new IDIOMException("Problem in HVA page, screenshot [HVAPageIssue.png]###HVAPageIssue");
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Some exception is generated, " + strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("AnalysePagesIssue", "fail");
		}finally{
			try{
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