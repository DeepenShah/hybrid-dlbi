package com.IDIOM.GlobalHeader;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/**
 * <p> <b>Test Case Name:</b> *Global Header - Verify whether Mega Menu shows all menu options even if some of the options are hidden on Project Dashboard </p>
 * <p> <b>Objective:</b>Verify whether Mega Menu shows all menu options even if some of the options are hidden on Project Dashboard </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8821.aspx </p>
 * <p> <b>Module:</b> Global Header </p>
 * @author Rohan Macwan
 * @since 17 June 2016
 *
 */
public class GlobalHeader8821 extends BaseClass{

	ProjectDashboardPage pdp=null;
	String strProjectName="";
	String strText="";
	@Test
	public void	verifyWhetherAllOptionsDisplayedInMegaMenu(){
		String strMsg = null;		
		boolean bProjectCreate = false;
		String strDetails ="";
		String strDigitalRanker="";
		String strProfile="";
		
		
				
		String strMediaRankerItems=property.getProperty("mediaRankerItems");
		try{
						
			//****************Test step starts here************************************************
			
			//loginToSelectClient
			//Step1: Open appropriate environment URL. and login with valid credentials
			//Step 2 - Select a client
			loginToSelectClient();
			
			//Step 3 - Create / Select Project
			strProjectName = clientListPage.createNewProject("");
			
			bProjectCreate=true;
			
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			rm.webElementSync("pageload");
			
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
			    throw new IDIOMException(strDigitalRanker + " link is still visible.###8821DigitalRankerStillVisible");
			
			CustomReporter.log(strDigitalRanker + " is correctly now not visible");
			
			if(pdp.isVisible("link", strProfile))
			    throw new IDIOMException(strProfile + " link is still visible.###8821ProfileIsStillVisible");
			
			CustomReporter.log(strProfile + " is correctly now not visible");
			
			//######################################################################
			
			
			//Step 4 - Click on IDIOM Logo
			func_VerifyAllLinksTextPresence();
			
			//#########Success Metrics############
			strText="";
			strText=property.getProperty("successMetrics");
			pageHeader.megaMenuLinksClick(property.getProperty("successMetrics"));
			
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);

			CustomReporter.log("Check whether Success Metrics Tab is Active when Audience Builder Section gets loaded");
					
			if (!((audienceBuilder.isVisible("selectedactivetab", "") && (audienceBuilder.SelectedActiveTab.getText().trim().toLowerCase().equalsIgnoreCase("Success Metrics")))))
				throw new IDIOMException("Success Metrics Tab seems to be not available or there may be some issues on the page. ###8821_SuccessMetricsTabNotAvailable");
			
			CustomReporter.log("Success Metrics Tab is available and visible on the page and currently Active");
			
			func_VerifyAllLinksTextPresence();
			
			//###############
			
			//Audience Definition
			strText="";
			strText=property.getProperty("audienceDefinition");
			pageHeader.megaMenuLinksClick(property.getProperty("audienceDefinition"));
						
			rm.webElementSync(audienceBuilder.addNewGroupLink,"visibility");
			CustomReporter.log("Navigated to audience definition page");
			
			func_VerifyAllLinksTextPresence();
			
			//Profile
			strText="";
			strText=property.getProperty("profile");
			pageHeader.megaMenuLinksClick(property.getProperty("profile"));
		    CustomReporter.log("Profile page loaded successfully");

		    rm.webElementSync("idiomspinningcomplete");
		    rm.webElementSync("pageload");
		    Thread.sleep(2000);
			
		    func_VerifyAllLinksTextPresence();
		    
		    //Webnet
		    strText="";
			strText=property.getProperty("webnet");
		    CustomReporter.log("Navigate to Webnet page");
			pageHeader.megaMenuLinksClick(property.getProperty("webnet"));
			
			
			Analyse_Webnet_Page webNet = new Analyse_Webnet_Page(driver);
			
			webNet.isVisible("webnetchart");
						
			if(!rm.webElementSync(webNet.chart,"visibility"))
				throw new IDIOMException("Webnet Page has not loaded successfully. Please refer screen shot for more information ###8821_WebnetPageNotLoadedSuccessfully");
			
			rm.webElementSync(pageHeader.clientLogo,"visibility");
			
			CustomReporter.log("Webnet page has loaded successfully");
		    
			func_VerifyAllLinksTextPresence();
			
			//############## HVA ##################
			strText="";
			strText=property.getProperty("hva");
			CustomReporter.log("Navigate to HVA page");
			pageHeader.megaMenuLinksClick(property.getProperty("hva"));;
			
			
			HVA_Page hva = new HVA_Page(driver);
			
			hva.isVisible("slowdown");
			
			if(!rm.webElementSync(hva.behaviour,"visibility"))
				throw new IDIOMException("Behavior dropdown is not available or there might be other issues on the page. ###8821_BehaviorsDropdownNotVisible");
							
			if(!rm.webElementSync(pageHeader.clientLogo,"visibility"))
				throw new IDIOMException("Client logo is not visible or there might be other issues on the page. ###8821_ClientLogo_Not_visible");

			CustomReporter.log("Verify Global Header");
			func_VerifyAllLinksTextPresence();
			
			//Pathing
			strText="";
			strText=property.getProperty("pathing");
			pageHeader.megaMenuLinksClick(property.getProperty("pathing"));
			rm.webElementSync("idiomspinningcomplete");
					
			Analyse_Pathing_Page paThing = new Analyse_Pathing_Page(driver);
			
			if(!rm.webElementSync(paThing.pathingWheel,"visibility"))
				throw new IDIOMException("Pathing page has not loaded successfully. There might be some issues present on it. ###8821_PathingWheelLoadingIssue");
						
			rm.webElementSync(pageHeader.clientLogo,"visibility");
			CustomReporter.log("Pathing page has loaded successfully");
			
			func_VerifyAllLinksTextPresence();
			
			//Media Ranker
			
			ArchitectMediaRankerPage AM= new ArchitectMediaRankerPage(driver);
			String [] strMediaRanker =pageHeader.getSplittedItems(strMediaRankerItems);
			for (int i=0;i<strMediaRanker.length;i++){
				strText="";
				strText=strMediaRanker[i];
				System.out.println(strMediaRanker[i]);
				Thread.sleep(3000);
				
				//Click on mega menu and click on digital ranker/tv ranker link				
				CustomReporter.log("Click on channel - '" + strMediaRanker[i] +"' from Mega Menu");
				pageHeader.megaMenuLinksClick(strMediaRanker[i]);
				
				rm.webElementSync("idiomspinningcomplete");
								
				if(!AM.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MediaRanker8821-missingScatterPlot");
								
				rm.webElementSync(pageHeader.clientLogo,"visibility");		
				
				func_VerifyAllLinksTextPresence();
			}
			
						
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
			rm.captureScreenShot("8821", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the Project" + strProjectName);
					
				}
				
				//Step 5 - Click on logout
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
		
			CustomReporter.log("---------------------------------------------------------");
			rm.webElementSync(pageHeader.megaMenuIcon,"clickable");
			CustomReporter.log("Click on Menu");
			pageHeader.megaMenuIcon.click();
			System.out.println("Clicked on mega menu");
			Thread.sleep(2000);
			
			//Project Inputs
			strText=property.getProperty("projectInputs");
			CustomReporter.log("Verify whether Header Text '" + strText + "' is present");
						
			if (!pageHeader.isVisible("headertext", strText))						
				throw new IDIOMException((strText + " is not present ###8821_ProjectInputsIsNotPresent"));
									
			CustomReporter.log(strText + " is present");
					
			//Build Audience
			strText="";
			strText=property.getProperty("buildAudience");
			
			CustomReporter.log("Verify whether Header Text '" + strText + "' is present");
			
			if (!pageHeader.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8821_buildAudienceIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//audienceProfiles
			strText="";
			strText=property.getProperty("audienceProfiles");
			CustomReporter.log("Verify whether Header Text '" + strText + "' is present");
			
			if (!pageHeader.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8821_audienceProfilesIsNotPresent");
									
			CustomReporter.log(strText + " is present");

						
			//audienceInsights
			strText="";
			strText=property.getProperty("audienceInsights");
			CustomReporter.log("Verify whether Header Text '" + strText + "' is present");
			
			if (!pageHeader.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8821_audienceInsightsIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//mediaTools
			strText="";
			strText=property.getProperty("mediaTools");
			CustomReporter.log("Verify whether Header Text '" + strText + "' is present");
			
			if (!pageHeader.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8821_mediaToolsIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//mediaRankers
			strText="";
			strText=property.getProperty("mediaRankers");
			CustomReporter.log("Verify whether Header Text '" + strText + "' is present");
			
			if (!pageHeader.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8821_mediaRankersIsNotPresent");
									
			CustomReporter.log(strText + " is present");
						
			//competitiveAnalysis
			strText="";
			strText=property.getProperty("competitiveAnalysis");
			CustomReporter.log("Verify whether Header Text '" + strText + "' is present");
			
			if (!pageHeader.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8821_competitiveAnalysisIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//mediaActivation
			/*strText="";
			strText=property.getProperty("mediaActivation");
			CustomReporter.log("Verify whether Header Text '" + strText + "' is present");
			
			if (!pageHeader.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8821_mediaActivationIsNotPresent");
									
			CustomReporter.log(strText + " is present");*/
			

			
			//Project Description
			strText="";
			strText=property.getProperty("projectDescription");
			CustomReporter.log("Verify whether '" + strText + "' is present");
			
			if (!pageHeader.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8821_ProjectDescriptionIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//projectDocuments
			strText="";
			strText=property.getProperty("projectDocuments");
			CustomReporter.log("Verify whether '" + strText + "' is present");
			
			if (!pageHeader.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8821_projectDocumentsIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//successMetrics
			strText="";
			strText=property.getProperty("successMetrics");
			CustomReporter.log("Verify whether '" + strText + "' is present");
			
			if (!pageHeader.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8821_successMetricsIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//audienceDefinition
			strText="";
			strText=property.getProperty("audienceDefinition");
			CustomReporter.log("Verify whether '" + strText + "' is present");
			
			if (!pageHeader.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8821_audienceDefinitionIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//profile
			strText="";
			strText=property.getProperty("profile");
			CustomReporter.log("Verify whether '" + strText + "' is present");
			
			if (!pageHeader.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8821_profileIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//seGmentation
			strText="";
			strText=property.getProperty("seGmentation");
			CustomReporter.log("Verify whether '" + strText + "' is present");
			
			if (!pageHeader.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8821_seGmentationIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//webnet
			strText="";
			strText=property.getProperty("webnet");
			CustomReporter.log("Verify whether '" + strText + "' is present");
			
			if (!pageHeader.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8821_webnetIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//hva
			strText="";
			strText=property.getProperty("hva");
			CustomReporter.log("Verify whether '" + strText + "' is present");
			
			if (!pageHeader.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8821_hvaIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//pathing
			strText="";
			strText=property.getProperty("pathing");
			CustomReporter.log("Verify whether '" + strText + "' is present");
			
			if (!pageHeader.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8821_pathingIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//atTribution
			strText="";
			strText=property.getProperty("atTribution");
			CustomReporter.log("Verify whether '" + strText + "' is present");
			
			if (!pageHeader.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8821_atTributionIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//mediaMixedModeling
			strText="";
			strText=property.getProperty("mediaMixedModeling");
			CustomReporter.log("Verify whether '" + strText + "' is present");
			
			if (!pageHeader.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8821_mediaMixedModelingIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//digitalRanker
			strText="";
			strText=property.getProperty("digitalRanker");
			CustomReporter.log("Verify whether '" + strText + "' is present");
			
			if (!pageHeader.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8821_digitalRankerIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//tvRanker
			strText="";
			strText=property.getProperty("tvRanker");
			CustomReporter.log("Verify whether '" + strText + "' is present");
			
			if (!pageHeader.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8821_tvRankerIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			//kantarStradegy
			strText="";
			strText=property.getProperty("kantarStradegy");
			CustomReporter.log("Verify whether '" + strText + "' is present");
			
			if (!pageHeader.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8821_kantarStradegyIsNotPresent");
									
			CustomReporter.log(strText + " is present");
			
			pageHeader.megaMenuIcon.click();
			
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
			rm.captureScreenShot("8821", "fail");
		}
	}
}
