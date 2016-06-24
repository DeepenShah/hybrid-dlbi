package com.IDIOM.Analyse.Pathing.scripts;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;















import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b> *Pathing_Verify Audience drop down when switching audience from Another Page</p>
 * <p> <b>Objective:</b>Verify that the audience which is selected in another  page is selected in pathing by default when we navigate to it from another page</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/9047.aspx</p>
 * <p> <b>Module:</b> Pathing</p>
 * @author Amrutha Nair
 * @since 1-June-2016
 *
 */
public class Pathing9047 extends BaseClass {
		
	@Test
	public void verifySwitchingAudience(){
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
			String strDefaultAudience=property.getProperty("defaultaudience");
			//****************Test step starts here************************************************
			
			//Step1 :	Open IDIOM URL and login with valid credentials
			//Step 2:Select any existing client from client dropdown
			loginToSelectClient();
			clientListPage=new ClientList_Page(driver);
			//Verifies whether all project list or No project msg is coming for selected client
			strMsg=clientListPage.verifyProjectsInClientHomePage(strClientName);
			
			CustomReporter.log(strMsg);
			
			//Step 3:Click edit for any project/Create a project
			
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
				throw new IDIOMException("Not able to verify new project window###9049-CreateProjectWindow");
										
			//Step 4:Input Project name and description and click on Save
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###9049-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			 //Step 5:	Click on new audience link ,provide name and click on 'create and save'
			String audienceName = clientListPage.createNewAudience("");
			strMsg = "The first audience ' " + audienceName +" 'is added for the project'"+strProjectName+"'";
			CustomReporter.log(strMsg);
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");				
			
			
			
			//Step 7:Click on launch project
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
				rm.captureScreenShot("9049_projectname", "fail");
			}
			
			
			
			//Step 7":Select Pathing destination link from here
			
			pdp.navigateTo(property.getProperty("pathing"));
			
			Analyse_Pathing_Page pathing = new Analyse_Pathing_Page(driver);
			
			rm.webElementSync("idiomspinningcomplete");	
			
			if(pathing.isVisible("pathing_wheel")){
				strMsg="Navigated to pathing  page, Pathing wheel is visible";
				CustomReporter.log(strMsg);
			}
			
			
			//Step 8:Navigate to any other page like ;HVA or webnet " from mega menu
			
			PageHeader PH= new PageHeader(driver);
			
			PH.megaMenuLinksClick(property.getProperty("hva"));
			rm.webElementSync("idiomspinningcomplete");		
			strMsg=" The user has navigated to hva page";
			CustomReporter.log(strMsg);
			
			Thread.sleep(4000);
			
			//Step 9:Switch the audience from drop down
			PH.selectAudienceFromDropDown(strDefaultAudience);
			strMsg=" The user has selected audience '"+strDefaultAudience+"' from HVA page";
			CustomReporter.log(strMsg);
			rm.webElementSync("idiomspinningcomplete");	
			HVA_Page hva=new HVA_Page(driver);
			hva.isVisible("hva_chart");
			//Step 10:From megamenu , click on pathing
			PH.megaMenuLinksClick(property.getProperty("pathing"));
			rm.webElementSync("idiomspinningcomplete");		
	
			if(pathing.isVisible("pathing_wheel")){
				strMsg="Navigated to pathing  page from HVA page, Pathing wheel is visible";
				CustomReporter.log(strMsg);
			}
			//Step 11:Check the audience selected by default on pathing page
			
			
			if(PH.verifySelectedAudienceInDropDown(strDefaultAudience)){
				strMsg="The auidence which the user selected in HVA page, ie"+strDefaultAudience+"' is coming by default in pathing page";
				CustomReporter.log(strMsg);
			}
		
			else{
				strMsg="The auidence which the user selected in HVA page is not coming by default in pathing page";
				throw new IDIOMException(strMsg+"###9047_AudienceSelectedInPathingPage");
				
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
				rm.captureScreenShot("9049", "fail");
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
