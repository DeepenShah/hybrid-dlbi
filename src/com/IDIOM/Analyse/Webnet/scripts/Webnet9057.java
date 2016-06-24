package com.IDIOM.Analyse.Webnet.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Webnet_Page;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;




import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b>Webnet_Verify Audience drop down when navigating from Another Page[After switching audience from both the pages</p>
 * <p> <b>Objective:</b> Verify that proper auidence is selected in audience drop down after switching audiences from both the pages</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/9057.aspx</p>
 * <p> <b>Module:</b> webnet</p>
 * @author Amrutha Nair
 * @since 31-May-2016
 *
 */
public class Webnet9057 extends BaseClass {
		
	@Test
	public void verifySwitchingAudience(){
	String strMsg = null;
		String strProjectName="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
	
		String strDetails=null;
		try{
			
			
		
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strClientName=property.getProperty("clientName");
			strProjectName="TestProject " + BaseClass.rm.getCurrentDateTime();
			String strProjectDescription=property.getProperty("projectDescriptionScenario3");
			//****************Test step starts here************************************************
			
			//Step1 :	Open IDIOM URL and login with valid credentials
			strMsg = "Launch the browser and enter valid credentials";
			CustomReporter.log(strMsg);
			Login_Page ln = new Login_Page(driver);
			Thread.sleep(3000);
			
			
	        ln.func_LoginToIDIOM(strEmailId, strPassword);	
	        
	        
	        strMsg="The user has logged in with user ID :"+strEmailId;
	        CustomReporter.log(strMsg);
	        
	        //Step 2:Select any existing client from client dropdown
	        clientListPage = new ClientList_Page(driver);
			
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
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
				throw new IDIOMException("Not able to verify new project window###9057-CreateProjectWindow");
										
			//Step 4:Input Project name and description and click on Save
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###9057-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			 //Step 5:	Click on new audience link ,provide name and click on 'create and save'[Say Audience A]
			String audienceName = clientListPage.createNewAudience("");
			strMsg = "The first audience ' " + audienceName +" 'is added for the project'"+strProjectName+"'";
			CustomReporter.log(strMsg);
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");				
			
			
			//Step :Create one more audience here(Say Audience B)
			String audienceName2=audienceName+"_1";
			clientListPage.createNewAudience(audienceName2);	
			strMsg = "The second  audience ' " + audienceName2 +" 'is added for the project'"+strProjectName+"'";
			CustomReporter.log(strMsg);
			
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
				rm.captureScreenShot("9057_projectname", "fail");
			}
			
			
			//Step 8:Select Webnet destination link from here
			pdp.navigateTo(property.getProperty("webnet"));
			strMsg="Navigated to webnet page";
			CustomReporter.log(strMsg);
		
			
			
			Analyse_Webnet_Page webnet = new Analyse_Webnet_Page(driver);
			
			rm.webElementSync("idiomspinningcomplete");			
			if(webnet.isVisible("webnetchart")){
				strMsg="The webnet page is loaded properly. The chart is visible";
				CustomReporter.log(strMsg);
			
			}
			else{
				strMsg="The webnet page is not loaded properly. The chart is not visible";
				CustomReporter.log(strMsg);
			}
					
			
			//Step 9:Switch the audience from drop down[Say Select Audience A here]
			PageHeader PH= new PageHeader(driver);
			PH.selectAudienceFromDropDown(audienceName);
			rm.webElementSync("idiomspinningcomplete");
			webnet.isVisible("webnetchart");
			strMsg=" The user has switched audience from audience drop down in webnet page";
			CustomReporter.log(strMsg);
			
			//Step 10:Navigate to any other page like ;HVA or Pathing" from mega menu
			
			PH.megaMenuLinksClick(property.getProperty("hva"));
			rm.webElementSync("idiomspinningcomplete");		
			strMsg=" The user has navigated to hva page";
			CustomReporter.log(strMsg);
			
			Thread.sleep(4000);
			
			//step 11:Switch the audience from drop down[Say Select Audience Bhere]
			PH.selectAudienceFromDropDown(audienceName2);
			rm.webElementSync("idiomspinningcomplete");
			webnet.isVisible("webnetchart");
			strMsg=" The user has switched audience from audience drop down in hva page";
			CustomReporter.log(strMsg);
			
			
			//Step 12:	From megamenu , click on Webnet

			PH.megaMenuLinksClick(property.getProperty("webnet"));
			rm.webElementSync("idiomspinningcomplete");	
			
			strMsg="Navigated to webnet page";
			CustomReporter.log(strMsg);
			
			rm.webElementSync("idiomspinningcomplete");			
			if(webnet.isVisible("webnetchart")){
				strMsg="The webnet page is loaded properly. The chart is visible";
				CustomReporter.log(strMsg);
			
			}
			else{
				strMsg="The webnet page is not loaded properly. The chart is not visible";
				CustomReporter.log(strMsg);
			}
			
			
			//Step 13:Check the audience selected by default on Webnet page
			
			if(PH.verifySelectedAudienceInDropDown(audienceName2)){
				strMsg="The auidence which the user selected before is coming by default in webnet page";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The auidence which the user selected before is not coming by default in webnet page";
				CustomReporter.errorLog(strMsg);
				throw new IDIOMException(strMsg+"###9057_AudienceSelectedIssue");
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
				rm.captureScreenShot("9057", "fail");
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
