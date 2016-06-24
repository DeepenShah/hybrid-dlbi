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
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p><b>Objective</b> Global Header - Changing Audiences - Verify whether Users can change to another audience in the project on any page.</p>
<p><b>Test Case Name</b> Global Header - Verify whether Users can change to another audience in the project on any page</p>
<p><b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/8841.aspx</p>
<p><b>Module</b> Global Header</p>
@author Shailesh Kava
@since 21 June 2016
**********************************************************************/
public class GlobalHeader8841 extends BaseClass {
	boolean bProjectCreate = false;
	String strDetails;
	Login_Page lp;
	
	@Test
	public void verifyChangeAudienceInAllPages() throws Exception{
	//****************Variables declaration and Initialization****************************
		String pathingLink = property.getProperty("pathing");	
		String strMsg = null;
		String strProjectName="8841_"+rm.getCurrentDateTime();
		String strAudName = "8841_"+rm.getCurrentDateTime();
		String strDefaultAud = property.getProperty("defaultaudience");
	//****************Test step starts here************************************************	
	try{
		lp = new Login_Page(driver);
		//Step1: Launch browser
		loginToSelectClient();
		CustomReporter.log("Creating new project as no project for this client");
		System.out.println("Creating new project as no project for this client");
		
		Thread.sleep(2000);
		clientListPage.createNewProject(strProjectName);
		Thread.sleep(1000);
		clientListPage.performActionOnProject("edit", strProjectName);
		Thread.sleep(1000);
		clientListPage.func_PerformAction("Audience Tab");
		clientListPage.createNewAudience(strAudName);
		
		//Getting project and client id to delete through REST service
		strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
		bProjectCreate = true;
		
		clientListPage.func_PerformAction("Launch Project");
		pageHeader = new PageHeader(driver);
		
		ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
		rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, pathingLink.trim()));
		Thread.sleep(2000);
		
		//Changing audience
		pageHeader.selectAudienceFromDropDown(strDefaultAud);
		
		Thread.sleep(5000);
		
		ArrayList<String> arrLinkNames = pdp.getActiveLinksName();
		String selectedAudName;
		
