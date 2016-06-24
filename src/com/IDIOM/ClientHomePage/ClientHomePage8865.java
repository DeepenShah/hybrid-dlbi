package com.IDIOM.ClientHomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;
/** <p> <b>Test Case Name:</b>Manage Projects 4 : Launch the project by clicking 'Launch Project' button from 'Create New Project' window.</p>
 *  <p> <b>Objective:</b>Clicking 'Launch Project' button available on create/edit project should launch the project. User should be navigated to project dashboard</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8865.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Abhishek Deshpande
 * @since 13 Jun 2016
 *
 */

public class ClientHomePage8865 extends BaseClass {
	@Test
	public void verifyProjectDashboard(){		
			
		String strProjectName="Automation Project - 8865";
		String strProjectDesc = "This project is created to verify Launch Project button";
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
			
            //Step5: Create new audience			
			clientListPage.func_PerformAction("Audience Tab");					
			clientListPage.func_PerformAction("New Audience");
			CustomReporter.log("Clicked on '+New Audience' link");			
			
			//Step6: Enter the audience name and don't click on 'Create and Save' button
			strAudienceName = "Auto Audi-8865 ";	
			clientListPage.fillAudience(strAudienceName,"");
			CustomReporter.log("Enter the Audience name "+strAudienceName+" and don't click on 'Create and Save' button");	
			
			//Step7: Click on Launch project
			clientListPage.func_PerformAction("Launch Project");
			CustomReporter.log("Clicked on Launch Project");
			Thread.sleep(3000);
			
			//Verify that user is navigated to Project dash board page
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			if(!pdp.verifyLinkExist("Success Metrics"))
				throw new IDIOMException("Failed to load project dashboard page.###8865:FailedToVerifyPDP");
			
			CustomReporter.log("Project dash board page is displayed successfully");

}catch(IDIOMException ie){
	BaseClass.testCaseStatus = false;
	String strMsgAndFileName[] = ie.getMessage().split("###");
	System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
	CustomReporter.errorLog("" + strMsgAndFileName[0] + ".");
	rm.captureScreenShot(strMsgAndFileName[1], "fail");
	
}catch(Exception e){
	
	BaseClass.testCaseStatus = false;
	e.printStackTrace(System.out);
	CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
	rm.captureScreenShot("8579", "fail");
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

