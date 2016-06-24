package com.IDIOM.ClientHomePage;

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
 * <p> <b>Test Case Name:</b> *Manage Projects 2.b.v : Delete the audience. </p>
 * <p> <b>Objective:</b> To verify delete functionality for audience </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8854.aspx </p>
 * <p> <b>Module:</b> Client Home Page </p>
 * @author Rohan Macwan
 * @since 13 June 2016
 *
 */
public class ClientHomePage8854 extends BaseClass {

	@Test
	public void	verifyDeletionOfAudience(){
		String strMsg = null;		
		boolean bProjectCreate = false;
		String strProjectName="";
		String strDetails ="";
		String strAudienceName="";
		
		try{
						
			//****************Test step starts here************************************************
			
			//loginToSelectClient
			//Step1: Open appropriate environment URL.
			//Step 2 - Provide valid credential
			//Step 3 - Select a client if not selected in previous login
			loginToSelectClient();
			
			//Step 4 - Click on new project button
			//Step 5 - Provide necessary details and click on 'Create' button or if project is already created click on 'Save' or 'Audience' tab. 
			strProjectName = clientListPage.createNewProject("");
			bProjectCreate=true;
			
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			rm.webElementSync("pageload");

			
			clientListPage.performActionOnProject("edit", strProjectName);
			
			//ArrayList<String> audiecnesInEditOverlay=clientListPage.func_getListOfAudiencesForEditedProject();
			
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			if(!clientListPage.isVisible("newaudiencebtn"))
				throw new IDIOMException("Audience Tab seems to be not properly loaded and there might be issues present on it###8854-AudienceTabIssue");
			
			strAudienceName= clientListPage.createNewAudience("");
			
			CustomReporter.log("Audience " + strAudienceName + " created successfully");
			
			clientListPage.func_PerformAction("Close Project");
			//############################
			
			clientListPage.performActionOnProject("edit", strProjectName);
			
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			if(!clientListPage.isVisible("newaudiencebtn"))
				throw new IDIOMException("Audience Tab seems to be not properly loaded and there might be issues present on it###8854-AudienceTabIssue_1");

			//Step 6 - For any audience click on 'Delete'. 
			CustomReporter.log("Delete the Audience " + strAudienceName);
			
			clientListPage.performActionOnAudience(strAudienceName, "delete");
			
			clientListPage.func_PerformAction("Close Project");
			
			//############################
			
			CustomReporter.log("Edit the project " + strProjectName);
			
			clientListPage.performActionOnProject("edit", strProjectName);
			
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			if(!clientListPage.isVisible("newaudiencebtn"))
				throw new IDIOMException("Audience Tab seems to be not properly loaded and there might be issues present on it###8854-AudienceTabIssue_2");
			
			CustomReporter.log("Check whether Audience " + strAudienceName + " is present");
			
			ArrayList<String> audiecnesInEditOverlay=clientListPage.func_getListOfAudiencesForEditedProject();
			
			if(audiecnesInEditOverlay.contains(strAudienceName))
				throw new IDIOMException("Audience " + strAudienceName + "is not deleted successfully it###8854-AudienceTabIssue_3");
			
			CustomReporter.log("Audience " + strAudienceName + " is deleted successfully");
			
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