		for(String linkName:arrLinkNames){
			System.out.println("Link name ==="+linkName);
			
			pdp.navigateTo(linkName);
			if(linkName.equalsIgnoreCase(property.getProperty("projectDescription"))){
				rm.webElementSync(clientListPage.newProjectBtn, "visibility");
				
				Thread.sleep(2000);
				clientListPage.func_PerformAction("Audience Tab");
				
				Thread.sleep(2000);
				selectedAudName = clientListPage.getSelectedAudNameFromOpenProjectWindow();
				System.out.println(selectedAudName);
				
				if(!selectedAudName.equalsIgnoreCase(strDefaultAud)){
					rm.captureScreenShot("8841_changedAudIsNotSelectedInClintListPage", "Fail");
					CustomReporter.errorLog("Changed audience is not preserved in Client list page, it should be ["+strDefaultAud+"] instead of ["+selectedAudName+"]");
					System.out.println("Changed audience is not preserved in Client list page, it should be ["+strDefaultAud+"] instead of ["+selectedAudName+"]");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log("Changed audience is preserved in Client list page ["+strDefaultAud+"]");
					System.out.println("Changed audience is preserved in Client list page ["+strDefaultAud+"]");
				}
			}
			
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);
			if(linkName.equalsIgnoreCase(property.getProperty("successMetrics"))){
				rm.webElementSync(audienceBuilder.noSuccessMetricText, "visible");
				
				if(!audienceBuilder.isVisible("nosuccessmetrictext", "")){
					rm.captureScreenShot("8841_failedToOpen"+linkName+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					System.out.println("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(linkName+" page is open on clicking ["+linkName+"] from project dashboard page");
					System.out.println(linkName+" home page is open on clicking ["+linkName+"] from project dashboard page");
					
					//changing audience
					pageHeader.selectAudienceFromDropDown(strAudName);
					Thread.sleep(2000);
					rm.webElementSync(audienceBuilder.noSuccessMetricText, "visible");
					
					selectedAudName = pageHeader.getSelectedAudienceName();
					System.out.println(selectedAudName);
					
					if(selectedAudName.equalsIgnoreCase(strAudName)){
						CustomReporter.log("Audience is changed successfully!");
						System.out.println("Audience is changed successfully!");
					}else{
						rm.captureScreenShot("8841_"+linkName+"FailedTochangeAud", "fail");
						CustomReporter.errorLog("Failed to change audience in drop down in page "+linkName+" it should be ["+strAudName+"] instead of ["+selectedAudName+"]");
						System.out.println("Failed to change audience in drop down in page "+linkName+" it should be ["+strAudName+"] instead of ["+selectedAudName+"]");
						BaseClass.testCaseStatus = false;
					}
				}
				
			}
			//Audience definition tab
			if(linkName.equalsIgnoreCase(property.getProperty("audienceDefinition"))){
				rm.webElementSync(audienceBuilder.audienceDefinitionTab, "visible");
				
				if(!audienceBuilder.isVisible("audiencedefinitiontab")){
					rm.captureScreenShot("8841_failedToOpen"+linkName+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					System.out.println("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(linkName+" page is open on clicking ["+linkName+"] from project dashboard page");
					System.out.println(linkName+" home page is open on clicking ["+linkName+"] from project dashboard page");
					
					//changing audience
					pageHeader.selectAudienceFromDropDown(strDefaultAud);
					Thread.sleep(5000);
					rm.webElementSync(audienceBuilder.audienceDefinitionTab, "visible");
					selectedAudName = pageHeader.getSelectedAudienceName();
					System.out.println(selectedAudName);
					
					if(selectedAudName.equalsIgnoreCase(strDefaultAud)){
						CustomReporter.log("Audience is changed successfully!");
						System.out.println("Audience is changed successfully!");
					}else{
						rm.captureScreenShot("8841_"+linkName+"FailedTochangeAud", "fail");
						CustomReporter.errorLog("Failed to change audience in drop down in page "+linkName+" it should be ["+strAudName+"] instead of ["+selectedAudName+"]");
						System.out.println("Failed to change audience in drop down in page "+linkName+" it should be ["+strAudName+"] instead of ["+selectedAudName+"]");
						BaseClass.testCaseStatus = false;
					}
				}
			}
			
			//profile tab
			if(linkName.equalsIgnoreCase(property.getProperty("profile"))){
				Analyse_Profile_Page ap= new Analyse_Profile_Page(driver);
				rm.webElementSync("idiomspinningcomplete");
				rm.webElementSync(ap.homeOwnerGraph,"visibility");
				if(!ap.pageTitle.isDisplayed()){
					rm.captureScreenShot("8841_failedToOpen"+linkName+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					System.out.println("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(linkName+" page is open on clicking ["+linkName+"] from project dashboard page");
					System.out.println(linkName+" home page is open on clicking ["+linkName+"] from project dashboard page");
					
					//changing audience
					pageHeader.selectAudienceFromDropDown(strAudName);
					Thread.sleep(5000);
					rm.webElementSync("idiomspinningcomplete");
					rm.webElementSync(ap.homeOwnerGraph,"visibility");
					selectedAudName = pageHeader.getSelectedAudienceName();
					System.out.println(selectedAudName);
					
					if(selectedAudName.equalsIgnoreCase(strAudName)){
						CustomReporter.log("Audience is changed successfully!");
						System.out.println("Audience is changed successfully!");
					}else{
						rm.captureScreenShot("8841_"+linkName+"FailedTochangeAud", "fail");
						CustomReporter.errorLog("Failed to change audience in drop down in page "+linkName+" it should be ["+strAudName+"] instead of ["+selectedAudName+"]");
						System.out.println("Failed to change audience in drop down in page "+linkName+" it should be ["+strAudName+"] instead of ["+selectedAudName+"]");
						BaseClass.testCaseStatus = false;
					}
				}
			}
			ArchitectMediaRankerPage mediaRanker = new ArchitectMediaRankerPage(driver);
			
			//digital ranker
			if(linkName.equalsIgnoreCase(property.getProperty("digitalRanker"))){
				rm.webElementSync(mediaRanker.weightedRankerBtn, "visibility");
				rm.webElementSync(mediaRanker.weightedRankerBtn, "clickable");
				
				if(!mediaRanker.weightedRankerBtn.isDisplayed()){
					rm.captureScreenShot("8841_failedToOpen"+linkName+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					System.out.println("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(linkName+" page is open on clicking ["+linkName+"] from project dashboard page");
					System.out.println(linkName+" home page is open on clicking ["+linkName+"] from project dashboard page");
					
					//changing audience
					pageHeader.selectAudienceFromDropDown(strDefaultAud);
					Thread.sleep(5000);
					rm.webElementSync(mediaRanker.weightedRankerBtn, "visibility");
					rm.webElementSync(mediaRanker.weightedRankerBtn, "clickable");
					selectedAudName = pageHeader.getSelectedAudienceName();
					System.out.println(selectedAudName);
					
					if(selectedAudName.equalsIgnoreCase(strDefaultAud)){
						CustomReporter.log("Audience is changed successfully!");
						System.out.println("Audience is changed successfully!");
					}else{
						rm.captureScreenShot("8841_"+linkName+"FailedTochangeAud", "fail");
						CustomReporter.errorLog("Failed to change audience in drop down in page "+linkName+" it should be ["+strAudName+"] instead of ["+selectedAudName+"]");
						System.out.println("Failed to change audience in drop down in page "+linkName+" it should be ["+strAudName+"] instead of ["+selectedAudName+"]");
						BaseClass.testCaseStatus = false;
					}
				}
			}
			
			//tv ranker
			if(linkName.equalsIgnoreCase(property.getProperty("tvRanker"))){
				rm.webElementSync(mediaRanker.weightedRankerBtn, "visibility");
				rm.webElementSync(mediaRanker.weightedRankerBtn, "clickable");
				
				if(!mediaRanker.weightedRankerBtn.isDisplayed()){
					rm.captureScreenShot("8841_failedToOpen"+linkName+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					System.out.println("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(linkName+" page is open on clicking ["+linkName+"] from project dashboard page");
					System.out.println(linkName+" home page is open on clicking ["+linkName+"] from project dashboard page");
					
					//changing audience
					pageHeader.selectAudienceFromDropDown(strAudName);
					Thread.sleep(5000);
					rm.webElementSync(mediaRanker.weightedRankerBtn, "visibility");
					rm.webElementSync(mediaRanker.weightedRankerBtn, "clickable");
					selectedAudName = pageHeader.getSelectedAudienceName();
					System.out.println(selectedAudName);
					
					if(selectedAudName.equalsIgnoreCase(strAudName)){
						CustomReporter.log("Audience is changed successfully!");
						System.out.println("Audience is changed successfully!");
					}else{
						rm.captureScreenShot("8841_"+linkName+"FailedTochangeAud", "fail");
						CustomReporter.errorLog("Failed to change audience in drop down in page "+linkName+" it should be ["+strAudName+"] instead of ["+selectedAudName+"]");
						System.out.println("Failed to change audience in drop down in page "+linkName+" it should be ["+strAudName+"] instead of ["+selectedAudName+"]");
						BaseClass.testCaseStatus = false;
					}
				}
			}
			
			//hva
			if(linkName.equalsIgnoreCase(property.getProperty("hva"))){
				HVA_Page hvaPage = new HVA_Page(driver);
				rm.webElementSync("idiomspinningcomplete");
				rm.webElementSync(hvaPage.behaviour, "clickable");
				
				if(!hvaPage.behaviour.isDisplayed()){
					rm.captureScreenShot("8841_failedToOpen"+linkName+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					System.out.println("Failed to open "+linkName+" page on clicking ["+linkName+" from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(linkName+" page is open on clicking ["+linkName+"] from project dashboard page");
					System.out.println(linkName+" home page is open on clicking ["+linkName+"] from project dashboard page");
					
					//changing audience
					pageHeader.selectAudienceFromDropDown(strDefaultAud);
					Thread.sleep(5000);
					rm.webElementSync(hvaPage.behaviour, "clickable");
					selectedAudName = pageHeader.getSelectedAudienceName();
					System.out.println(selectedAudName);
					
					if(selectedAudName.equalsIgnoreCase(strDefaultAud)){
						CustomReporter.log("Audience is changed successfully!");
						System.out.println("Audience is changed successfully!");
					}else{
						rm.captureScreenShot("8841_"+linkName+"FailedTochangeAud", "fail");
						CustomReporter.errorLog("Failed to change audience in drop down in page "+linkName+" it should be ["+strAudName+"] instead of ["+selectedAudName+"]");
						System.out.println("Failed to change audience in drop down in page "+linkName+" it should be ["+strAudName+"] instead of ["+selectedAudName+"]");
						BaseClass.testCaseStatus = false;
					}
				}
			}
			
			//Pathing
			
			if(linkName.equalsIgnoreCase(property.getProperty("pathing"))){
				Analyse_Pathing_Page pathingPage = new Analyse_Pathing_Page(driver);
				rm.webElementSync("idiomspinningcomplete");
				rm.webElementSync(pathingPage.pathing_selectPathOnWheel, "clickable");
				
				if(!pathingPage.pathingWheel.isDisplayed()){
					rm.captureScreenShot("8841_failedToOpen"+linkName+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					System.out.println("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(linkName+" page is open on clicking ["+linkName+"] from project dashboard page");
					System.out.println(linkName+" home page is open on clicking ["+linkName+"] from project dashboard page");
					
					//changing audience
					pageHeader.selectAudienceFromDropDown(strAudName);
					Thread.sleep(5000);
					rm.webElementSync(pathingPage.pathing_selectPathOnWheel, "visible");
					rm.webElementSync(pathingPage.pathing_selectPathOnWheel, "clickable");
					selectedAudName = pageHeader.getSelectedAudienceName();
					System.out.println(selectedAudName);
					
					if(selectedAudName.equalsIgnoreCase(strAudName)){
						CustomReporter.log("Audience is changed successfully!");
						System.out.println("Audience is changed successfully!");
					}else{
						rm.captureScreenShot("8841_"+linkName+"FailedTochangeAud", "fail");
						CustomReporter.errorLog("Failed to change audience in drop down in page "+linkName+" it should be ["+strAudName+"] instead of ["+selectedAudName+"]");
						System.out.println("Failed to change audience in drop down in page "+linkName+" it should be ["+strAudName+"] instead of ["+selectedAudName+"]");
						BaseClass.testCaseStatus = false;
					}
				}
			}
			
			//web net
			if(linkName.equalsIgnoreCase(property.getProperty("webnet"))){
				rm.webElementSync("idiomspinningcomplete");
				Analyse_Webnet_Page webNetPage = new Analyse_Webnet_Page(driver);
				rm.webElementSync(webNetPage.chart, "visibility");
				
				if(!webNetPage.chart.isDisplayed()){
					rm.captureScreenShot("8841_failedToOpen"+linkName+"Page", "fail");
					CustomReporter.errorLog("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					System.out.println("Failed to open "+linkName+" page on clicking ["+linkName+"] from project dashboard page");
					BaseClass.testCaseStatus = false;
				}else{
					CustomReporter.log(linkName+" page is open on clicking ["+linkName+"] from project dashboard page");
					System.out.println(linkName+" home page is open on clicking ["+linkName+"] from project dashboard page");
					
					//changing audience
					pageHeader.selectAudienceFromDropDown(strDefaultAud);
					Thread.sleep(5000);
					
					rm.webElementSync(webNetPage.chart, "visibility");
					
					selectedAudName = pageHeader.getSelectedAudienceName();
					System.out.println(selectedAudName);
					
					if(selectedAudName.equalsIgnoreCase(strDefaultAud)){
						CustomReporter.log("Audience is changed successfully!");
						System.out.println("Audience is changed successfully!");
					}else{
						rm.captureScreenShot("8841_"+linkName+"FailedTochangeAud", "fail");
						CustomReporter.errorLog("Failed to change audience in drop down in page "+linkName+" it should be ["+strAudName+"] instead of ["+selectedAudName+"]");
						System.out.println("Failed to change audience in drop down in page "+linkName+" it should be ["+strAudName+"] instead of ["+selectedAudName+"]");
						BaseClass.testCaseStatus = false;
					}
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
		rm.captureScreenShot("8841", "fail");
	}finally{
		try{
			lp.returnToIdiomLink.click();
			Thread.sleep(2000);
			lp.func_LoginToIDIOM(property.getProperty("SuperAdminUser"),property.getProperty("SuperAdminpassword"));
			
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
}
}
