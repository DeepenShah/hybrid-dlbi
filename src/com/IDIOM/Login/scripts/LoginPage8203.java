package com.IDIOM.Login.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.Audience_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ManageIdiom_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.IDIOM.pages.UserAdminMyAccount_Page;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;
//import com.msat.frameworks.data_driven.generic.reusables.GenericComponents;





import common.BaseClass;
import common.IDIOMException;
/** 
 * <p> <b>Test Case Name:</b>323_Login_Verify Refreshing a page does not redirects to login page</p>
 * <p> <b>Objective:</b>Login_Verify Refreshing a page does not redirects to login page.</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8203.aspx/p>
 * <p> <b>Module:</b> Login</p>
 * @author Amrutha Nair
 * @since  4th August 2015
 * @modified Date:23 June 2016
 *
 */


public class LoginPage8203 extends BaseClass {
		
	@Test
	public void verifyRefreshingNotLeadingToLoginPage() throws InterruptedException {
		String strMsg=null;
		String strProjectName=null;
		boolean bProjectCreate=false;
		String strDetails=null;
				//****************Variables declaration and Initialization****************************	
				String strClientName=property.getProperty("clientName");
				strProjectName="TestProject " + BaseClass.rm.getCurrentDateTime();
				String strProjectDescription=property.getProperty("projectDescriptionScenario3");
				
				String strEmailId = property.getProperty("SuperAdminUser");
				String strPassword = property.getProperty("SuperAdminpassword");
				
				//****************Test step starts here************************************************
				
				try{
		
				//Step 1:Open URL 
				
				CustomReporter.log("Launch Browser and Enter URL");
				Login_Page ln = new Login_Page(driver);	
				//Step 2:Login with valid creds
				CustomReporter.log("Enter domain email id and password");
				ln.func_LoginToIDIOM(strEmailId, strPassword);
				
				
				CustomReporter.log("Do refresh from Client list page");
				
				//Step 3:Navigate to any page & refresh the page
				Thread.sleep(3000);
				driver.navigate().refresh();
				if (ln.func_ElementExist("Login Button")){
					strMsg="On refreshing from Client home page, the user got logged out";
					throw new IDIOMException(strMsg+"###8203_RefreshIssue_ClientHomePage");
					
					
				}
				else {
					CustomReporter.log("On refreshing from Client home page, the user didn't got logged out");
				}
				
				
				clientListPage=new ClientList_Page(driver);
				
				//Verifies whether all project list or No project msg is coming for selected client
				strMsg=clientListPage.verifyProjectsInClientHomePage(strClientName);
				
				CustomReporter.log(strMsg);
				
				//Click on 'New Project'
				//Provide name and description. And click save
				
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
					throw new IDIOMException("Not able to verify new project window###8203-CreateProjectWindow");
											
				//Input Project name and description and click on Save
			
				clientListPage.fillProject(strProjectName,strProjectDescription);				
				clientListPage.func_PerformAction("Save Project");
				
				// Verifying if project is saved and landed to audience tab
				if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
					throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8203-AudienceTabNotFound");
					
				strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
				CustomReporter.log(strMsg);
				bProjectCreate=true;
				Thread.sleep(2000);
				 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
				
				 //Step 5:		On audience tab, Click on '+New Audience'
				 //Step 6:Enter name and click on 'CreateAndSave'
				String audienceName = clientListPage.createNewAudience("");
				strMsg = "The frst audience ' " + audienceName +" 'is added for the project'"+strProjectName+"'";
				CustomReporter.log(strMsg);
				strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");				
				
				
				
				//Step 8:Click on 'Launch Project'
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
					rm.captureScreenShot("8203_projectname", "fail");
				}
				
				
				
				driver.navigate().refresh();
				if (ln.func_ElementExist("Login Button")){
					strMsg="On refreshing from Project dashboard page, the user got logged out";
					throw new IDIOMException(strMsg+"###8203_RefreshIssue_ProjectHomePage");
					
					
				}
				else {
					CustomReporter.log("On refreshing from Project dashboard page, the user didn't got logged out");
				}
				
				//Open Success Metrics page
				pdp.navigateTo(property.getProperty("successMetrics"));
				strMsg="Navigated to success metric page";
				CustomReporter.log(strMsg);
			
				AudienceBuilderPage AD = new AudienceBuilderPage(driver);
				
				AD.isVisible("nosuccessmetrictext", "");
				strMsg="Successfully landed on Success Metric page";
				CustomReporter.log(strMsg);
			
				driver.navigate().refresh();
				if (ln.func_ElementExist("Login Button")){
					strMsg="On refreshing from Success Metric page, the user got logged out";
					throw new IDIOMException(strMsg+"###8203_RefreshIssue_Metrics");
					
					
				}
				else {
					CustomReporter.log("On refreshing from Success Metric page, the user didn't got logged out");
				}
				
				
				//Click on 'Audience Definition' tab
				AD.performAction("gotoaudiencedefinition");
				rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
				CustomReporter.log("Clicked on Audience Definition tab");
				
				
				
				driver.navigate().refresh();
				if (ln.func_ElementExist("Login Button")){
					strMsg="On refreshing from Audience Definitio page, the user got logged out";
					throw new IDIOMException(strMsg+"###8203_RefreshIssue_AudienceDefinition");
					
					
				}
				else {
					CustomReporter.log("On refreshing from Audience Definition page, the user didn't got logged out");
				}
				
				
				
				//Click on '>' from bottom navigation bar
				pageHeader.megaMenuLinksClick(property.getProperty("profile"));
				
				rm.webElementSync("idiomspinningcomplete");
				Analyse_Profile_Page ap=new Analyse_Profile_Page(driver);
				if(ap.isVisible("summary")){
					strMsg="The Profile page is visible";
					CustomReporter.log(strMsg);
				}
				
				driver.navigate().refresh();
				if (ln.func_ElementExist("Login Button")){
					strMsg="On refreshing from profile page, the user got logged out";
					throw new IDIOMException(strMsg+"###8203_RefreshIssue_Profile");
					
					
				}
				else {
					CustomReporter.log("On refreshing from profile page, the user didn't got logged out");
				}
				rm.webElementSync("idiomspinningcomplete");
				
				if(ap.isVisible("summary")){
					strMsg="The Profile page is visible";
					CustomReporter.log(strMsg);
				}
				Thread.sleep(5000);
				//Click on mega menu and select 'Webnet'
				pageHeader.megaMenuLinksClick(property.getProperty("webnet"));
				
				
				strMsg="The user has clicked on 'webnet' link from  megamenu";
				CustomReporter.log(strMsg);
				
				rm.webElementSync("idiomspinningcomplete");
				Analyse_Webnet_Page webnet=new Analyse_Webnet_Page(driver);
				if(webnet.isVisible("webnetchart")){
					strMsg="The webnet  page is visible";
					CustomReporter.log(strMsg);
				}
				
				
				Thread.sleep(3000);
				driver.navigate().refresh();
				if (ln.func_ElementExist("Login Button")){
					strMsg="On refreshing from webnet page, the user got logged out";
					throw new IDIOMException(strMsg+"###8203_RefreshIssue_Webnet");
					
					
				}
				else {
					CustomReporter.log("On refreshing from Webnet page, the user didn't got logged out");
				}
				if(webnet.isVisible("webnetchart")){
					strMsg="The webnet  page is visible";
					CustomReporter.log(strMsg);
				}
				
				Thread.sleep(5000);
				//Step 15:Select HVA from mega menu
				
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
				
				driver.navigate().refresh();
				if (ln.func_ElementExist("Login Button")){
					strMsg="On refreshing from hva page, the user got logged out";
					throw new IDIOMException(strMsg+"###8203_RefreshIssue_HVA");
					
					
				}
				else {
					CustomReporter.log("On refreshing from HVA page, the user didn't got logged out");
				}
				rm.webElementSync("idiomspinningcomplete");
				
				if(hva.isVisible("hva_chart")){
					strMsg="The HVA  page is visible";
					CustomReporter.log(strMsg);
				}
				
				Thread.sleep(5000);
				
				
				
				
				
				pageHeader.megaMenuLinksClick(property.getProperty("pathing"));
				strMsg="The user has clicked on 'pathing' link ";
				CustomReporter.log(strMsg);
				
				rm.webElementSync("idiomspinningcomplete");
				Analyse_Pathing_Page pathing=new Analyse_Pathing_Page(driver);
				if(pathing.isVisible("behaviourdropdown")){
					strMsg="The pathing  page is visible";
					CustomReporter.log(strMsg);
				}
				
				driver.navigate().refresh();
				if (ln.func_ElementExist("Login Button")){
					strMsg="On refreshing from pathing page, the user got logged out";
					throw new IDIOMException(strMsg+"###8203_RefreshIssue_pathing");
					
					
				}
				else {
					CustomReporter.log("On refreshing from pathing page, the user didn't got logged out");
				}
				rm.webElementSync("idiomspinningcomplete");
				
				if(pathing.isVisible("behaviourdropdown")){
					strMsg="The pathing  page is visible";
					CustomReporter.log(strMsg);
				}
				
				Thread.sleep(5000);
				
				
				//	Select Digital Ranker page from Mega menu
				pageHeader.megaMenuLinksClick(property.getProperty("digitalRanker"));
				
				
				strMsg="The user has clicked on 'digitalRanker' link from  megamenu";
				CustomReporter.log(strMsg);
				
				rm.webElementSync("idiomspinningcomplete");
				ArchitectMediaRankerPage mediaRanker=new ArchitectMediaRankerPage(driver);
				if(mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "")){
					strMsg="The 'digitalRanker' page is visible  ";
					CustomReporter.log(strMsg);
				}
				
