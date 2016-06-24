package com.IDIOM.GlobalHeader;

import java.text.MessageFormat;
import java.util.ArrayList;

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

/********************************************************************
<p><b>Objective</b> Verify that project link from header should redirect to project dashboard page from any page of application</p>
<p><b>Test Case Name</b> 2541: Project link from header of any page should redirect on Project dashboard page.</p>
<p><b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/8907.aspx</p>
<p><b>Module</b> Global Header</p>
@author Shailesh Kava
@since 17 June 2016
**********************************************************************/
public class GlobalHeader8907 extends BaseClass {
	boolean bProjectCreate = false;
	String strDetails;
	
	@Test
	public void verifyClientLogoClickOnEachPage() throws Exception{
	//****************Variables declaration and Initialization****************************
		String pathingLink = property.getProperty("pathing");	
		String strMsg = null;
		String strProjectName="8907_"+rm.getCurrentDateTime();
	//****************Test step starts here************************************************	
	try{
		//Step1: Launch browser
		loginToSelectClient();
		CustomReporter.log("Creating new project as no project for this client");
		System.out.println("Creating new project as no project for this client");
		
		Thread.sleep(2000);
		clientListPage.createNewProject(strProjectName);
		
		pageHeader = new PageHeader(driver);
		
		//Getting project and client id to delete through REST service
		strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
		clientListPage.launchProject(strProjectName);
		bProjectCreate = true;
		
		ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
		System.out.println("starttime "+System.currentTimeMillis());
		rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, pathingLink.trim()));
		System.out.println("endtime "+System.currentTimeMillis());
		Thread.sleep(2000);
		
		ArrayList<String> arrLinkNames = pdp.getActiveLinksName();
		
