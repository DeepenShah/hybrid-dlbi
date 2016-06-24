package com.IDIOM.ClientHomePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Manage Projects 2.a.vii : Verify edit project window after coming back to audience tab.</p>
 *  <p> <b>Objective:</b>After providing details and clicking on 'Create', project will be saved and user will be navigated to 'Audience'. Now if user again click on 'Project' tab then again same window of create project will appear with minor changes.</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8835.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 13 Jun 2016
 *
 */
public class ClientHomePage8835 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyEdiProjectWindowNavigatingFromAudienceTab(){		
		String strMsg="";		
		String strProjectDetails="";
		
		boolean bStatus = true;
		
		try{
			
			String strExpEditProjectLabel = property.getProperty("editProjectLabel");			
			//****************Test step starts here************************************************
						
			//Step1-3: Login To Selecting Client
			loginToSelectClient();			
			
			//Step4: Clicking on 'New Project'
			clientListPage.func_PerformAction("New Project");
			if(!rm.webElementSync(clientListPage.newProjectWindow, "visibility"))
				throw new IDIOMException("No new project window found.###NoNewProjectWindow");
			
			CustomReporter.log("New project window found");		
			
			//Step5: Fill necessary details and click create.
			clientListPage.findAndSaveProjectWindow(true, "");
			clientListPage.fillAndSaveProject(strClassName, "This project will get deleted");
			Thread.sleep(2000);
			strProjectDetails = clientListPage.getAudienceProjectClientId(strClassName, "");
			CustomReporter.log("Saved Project");
			
			//Step6: Click on project tab
			clientListPage.func_PerformAction("Project Tab");
			Thread.sleep(1000);
			CustomReporter.log("Moved to 'Project Tab'.");
			
			//Verify label and 'Launch Project button			
			String strActualEditProjectLabel = clientListPage.globalProjectWindow.findElement(By.xpath(clientListPage.strCreateNewProjectLabel)).getText().trim();			
			
			if(strActualEditProjectLabel.equalsIgnoreCase(strExpEditProjectLabel)){
				CustomReporter.log("Verified Edit Project window label: " + strActualEditProjectLabel);
			}else{
				bStatus = false;
				CustomReporter.errorLog("Failed to compare project window label. Expected " + strExpEditProjectLabel +
						" and found " + strActualEditProjectLabel);
			}
			
			WebElement launchProjectBtn = clientListPage.globalProjectWindow.findElement(By.xpath(clientListPage.strLaunchProjectBtn));		
			
			if(launchProjectBtn.isEnabled()){				
				CustomReporter.log("Launch Project button found enabled");
			}else{
				bStatus = false;
				CustomReporter.errorLog("Launch Project button is disabled");
			}
			
			if(!bStatus)
				throw new IDIOMException("Validation failed. Check above messages.###EditProjectWindowValidationFailed");
				
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
