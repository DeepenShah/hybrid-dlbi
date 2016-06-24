package com.IDIOM.ProjectDashBoard;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/**
 * <p> <b>Test Case Name:</b> *2543_Dashboard - Verify "Hide" </p>
 * <p> <b>Objective:</b> Verify another projects of same Client and another client whether all links are shown after hiding few of the links for current project </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8897.aspx </p>
 * <p> <b>Module:</b> Project Dashboard </p>
 * @author Rohan Macwan
 * @since 16 June 2016
 *
 */
public class ProjectDashBoard8897 extends BaseClass {

	public String strText="";
	
	public ProjectDashboardPage pdp;
	@Test
	public void	verifyEachLinkAndTextPresenceInAnotherProjectsAfterHidingThemInCurrentProject(){
		String strMsg = null;		
		boolean bProjectCreate = false;
		String strProjectName="";
		String strDetails ="";
		
		String strProjectName1="";
		String strDetails1 ="";
		
		String strProjectName2="";
		String strDetails2 ="";
		
		String strDigitalRanker="";
		String strProfile="";
		try{
						
			//****************Test step starts here************************************************
			
			//loginToSelectClient
			//Step1: Open appropriate environment URL. and login with valid credentials
			//Step 2 - select a client "hasConfig = true and hasChild = false
			loginToSelectClient();
			
			//Step 3 - Create Project
			strProjectName = clientListPage.createNewProject("");
			
			bProjectCreate=true;
			
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			rm.webElementSync("pageload");
			
			//Step4 - Launch project
			CustomReporter.log("Launch Project");

			clientListPage.launchProject(strProjectName);
			
			 pdp = new ProjectDashboardPage(driver);
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			CustomReporter.log("Project dashboard page is open");
			
			//#################### Code for hiding some of the links ###################
			//Step 5 - Hide some of the links
			strDigitalRanker=property.getProperty("digitalRanker");
			strProfile=property.getProperty("profile");
			
			pdp.hideLink(strDigitalRanker);
			pdp.hideLink(strProfile);
			
			pdp.isVisible("showhiddenlinkslabel", "");
			
			if(pdp.isVisible("link", strDigitalRanker))
			    throw new IDIOMException(strDigitalRanker + " link is still visible.###DigitalRankerStillVisible");
			
			CustomReporter.log(strDigitalRanker + " is correctly now not visible");
			
			if(pdp.isVisible("link", strProfile))
			    throw new IDIOMException(strProfile + " link is still visible.###ProfileIsStillVisible");
			
			CustomReporter.log(strProfile + " is correctly now not visible");
			
			//######################################################################
			
			//############## Create Another Project for same client###############
			//Step 6 - Go back to Client Home page
			CustomReporter.log("########################Go back to Client Home page for the selection of another Project by creating it ############################## ");
			pageHeader.idiomLogo.click();
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			//Step 7 - Create another project
			strProjectName1 = clientListPage.createNewProject("");
			
			bProjectCreate=true;
			
			strDetails1=clientListPage.getAudienceProjectClientId(strProjectName1, "");
			
			rm.webElementSync("pageload");

			//Step 8 - Launch Project and  check Links present on Dashboard
			clientListPage.launchProject(strProjectName1);
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			CustomReporter.log("Project dashboard page is open");
			
			func_VerifyAllLinksTextPresence();
			
			//####################################################################
			
			
			//############## Create Another Project for another client###############
			//Step 9 - Go back to Client Home page and Change the Client
			CustomReporter.log("########################Go back to Client Home page for the selection of another Client ############################## ");
			pageHeader.idiomLogo.click();
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			String strClientNameclientFor8897=property.getProperty("clientFor8897");
			
			if(clientListPage.selectClient(strClientNameclientFor8897)){
				strMsg = "Selected '"+strClientNameclientFor8897+"' client successfully.";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
				
				Thread.sleep(5000);
				rm.webElementSync("pageload");
				rm.webElementSync("jqueryload");
				
				CustomReporter.log("Selected client ["+strClientNameclientFor8897+"]");
			}
			
			
			strProjectName2 = clientListPage.createNewProject("");
			
			bProjectCreate=true;
			
			strDetails2=clientListPage.getAudienceProjectClientId(strProjectName2, "");
			
			rm.webElementSync("pageload");

			//Step 10 - Now Launch project  and check Links present on Dashboard
			clientListPage.launchProject(strProjectName2);
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			CustomReporter.log("Project dashboard page is open");
			
			func_VerifyAllLinksTextPresence();
			
			//####################################################################
			
			
						
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog(strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8897", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the Project" + strProjectName);
					
					rm.deleteProjectOrAudience(strDetails1, true);
					CustomReporter.log("Deleted the Project" + strProjectName1);
					
					rm.deleteProjectOrAudience(strDetails2, true);
					CustomReporter.log("Deleted the Project" + strProjectName2);
				}
				
				//Step 11 - Click on logout
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


	public void func_VerifyAllLinksTextPresence()
	{
		// Header Text
		try
		{
		
				//Project Inputs
				strText=property.getProperty("projectInputs");
				CustomReporter.log("Verify whether Header Text '" + strText + "' is present");
							
				if (!pdp.isVisible("headertext", strText))						
					throw new IDIOMException((strText + " is not present ###8897_ProjectInputsIsNotPresent"));
										
				CustomReporter.log(strText + " is present");
						
				//Build Audience
				strText="";
				strText=property.getProperty("buildAudience");
				
				CustomReporter.log("Verify whether Header Text '" + strText + "' is present");
				
				if (!pdp.isVisible("headertext", strText))
					throw new IDIOMException(strText + " is not present ###8897_buildAudienceIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				
				//audienceProfiles
				strText="";
				strText=property.getProperty("audienceProfiles");
				CustomReporter.log("Verify whether Header Text '" + strText + "' is present");
				
				if (!pdp.isVisible("headertext", strText))
					throw new IDIOMException(strText + " is not present ###8897_audienceProfilesIsNotPresent");
										
				CustomReporter.log(strText + " is present");

							
				//audienceInsights
				strText="";
				strText=property.getProperty("audienceInsights");
				CustomReporter.log("Verify whether Header Text '" + strText + "' is present");
				
				if (!pdp.isVisible("headertext", strText))
					throw new IDIOMException(strText + " is not present ###8897_audienceInsightsIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				
				//mediaTools
				strText="";
				strText=property.getProperty("mediaTools");
				CustomReporter.log("Verify whether Header Text '" + strText + "' is present");
				
				if (!pdp.isVisible("headertext", strText))
					throw new IDIOMException(strText + " is not present ###8897_mediaToolsIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				
				//mediaRankers
				strText="";
				strText=property.getProperty("mediaRankers");
				CustomReporter.log("Verify whether Header Text '" + strText + "' is present");
				
				if (!pdp.isVisible("headertext", strText))
					throw new IDIOMException(strText + " is not present ###8897_mediaRankersIsNotPresent");
										
				CustomReporter.log(strText + " is present");
							
				//competitiveAnalysis
				strText="";
				strText=property.getProperty("competitiveAnalysis");
				CustomReporter.log("Verify whether Header Text '" + strText + "' is present");
				
				if (!pdp.isVisible("headertext", strText))
					throw new IDIOMException(strText + " is not present ###8897_competitiveAnalysisIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				
				//mediaActivation
				strText="";
				strText=property.getProperty("mediaActivation");
				CustomReporter.log("Verify whether Header Text '" + strText + "' is present");
				
				if (!pdp.isVisible("headertext", strText))
					throw new IDIOMException(strText + " is not present ###8897_mediaActivationIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				

				
				//Project Description
				strText="";
				strText=property.getProperty("projectDescription");
				CustomReporter.log("Verify whether '" + strText + "' is present");
				
				if (!pdp.isVisible("link", strText))
					throw new IDIOMException(strText + " is not present ###8897_ProjectDescriptionIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				
				//projectDocuments
				strText="";
				strText=property.getProperty("projectDocuments");
				CustomReporter.log("Verify whether '" + strText + "' is present");
				
				if (!pdp.isVisible("link", strText))
					throw new IDIOMException(strText + " is not present ###8897_projectDocumentsIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				
				//successMetrics
				strText="";
				strText=property.getProperty("successMetrics");
				CustomReporter.log("Verify whether '" + strText + "' is present");
				
				if (!pdp.isVisible("link", strText))
					throw new IDIOMException(strText + " is not present ###8897_successMetricsIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				
				//audienceDefinition
				strText="";
				strText=property.getProperty("audienceDefinition");
				CustomReporter.log("Verify whether '" + strText + "' is present");
				
				if (!pdp.isVisible("link", strText))
					throw new IDIOMException(strText + " is not present ###8897_audienceDefinitionIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				
				//profile
				strText="";
				strText=property.getProperty("profile");
				CustomReporter.log("Verify whether '" + strText + "' is present");
				
				if (!pdp.isVisible("link", strText))
					throw new IDIOMException(strText + " is not present ###8897_profileIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				
				//seGmentation
				strText="";
				strText=property.getProperty("seGmentation");
				CustomReporter.log("Verify whether '" + strText + "' is present");
				
				if (!pdp.isVisible("link", strText))
					throw new IDIOMException(strText + " is not present ###8897_seGmentationIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				
				//webnet
				strText="";
				strText=property.getProperty("webnet");
				CustomReporter.log("Verify whether '" + strText + "' is present");
				
				if (!pdp.isVisible("link", strText))
					throw new IDIOMException(strText + " is not present ###8897_webnetIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				
				//hva
				strText="";
				strText=property.getProperty("hva");
				CustomReporter.log("Verify whether '" + strText + "' is present");
				
				if (!pdp.isVisible("link", strText))
					throw new IDIOMException(strText + " is not present ###8897_hvaIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				
				//pathing
				strText="";
				strText=property.getProperty("pathing");
				CustomReporter.log("Verify whether '" + strText + "' is present");
				
				if (!pdp.isVisible("link", strText))
					throw new IDIOMException(strText + " is not present ###8897_pathingIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				
				//atTribution
				strText="";
				strText=property.getProperty("atTribution");
				CustomReporter.log("Verify whether '" + strText + "' is present");
				
				if (!pdp.isVisible("link", strText))
					throw new IDIOMException(strText + " is not present ###8897_atTributionIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				
				//mediaMixedModeling
				strText="";
				strText=property.getProperty("mediaMixedModeling");
				CustomReporter.log("Verify whether '" + strText + "' is present");
				
				if (!pdp.isVisible("link", strText))
					throw new IDIOMException(strText + " is not present ###8897_mediaMixedModelingIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				
				//digitalRanker
				strText="";
				strText=property.getProperty("digitalRanker");
				CustomReporter.log("Verify whether '" + strText + "' is present");
				
				if (!pdp.isVisible("link", strText))
					throw new IDIOMException(strText + " is not present ###8897_digitalRankerIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				
				//tvRanker
				strText="";
				strText=property.getProperty("tvRanker");
				CustomReporter.log("Verify whether '" + strText + "' is present");
				
				if (!pdp.isVisible("link", strText))
					throw new IDIOMException(strText + " is not present ###8897_tvRankerIsNotPresent");
										
				CustomReporter.log(strText + " is present");
				
				//kantarStradegy
				strText="";
				strText=property.getProperty("kantarStradegy");
				CustomReporter.log("Verify whether '" + strText + "' is present");
				
				if (!pdp.isVisible("link", strText))
					throw new IDIOMException(strText + " is not present ###8897_kantarStradegyIsNotPresent");
										
				CustomReporter.log(strText + " is present");
		}
		catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog(strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8897", "fail");
		}
	}

}

