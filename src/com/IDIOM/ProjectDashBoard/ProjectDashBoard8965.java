package com.IDIOM.ProjectDashBoard;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Def 2221,2639,2609,2382_Verify all links available in project home page</p>
 *  <p> <b>Objective:</b>Verify that the user is able access all the links and able to navigate each page through project home page</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8965.aspx</p>
 *  <p> <b>Module:</b>Project Dashboard</p>
 *  
 * @author Abhishek Deshpande
 * @since 17 June 2016
 *
 */
public class ProjectDashBoard8965 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyDefaultViewOfPDP(){		
		String strMsg="";		
		String strProjectDetails="";
		String strProjectName="";
		String strText="";
		
		try{
			
			//****************Test step starts here************************************************
						
			//Step1-3: Login To Selecting Client
			loginToSelectClient();			
			
			//Step4: Create Project
			strProjectName = clientListPage.createNewProject("");
			strProjectDetails = clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			//Launch Project
			clientListPage.launchProject(strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			PageHeader ph = new PageHeader(driver);
			
			if(!rm.webElementSync(pdp.projectName, "visibility"))
				throw new IDIOMException("Failed to land on Project Dashboard page.###FailedToLandOnProjectDashboardPage");
			
			CustomReporter.log("Navigated to Project Dashboard page");
			
			//Verify IDIOM logo, Megamenu, Projectname, Audience dropdown in header of project dash board page
			if(!ph.isVisible("header_megamenu_icon")&&ph.isVisible("idiomlogo")&&ph.isVisible("audiencedropdown")&&ph.isVisible("projectname"))
				throw new IDIOMException("Failed to verify header on Project Dashboard page.###FailedToVerifyHeaderOnProjectDashboardPage");
			
			CustomReporter.log("Verified IDIOM logo, Projectname, Audience dropdown and megamenu elements in page header");
			
			//Verifying visibility of links
						
			//Project Inputs
			strText=property.getProperty("projectInputs");
			CustomReporter.log("Verify whether Header Text " + strText + " is present");

			if (!pdp.isVisible("headertext", strText))
				throw new IDIOMException((strText + " is not present ###8892_ProjectInputsIsNotPresent"));

			CustomReporter.log(strText + " is present");

			//Build Audience
			strText="";
			strText=property.getProperty("buildAudience");

			CustomReporter.log("Verify whether Header Text " + strText + " is present");

			if (!pdp.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8892_buildAudienceIsNotPresent");

			CustomReporter.log(strText + " is present");

			//audienceProfiles
			strText="";
			strText=property.getProperty("audienceProfiles");
			CustomReporter.log("Verify whether Header Text " + strText + " is present");

			if (!pdp.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8892_audienceProfilesIsNotPresent");

			CustomReporter.log(strText + " is present");


			//audienceInsights
			strText="";
			strText=property.getProperty("audienceInsights");
			CustomReporter.log("Verify whether Header Text " + strText + " is present");

			if (!pdp.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8892_audienceInsightsIsNotPresent");

			CustomReporter.log(strText + " is present");

			//mediaTools
			strText="";
			strText=property.getProperty("mediaTools");
			CustomReporter.log("Verify whether Header Text " + strText + " is present");

			if (!pdp.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8892_mediaToolsIsNotPresent");

			CustomReporter.log(strText + " is present");

			//mediaRankers
			strText="";
			strText=property.getProperty("mediaRankers");
			CustomReporter.log("Verify whether Header Text " + strText + " is present");

			if (!pdp.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8892_mediaRankersIsNotPresent");

			CustomReporter.log(strText + " is present");

			//competitiveAnalysis
			strText="";
			strText=property.getProperty("competitiveAnalysis");
			CustomReporter.log("Verify whether Header Text " + strText + " is present");

			if (!pdp.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8892_competitiveAnalysisIsNotPresent");

			CustomReporter.log(strText + " is present");

			//mediaActivation
			strText="";
			strText=property.getProperty("mediaActivation");
			CustomReporter.log("Verify whether Header Text " + strText + " is present");

			if (!pdp.isVisible("headertext", strText))
				throw new IDIOMException(strText + " is not present ###8892_mediaActivationIsNotPresent");

			CustomReporter.log(strText + " is present");



			//Project Description
			strText="";
			strText=property.getProperty("projectDescription");
			CustomReporter.log("Verify whether " + strText + " is present");

			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8892_ProjectDescriptionIsNotPresent");

			CustomReporter.log(strText + " is present");
			pdp.navigateTo(strText);
			if(strText.equalsIgnoreCase(property.getProperty("projectDescription"))){
				rm.webElementSync(clientListPage.newProjectBtn, "visibility");
				if(!clientListPage.isVisible("newproject")){
					rm.captureScreenShot("8892_failedToOpen"+strText+"Page", "fail");
					CustomReporter.errorLog("Failed to open Client home page on clicking ["+strText+" from project dashboard page");					
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(strText+" page is open on clicking ["+strText+"] from project dashboard page");					
					clientListPage.findAndSaveProjectWindow(false, strProjectName);
					Thread.sleep(1000);
					System.out.println("Clicking on launch project");
					clientListPage.func_PerformAction("Launch Project");
				}
			}

			//projectDocuments
			strText="";
			strText=property.getProperty("projectDocuments");
			CustomReporter.log("Verify whether " + strText + " is present");

			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8892_projectDocumentsIsNotPresent");

			CustomReporter.log(strText + " is present");
			

			//successMetrics
			strText="";
			strText=property.getProperty("successMetrics");
			CustomReporter.log("Verify whether " + strText + " is present");

			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8892_successMetricsIsNotPresent");

			CustomReporter.log(strText + " is present");
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);
			pdp.navigateTo(strText);
			if(strText.equalsIgnoreCase(property.getProperty("successMetrics"))){
				rm.webElementSync(audienceBuilder.noSuccessMetricText, "visible");
				
				if(!audienceBuilder.isVisible("nosuccessmetrictext", "")){
					rm.captureScreenShot("8892_failedToOpen"+strText+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+strText+" page on clicking ["+strText+"] from project dashboard page");
					System.out.println("Failed to open "+strText+" page on clicking ["+strText+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(strText+" page is open on clicking ["+strText+"] from project dashboard page");
					System.out.println(strText+" home page is open on clicking ["+strText+"] from project dashboard page");
					rm.webElementSync(pageHeader.clientLogo, "clickable");
					pageHeader.clientLogo.click();
				}
				
			}

			//audienceDefinition
			strText="";
			strText=property.getProperty("audienceDefinition");
			CustomReporter.log("Verify whether " + strText + " is present");

			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8892_audienceDefinitionIsNotPresent");

			CustomReporter.log(strText + " is present");
			pdp.navigateTo(strText);
			if(strText.equalsIgnoreCase(property.getProperty("audienceDefinition"))){
				rm.webElementSync(audienceBuilder.audienceDefinitionTab, "visible");
				
				if(!audienceBuilder.isVisible("audiencedefinitiontab")){
					rm.captureScreenShot("8838_failedToOpen"+strText+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+strText+" page on clicking ["+strText+"] from project dashboard page");
					System.out.println("Failed to open "+strText+" page on clicking ["+strText+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(strText+" page is open on clicking ["+strText+"] from project dashboard page");
					System.out.println(strText+" home page is open on clicking ["+strText+"] from project dashboard page");
					rm.webElementSync(pageHeader.clientLogo, "clickable");
					pageHeader.clientLogo.click();
				}
			}

			//profile
			strText="";
			strText=property.getProperty("profile");
			CustomReporter.log("Verify whether " + strText + " is present");

			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8892_profileIsNotPresent");

			CustomReporter.log(strText + " is present");
			pdp.navigateTo(strText);
			if(strText.equalsIgnoreCase(property.getProperty("profile"))){
				Analyse_Profile_Page ap= new Analyse_Profile_Page(driver);
				rm.webElementSync("idiomspinningcomplete");
				rm.webElementSync(ap.homeOwnerGraph,"visibility");
				if(!ap.pageTitle.isDisplayed()){
					rm.captureScreenShot("8892_failedToOpen"+strText+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+strText+" page on clicking ["+strText+"] from project dashboard page");
					System.out.println("Failed to open "+strText+" page on clicking ["+strText+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(strText+" page is open on clicking ["+strText+"] from project dashboard page");
					System.out.println(strText+" home page is open on clicking ["+strText+"] from project dashboard page");
					rm.webElementSync(pageHeader.clientLogo, "clickable");
					pageHeader.clientLogo.click();
				}
			}

			//seGmentation
			strText="";
			strText=property.getProperty("seGmentation");
			CustomReporter.log("Verify whether " + strText + " is present");

			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_seGmentationIsNotPresent");

			CustomReporter.log(strText + " is present");

			//webnet
			strText="";
			strText=property.getProperty("webnet");
			CustomReporter.log("Verify whether " + strText + " is present");

			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_webnetIsNotPresent");

			CustomReporter.log(strText + " is present");
			pdp.navigateTo(strText);
			if(strText.equalsIgnoreCase(property.getProperty("webnet"))){
				rm.webElementSync("idiomspinningcomplete");
				Analyse_Webnet_Page webNetPage = new Analyse_Webnet_Page(driver);
				rm.webElementSync(webNetPage.chart, "visibility");
				
				if(!webNetPage.chart.isDisplayed()){
					rm.captureScreenShot("8838_failedToOpen"+strText+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+strText+" page on clicking ["+strText+"] from project dashboard page");
					System.out.println("Failed to open "+strText+" page on clicking ["+strText+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(strText+" page is open on clicking ["+strText+"] from project dashboard page");
					System.out.println(strText+" home page is open on clicking ["+strText+"] from project dashboard page");
					rm.webElementSync(pageHeader.clientLogo, "clickable");
					pageHeader.clientLogo.click();
				}
			}

			//hva
			strText="";
			strText=property.getProperty("hva");
			CustomReporter.log("Verify whether " + strText + " is present");

			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_hvaIsNotPresent");

			CustomReporter.log(strText + " is present");
			pdp.navigateTo(strText);
			if(strText.equalsIgnoreCase(property.getProperty("hva"))){
				HVA_Page hvaPage = new HVA_Page(driver);
				
				rm.webElementSync(hvaPage.behaviour, "clickable");
				
				if(!hvaPage.behaviour.isDisplayed()){
					rm.captureScreenShot("8838_failedToOpen"+strText+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+strText+" page on clicking ["+strText+"] from project dashboard page");
					System.out.println("Failed to open "+strText+" page on clicking ["+strText+" from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(strText+" page is open on clicking ["+strText+"] from project dashboard page");
					System.out.println(strText+" home page is open on clicking ["+strText+"] from project dashboard page");
					rm.webElementSync(pageHeader.clientLogo, "clickable");
					pageHeader.clientLogo.click();
				}
			}

			//pathing
			strText="";
			strText=property.getProperty("pathing");
			CustomReporter.log("Verify whether " + strText + " is present");

			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_pathingIsNotPresent");

			CustomReporter.log(strText + " is present");
			pdp.navigateTo(strText);
			if(strText.equalsIgnoreCase(property.getProperty("pathing"))){
				rm.webElementSync("idiomspinningcomplete");
				Analyse_Pathing_Page pathingPage = new Analyse_Pathing_Page(driver);
				rm.webElementSync(pathingPage.pathingWheel, "visibility");
				
				if(!pathingPage.pathingWheel.isDisplayed()){
					rm.captureScreenShot("8838_failedToOpen"+strText+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+strText+" page on clicking ["+strText+"] from project dashboard page");
					System.out.println("Failed to open "+strText+" page on clicking ["+strText+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(strText+" page is open on clicking ["+strText+"] from project dashboard page");
					System.out.println(strText+" home page is open on clicking ["+strText+"] from project dashboard page");
					rm.webElementSync(pageHeader.clientLogo, "clickable");
					pageHeader.clientLogo.click();
				}
			}

			//atTribution
			strText="";
			strText=property.getProperty("atTribution");
			CustomReporter.log("Verify whether " + strText + " is present");

			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_atTributionIsNotPresent");

			CustomReporter.log(strText + " is present");

			//mediaMixedModeling
			strText="";
			strText=property.getProperty("mediaMixedModeling");
			CustomReporter.log("Verify whether " + strText + " is present");

			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_mediaMixedModelingIsNotPresent");

			CustomReporter.log(strText + " is present");

			//digitalRanker
			ArchitectMediaRankerPage mediaRanker = new ArchitectMediaRankerPage(driver);
			strText="";
			strText=property.getProperty("digitalRanker");
			CustomReporter.log("Verify whether " + strText + " is present");

			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_digitalRankerIsNotPresent");

			CustomReporter.log(strText + " is present");
			pdp.navigateTo(strText);
			if(strText.equalsIgnoreCase(property.getProperty("digitalRanker"))){
				rm.webElementSync(mediaRanker.weightedRankerBtn, "visibility");
				rm.webElementSync(mediaRanker.weightedRankerBtn, "clickable");
				
				if(!mediaRanker.weightedRankerBtn.isDisplayed()){
					rm.captureScreenShot("8838_failedToOpen"+strText+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+strText+" page on clicking ["+strText+"] from project dashboard page");
					System.out.println("Failed to open "+strText+" page on clicking ["+strText+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(strText+" page is open on clicking ["+strText+"] from project dashboard page");
					System.out.println(strText+" home page is open on clicking ["+strText+"] from project dashboard page");
					rm.webElementSync(pageHeader.clientLogo, "clickable");
					pageHeader.clientLogo.click();
				}
			}

			//tvRanker
			strText="";
			strText=property.getProperty("tvRanker");
			CustomReporter.log("Verify whether " + strText + " is present");

			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_tvRankerIsNotPresent");

			CustomReporter.log(strText + " is present");
			pdp.navigateTo(strText);
			if(strText.equalsIgnoreCase(property.getProperty("tvRanker"))){
				rm.webElementSync(mediaRanker.weightedRankerBtn, "visibility");
				rm.webElementSync(mediaRanker.weightedRankerBtn, "clickable");
				
				if(!mediaRanker.weightedRankerBtn.isDisplayed()){
					rm.captureScreenShot("8838_failedToOpen"+strText+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+strText+" page on clicking ["+strText+"] from project dashboard page");
					System.out.println("Failed to open "+strText+" page on clicking ["+strText+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(strText+" page is open on clicking ["+strText+"] from project dashboard page");
					System.out.println(strText+" home page is open on clicking ["+strText+"] from project dashboard page");
					rm.webElementSync(pageHeader.clientLogo, "clickable");
					pageHeader.clientLogo.click();
				}
			}

			//kantarStradegy
			strText="";
			strText=property.getProperty("kantarStradegy");
			CustomReporter.log("Verify whether " + strText + " is present");

			if (!pdp.isVisible("link", strText))
				throw new IDIOMException(strText + " is not present ###8823_kantarStradegyIsNotPresent");

			CustomReporter.log(strText + " is present");
			
			
		}catch(Exception e){
			exceptionCode(e,strClassName);
		}finally{
			try{
				
				if(!strProjectDetails.equalsIgnoreCase("")){
					util.deleteProjectOrAudience(strProjectDetails,true);
					CustomReporter.log("Deleted the project");
				}
				
				//Logout
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(strClassName + ": " + strMsg);
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
		
		if(!BaseClass.testCaseStatus){
			  CustomReporter.errorLog("Test case failed");
			  Assert.assertTrue(false);
		  }else{
			  CustomReporter.log("Test case passed");
		  }	
	}
}

