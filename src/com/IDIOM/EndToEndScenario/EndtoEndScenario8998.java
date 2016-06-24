package com.IDIOM.EndToEndScenario;

import java.util.List;
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
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.IDIOM.pages.UserAdminMyAccount_Page;
import com.reports.CustomReporter;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;

import common.BaseClass;
import common.IDIOMException;
/** <p> <b>Test Case Name:</b>*Client Admin User - Verify end to end scenario covering all functionalities at higher level from Client Admin User</p>
 *  <p> <b>Objective:</b>This test case is for the verification of end to end scenario covering all functionalities at higher level from Client Admin User</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8998.aspx</p>
 *  <p> <b>Module:</b>EndToEndScenario</p>
 *  
 * @author Amrutha Nair
 * @since 21 Jun 2016
 *
 */
public class EndtoEndScenario8998 extends BaseClass {
	@Test
	public void verifyClientAdminEndToEndScenario (){
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
			String strSuccessMetrics=property.getProperty("commonSuccessMetrics");
			String strEmailId = property.getProperty("ClientAdminEmail");
			String strPassword = property.getProperty("ClientAdminPassword");
			String strDefaultAudience=property.getProperty("defaultaudience");
			String strAudienceAttributes1=property.getProperty("commonAudienceAttribute2");
			//****************Test step starts here************************************************
			
			//Step1 :	Launch browser and enter IDIOM Url
			//Step 2:Enter valid username and password for Client Admin Click on 'login to idiom' button
			
			
			Login_Page ln = new Login_Page(driver);
			
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			clientListPage=new ClientList_Page(driver);
			strMsg = "The user has loged in with valid client admin credentials";
			CustomReporter.log(strMsg);
			
			
			//Stp 3:	Click on Person icon from top right corner and verify Admin access link is available or not
			
			
				
			//Step 4:Click on Admin Access Link from Header
				pageHeader.performAction("adminaccess");
				strMsg="The user has clicked on 'Admin Access ' link from  person icon";
				CustomReporter.log(strMsg);
				rm.webElementSync("idiomspinningcomplete");
				Thread.sleep(3000);
				UserAdmin_UserPermissions_Page adminAccess= new UserAdmin_UserPermissions_Page(driver);
				
			//Step 5:Check whether Create User Circle button is available
				if(adminAccess.isVisible("createuserbutton")){
					
					strMsg="The 'Create User button' is  present in user admin page on loging in with client admin user";
					CustomReporter.errorLog(strMsg);
					rm.captureScreenShot("8998_CreateUserPresent", "fail");
					BaseClass.testCaseStatus=false;
				}
				else{
					strMsg="The 'Create User button' is not  present in user admin page on loging in with client admin user";
					CustomReporter.log(strMsg);
				}
				
				
			//Step 6:Edit one of the Users
				
				if(adminAccess.getList_Users().size()>0){
					adminAccess. func_ClickElement("FirstEditButton");
					rm.webElementSync("idiomspinningcomplete");
					
					strMsg="The user, has clicked on edit button for the first user in list ";
					CustomReporter.log(strMsg);
					
					if(!adminAccess.isVisible("resetPassword")){
						strMsg="The reset password button is not coming for selected user when accessed with client admin ";
						CustomReporter.log(strMsg);
						
					}
					else{
						strMsg="The reset password button is  coming for selected user when accessed with client admin ";
						CustomReporter.errorLog(strMsg);
						rm.captureScreenShot("8998_ResetPasswordPresent", "fail");
						BaseClass.testCaseStatus=false;
					}
					
					if(!adminAccess.isVisible("disableButton")){
						strMsg="The disable button is not coming for selected user when accessed with client admin ";
						CustomReporter.log(strMsg);
						
					}
					else{
						strMsg="The disable button is  coming for selected user when accessed with client admin ";
						CustomReporter.errorLog(strMsg);
						rm.captureScreenShot("8998_disablePresent", "fail");
						BaseClass.testCaseStatus=false;
					}
					
			//Sep 7:Assign single/multiple Clients and click on Save button
					if(adminAccess.AssignOrAnassignClientsLst.size()>0){
						adminAccess.func_ClickElement("Assign Or Unassign Clients");
						strMsg="The user has clicked on Assign/unassign clients";
						CustomReporter.log(strMsg);
					}
					else{
						strMsg="There are no clients present";
						CustomReporter.log(strMsg);
					}
					
					adminAccess.func_ClickElement("Save Button");
					
					Thread.sleep(2000);
					strMsg="The user, has clicked on save   button  ";
					
					CustomReporter.log(strMsg);
					
					
					
			//Step 8:Select single/multiple users and click on Bulk Assignment icon
					
					adminAccess.func_selectUsers(2);
					strMsg="The user has selected 2 users ";
					CustomReporter.log(strMsg);
					
					adminAccess.func_ClickElement("Bulk Icon Click");
					strMsg="The user has clicked on bulk assignment icon";
					CustomReporter.log(strMsg);
					
					
					
			//Step 9:Select single/multiple clients and click on Assign button
					 List<String> clients=adminAccess.bulkAssignAssignClients(1);
					 if(clients.size()>0){
						 adminAccess.func_ClickElement("BulkAsssignmnet AssignButton");
						 
						strMsg="The user has assigned clients '"+clients+"' for the selected users";
						CustomReporter.log(strMsg);
						rm.webElementSync("idiomspinningcomplete");
						Thread.sleep(1000);
					 }
					 else{
						 strMsg="There are no clients in bulk client assignment window";
						 CustomReporter.log(strMsg);
						 adminAccess.func_ClickElement("BulkAsssignmentCancelButton");
						 Thread.sleep(1000);
					 }
			//Step 10:Check Search functionality in User List
					
					adminAccess.func_CheckSearchFunctionality("test");
					strMsg="The user has entered search key word as 'test'";
					CustomReporter.log(strMsg);
					Thread.sleep(3000);
					//Step 23:Search and open newly created user
					if(adminAccess.getList_Users().size()>0){
						strMsg="There are '"+adminAccess.getList_Users().size()+"' users present with search key word 'test'";
						CustomReporter.log(strMsg);
						
			//Step 11:Edit one of the Users and check Search functionality in Client List
						adminAccess. func_ClickElement("FirstEditButton");
						rm.webElementSync("idiomspinningcomplete");
						Thread.sleep(1000);
						strMsg="The user, has clicked on edit button for a user in list ";
						
						CustomReporter.log(strMsg);
						if(adminAccess.isVisible("userEditPanel")){
							
							strMsg="The edit panel has been opened for  selected user";
							
							CustomReporter.log(strMsg);
							
							adminAccess.func_TypeSearchCriteria("ecom","Client");
							strMsg="The user has searched for 'ecom' with  clients ";
							
							CustomReporter.log(strMsg);
							Thread.sleep(1000);
							
							if(adminAccess.AssignOrAnassignClientsLst.size()>0){
								strMsg="There are '"+adminAccess.AssignOrAnassignClientsLst.size()+"' clients  present with search key word 'ecom'";
								CustomReporter.log(strMsg);
								
							}
							else{
								strMsg="There are NO clients  present with search key word 'ecom'";
								CustomReporter.log(strMsg);
							}
						}
						else{
							strMsg="The edit panel is not getting opened for the   selected user";
							CustomReporter.errorLog(strMsg);
							rm.captureScreenShot("8998_EditpanelIssue", "fail");
						}
							
							
					}
					else{
						strMsg="There are NO  users present with search key word 'test'";
						CustomReporter.log(strMsg);
					}
					
			//Step 12:Select single/multiple users and click on Bulk Assignment icon and check Search functionality in Client List
						
					adminAccess.func_CheckSearchFunctionality("");
					adminAccess.func_selectUsers(1);
					strMsg="The user has selected one user ";
					CustomReporter.log(strMsg);
					
					adminAccess.func_ClickElement("Bulk Icon Click");
					strMsg="The user has clicked on bulk assignment icon";
					CustomReporter.log(strMsg);
					
					adminAccess.func_TypeSearchCriteria("ecom","bulkClient");
					strMsg="The user has searched for 'ecom' with  clients in bulk assignmmnet area ";
					CustomReporter.log(strMsg);
				}
				else{
					strMsg="There are no users present ";
					CustomReporter.log(strMsg);
				}
				adminAccess.func_ClickElement("Return To Idiom");
				//Step 13:Click on 'Return to idiom
				strMsg="The user has clicked on 'Return to Idiom' link ";
				CustomReporter.log(strMsg);
				
				Thread.sleep(5000);
				rm.webElementSync("pageload");
				rm.webElementSync("jqueryload");
				
				//Step 14:In client home page, select a client from dropdown
				
				clientListPage.selectClient(strClientName);
				strMsg = "Selected '"+strClientName+"' client successfully.";
				
				clientListPage.selectClient(strClientName);
				strMsg = "Selected '"+strClientName+"' client successfully.";
				
				CustomReporter.log(strMsg);
				Thread.sleep(5000);
				rm.webElementSync("pageload");
				rm.webElementSync("jqueryload");
				
				//Verifies whether all project list or No project msg is coming for selected client
				strMsg=clientListPage.verifyProjectsInClientHomePage(strClientName);
				
				CustomReporter.log(strMsg);
				
				//Step 15:Click on new project button
				
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
					throw new IDIOMException("Not able to verify new project window###8998-CreateProjectWindow");
											
				//Step 16:Enter valid name and description for project and click on Save button
			
				clientListPage.fillProject(strProjectName,strProjectDescription);				
				clientListPage.func_PerformAction("Save Project");
				
				// Verifying if project is saved and landed to audience tab
				if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
					throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8998-AudienceTabNotFound");
					
				strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
				CustomReporter.log(strMsg);
				bProjectCreate=true;
				Thread.sleep(2000);
				 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
				
			
				//Step 17:Click on launch project
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
					rm.captureScreenShot("8998_projectname", "fail");
				}
			
