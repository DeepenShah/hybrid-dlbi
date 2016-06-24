package com.IDIOM.ClientHomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>2623 : Project detail should not get reflected on any page without saving it.</p>
 *  <p> <b>Objective:</b>For edit or duplicate project, no any action should reflect updated information without saving it. For example, launching project, close the project or duplicating project.</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8830.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 09 Jun 2016
 *
 */
public class ClientHomePage8896 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyUnSavedProjectDetailsOnDifferentCases(){		
		String strMsg="";
		String strProjectName="";
		String strProjectOrigName="";
		String strProjectDetails="";
		String strProjectDetails2="";
		
		boolean bDuplicateCreated = false;
		boolean bProjectCreated = false;			
		
		try{			
			//****************Test step starts here************************************************
						
			//Step1-3: Login To Selecting Client
			loginToSelectClient();			
			
			//Create new project
			strProjectName = clientListPage.createNewProject("");
			bProjectCreated = true;
			strProjectDetails = clientListPage.getAudienceProjectClientId(strProjectName, "");		
			
			//Step4: Edit project			
			clientListPage.performActionOnProject("edit", strProjectName);
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			CustomReporter.log("Open project to edit");
			
			//Step5: Modify name & Click X
			strProjectOrigName = strProjectName;
			strProjectName = "Step5: "  + strProjectName;
			clientListPage.func_PerformAction("Project Tab");
			Thread.sleep(1000);
			
			clientListPage.fillProject(strProjectName, "");
			clientListPage.func_PerformAction("Close Project");
			
			CustomReporter.log("Updated new details and clicked on 'X' to close project");
			
			//Verifying details has been saved or not
			if(clientListPage.verfiyProjectIsExist(strProjectName))
				throw new IDIOMException("Project name got saved even without clicking 'Save'. New name " + strProjectName +".###ProjectNameSavedAfterClickingClose");
			
			CustomReporter.log("Verified: Project details are not saved on Closing project");
			
			//Step6: Duplicate project
			clientListPage.performActionOnProject("duplicate", strProjectOrigName);
			CustomReporter.log("Clicked on duplicate for project " + strProjectOrigName);
			bDuplicateCreated = true;
			Thread.sleep(2000);
			
			strProjectDetails2 = clientListPage.getAudienceProjectClientId(strProjectOrigName, "", 2);
			System.out.println("Found details for Project1: " + strProjectDetails + " and Project2: " + strProjectDetails2);
			
			//Step7: Update the details & Click X
			clientListPage.findAndSaveProjectWindow(false, strProjectOrigName, 2);
			strProjectName = "Step7: "  + strProjectOrigName;
			clientListPage.fillProject(strProjectName, "");
			
			clientListPage.func_PerformAction("Close Project");
			CustomReporter.log("Updated the details and closed the project");
			
			//Verify details has been saved or not
			if(clientListPage.verfiyProjectIsExist(strProjectName))
				throw new IDIOMException("Project name got saved even without clicking 'Save' on duplicate window. New name " + strProjectName +".###DuplicateProjectNameSavedAfterClickingClose");
			
			CustomReporter.log("Verified: Project details are not saved on closing duplicated project window ");
			
			
			//Step8: Edit project
			clientListPage.performActionOnProject("edit", strProjectOrigName);
			clientListPage.findAndSaveProjectWindow(false, strProjectOrigName,1);
			CustomReporter.log("Open project to edit");
			
			//Step9: Update the details and Launch project
			strProjectName = "Step9: "  + strProjectOrigName;
			clientListPage.fillProject(strProjectName, "");
			clientListPage.func_PerformAction("Launch Project");
			CustomReporter.log("Updated the details and launch the project");
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			
			if(pdp.getProjectName().equalsIgnoreCase(strProjectName))
				throw new IDIOMException("Project name saved on Launch Project. Found " + pdp.getProjectName() +
						" as project name on dashbord page.###ProjectDashboard-NameSavedOnLaunchProject");
			
			CustomReporter.log("Project name on Dashboard title is not saved with new details");
			
			if(pageHeader.getProjectName().equalsIgnoreCase(strProjectName))
				throw new IDIOMException("Project name saved on Launch Project. Found " + pageHeader.getProjectName() +
						" as project name on Page Header.###PageHeader-NameSavedOnLaunchProject");
			
			CustomReporter.log("Project name not saved verified on page header");
			
			
		}catch(Exception e){
			exceptionCode(e,strClassName);
		}finally{
			try{
				//Deleting project
				if(bProjectCreated){
					util.deleteProjectOrAudience(strProjectDetails, true);
					CustomReporter.log("Deleted project " + strProjectName);					
				}
				
				if(bDuplicateCreated){
					util.deleteProjectOrAudience(strProjectDetails2, true);
					CustomReporter.log("Deleted duplicated project as well");					
				}
				
				//Logout
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(strClassName + ": " + strMsg);
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