				driver.navigate().refresh();
				if (ln.func_ElementExist("Login Button")){
					strMsg="On refreshing from digitalRanker page, the user got logged out";
					throw new IDIOMException(strMsg+"###8203_RefreshIssue_digitalRanker");
					
					
				}
				else {
					CustomReporter.log("On refreshing from digitalRanker page, the user didn't got logged out");
				}
				rm.webElementSync("idiomspinningcomplete");
				
				if(mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "")){
					strMsg="The 'digitalRanker' page is visible  ";
					CustomReporter.log(strMsg);
				}
				
				
				pageHeader.megaMenuLinksClick(property.getProperty("tvRanker"));
				strMsg="The user has clicked on 'tvRanker' ";
				CustomReporter.log(strMsg);
				
				rm.webElementSync("idiomspinningcomplete");
				
				if(mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "")){
					strMsg="The 'tvRanker' page is visible  ";
					CustomReporter.log(strMsg);
				}
				
				driver.navigate().refresh();
				if (ln.func_ElementExist("Login Button")){
					strMsg="On refreshing from tv Ranker page, the user got logged out";
					throw new IDIOMException(strMsg+"###8203_RefreshIssue_tvRanker");
					
					
				}
				else {
					CustomReporter.log("On refreshing from tv Ranker page, the user didn't got logged out");
				}
				rm.webElementSync("idiomspinningcomplete");
				
				if(mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "")){
					strMsg="The 'tv' page is visible  ";
					CustomReporter.log(strMsg);
				}
				//Click on Person icon from top right corner and click My Account page
				Thread.sleep(5000);
				pageHeader.performAction("myaccount");
				UserAdminMyAccount_Page myAccount=new UserAdminMyAccount_Page(driver);
				strMsg="The user has clicked on 'My account ' link from  person icon";
				CustomReporter.log(strMsg);
				
				Thread.sleep(3000);
				 if(myAccount.isVisible("clientssection")){
					 strMsg="The My Account page is landed";
						CustomReporter.log(strMsg);
				 }
				
				 
				 driver.navigate().refresh();
				if (ln.func_ElementExist("Login Button")){
					strMsg="On refreshing from  my acount page, the user got logged out";
					throw new IDIOMException(strMsg+"###8203_RefreshIssue_myaccount");
					
					
				}
				else {
					CustomReporter.log("On refreshing from my account page, the user didn't got logged out");
				}
				
				Thread.sleep(3000);
				 if(myAccount.isVisible("clientssection")){
					 strMsg="The My Account page is landed";
						CustomReporter.log(strMsg);
				 }
					 
					 
				 //:Click on 'Admin Access' from person menu on top
				 pageHeader.performAction("adminaccess");
				strMsg="The user has clicked on 'Admin Access ' link from  person icon";
				CustomReporter.log(strMsg);
				rm.webElementSync("idiomspinningcomplete");
				Thread.sleep(3000);
				UserAdmin_UserPermissions_Page adminAccess= new UserAdmin_UserPermissions_Page(driver);
				if(adminAccess.isVisible("createuserbutton")){
					strMsg="The admin access page is landed with Create new user button";
					CustomReporter.log(strMsg);
					
					}
				
				 driver.navigate().refresh();
					if (ln.func_ElementExist("Login Button")){
						strMsg="On refreshing from  admin access page, the user got logged out";
						throw new IDIOMException(strMsg+"###8203_RefreshIssue_adminaccess");
						
						
					}
					else {
						CustomReporter.log("On refreshing from admin access page, the user didn't got logged out");
					}
					
					Thread.sleep(3000);
					 if(myAccount.isVisible("clientssection")){
						 strMsg="The My Account page is landed";
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
					rm.captureScreenShot("8203", "fail");
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
				