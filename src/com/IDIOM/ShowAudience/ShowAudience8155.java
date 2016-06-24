package com.IDIOM.ShowAudience;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.IDIOM.pages.ShowAudiencePage;
import com.reports.CustomReporter;


import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b>**Analysis_Show_Audience Widget_Verify_Widget_Display</p>
 * <p> <b>Objective:</b>OVerify the user is able to access the "show audience" widget from every "analyze" panel (profile, pathing, HVA, web net).</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8155.aspx/p>
 * <p> <b>Module:</b> Show Audience</p>
 * @author Amrutha Nair
 * @since 17-June-2016
 *
 */
public class ShowAudience8155 extends BaseClass {
		
	@Test
	public void verifyShowAudiencePanelDisplay(){
	String strMsg = null;
		String strProjectName="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
	
		String strDetails=null;
		try{
			
			
		
			//****************Variables declaration and Initialization****************************	
		
			String strClientName=property.getProperty("clientName");
			strProjectName="TestProject " + BaseClass.rm.getCurrentDateTime();
			String strProjectDescription=property.getProperty("projectDescriptionScenario3");
			String strMetrics=property.getProperty("commonSuccessMetrics");
			String strAudiences=property.getProperty("commonAudienceAttribute2");
			
			//****************Test step starts here************************************************
			
			//Step1 :			Launch browser and enter IDIOM Url
			//Step 2:Enter valid username and password Click on 'login to idiom' button
			
			//Step 3:In client home page, select a client from dropdown
			loginToSelectClient();
			clientListPage=new ClientList_Page(driver);
			//Verifies whether all project list or No project msg is coming for selected client
			strMsg=clientListPage.verifyProjectsInClientHomePage(strClientName);
			
			CustomReporter.log(strMsg);
			
			//Step 4:Click on new project button
			
			//create New Project
			clientListPage.func_PerformAction("New Project");
			rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Fill the project
			clientListPage.findAndSaveProjectWindow(true, "");
			
			//Before filling details, checking 'Audience' tab is exist or not. It should be false.
			if(clientListPage.func_ClientListPageElementExist("audiencetab"))
				throw new IDIOMException("Not able to verify new project window###8155-CreateProjectWindow");
										
			//Step 5:Enter valid name and description for project and click on Save button
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8155-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
						
			
			//Step 6:Click on launch project
			clientListPage.func_PerformAction("Launch Project");
			strMsg = "Clicked on Launch Project";
			CustomReporter.log(strMsg);
			Thread.sleep(2000);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Waiting till page get loaded
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			if(pdp.isVisible("projectname", strProjectName)){
				strMsg="The project home page has been launched and it is getting updated with selected project name";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The seleced project name is not coming in project home page";
				CustomReporter.errorLog(strMsg);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8155_projectname", "fail");
			}
			
			
			
			//Step 7:Navigate to success metrics
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			
			//Step8:Define few success metrics or do not define
			
			AD.selectMetricByName(strMetrics);
			strMsg="The user has added a few success metrics ";
			CustomReporter.log(strMsg);
			
			
			
			//Step 9:Click on Audience Definition link from project dashboard
			//Step 5:Click on Audience definition tab
			AD.performAction("gotoaudiencedefinition");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on Audience Definition tab");
			
			
			Thread.sleep(3000);
		
			
			//Step 10:Select few attributes and add them
			AD.selectAttributeOnAudienceDefinition(strAudiences);
			AD.performAction("addattribute");
			
			strMsg="The user has selected a query in audience definition page";
			CustomReporter.log(strMsg);
			
			Thread.sleep(5000);
			AD.goToFirstLevelForMetricOrAttribute();
			
			
			//Step 10:Select Profile from mega menu
			
			pageHeader.megaMenuLinksClick(property.getProperty("profile"));
			
			
			strMsg="The user has clicked on 'Profile' link from  megamenu";
			CustomReporter.log(strMsg);
			
			rm.webElementSync("idiomspinningcomplete");
			Analyse_Profile_Page ap=new Analyse_Profile_Page(driver);
			if(ap.isVisible("summary")){
				strMsg="The Profile page is visible";
				CustomReporter.log(strMsg);
			}
			
			//Step 11:Click on the "show audience" button.
			
			Thread.sleep(5000);
			ap.performAction("showaudience");
			
			strMsg="The user has clicked on 'Show Audience Button";
			CustomReporter.log(strMsg);
			
			ShowAudiencePage SH=new ShowAudiencePage(driver);
			if(SH.isVisible("populationpane")){
				strMsg="Show audience panel is visible";
				CustomReporter.log(strMsg);
			}
			if(SH.verifyShowAudiencePanelWidth()){
				strMsg="Show audience panel width is fraction of page width. It is less than half of page width in profile page";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="Show audience panel width is not less than half of the page width in profile page";
				CustomReporter.errorLog(strMsg);
				rm.captureScreenShot("8155_ProfileShowAudienceWidth", "fail");
				BaseClass.testCaseStatus=false;
			}
			Thread.sleep(3000);
			//Step 12:Click again on the "show audience" button
			
			
			
			ap.performAction("showaudience");
			Thread.sleep(5000);
			strMsg="The user has clicked on 'Show Audience Button again";
			CustomReporter.log(strMsg);
			if(SH.isVisible("populationpane")){
				strMsg="Show audience panel is visible in profile page after clicking to minimise the same";
				CustomReporter.errorLog(strMsg);
				rm.captureScreenShot("8155_ProfileShowAudienceVisible", "fail");
				BaseClass.testCaseStatus=false;
			}
			else{
				strMsg="Show audience panel is not visible in profile page after clicking to minimise the same";
				CustomReporter.log(strMsg);
			}
			
			
			//Step 13:Click on the "pathing" button from megamenu
			pageHeader.megaMenuLinksClick(property.getProperty("pathing"));
			
			
			strMsg="The user has clicked on 'pathing' link from  megamenu";
			CustomReporter.log(strMsg);
			
			rm.webElementSync("idiomspinningcomplete");
			Analyse_Pathing_Page pathing=new Analyse_Pathing_Page(driver);
			if(pathing.isVisible("behaviourdropdown")){
				strMsg="The pathing  page is visible";
				CustomReporter.log(strMsg);
			}
			
			Thread.sleep(5000);
			
			
			
			//Step 13:Click on the "show audience" button..
			
		
			ap.performAction("showaudience");
			
			strMsg="The user has clicked on 'Show Audience Button";
			CustomReporter.log(strMsg);
			
			
			if(SH.isVisible("populationpane")){
				strMsg="Show audience panel is visible";
				CustomReporter.log(strMsg);
			}
			if(SH.verifyShowAudiencePanelWidth()){
				strMsg="Show audience panel width is fraction of page width. It is less than half of page width in pathing page";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="Show audience panel width is not less than half of the page width in pathing page";
				CustomReporter.errorLog(strMsg);
				rm.captureScreenShot("8155_PathingShowAudienceWidth", "fail");
				BaseClass.testCaseStatus=false;
			}
			
			Thread.sleep(3000);
			
			//Step 14:Click again on the "show audience" button.
			ap.performAction("showaudience");
			Thread.sleep(3000);
			strMsg="The user has clicked on 'Show Audience Button again";
			CustomReporter.log(strMsg);
			if(SH.isVisible("populationpane")){
				strMsg="Show audience panel is visible in pathing page after clicking to minimise the same";
				CustomReporter.errorLog(strMsg);
				rm.captureScreenShot("8155_pathingShowAudienceVisible", "fail");
				BaseClass.testCaseStatus=false;
			}
			else{
				strMsg="Show audience panel is not visible in pathing page after clicking to minimise the same";
				CustomReporter.log(strMsg);
			}
			
			
			//Step 14:Click on the "HVA" button from megamenu
			pageHeader.megaMenuLinksClick(property.getProperty("hva"));
			
			
			strMsg="The user has clicked on 'hva' link from  megamenu";
			CustomReporter.log(strMsg);
			
			rm.webElementSync("idiomspinningcomplete");
			HVA_Page hva=new HVA_Page(driver);
			if(hva.isVisible("hva_chart")){
				strMsg="The HVA  page is visible";
				CustomReporter.log(strMsg);
			}
			
			Thread.sleep(5000);
			
		
			
			//Step 15:Click on the "show audience" button..
			
		
			ap.performAction("showaudience");
			
			strMsg="The user has clicked on 'Show Audience Button";
			CustomReporter.log(strMsg);
			
			
			if(SH.isVisible("populationpane")){
				strMsg="Show audience panel is visible";
				CustomReporter.log(strMsg);
			}
			if(SH.verifyShowAudiencePanelWidth()){
				strMsg="Show audience panel width is fraction of page width. It is less than half of page width in HVA page";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="Show audience panel width is not less than half of the page width in HVA page";
				CustomReporter.errorLog(strMsg);
				rm.captureScreenShot("8155_HVAShowAudienceWidth", "fail");
				BaseClass.testCaseStatus=false;
			}
			Thread.sleep(3000);
			
			
			//Step 16::Click again on the "show audience" button.
			ap.performAction("showaudience");
			Thread.sleep(3000);
			strMsg="The user has clicked on 'Show Audience Button again";
			CustomReporter.log(strMsg);
			if(SH.isVisible("populationpane")){
				strMsg="Show audience panel is visible in hva page after clicking to minimise the same";
				CustomReporter.errorLog(strMsg);
				rm.captureScreenShot("8155_hvaShowAudienceVisible", "fail");
				BaseClass.testCaseStatus=false;
			}
			else{
				strMsg="Show audience panel is not visible in hva page after clicking to minimise the same";
				CustomReporter.log(strMsg);
			}
			
			
			//Step 17:Click on the "Web net" button from megamenu
			
			
			pageHeader.megaMenuLinksClick(property.getProperty("webnet"));
			
			
			strMsg="The user has clicked on 'webnet' link from  megamenu";
			CustomReporter.log(strMsg);
			
			rm.webElementSync("idiomspinningcomplete");
			Analyse_Webnet_Page webnet=new Analyse_Webnet_Page(driver);
			if(webnet.isVisible("webnetchart")){
				strMsg="The webnet  page is visible";
				CustomReporter.log(strMsg);
			}
			
			Thread.sleep(5000);
			
		
			
			//Step 17:Click on the "show audience" button..
			
		
			ap.performAction("showaudience");
			
			strMsg="The user has clicked on 'Show Audience Button";
			CustomReporter.log(strMsg);
			
			
			if(SH.isVisible("populationpane")){
				strMsg="Show audience panel is visible";
				CustomReporter.log(strMsg);
			}
			if(SH.verifyShowAudiencePanelWidth()){
				strMsg="Show audience panel width is fraction of page width. It is less than half of page width in webnet page";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="Show audience panel width is not less than half of the page width in webnet page";
				CustomReporter.errorLog(strMsg);
				rm.captureScreenShot("8155_webnetShowAudienceWidth", "fail");
				BaseClass.testCaseStatus=false;
			}
			
			
			Thread.sleep(3000);
			
			
			//Step 18:Click again on the "show audience" button.
			ap.performAction("showaudience");
			Thread.sleep(3000);
			strMsg="The user has clicked on 'Show Audience Button again";
			CustomReporter.log(strMsg);
			if(SH.isVisible("populationpane")){
				strMsg="Show audience panel is visible in webnet page after clicking to minimise the same";
				CustomReporter.errorLog(strMsg);
				rm.captureScreenShot("8155_webnetShowAudienceVisible", "fail");
				BaseClass.testCaseStatus=false;
			}
			else{
				strMsg="Show audience panel is not visible in webnet page after clicking to minimise the same";
				CustomReporter.log(strMsg);
			}
			
			
			//Step 19:Click on the "Digital Ranker" button from the megamenu
			pageHeader.megaMenuLinksClick(property.getProperty("digitalRanker"));
			
			
			strMsg="The user has clicked on 'digitalRanker' link from  megamenu";
			CustomReporter.log(strMsg);
			
			rm.webElementSync("idiomspinningcomplete");
			ArchitectMediaRankerPage mediaRanker=new ArchitectMediaRankerPage(driver);
			if(mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "")){
				strMsg="The 'digitalRanker' page is visible  ";
				CustomReporter.log(strMsg);
			}
		
			Thread.sleep(5000);
			
		
			//Step 19:Click on the "show audience" button..
			
		
			ap.performAction("showaudience");
			
			strMsg="The user has clicked on 'Show Audience Button";
			CustomReporter.log(strMsg);
			
			
			if(SH.isVisible("populationpane")){
				strMsg="Show audience panel is visible";
				CustomReporter.log(strMsg);
			}
			if(SH.verifyShowAudiencePanelWidth()){
				strMsg="Show audience panel width is fraction of page width. It is less than half of page width in digitalRanker page";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="Show audience panel width is not less than half of the page width in digitalRanker page";
				CustomReporter.errorLog(strMsg);
				rm.captureScreenShot("8155_digitalRankerShowAudienceWidth", "fail");
				BaseClass.testCaseStatus=false;
			}
			
			Thread.sleep(3000);
			
			
			//Step21:Click again on the "show audience" button.
			ap.performAction("showaudience");
			Thread.sleep(5000);
			strMsg="The user has clicked on 'Show Audience Button again";
			CustomReporter.log(strMsg);
			if(SH.isVisible("populationpane")){
				strMsg="Show audience panel is visible in webnet page after clicking to minimise the same";
				CustomReporter.errorLog(strMsg);
				rm.captureScreenShot("8155_webnetShowAudienceVisible", "fail");
				BaseClass.testCaseStatus=false;
			}
			else{
				strMsg="Show audience panel is not visible in webnet page after clicking to minimise the same";
				CustomReporter.log(strMsg);
			}
			
			
			
			//Step 22:Click on the "TVRanker" button from the megamenu
			pageHeader.megaMenuLinksClick(property.getProperty("tvRanker"));
			
			
			strMsg="The user has clicked on 'tvRanker' link from  megamenu";
			CustomReporter.log(strMsg);
			
			rm.webElementSync("idiomspinningcomplete");
			
			if(mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "")){
				strMsg="The 'tvRanker' page is visible  ";
				CustomReporter.log(strMsg);
			}
		
