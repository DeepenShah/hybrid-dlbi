package com.IDIOM.ProjectDashBoard;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;





import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b>**2656: Project dashboard: Project name should proper on project dashboard page </p>
 * <p> <b>Objective:</b>: Project name should be the same on Project dashboard and Project listing (Launched project)</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8954.aspx</p>
 * <p> <b>Module:</b> ProjectDashBoard</p>
 * @author Amrutha Nair
 * @since 16-June-2016
 *
 */
public class ProjectDashBoard8954 extends BaseClass {
		
	@Test
	public void verifyProjectNameInDashBoard(){
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
			
			String projectDesc=property.getProperty("projectDescription");
			//****************Test step starts here************************************************
			
			//Step1 :		Open application URL
			//Step 2:Login with valid client admin/super admin user
			
			//Step 3:	Select any client and click on New Project to create new project under this client
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
				throw new IDIOMException("Not able to verify new project window###8954-CreateProjectWindow");
										
			//Step4:Enter valid project name say "abc" and submit the form
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8954-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			 
			//Step 5:Click on New Audience link from audience tab (top right corner) - Reference JIRA ID#2387
			 //Step 6:Enter valid audience name and click on save button
			 String audienceName = clientListPage.createNewAudience("");
			strMsg = "The first audience ' " + audienceName +" 'is added for the project'"+strProjectName+"'";
			CustomReporter.log(strMsg);
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");				
				
			
			//Step 7:	Now go to project tab and change project name from "abc" to "xyz"
			strProjectName=strProjectName+"_8954";
			
			clientListPage.func_PerformAction("Project Tab");
			
			strMsg = "The user has clicked on project tab in overlay";
			CustomReporter.log(strMsg);
			
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			strMsg = "The user has entered new project name '"+strProjectName+"' in the overlay and saved the same";
			CustomReporter.log(strMsg);
			
			
			//Step 8:	Now click on Audience tab
			
			clientListPage.func_PerformAction("Audience Tab");
			
			strMsg = "The user has clicked on Audience Tab in overlay";
			CustomReporter.log(strMsg);
			
			
			//Step 9: Click on create new audience and create new audience and click on save button
			
			 String audienceName1 = clientListPage.createNewAudience("");
			strMsg = "The  audience ' " + audienceName1 +" 'is added for the project'"+strProjectName+"'";
			CustomReporter.log(strMsg);
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");		
			
			
			//Step 10:Now click on Launch Project button from audience tab
			
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
				strMsg="The project home page has been launched and it is getting updated with selected project name. The project name is '"+strProjectName+"'";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The project dash board is getting update with project name '"+pdp.getProjectName()+"'";
				throw new IDIOMException(strMsg+"###8954_ProjectNameIssue");
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
			rm.captureScreenShot("8954", "fail");
		}finally{
			try{
				
				//Deleting newly created project
				if(bProjectCreate){
					
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

				
				
