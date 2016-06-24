package com.IDIOM.EndToEndScenario;

import java.util.Random;

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
import com.IDIOM.pages.UserAdminMyAccount_Page;
import com.reports.CustomReporter;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;

import common.BaseClass;
import common.IDIOMException;
/** <p> <b>Test Case Name:</b>*Super Admin user - Verify user journey</p>
 *  <p> <b>Objective:</b>This test case is for verifying all the pages from the eye of super user.</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8999.aspx</p>
 *  <p> <b>Module:</b>EndToEndScenario</p>
 *  
 * @author Amrutha Nair
 * @since  22 Jun 2016
 *
 */


public class EndtoEndScenario8999 extends BaseClass {
	@Test
	public void verifySuperUserEndToEndScenario (){
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
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strDefaultAudience=property.getProperty("defaultaudience");
			String strAudiences=property.getProperty("commonAudienceAttribute2");
			//****************Test step starts here************************************************
			
			//Step1 :	Launch browser and enter IDIOM Url
			//Step 2:Login with valid super admin user
			
			//Step 3:Select client 
			loginToSelectClient(strEmailId,strPassword);
			strMsg="The user has logged in with valid super admin credentials and selected a client";
			CustomReporter.log(strMsg);
			clientListPage=new ClientList_Page(driver);
			
			//Verifies whether all project list or No project msg is coming for selected client
			strMsg=clientListPage.verifyProjectsInClientHomePage(strClientName);
			
			CustomReporter.log(strMsg);
			
			//Step 4:Click on 'New Project'
			//Step 5:Provide name and description. And click save
			
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
				throw new IDIOMException("Not able to verify new project window###8999-CreateProjectWindow");
										
			//Input Project name and description and click on Save
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8999-AudienceTabNotFound");
				
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
				rm.captureScreenShot("8999_projectname", "fail");
			}
			
			//Step 9:Open Success Metrics page
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			
			//Step 10:Add few metric from any category
			
			AD.selectMetricByName(strMetrics);
			strMsg="The user has added a few success metrics ";
			CustomReporter.log(strMsg);
			
			//Step 11:Click on 'Audience Definition' tab
			AD.performAction("gotoaudiencedefinition");
			rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Clicked on Audience Definition tab");
			
			
			Thread.sleep(3000);
		
			
			//Step 12:Add few attribute from various category
			
			AD.selectAttributeOnAudienceDefinition(strAudiences);
			AD.performAction("addattribute");
			
			strMsg="The user has selected a query in audience definition page";
			CustomReporter.log(strMsg);
			
			Thread.sleep(5000);
			AD.goToFirstLevelForMetricOrAttribute();
			
			
			//Step 13:Click on '>' from bottom navigation bar
			AD.arrowLinkClickForSuccessMetricsOrAudienceDefinition();
			strMsg="The user has clicked on '>' link from  audience definition page to navigate to profile page";
			CustomReporter.log(strMsg);
			
			rm.webElementSync("idiomspinningcomplete");
			Analyse_Profile_Page ap=new Analyse_Profile_Page(driver);
			if(ap.isVisible("summary")){
				strMsg="The Profile page is visible";
				CustomReporter.log(strMsg);
			}
			
			
			//Step 14:Click on mega menu and select 'Webnet'
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
			
		

			//Step 16:Go to project dashboard page and open Pathing page
			
			pageHeader.performAction("projecthomepage");
			
			strMsg="The user has navigated to project home page";
			
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			if(pdp.isVisible("projectname", strProjectName)){
				strMsg="The project home page has been launched and it is getting updated with selected project name";
				CustomReporter.log(strMsg);
			}
			
			pdp.navigateTo(property.getProperty("pathing"));
			strMsg="The user has clicked on 'pathing' link from  project home page";
			CustomReporter.log(strMsg);
			
			rm.webElementSync("idiomspinningcomplete");
			Analyse_Pathing_Page pathing=new Analyse_Pathing_Page(driver);
			if(pathing.isVisible("behaviourdropdown")){
				strMsg="The pathing  page is visible";
				CustomReporter.log(strMsg);
			}
			
			Thread.sleep(5000);
			
			
			//Step 17:	Select Digital Ranker page from Mega menu
			pageHeader.megaMenuLinksClick(property.getProperty("digitalRanker"));
			
			
			strMsg="The user has clicked on 'digitalRanker' link from  megamenu";
			CustomReporter.log(strMsg);
			