			Thread.sleep(5000);
			
			
			//Step 23:Click on the "show audience" button..
			
			
			ap.performAction("showaudience");
			
			strMsg="The user has clicked on 'Show Audience Button";
			CustomReporter.log(strMsg);
			
			
			if(SH.isVisible("populationpane")){
				strMsg="Show audience panel is visible";
				CustomReporter.log(strMsg);
			}
			if(SH.verifyShowAudiencePanelWidth()){
				strMsg="Show audience panel width is fraction of page width. It is less than half of page width in TV Ranker page";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="Show audience panel width is not less than half of the page width in TV Ranker page";
				CustomReporter.errorLog(strMsg);
				rm.captureScreenShot("8155_TVRankerShowAudienceWidth", "fail");
				BaseClass.testCaseStatus=false;
			}
			
			Thread.sleep(3000);
			
			
			//Step24:Click again on the "show audience" button.
			ap.performAction("showaudience");
			Thread.sleep(5000);
			strMsg="The user has clicked on 'Show Audience Button again";
			CustomReporter.log(strMsg);
			if(SH.isVisible("populationpane")){
				strMsg="Show audience panel is visible in TVRanker page after clicking to minimise the same";
				CustomReporter.errorLog(strMsg);
				rm.captureScreenShot("8155_TVRankerShowAudienceVisible", "fail");
				BaseClass.testCaseStatus=false;
			}
			else{
				strMsg="Show audience panel is not visible in TVRanker page after clicking to minimise the same";
				CustomReporter.log(strMsg);
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
				rm.captureScreenShot("8155", "fail");
			}finally{
				try{
					
					//Deleting newly created project
					if(bProjectCreate){
						
						rm.deleteProjectOrAudience(strDetails, true);
						Thread.sleep(2000);
						
						CustomReporter.log("Deleted the project");
					
					}
					
					//Click on logout				
					pageHeader.performAction("logout");
					strMsg = "Logged out successfully";
					CustomReporter.log(strMsg);
					System.out.println(getClass().getSimpleName() + ": " + strMsg);
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
