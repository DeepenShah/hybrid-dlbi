package com.IDIOM.ClientHomePage;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/**
 * <p> <b>Test Case Name:</b> *Manage Projects 4 : Launch the project by clicking project row. </p>
 * <p> <b>Objective:</b> Clicking project from project list should launch the project. User should be navigated to project dashboard. </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8864.aspx </p>
 * <p> <b>Module:</b> Client Home Page </p>
 * @author Rohan Macwan
 * @since 14 June 2016
 *
 */
public class ClientHomePage8864 extends BaseClass {

	@Test
	public void	verifyAudienceDuplication(){
		String strMsg = null;		
		boolean bProjectCreate = false;
		String strProjectName="";
		String strDetails ="";
		
		try{
						
			//****************Test step starts here************************************************
			
			//loginToSelectClient
			//Step1: Open appropriate environment URL.
			//Step 2 - Provide valid credential
			//Step 3 - Select a client if not selected in previous login
			loginToSelectClient();
			
			//Click on new project button
			//Provide necessary details and click on 'Create' button or if project is already created click on 'Save' or 'Audience' tab. 
			strProjectName = clientListPage.createNewProject("");

			bProjectCreate=true;
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");

			
			rm.webElementSync("pageload");
			
			//clientListPage.func_PerformAction("Close Project");
			
			//Step 4 - On Project list,click on any project
			CustomReporter.log("Click on project row for Project - " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			
			clientListPage.launchProject(strProjectName);
			
			rm.webElementSync("pageload");
			
			CustomReporter.log("Verify whether Project Dashboard has loaded successfully ");
			
			if (!rm.webElementSync(pdp.projectName, "visibility"))
				throw new IDIOMException("Project Dashboard has not succcesfully loaded ###8864-ProjectDashboardNot Loaded");
						
			Thread.sleep(2000);
			
			if (!rm.webElementSync(MessageFormat.format(pdp.strLinkContains, property.getProperty("successMetrics")),"visibiltiybyxpath"))
				throw new IDIOMException("'Success Metrics' link is not present. Probably there might be some issues on the Dashboard page ###8864-ProjectDashboardSuccessMetricsLinkNotFound");
			
			CustomReporter.log("'Success Metrics' link is present");
			
			CustomReporter.log("Project Dashboard has loaded successfully ");
												
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog(strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8864", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the Project");
				}
			
				//Step 5 - Click on logout
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
