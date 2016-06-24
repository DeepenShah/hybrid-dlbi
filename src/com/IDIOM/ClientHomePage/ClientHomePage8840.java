package com.IDIOM.ClientHomePage;

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
 * <p> <b>Test Case Name:</b> *Manage Projects 2.b.i : Verify default audience for newly created project. </p>
 * <p> <b>Objective:</b> After creating project, default audience name 'Total Population' will get created. </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8840.aspx </p>
 * <p> <b>Module:</b> Client Home Page </p>
 * @author Rohan Macwan
 * @since 10 June 2016
 *
 */
public class ClientHomePage8840 extends BaseClass {

	@Test
	public void	verifyDefaultAudienceName(){
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
			
			//Step 4 - Click on 'New Project' button
			//Step 5 - Provide necessary details and click on 'Create' button.		
			CustomReporter.log("Create the project");
			
			if(strProjectName.equalsIgnoreCase(""))
				strProjectName="Automation Project " + BaseClass.rm.getCurrentDateTime();
			
			clientListPage.func_PerformAction("New Project");
			BaseClass.rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
							
			clientListPage.findAndSaveProjectWindow(true, "");
			clientListPage.fillProject(strProjectName,"");				
			clientListPage.func_PerformAction("Save Project");
			Thread.sleep(2000);
			
			strMsg = "Project "+ strProjectName+" created successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
						
			bProjectCreate=true;
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");

			if(!rm.webElementSync(clientListPage.globalProjectWindow.findElement(By.xpath(clientListPage.strAudienceTab)), "visibility"))
				throw new IDIOMException("Audience Tab has not got visible ###8840_AudienceTabNotMadeVisible");
			
			CustomReporter.log("Audience Tab is visible");
			
			//Step 6 - Check the audience list.	
			if(!rm.webElementSync(clientListPage.globalProjectWindow.findElement(By.xpath(clientListPage.strTotalPopulation)), "visibility"))
				throw new IDIOMException("Default Audiecne is not found ###8840_AudienceTabNotMadeVisible_1");
			
			CustomReporter.log("Default Audience is found");
			
			CustomReporter.log("Verify the text of default Audience");
			
			if(!clientListPage.globalProjectWindow.findElement(By.xpath(clientListPage.strTotalPopulation)).getText().trim().equals(property.getProperty("defaultaudience")))
				throw new IDIOMException("Default Audiecne Text is not set as '" + property.getProperty("defaultaudience") + "' ###8840_DefaultAudienceTextDidNotMatch");
			
			CustomReporter.log("Default Audience Text is correctly set as '" + property.getProperty("defaultaudience") + "'");
			
			clientListPage.func_PerformAction("Close Project");
									
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
			rm.captureScreenShot("8840", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the Project");
				}
				
				//Step 7 - Click on logout
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