			rm.webElementSync("idiomspinningcomplete");
			ArchitectMediaRankerPage mediaRanker=new ArchitectMediaRankerPage(driver);
			if(mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "")){
				strMsg="The 'digitalRanker' page is visible  ";
				CustomReporter.log(strMsg);
			}
		
			//Step 18:Switch the audience from drop down.
			pageHeader.selectAudienceFromDropDown(strDefaultAudience);
			strMsg="The user has swiched audience from drop down";
			CustomReporter.log(strMsg);
			rm.webElementSync("idiomspinningcomplete");
			
			if(mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "")){
				strMsg="The 'digitalRanker' page is geting updated on selecting new audience  ";
				CustomReporter.log(strMsg);
			}
			
			//Step 19:Go to project dashboard page and open TV Ranker page
		
			pageHeader.performAction("projecthomepage");
			
			strMsg="The user has navigated to project home page";
			
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			
			if(pdp.isVisible("projectname", strProjectName)){
				strMsg="The project home page has been launched and it is getting updated with selected project name";
				CustomReporter.log(strMsg);
			}
			
			pdp.navigateTo(property.getProperty("tvRanker"));
			strMsg="The user has clicked on 'tvRanker' link from  project dash board";
			CustomReporter.log(strMsg);
			
			rm.webElementSync("idiomspinningcomplete");
			
			if(mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "")){
				strMsg="The 'tvRanker' page is visible  ";
				CustomReporter.log(strMsg);
			}
			
			
			//Step 20:Click on Person icon from top right corner and click My Account page
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
			
			 //Step 21:Click on 'Admin Access' from person menu on top
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
			
			//Step 22:Click on create 'New User'
			String[] userDetails=adminAccess.func_CreateUserDetails("8999");
			String userNAme=userDetails[0];
			
			String emailAddress=userDetails[1];
			System.out.println("email:"+emailAddress);
	    	adminAccess.func_CreateUser(userNAme, emailAddress, 1, "Add New User");
	    	strMsg="The user has clicked on 'Create User button'";
			CustomReporter.log(strMsg);
	    	strMsg="The user has entered user name as '"+userNAme+"' and email adress as '"+emailAddress+" in created user overlay";
			CustomReporter.log(strMsg);
			Thread.sleep(3000);
			adminAccess.isVisible("createuserbutton");
			rm.webElementSync("idiomspinningcomplete");
			adminAccess.func_CheckSearchFunctionality(emailAddress);
			
			Thread.sleep(3000);
			//Step 23:Search and open newly created user
			if(adminAccess.getList_Users().size()>0){
				strMsg="The user, '"+userNAme+"' : '"+emailAddress+" ' has been created successfully";
				CustomReporter.log(strMsg);
				
				//STop 25:Select any client and click 'Edit'
				adminAccess. func_ClickElement("FirstEditButton");
				rm.webElementSync("idiomspinningcomplete");
				Thread.sleep(1000);
				strMsg="The user, has clicked on edit button for created user '"+userNAme+"' : '"+emailAddress+" ";
				
				CustomReporter.log(strMsg);
				if(adminAccess.isVisible("userEditPanel")){
					
					strMsg="The edit panel has been opened for  created user '"+userNAme+"' : '"+emailAddress+" ";
					
					CustomReporter.log(strMsg);
					
					
					//Step 28:Click on 'Reset Password' and 'Save'
					
					
					adminAccess.func_ClickElement("Reset Password");
					Thread.sleep(1000);
					strMsg="The user, has clicked on RESET PASSWORD  button for created user '"+userNAme+"' : '"+emailAddress+" ";
					
					CustomReporter.log(strMsg);
					
					adminAccess.func_ClickElement("Save Button");
					rm.webElementSync("idiomspinningcomplete");
					Thread.sleep(2000);
					strMsg="The user, has clicked on save   button for created user '"+userNAme+"' : '"+emailAddress+" ";
					
					CustomReporter.log(strMsg);
				}
				
				else{
					strMsg="The edit panel has not getting opened for  created user '"+userNAme+"' : '"+emailAddress+" ";
					throw new IDIOMException(strMsg+"###8999_EditPanelNotOpening");
				}
				//STop 25:Select any client and click 'Edit'
				adminAccess. func_ClickElement("FirstEditButton");
				rm.webElementSync("idiomspinningcomplete");
				Thread.sleep(2000);
				strMsg="The user, has clicked on edit button for created user '"+userNAme+"' : '"+emailAddress+" ";
				
				CustomReporter.log(strMsg);
				if(adminAccess.isVisible("userEditPanel")){
				//Step 26:Click on 'Disable Account' and click Save
					adminAccess. func_ClickElement("DisableButton");
					Thread.sleep(2000);
					adminAccess.func_ClickElement("Save Button");
					rm.webElementSync("idiomspinningcomplete");
					
					strMsg="The user, has clicked on disable button for created user '"+userNAme+"' : '"+emailAddress+" ";
					
					CustomReporter.log(strMsg);
					
					Thread.sleep(5000);
					if(adminAccess.isVisible("DisabledUser"))
					{
						strMsg="The user,'"+userNAme+"' : '"+emailAddress+" got disabled";
						
						CustomReporter.log(strMsg);
						
		
					}
					else{
						strMsg="The user,'"+userNAme+"' : '"+emailAddress+" is not getting disabled";
						CustomReporter.errorLog(strMsg);
						rm.captureScreenShot("8999_DisableIssue", "fail");
						BaseClass.testCaseStatus=false;
					}
					
				}
			
			
			else{
				strMsg="The edit panel has not getting opened for  created user '"+userNAme+"' : '"+emailAddress+" ";
				throw new IDIOMException(strMsg+"###8999_EditPanelNotOpening");
			}
			}
			
			else{
				strMsg="The user, '"+userNAme+"' : '"+emailAddress+" ' has not been created successfully";
				throw new IDIOMException(strMsg+"###8999_CreateUserFailure");
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
		rm.captureScreenShot("8999", "fail");
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