			//Step 18:Click on Success Metrics
				pdp.navigateTo(property.getProperty("successMetrics"));
				strMsg="Navigated to success metric page";
				CustomReporter.log(strMsg);
			
				AudienceBuilderPage AD = new AudienceBuilderPage(driver);
				
				AD.isVisible("nosuccessmetrictext", "");
				strMsg="Successfully landed on Success Metric page";
				CustomReporter.log(strMsg);
				Thread.sleep(3000);
				
				
			//Step 19:Define few success metrics or do not define
				AD.selectMetricByName(strSuccessMetrics);
				
				strMsg="The user has selected a few success metrics ";
				CustomReporter.log(strMsg);		
						
			//Step 20:Click on Audience Definition link from project dashboard
				AD.performAction("successmetrics>arrow");
				rm.webElementSync(AD.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
				CustomReporter.log("Clicked on '>' to navigate to audience definition tab");
				

				Thread.sleep(3000);
				
			//Step 21:	Select few attributes and add them
				AD.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
				AD.performAction("addattribute");
				Thread.sleep(2000);
			
				
				strMsg="The user has added a query in default group for the project  '"+strProjectName+"'";
				CustomReporter.log(strMsg);
				
				
				AD.goToFirstLevelForMetricOrAttribute();
				
			
			//Step 22:Click on Arrow at the top right bottom corner of the page and go to Profile Page
				AD.arrowLinkClickForSuccessMetricsOrAudienceDefinition();
				strMsg="The user has clicked on '>' link from  audience definition page to navigate to profile page";
				CustomReporter.log(strMsg);
				
				rm.webElementSync("idiomspinningcomplete");
				Analyse_Profile_Page ap=new Analyse_Profile_Page(driver);
				if(ap.isVisible("summary")){
					strMsg="The Profile page is visible";
					CustomReporter.log(strMsg);
				}
				
				
			//Step 23:Go to project dashboard page and open WebNet page
				
				pageHeader.performAction("projecthomepage");
				
				strMsg="The user has navigated to project home page";
				
				
				rm.webElementSync(pdp.projectName, "visibility");
				Thread.sleep(2000);
				
				if(pdp.isVisible("projectname", strProjectName)){
					strMsg="The project home page has been launched and it is getting updated with selected project name";
					CustomReporter.log(strMsg);
				}
				
				pdp.navigateTo(property.getProperty("webnet"));
			
			
				strMsg="The user has clicked on 'webnet' link from  project dashboard";
				CustomReporter.log(strMsg);
				
				rm.webElementSync("idiomspinningcomplete");
				Analyse_Webnet_Page webnet=new Analyse_Webnet_Page(driver);
				if(webnet.isVisible("webnetchart")){
					strMsg="The webnet  page is visible";
					CustomReporter.log(strMsg);
				}
				
				Thread.sleep(5000);
				
				
		 //Step 24:Go to project dashboard page and open HVA page
		
				pageHeader.performAction("projecthomepage");
				
				strMsg="The user has navigated to project home page";
				
				
				rm.webElementSync(pdp.projectName, "visibility");
				Thread.sleep(2000);
				
				if(pdp.isVisible("projectname", strProjectName)){
					strMsg="The project home page has been launched and it is getting updated with selected project name";
					CustomReporter.log(strMsg);
				}
				
				pdp.navigateTo(property.getProperty("hva"));
				
				
				strMsg="The user has clicked on 'hva' link from  project dash board";
				CustomReporter.log(strMsg);
				
				rm.webElementSync("idiomspinningcomplete");
				HVA_Page hva=new HVA_Page(driver);
				if(hva.isVisible("hva_chart")){
					strMsg="The HVA  page is visible";
					CustomReporter.log(strMsg);
				}
				
				Thread.sleep(5000);
			//Step 25:Go to project dashboard page and open Pathing page
				
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
				
				
			//sTEP 26:	Go to project dashboard page and open Digital Ranker page
				pageHeader.performAction("projecthomepage");
				
				strMsg="The user has navigated to project home page";
				
				
				rm.webElementSync(pdp.projectName, "visibility");
				Thread.sleep(2000);
				
				if(pdp.isVisible("projectname", strProjectName)){
					strMsg="The project home page has been launched and it is getting updated with selected project name";
					CustomReporter.log(strMsg);
				}
				
				pdp.navigateTo(property.getProperty("digitalRanker"));
			
			
				strMsg="The user has clicked on 'digitalRanker' link from  megamenu";
				CustomReporter.log(strMsg);
				
				rm.webElementSync("idiomspinningcomplete");
				ArchitectMediaRankerPage mediaRanker=new ArchitectMediaRankerPage(driver);
				if(mediaRanker.func_VerifyVisibilityOfElement("slowtvranker", "")){
					strMsg="The 'digitalRanker' page is visible  ";
					CustomReporter.log(strMsg);
				}
				
				
				
			//sTEP 27:Go to project dashboard page and open TV Ranker page
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
			
		
			//sTEP 28:Go to My Account page by clicking on My Account Link from Header
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
		rm.captureScreenShot("8998", "fail");
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

