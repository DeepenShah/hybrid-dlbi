package com.IDIOM.ClientHomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Def 2309_Verify header of client home page</p>
 *  <p> <b>Objective:</b>Verify that the project name, client logo and audience drop down is not there in client home page header </p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8970.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Abhishek Deshpande
 * @since 01 Jun 2016
 *
 */
public class ClientHomePage8970 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyEditOverlay(){		
			
		String strProjectName="Automation Project - 8970";
		String strProjectDesc = "This project is created to verify the duplicate functionality";
		String strMsg="";
		String strDeletionDetails="";
		String strAudienceName="";	
		
		boolean bProjectCreated = false;	
		
		try{
			
			//****************Test step starts here************************************************
			
			//Step 1 and 2: Login to application with Valid credentials and Select a client
			loginToSelectClient();			
			
			//Step3: Click on New Project			
			clientListPage.func_PerformAction("New Project");
			if(!rm.webElementSync(clientListPage.newProjectWindow, "visibility"))
				throw new IDIOMException("Not able to find new project overlay.###FailedToVerifyNewProjectWindow");
			
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);							
			clientListPage.findAndSaveProjectWindow(true, "");
			
			//Step4: Add project name and description and click on Save button
			clientListPage.fillProject(strProjectName,strProjectDesc);				
			clientListPage.func_PerformAction("Save Project");
			Thread.sleep(2000);
			CustomReporter.log("Project " +strProjectName + " created successfully");
			
			//Storing detail for deletion
			strDeletionDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			bProjectCreated = true;	            	
			
			//Click on Launch project
			clientListPage.func_PerformAction("Launch Project");
			CustomReporter.log("Clicked on Launch Project");
			
			//Click on IDIOM logo in the header
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			pdp.performActionOnElement("clickidiomlogo");
			CustomReporter.log("Clicked on IDIOM Logo");
			Thread.sleep(5000);
			
			
			//Verify in client home page header - IDIOM Logo, Admin Access, My Account links
			PageHeader ph = new PageHeader(driver);		
			if(ph.isVisible("adminaccess")&&ph.isVisible("idiomlogo")&&ph.isVisible("myaccount")){
				CustomReporter.log("Verified IDIOM Logo, Admin Access and My Account link in Client home page");
			}else{				
				throw new IDIOMException("Failed to verify Logo, Adminaccess and MyAccountLink.###FailedToVerifyFewElements");				
			}
			
			
			//Verify in Client home page header - Client logo, Project name and Audience drop down is not present			
			if(ph.isVisible("audiencedropdown")&&ph.isVisible("projectname")&&ph.isVisible("client_logo")){
				throw new IDIOMException("Failed to verify Audience dropdown, Projectname and Client logo.###FailedToVerifyFewElementsinpageheader");
			}else{
				CustomReporter.log("Verified Audience dropdown, Project name and Client logo is NOT present in Client home page");
			}		
			
			//Logout from the application
			pageHeader.performAction("logout");
			CustomReporter.log("Logout from the application");
			
			//Login in again
			loginToSelectClient();
			CustomReporter.log("Login again into IDIOM");
			
			//Check that same project should exist
			if(clientListPage.verfiyProjectIsExist(strProjectName)){
				CustomReporter.log("The project created in step 3 is displayed");
			}else{
				CustomReporter.errorLog("The project created in step 3 is not displayed");
				throw new IDIOMException("Failed to verify the project.###ProjectNotExist");
			}
			
			//Second time - Verify in client home page header - IDIOM Logo, Admin Access, My Account links				
			if(ph.isVisible("adminaccess")&&ph.isVisible("idiomlogo")&&ph.isVisible("myaccount")){
				CustomReporter.log("Second time - Verified IDIOM Logo, Admin Access and My Account link in Client home page");
			}else{				
				throw new IDIOMException("Second time - Failed to verify Logo, Adminaccess and MyAccountLink.###FailedToVerifyFewElements");				
			}
			
			
			//Second time - Verify in Client home page header - Client logo, Project name and Audience drop down is not present			
			if(ph.isVisible("audiencedropdown")&&ph.isVisible("projectname")&&ph.isVisible("client_logo")){
				throw new IDIOMException("Second time - Failed to verify Audience dropdown, Projectname and Client logo.###FailedToVerifyFewElementsinpageheader");
			}else{
				CustomReporter.log("Second time - Verified Audience dropdown, Project name and Client logo is NOT present in Client home page");
			}
			
			
			
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8970", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreated){				
					rm.deleteProjectOrAudience(strDeletionDetails, true);
					CustomReporter.log("Deleted the project");					
				}
				
				//Step 10 - Click on logout 
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
		//****************Test step ends here************************************************
		
		if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}
	}
}