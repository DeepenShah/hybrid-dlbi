package com.IDIOM.ClientHomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;

import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;


import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b>*2492: Manage projects: Error should not be generated while navigating to manage projects from any other pages</p>
 * <p> <b>Objective:</b>Verify that error should not be generated while user navigating to manage projects from any other pages after deleting audience</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8966.aspx</p>
 * <p> <b>Module:</b> ClientHomePage</p>
 * @author Amrutha Nair
 * @since 14-June-2016
 *
 */
public class ClientHomePage8966 extends BaseClass {
		
	@Test
	public void verifyDeletingAudience(){
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
			
			String strDefaultAudience=property.getProperty("defaultaudience");
			//****************Test step starts here************************************************
			
			//Step1 :		Open application URL
			//Step 2:Login with valid client admin/super admin user
			
			//Step 3:Select any client and click on new project
			loginToSelectClient();
			clientListPage=new ClientList_Page(driver);
			//Verifies whether all project list or No project msg is coming for selected client
			strMsg=clientListPage.verifyProjectsInClientHomePage(strClientName);
			
			CustomReporter.log(strMsg);
			
			//Step 4:Enter valid project name and click on save button
			
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
				throw new IDIOMException("Not able to verify new project window###8966-CreateProjectWindow");
										
			//Input Project name and description and click on Save
		
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			
			// Verifying if project is saved and landed to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###8966-AudienceTabNotFound");
				
			strMsg = "Project " + strProjectName +" saved successfully. And found Audience tab";
			CustomReporter.log(strMsg);
			bProjectCreate=true;
			Thread.sleep(2000);
			 strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			 //Step 5:	Now click on Add New Audience link
			 //Step 6:Create new audience with valid name say "abc"
			String audienceName = clientListPage.createNewAudience("");
			strMsg = "The first audience ' " + audienceName +" 'is added for the project'"+strProjectName+"'";
			CustomReporter.log(strMsg);
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");				
			
			
			
			//Step 7:	Now select new audience "abc" and click on launch project button
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
				rm.captureScreenShot("8966_projectname", "fail");
			}
			
			
			
			//Step 8:Now go to any page like success metrics
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AudienceBuilderPage AD = new AudienceBuilderPage(driver);
			
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			
			//Step 9: add few behaviors in success metrics
			
			AD.selectMetricByName(strMetrics);
			strMsg="The user has added a few success metrics ";
			CustomReporter.log(strMsg);
			//Step 10:Now click on IDIOM logo to go back to client home page
			pageHeader.performAction("idiomlogo");
			strMsg="The user has clicked on idiom logo from success metrics page";
			CustomReporter.log(strMsg);
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			
			//step 11:Now delete audience "abc"
			
			
			clientListPage.performActionOnProject("edit", strProjectName);
			Thread.sleep(3000);
			clientListPage.func_PerformAction("Audience Tab");
			Thread.sleep(3000);
			clientListPage.performActionOnAudience(audienceName,"delete");
			Thread.sleep(3000);
			if(clientListPage.getAudienceCount(audienceName)==0){
				strMsg="The audience '"+audienceName+"' is getting deleted successfully";
				CustomReporter.log(strMsg);
			}
			
			else{
				strMsg="The audience '"+audienceName+"' is not getting deleted successfully";
				throw new IDIOMException(strMsg+"###8966_AudienceNotGettingDeleted");
			}
			
			
			
			//Step 12:Now click on Launch project
			clientListPage.func_PerformAction("Launch Project");
			strMsg = "Clicked on Launch Project";
			CustomReporter.log(strMsg);
			Thread.sleep(2000);
			
			if(pageHeader.getSelectedAudienceName().trim().toLowerCase().contentEquals(strDefaultAudience.trim().toLowerCase())){
				strMsg="The audience selected is  '"+strDefaultAudience+"' as expected";
				CustomReporter.log(strMsg);
			}
			else{
				strMsg="The audience selected is  '"+pageHeader.getSelectedAudienceName()+"' not '"+strDefaultAudience+"'";
				throw new IDIOMException(strMsg+"###8966_AudienceSelectedIssue");
			}
			
			
			
			//Step 13:Now click on any page like Success Metrics from Project Dashboard page
			
			pdp.navigateTo(property.getProperty("successMetrics"));
			strMsg="Navigated to success metric page";
			CustomReporter.log(strMsg);
		
			AD.isVisible("nosuccessmetrictext", "");
			strMsg="Successfully landed on Success Metric page";
			CustomReporter.log(strMsg);
			
			
			
			
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
				rm.captureScreenShot("8966", "fail");
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
