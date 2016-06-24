package com.IDIOM.ClientHomePage;


import org.testng.Assert;
import org.testng.annotations.Test;


import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;

import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;


















import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b> *2574: Client Home Page: Launched project should not be in editable mode after coming back from any other page</p>
 * <p> <b>Objective:</b>Verify that Launched project should not be in editable mode after coming back from any other page</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8958.aspx</p>
 * <p> <b>Module:</b> Pathing</p>
 * @author Amrutha Nair
 * @since 14-June-2016
 *
 */
public class ClientHomePage8958 extends BaseClass {
		
	@Test
	public void verifyProjectNotInEditableMode(){
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
			
			//****************Test step starts here************************************************
			
			//Step1 :	Open site URL
			//Step 2:Login with valid username and password
			//Step 3:Select any client and click on New Project to create new project under this client
			loginToSelectClient();
			clientListPage=new ClientList_Page(driver);
			//Verifies whether all project list or No project msg is coming for selected client
			strMsg=clientListPage.verifyProjectsInClientHomePage(strClientName);
			
			CustomReporter.log(strMsg);
			
			
			
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
				throw new IDIOMException("Not able to verify new project window###8958-CreateProjectWindow");
										
			//Step 4:Enter valid project name and submit the form
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8958-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			
			
			
			//Step 5:Click on launch project
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
				rm.captureScreenShot("8958_projectname", "fail");
			}
			
			
			
			
			//Step 6:Now click on IDIOM logo from left top corner
			pageHeader.performAction("idiomlogo");
			strMsg="The user has clicked on idiom logo from project dash board";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			if(clientListPage.isVisible("projectwindow")){
				strMsg="The project is coming in editable mode on navigating back from project home page";
				throw new IDIOMException(strMsg+"###8958_ProjectEditableMode");
			}
			else{
				strMsg="The project is coming in editable mode on navigating back from project home page";
				CustomReporter.log(strMsg);
			}
			
			
			//Step 7:Edit any project and launch it and open success metrics page from project dashboard
			
			
			
			clientListPage.launchProject(strProjectName);
			
			strMsg="The user has selected project '"+strProjectName+"'";
			CustomReporter.log(strMsg);
			
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			Thread.sleep(2000);
			
