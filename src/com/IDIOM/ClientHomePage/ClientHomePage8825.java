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
 * <p> <b>Test Case Name:</b> *Manage Projects 2.a.iii : Check behaviour of 'Create' button in Create New Project window. </p>
 * <p> <b>Objective:</b> To check validation after clicking create button. Also if everything is fine then it should lead to 'Audience' tab. </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8825.aspx </p>
 * <p> <b>Module:</b> Client Home Page </p>
 * @author Rohan Macwan
 * @since 03 June 2016
 *
 */

public class ClientHomePage8825 extends BaseClass {

	@Test
	public void	verifyWhetherValuesPresentForEachProject(){
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
			clientListPage.func_PerformAction("New Project");
			BaseClass.rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			clientListPage.findAndSaveProjectWindow(true, "");
			
			//Step 5 - Provide any description and do not provide the name. And check whether Save button is enabled or disabled
			if (clientListPage.globalProjectWindow.findElement(By.xpath(clientListPage.strProjectSaveBtn)).isEnabled()==true)
			{
				CustomReporter.errorLog("Save button is found Enabled.");
				BaseClass.testCaseStatus = false;
			}
			else
			{
				CustomReporter.log("Save button is correctly found as Disabled.");
			}
			
			clientListPage.func_PerformAction("Close Project");
			
			//rm.webElementSync("pageload");
			
			CustomReporter.log("Now try to create the project");
			
			String strMsg1;
			
			if(strProjectName.equalsIgnoreCase(""))
				strProjectName="Automation Project " + BaseClass.rm.getCurrentDateTime();
			
			clientListPage.func_PerformAction("New Project");
			BaseClass.rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg1 = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg1);
			System.out.println(getClass().getSimpleName() + ": " + strMsg1);
							
			clientListPage.findAndSaveProjectWindow(true, "");
			clientListPage.fillProject(strProjectName,"");				
			clientListPage.func_PerformAction("Save Project");
			Thread.sleep(2000);
			
			strMsg1 = "Project "+ strProjectName+" created successfully";
			CustomReporter.log(strMsg1);
			System.out.println(getClass().getSimpleName() + ": " + strMsg1);
						
			bProjectCreate=true;
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");

			if(!rm.webElementSync(clientListPage.globalProjectWindow.findElement(By.xpath(clientListPage.strAudienceTab)), "visibility"))
				throw new IDIOMException("Audience Tab has not got visible ###8825_AudienceTabNotMadeVisible");
			
			CustomReporter.log("Audience Tab is visible");
			
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
			rm.captureScreenShot("8825", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the Project");
				}
				
				//Step 6 - Click on logout
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
