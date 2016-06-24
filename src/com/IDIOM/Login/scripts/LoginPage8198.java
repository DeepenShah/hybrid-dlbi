
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

import common.BaseClass;
import common.IDIOMException;
/** 
 * <p> <b>Test Case Name:</b>534_Logout_Verify Logout Functionality</p>
 * <p> <b>Objective:</b>Verify the logout functionality from all the pages.</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8198.aspx/p>
 * <p> <b>Module:</b> Login</p>
 * @author Amrutha Nair
 * @since  4th August 2015
 * @modified Date:23 June 2016
 *
 */


public class LoginPage8198 extends BaseClass {
		
	@Test
	public void verifyLogout() throws InterruptedException {
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
		
			//Step 1: 	Open  url				
					
			CustomReporter.log("Launch Browser and Enter URL");
			Login_Page ln = new Login_Page(driver);	
			
			//Step 2:Login with valid credentails
					
			CustomReporter.log("Enter domain email id and password");
			ln.func_LoginToIDIOM(strEmailId, strPassword);
					
			ClientList_Page CL = new ClientList_Page(driver);
			
			//Step 3:Click on Logout at the right top  
			
			
			CustomReporter.log("---------------Client Home page, Logout Functionality---------------");
			pageHeader.performAction("logout");
				
			if (ln.func_ElementExist("Login Button")){
				CustomReporter.log("Successfully clicked on logout button from Clienthom page and reverted back to Login page");
			}
			else {
				strMsg="Client home page is not get redirected to login page on clicking on Logout ";
				throw new IDIOMException(strMsg+"###8198_CL_Logout");
				
			}
				
		
			
			

			CustomReporter.log("---------------Project page, Logout Functionality---------------");
			
			//Step 4:Login with valid credentails again
			loginToSelectClient(strEmailId,strPassword);
			strMsg="The user has logged in with valid super admin credentials and selected a client";
			CustomReporter.log(strMsg);
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
				throw new IDIOMException("Not able to verify new project window###8198-CreateProjectWindow");
										
			//Input Project name and description and click on Save
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8198-AudienceTabNotFound");
				
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
				rm.captureScreenShot("8198_projectname", "fail");
			}
			
			
			
			//Click on Logout at the right top  
			
			pageHeader.performAction("logout");
				
			if (ln.func_ElementExist("Login Button")){
				CustomReporter.log("Successfully clicked on logout button from project dash board page and reverted back to Login page");
			}
			else {
				strMsg="Client home page is not get redirected to login page on clicking on Logout ";
				throw new IDIOMException(strMsg+"###8198_CL_Logout_PDP");
				
			}
			
			
			CustomReporter.log("--------------Audience Builder, Logout Functionality---------------");
			
			//Login and access till project home page
			
			loginToSelectClient(strEmailId,strPassword);
			strMsg="The user has logged in with valid super admin credentials and selected a client";
			CustomReporter.log(strMsg);
			
