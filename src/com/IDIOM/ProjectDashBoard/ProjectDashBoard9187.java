package com.IDIOM.ProjectDashBoard;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;




import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b>*Dashboard: Verify Click of Project description</p>
 * <p> <b>Objective:</b>: Verify Click of Project description</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/9187.aspx</p>
 * <p> <b>Module:</b> ProjectDashBoard</p>
 * @author Amrutha Nair
 * @since 15-June-2016
 *
 */
public class ProjectDashBoard9187 extends BaseClass {
		
	@Test
	public void verifyProjectDescriptionLink(){
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
			
			//Step 3:Select any client
			loginToSelectClient();
			clientListPage=new ClientList_Page(driver);
			//Verifies whether all project list or No project msg is coming for selected client
			strMsg=clientListPage.verifyProjectsInClientHomePage(strClientName);
			
			CustomReporter.log(strMsg);
			
			//Step 4:Select any project from project listing page/Create New and click on Launch button
			
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
				throw new IDIOMException("Not able to verify new project window###9187-CreateProjectWindow");
										
			//Input Project name and description and click on Save
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###9187-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			 
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
				rm.captureScreenShot("9187_projectname", "fail");
			}
			
			//Step 5:Verify click on Project description
			pdp.navigateTo(projectDesc);
			strMsg="The user has clicked on project description link from project dash board";
		
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			if(clientListPage.isVisible("projectwindow")){
				strMsg="On clicking on project description from project dash board, the project window is getting opened";
				
				CustomReporter.log(strMsg);
				
				String [] details= new String[2];
				details=clientListPage.getProjectNameAndDescriptionFromOverlay();
				
				if(details[0].trim().contentEquals(strProjectName) && details[1].trim().contentEquals(strProjectDescription)){
					strMsg="The project name is coming proper in overlay ie: "+strProjectName+" . And Project description is also coming proper in overlay ie:"+strProjectDescription;
					
					CustomReporter.log(strMsg);
				}
				else{
					strMsg="The project name and Project description are not proper in overlay";
					
					throw new IDIOMException(strMsg+"###9187_OverlayIssue");
				}
			}
			else{
				strMsg="On clicking on project description from project dash board, the project window is not getting opened";
				
				throw new IDIOMException(strMsg+"###9187_OverlayNtgettingOpened");
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
			rm.captureScreenShot("9187", "fail");
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

				
				