			//Step 8:	Now click on IDIOM logo from left top corner
			pageHeader.performAction("idiomlogo");
			strMsg="The user has clicked on idiom logo from success metrics page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			if(clientListPage.isVisible("projectwindow")){
				strMsg="The project is coming in editable mode on navigating back from Success Metric page";
				throw new IDIOMException(strMsg+"###8958_ProjectEditableMode");
			}
			else{
				strMsg="The project is coming not in editable mode on navigating back from Success Metric page";
				CustomReporter.log(strMsg);
			}
			
			
			
			//Step 9:Edit any project and launch it and open audience definition page from project dashboard
			
			clientListPage.launchProject(strProjectName);
			
			strMsg="The user has selected project '"+strProjectName+"'";
			CustomReporter.log(strMsg);
			
			pdp.navigateTo(property.getProperty("audienceDefinition"));
			strMsg="Navigated to audienceDefinition page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			
			//Step 10:Now click on IDIOM logo from left top corner
			pageHeader.performAction("idiomlogo");
			strMsg="The user has clicked on idiom logo from audienceDefinition page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			if(clientListPage.isVisible("projectwindow")){
				strMsg="The project is coming in editable mode on navigating back from audienceDefinition page";
				throw new IDIOMException(strMsg+"###8958_ProjectEditableMode");
			}
			else{
				strMsg="The project is notcoming in editable mode on navigating back from audienceDefinition page";
				CustomReporter.log(strMsg);
			}
			
			
			
			//Step 11:Edit any project and launch it and open profile page from project dashboard
			
			clientListPage.launchProject(strProjectName);
			
			strMsg="The user has selected project '"+strProjectName+"'";
			CustomReporter.log(strMsg);
			
			pdp.navigateTo(property.getProperty("profile"));
			strMsg="Navigated to profile page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("idiomspinningcomplete");
			
			//Step 12:Now click on IDIOM logo from left top corne
			pageHeader.performAction("idiomlogo");
			strMsg="The user has clicked on idiom logo from profile page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			if(clientListPage.isVisible("projectwindow")){
				strMsg="The project is coming in editable mode on navigating back from profile page";
				throw new IDIOMException(strMsg+"###8958_ProjectEditableMode");
			}
			else{
				strMsg="The project is not coming   in editable mode on navigating back from profile page";
				CustomReporter.log(strMsg);
			}
			
			
			//Step 12:Edit any project and launch it and open WEBNET page from project dashboard
			clientListPage.launchProject(strProjectName);
			
			strMsg="The user has selected project '"+strProjectName+"'";
			CustomReporter.log(strMsg);
			
			pdp.navigateTo(property.getProperty("webnet"));
			strMsg="Navigated to webnet page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("idiomspinningcomplete");
			
			//Step 14:Now click on IDIOM logo from left top corner
			pageHeader.performAction("idiomlogo");
			strMsg="The user has clicked on idiom logo from webnet page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			if(clientListPage.isVisible("projectwindow")){
				strMsg="The project is coming in editable mode on navigating back from webnet page";
				throw new IDIOMException(strMsg+"###8958_ProjectEditableMode");
			}
			else{
				strMsg="The project is not coming in editable mode on navigating back from webnet page";
				CustomReporter.log(strMsg);
			}
			
			
			//Step 15:Edit any project and launch it and open Pathing page from project dashboard
			
			
			clientListPage.launchProject(strProjectName);
			
			strMsg="The user has selected project '"+strProjectName+"'";
			CustomReporter.log(strMsg);
			
			pdp.navigateTo(property.getProperty("pathing"));
			strMsg="Navigated to pathing page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("idiomspinningcomplete");
			
			//Step 16:Now click on IDIOM logo from left top corner
			
			pageHeader.performAction("idiomlogo");
			strMsg="The user has clicked on idiom logo from pathing page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			if(clientListPage.isVisible("projectwindow")){
				strMsg="The project is coming in editable mode on navigating back from pathing page";
				throw new IDIOMException(strMsg+"###8958_ProjectEditableMode");
			}
			else{
				strMsg="The project is not coming in editable mode on navigating back from pathing page";
				CustomReporter.log(strMsg);
			}
			
			
			
			//Step 17:Edit any project and launch it and open HVA page from project dashboard
			
			
			clientListPage.launchProject(strProjectName);
			
			strMsg="The user has selected project '"+strProjectName+"'";
			CustomReporter.log(strMsg);
			
			pdp.navigateTo(property.getProperty("hva"));
			strMsg="Navigated to hva page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("idiomspinningcomplete");
			
			
			//Step 18:Now click on IDIOM logo from left top corner
			
			
			pageHeader.performAction("idiomlogo");
			strMsg="The user has clicked on idiom logo from hva page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			if(clientListPage.isVisible("projectwindow")){
				strMsg="The project is coming in editable mode on navigating back from hva page";
				throw new IDIOMException(strMsg+"###8958_ProjectEditableMode");
			}
			else{
				strMsg="The project is not coming in editable mode on navigating back from hva page";
				CustomReporter.log(strMsg);
			}
			
			
			
			//Step 19:Edit any project and launch it and open digital ranker page from project dashboard

			clientListPage.launchProject(strProjectName);
			
			strMsg="The user has selected project '"+strProjectName+"'";
			CustomReporter.log(strMsg);
			
			pdp.navigateTo(property.getProperty("digitalRanker"));
			strMsg="Navigated to digitalRanker page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("idiomspinningcomplete");
			
			ArchitectMediaRankerPage AM= new ArchitectMediaRankerPage(driver);
			if(AM.func_VerifyVisibilityOfElement("datepicker", "")){
				strMsg="The 'digital ranker' page is visible  ";
				CustomReporter.log(strMsg);
			}
			
			
			
			//Step 20:Now click on IDIOM logo from left top corner
			
			
			pageHeader.performAction("idiomlogo");
			strMsg="The user has clicked on idiom logo from digital ranker page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			if(clientListPage.isVisible("projectwindow")){
				strMsg="The project is coming in editable mode on navigating back from digital ranker page";
				throw new IDIOMException(strMsg+"###8958_ProjectEditableMode");
			}
			else{
				strMsg="The project is not coming in editable mode on navigating back from digital ranker page";
				CustomReporter.log(strMsg);
			}
			
			
			//Step 21:'Edit any project and launch it and open TV Ranker page from project dashboard
			clientListPage.launchProject(strProjectName);
			
			strMsg="The user has selected project '"+strProjectName+"'";
			CustomReporter.log(strMsg);
			
			pdp.navigateTo(property.getProperty("tvRanker"));
			strMsg="Navigated to tvRanker page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("idiomspinningcomplete");
		
			if(AM.func_VerifyVisibilityOfElement("datepicker", "")){
				strMsg="The 'tv ranker' page is visible  ";
				CustomReporter.log(strMsg);
			}
			
			//Step 22:Now click on IDIOM logo from left top corner
			

			pageHeader.performAction("idiomlogo");
			strMsg="The user has clicked on idiom logo from tv  ranker page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			if(clientListPage.isVisible("projectwindow")){
				strMsg="The project is coming in editable mode on navigating back from tv ranker page";
				throw new IDIOMException(strMsg+"###8958_ProjectEditableMode");
			}
			else{
				strMsg="The project is notcoming in editable mode on navigating back from tv ranker page";
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
				rm.captureScreenShot("8958", "fail");
			}finally{
				try{
					
					//Deleting newly created project
					if(bProjectCreate){
						///////////pageHeader.navigateTo("projecthomepage");
						rm.deleteProjectOrAudience(strDetails, true);
						Thread.sleep(2000);
						
						CustomReporter.log("Deleted the project");
					
					}
					
					//Step 13:Click on logout				
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