			clientListPage.launchProject(strProjectName);
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
				rm.captureScreenShot("8198_projectname", "fail");
			}
						
			//Open Success Metrics page
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
		
			pageHeader.performAction("logout");
			
			if (ln.func_ElementExist("Login Button")){
				CustomReporter.log("Successfully clicked on logout button from success metrics page and reverted back to Login page");
			}
			else {
				strMsg="Client home page is not get redirected to login on clicking on Logout ";
				throw new IDIOMException(strMsg+"###8198_CL_Logout_SM");
				
			}


			CustomReporter.log("---------------Profile, Logout Functionality---------------");
			
			loginToSelectClient(strEmailId,strPassword);
			strMsg="The user has logged in with valid super admin credentials and selected a client";
			CustomReporter.log(strMsg);
			
			clientListPage.launchProject(strProjectName);
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
				rm.captureScreenShot("8198_projectname", "fail");
			}
						
			
			pdp.navigateTo(property.getProperty("profile"));
			
			rm.webElementSync("idiomspinningcomplete");
			Analyse_Profile_Page ap=new Analyse_Profile_Page(driver);
			if(ap.isVisible("summary")){
				strMsg="The Profile page is visible";
				CustomReporter.log(strMsg);
			}
			
			pageHeader.performAction("logout");
			
			if (ln.func_ElementExist("Login Button")){
				CustomReporter.log("Successfully clicked on logout button from profile page and reverted back to Login page");
			}
			else {
				strMsg="Client home page is not get redirected to login page on clicking on Logout ";
				throw new IDIOMException(strMsg+"###8198_CL_Logout_profile");
				
			}
			
			
			
			
			CustomReporter.log("---------------webnet page, Logout Functionality---------------");
			
			loginToSelectClient(strEmailId,strPassword);
			strMsg="The user has logged in with valid super admin credentials and selected a client";
			CustomReporter.log(strMsg);
			
			clientListPage.launchProject(strProjectName);
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
				rm.captureScreenShot("8198_projectname", "fail");
			}
						
			
			pdp.navigateTo(property.getProperty("webnet"));
			
			rm.webElementSync("idiomspinningcomplete");
			Analyse_Webnet_Page webnet=new Analyse_Webnet_Page(driver);
			if(webnet.isVisible("webnetchart")){
				strMsg="The webnet  page is visible";
				CustomReporter.log(strMsg);
			}
			
			
			pageHeader.performAction("logout");
			if (ln.func_ElementExist("Login Button")){
				CustomReporter.log("Successfully clicked on logout button from webnet page and reverted back to Login page");
			}
			else {
				strMsg="Client home page is not get redirected to login page on clicking on Logout ";
				throw new IDIOMException(strMsg+"###8198_CL_Logout_webnet");
				
			}
			
			
			
			//hVA
			CustomReporter.log("----- HVA page Logout Checking-------");
			loginToSelectClient(strEmailId,strPassword);
			strMsg="The user has logged in with valid super admin credentials and selected a client";
			CustomReporter.log(strMsg);
			
			clientListPage.launchProject(strProjectName);
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
				rm.captureScreenShot("8198_projectname", "fail");
			}
						
			
			pdp.navigateTo(property.getProperty("hva"));
			

			strMsg="The user has clicked on 'hva' link ";
			CustomReporter.log(strMsg);
			
			rm.webElementSync("idiomspinningcomplete");
			HVA_Page hva=new HVA_Page(driver);
			if(hva.isVisible("hva_chart")){
				strMsg="The HVA  page is visible";
				CustomReporter.log(strMsg);
			}
			
			Thread.sleep(5000);
			
			pageHeader.performAction("logout");
			if (ln.func_ElementExist("Login Button")){
				CustomReporter.log("Successfully clicked on logout button from hva page and reverted back to Login page");
			}
			else {
				strMsg="Client home page is not get redirected to login page on clicking on Logout ";
				throw new IDIOMException(strMsg+"###8198_CL_Logout_hva");
				
			}
			
			
			//Pathing
			CustomReporter.log("----- Pathing page Logout Checking-------");
			
			loginToSelectClient(strEmailId,strPassword);
			strMsg="The user has logged in with valid super admin credentials and selected a client";
			CustomReporter.log(strMsg);
			
			clientListPage.launchProject(strProjectName);
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
				rm.captureScreenShot("8198_projectname", "fail");
			}
						
			
			pdp.navigateTo(property.getProperty("pathing"));
			

			strMsg="The user has clicked on 'pathing' link ";
			CustomReporter.log(strMsg);
			
			rm.webElementSync("idiomspinningcomplete");
			Analyse_Pathing_Page pathing=new Analyse_Pathing_Page(driver);
			if(pathing.isVisible("behaviourdropdown")){
				strMsg="The pathing  page is visible";
				CustomReporter.log(strMsg);
			}
			
			
			pageHeader.performAction("logout");
			if (ln.func_ElementExist("Login Button")){
				CustomReporter.log("Successfully clicked on logout button from pathing page and reverted back to Login page");
			}
			else {
				strMsg="Client home page is not get redirected to login page on clicking on Logout ";
				throw new IDIOMException(strMsg+"###8198_CL_Logout_pathing");
				
			}
			
			
			
			CustomReporter.log("----- Digital Ranker page Logout Checking-------");
			

			loginToSelectClient(strEmailId,strPassword);
			strMsg="The user has logged in with valid super admin credentials and selected a client";
			CustomReporter.log(strMsg);
			
			clientListPage.launchProject(strProjectName);
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
				rm.captureScreenShot("8198_projectname", "fail");
			}
						
			
			pdp.navigateTo(property.getProperty("digitalRanker"));
			

			strMsg="The user has clicked on 'digitalRanker' link ";
			CustomReporter.log(strMsg);
			
			rm.webElementSync("idiomspinningcomplete");
			ArchitectMediaRankerPage mediaRanker=new ArchitectMediaRankerPage(driver);
			if(mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "")){
				strMsg="The 'digitalRanker' page is visible  ";
				CustomReporter.log(strMsg);
			}
			
			
			
			pageHeader.performAction("logout");
			if (ln.func_ElementExist("Login Button")){
				CustomReporter.log("Successfully clicked on logout button from digitalRanker page and reverted back to Login page");
			}
			else {
				strMsg="Client home page is not get redirected to login page on clicking on Logout ";
				throw new IDIOMException(strMsg+"###8198_CL_Logout_digitalRanker");
				
			}
			
			CustomReporter.log("----- TV Ranker page Logout Checking-------");
			

			loginToSelectClient(strEmailId,strPassword);
			strMsg="The user has logged in with valid super admin credentials and selected a client";
			CustomReporter.log(strMsg);
			
			clientListPage.launchProject(strProjectName);
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
				rm.captureScreenShot("8198_projectname", "fail");
			}
						
			
			pdp.navigateTo(property.getProperty("tvRanker"));
			

			strMsg="The user has clicked on 'digitalRanker' link ";
			CustomReporter.log(strMsg);
			
			rm.webElementSync("idiomspinningcomplete");
			
			if(mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "")){
				strMsg="The 'tvRanker' page is visible  ";
				CustomReporter.log(strMsg);
			}
			
			pageHeader.performAction("logout");
			if (ln.func_ElementExist("Login Button")){
				CustomReporter.log("Successfully clicked on logout button from tvRanker page and reverted back to Login page");
			}
			else {
				strMsg="Client home page is not get redirected to login page on clicking on Logout ";
				throw new IDIOMException(strMsg+"###8198_CL_Logout_tvRanker");
				
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
				rm.captureScreenShot("8198", "fail");
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