		for(String linkName:arrLinkNames){
			System.out.println("Link name ==="+linkName);
			
			pdp.navigateTo(linkName);
			if(linkName.equalsIgnoreCase(property.getProperty("projectDescription"))){
				rm.webElementSync(clientListPage.newProjectBtn, "visibility");
				
				if(!clientListPage.isVisible("newproject")){
					rm.captureScreenShot("8907_failedToOpen"+linkName+"Page", "fail");
					CustomReporter.errorLog("Failed to open Client home page on clicking ["+linkName+" from project dashboard page");
					System.out.println("Failed to open Client home page on clicking ["+linkName+" from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(linkName+" page is open on clicking ["+linkName+"] from project dashboard page");
					System.out.println(linkName+" home page is open on clicking ["+linkName+"] from project dashboard page");
				}
			}
			
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);
			if(linkName.equalsIgnoreCase(property.getProperty("successMetrics"))){
				rm.webElementSync(audienceBuilder.noSuccessMetricText, "visible");
				
				if(!audienceBuilder.isVisible("nosuccessmetrictext", "")){
					rm.captureScreenShot("8907_failedToOpen"+linkName+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					System.out.println("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(linkName+" page is open on clicking ["+linkName+"] from project dashboard page");
					System.out.println(linkName+" home page is open on clicking ["+linkName+"] from project dashboard page");
				}
				
			}
			//Audience definition tab
			if(linkName.equalsIgnoreCase(property.getProperty("audienceDefinition"))){
				rm.webElementSync(audienceBuilder.audienceDefinitionTab, "visible");
				
				if(!audienceBuilder.isVisible("audiencedefinitiontab")){
					rm.captureScreenShot("8907_failedToOpen"+linkName+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					System.out.println("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(linkName+" page is open on clicking ["+linkName+"] from project dashboard page");
					System.out.println(linkName+" home page is open on clicking ["+linkName+"] from project dashboard page");
				}
			}
			
			//profile tab
			if(linkName.equalsIgnoreCase(property.getProperty("profile"))){
				Analyse_Profile_Page ap= new Analyse_Profile_Page(driver);
				rm.webElementSync("idiomspinningcomplete");
				rm.webElementSync(ap.homeOwnerGraph,"visibility");
				if(!ap.pageTitle.isDisplayed()){
					rm.captureScreenShot("8907_failedToOpen"+linkName+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					System.out.println("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(linkName+" page is open on clicking ["+linkName+"] from project dashboard page");
					System.out.println(linkName+" home page is open on clicking ["+linkName+"] from project dashboard page");
				}
			}
			ArchitectMediaRankerPage mediaRanker = new ArchitectMediaRankerPage(driver);
			
			//digital ranker
			if(linkName.equalsIgnoreCase(property.getProperty("digitalRanker"))){
				rm.webElementSync(mediaRanker.weightedRankerBtn, "visibility");
				rm.webElementSync(mediaRanker.weightedRankerBtn, "clickable");
				
				if(!mediaRanker.weightedRankerBtn.isDisplayed()){
					rm.captureScreenShot("8907_failedToOpen"+linkName+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					System.out.println("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(linkName+" page is open on clicking ["+linkName+"] from project dashboard page");
					System.out.println(linkName+" home page is open on clicking ["+linkName+"] from project dashboard page");
				}
			}
			
			//tv ranker
			if(linkName.equalsIgnoreCase(property.getProperty("tvRanker"))){
				rm.webElementSync(mediaRanker.weightedRankerBtn, "visibility");
				rm.webElementSync(mediaRanker.weightedRankerBtn, "clickable");
				
				if(!mediaRanker.weightedRankerBtn.isDisplayed()){
					rm.captureScreenShot("8907_failedToOpen"+linkName+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					System.out.println("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(linkName+" page is open on clicking ["+linkName+"] from project dashboard page");
					System.out.println(linkName+" home page is open on clicking ["+linkName+"] from project dashboard page");
				}
			}
			
			//hva
			if(linkName.equalsIgnoreCase(property.getProperty("hva"))){
				HVA_Page hvaPage = new HVA_Page(driver);
				
				rm.webElementSync(hvaPage.behaviour, "clickable");
				
				if(!hvaPage.behaviour.isDisplayed()){
					rm.captureScreenShot("8907_failedToOpen"+linkName+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					System.out.println("Failed to open "+linkName+" page on clicking ["+linkName+" from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(linkName+" page is open on clicking ["+linkName+"] from project dashboard page");
					System.out.println(linkName+" home page is open on clicking ["+linkName+"] from project dashboard page");
				}
			}
			
			//Pathing
			if(linkName.equalsIgnoreCase(property.getProperty("pathing"))){
				rm.webElementSync("idiomspinningcomplete");
				Analyse_Pathing_Page pathingPage = new Analyse_Pathing_Page(driver);
				rm.webElementSync(pathingPage.pathingWheel, "visibility");
				
				if(!pathingPage.pathingWheel.isDisplayed()){
					rm.captureScreenShot("8907_failedToOpen"+linkName+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					System.out.println("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(linkName+" page is open on clicking ["+linkName+"] from project dashboard page");
					System.out.println(linkName+" home page is open on clicking ["+linkName+"] from project dashboard page");
				}
			}
			
			//web net
			if(linkName.equalsIgnoreCase(property.getProperty("webnet"))){
				rm.webElementSync("idiomspinningcomplete");
				Analyse_Webnet_Page webNetPage = new Analyse_Webnet_Page(driver);
				rm.webElementSync(webNetPage.chart, "visibility");
				
				if(!webNetPage.chart.isDisplayed()){
					rm.captureScreenShot("8907_failedToOpen"+linkName+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					System.out.println("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(linkName+" page is open on clicking ["+linkName+"] from project dashboard page");
					System.out.println(linkName+" home page is open on clicking ["+linkName+"] from project dashboard page");
				}
			}
			
			if(linkName.equalsIgnoreCase(property.getProperty("projectDescription"))){
				clientListPage.findAndSaveProjectWindow(false, strProjectName);
				Thread.sleep(1000);
				System.out.println("Clicking on launch project");
				clientListPage.func_PerformAction("Launch Project");
			}else{
				rm.webElementSync(pageHeader.clientLogo, "clickable");
				pageHeader.clientLogo.click();
			}
			
			rm.webElementSync(pdp.projectName, "visibility");
			pdp.isVisible("link", linkName);
			rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, pathingLink.trim()));
			Thread.sleep(2000);
			
			if(pdp.projectName.isDisplayed()){
				CustomReporter.log("Successfully moved to Project dashboard from page ["+linkName+"]");
			}else{
				CustomReporter.log("Failed to moved to Project dashboard from page ["+linkName+"]");
				BaseClass.testCaseStatus = false;
			}
		}
		
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
		rm.captureScreenShot("8907", "fail");
	}finally{
		try{
			
			if(bProjectCreate){
				rm.deleteProjectOrAudience(strDetails, true);
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
	
	if(!BaseClass.testCaseStatus){
		CustomReporter.errorLog("Test case failed");
		Assert.assertTrue(false);
	}else{
		CustomReporter.log("Test case passed");
	}
}}
