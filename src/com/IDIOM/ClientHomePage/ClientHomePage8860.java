package com.IDIOM.ClientHomePage;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Manage Projects 3.a. : Edit project</p>
 *  <p> <b>Objective:</b>To verify 'Edit' project from toggle option.</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8860.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 14 Jun 2016
 *
 */
public class ClientHomePage8860 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyEditProject(){		
		String strMsg="";		
		String strProjectDetails="";
		String strProjectName="Automation 8860";
		String strProjDesc = "This is going to get delete. Do not worry!!!";		
		
		boolean bStatus = true;
		try{
			String strExpEditProjectLabel = property.getProperty("editProjectLabel");
			//****************Test step starts here************************************************
						
			//Step1-3: Login To Selecting Client
			loginToSelectClient();			
			
			//Select/Create project			
			clientListPage.func_PerformAction("New Project");
			BaseClass.rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
							
			clientListPage.findAndSaveProjectWindow(true, "");
			clientListPage.fillAndSaveProject(strProjectName,strProjDesc);			
			Thread.sleep(2000);
			
			strMsg = "Project "+ strProjectName+" created successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			clientListPage.func_PerformAction("Close Project");
			Thread.sleep(1000);
			strProjectDetails = clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			//Step4: Edit Project
			clientListPage.performActionOnProject("edit", strProjectName);
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			clientListPage.func_PerformAction("Project Tab");
			Thread.sleep(1000);
			
			CustomReporter.log("Open project in 'Edit' mode");
			
			String[] strProjNameDesc = clientListPage.getProjectNameAndDescriptionFromOverlay();
			
			if(!strProjectName.equalsIgnoreCase(strProjNameDesc[0])){
				bStatus = false;
				CustomReporter.errorLog("Failed to verify name from edit project windiow. Expected " +
						strProjectName +" and found " + strProjNameDesc[0] +".###FailedToVerifyName");
			}else{
				CustomReporter.log("Successfully verified name from edit window");
			}
			
			if(!strProjDesc.equalsIgnoreCase(strProjNameDesc[1])){
				bStatus = false;
				CustomReporter.errorLog("Failed to verify description from edit project windiow. Expected " +
						strProjectName +" and found " + strProjNameDesc[0] +".###FailedToVerifyDesc");
			}else{
				CustomReporter.log("Successfully verified description from edit window");
			}
			
			String strActualEditProjectLabel = clientListPage.globalProjectWindow.findElement(By.xpath(clientListPage.strCreateNewProjectLabel)).getText().trim();			
			
			if(strActualEditProjectLabel.equalsIgnoreCase(strExpEditProjectLabel)){
				CustomReporter.log("Verified Edit Project window label: " + strActualEditProjectLabel);
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to compare project window label. Expected " + strExpEditProjectLabel +
						" and found " + strActualEditProjectLabel);
			}
			
			//Move to audience tab and check 'Launch Project' button
			clientListPage.func_PerformAction("Audience Tab");
			Thread.sleep(1000);
			
			if(clientListPage.isVisible("launchprojectbtn")){
				CustomReporter.log("'Launch Project' button found on Audience Tab");
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to find 'Launch Project' button on audience tab");
			}
			
			//Move to audience tab and check 'Launch Project' button
			clientListPage.func_PerformAction("Project Tab");
			Thread.sleep(1000);
			
			if(clientListPage.isVisible("launchprojectbtn")){
				CustomReporter.log("'Launch Project' button found on Project Tab");
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to find 'Launch Project' button on project tab");
			}
			
			if(!bStatus)
				throw new IDIOMException("Validation failed.Refer above message.###FailedVerification");
			
		}catch(Exception e){
			exceptionCode(e,strClassName);
		}finally{
			try{
				
				if(!strProjectDetails.equalsIgnoreCase("")){
					util.deleteProjectOrAudience(strProjectDetails, true);
					CustomReporter.log("Deleted Project");
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
