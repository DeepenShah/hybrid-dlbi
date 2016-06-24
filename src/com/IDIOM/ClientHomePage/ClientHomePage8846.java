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
 * <p> <b>Test Case Name:</b> *Manage Projects 2.b : Verify audience tab on Create/Edit project window. </p>
 * <p> <b>Objective:</b> This test case will verify various elements available on Audience tab. </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8846.aspx </p>
 * <p> <b>Module:</b> Client Home Page </p>
 * @author Rohan Macwan
 * @since 14 June 2016
 *
 */
public class ClientHomePage8846 extends BaseClass {

	@Test
	public void	verifyAudienceTabComponents(){
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
			
			bProjectCreate=true;
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");

			
			rm.webElementSync("pageload");

			
			clientListPage.performActionOnProject("edit", strProjectName);
			
			//ArrayList<String> audiecnesInEditOverlay=clientListPage.func_getListOfAudiencesForEditedProject();
			
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			if(!clientListPage.isVisible("newaudiencebtn"))
				throw new IDIOMException("Audience Tab seems to be not properly loaded and there might be issues present on it###8846-AudienceTabIssue");
			
			strAudienceName= clientListPage.createNewAudience("");
			
			CustomReporter.log("Audience " + strAudienceName + " created successfully");
			
			clientListPage.func_PerformAction("Close Project");
			
			CustomReporter.log("Now Edit the project and click on Audience Tab");
			
			clientListPage.performActionOnProject("edit", strProjectName);
			
			//ArrayList<String> audiecnesInEditOverlay=clientListPage.func_getListOfAudiencesForEditedProject();
			
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			if(!clientListPage.isVisible("newaudiencebtn"))
				throw new IDIOMException("Audience Tab seems to be not properly loaded and there might be issues present on it###8846-AudienceTabIssue_Edit");
			
			CustomReporter.log("Audience tab is highlighted successfully");
			
			
			ArrayList<String> totalAudiences = new ArrayList<String>();
			totalAudiences = clientListPage.func_getListOfAudiencesForEditedProject();
			//Step 6 - Check the audience.
			CustomReporter.log("Total Count for Audience list is " + totalAudiences.size());
			
			//For Edit buttons
			CustomReporter.log("Verify whether Total Edit buttons count match with Total Audiences count");
			
			CustomReporter.log("Total Count for Edit buttons is " + clientListPage.listEditButtonsForEditedAudience.size());
			
			if (!(totalAudiences.size()==clientListPage.listEditButtonsForEditedAudience.size()))
				throw new IDIOMException("Total count for Edit buttons and Audiences does not match ###8846-EditButtonsTotalCountDoesNotMatch");
			
			CustomReporter.log("Total Count is matching for Edit buttons and list of Audiences");
			
			//For Radio buttnos
			CustomReporter.log("Verify whether Total Radio buttons count match with Total Audiences count");
			
			CustomReporter.log("Total Count for Radio buttons is " + clientListPage.listRadioButtonsForEditedAudience.size());
			
			if (!(totalAudiences.size()==clientListPage.listRadioButtonsForEditedAudience.size()))
				throw new IDIOMException("Total count for Radio buttons and Audiences does not match ###8846-CancelLinksTotalCountDoesNotMatch");
			
			CustomReporter.log("Total Count is matching for Radio buttons and list of Audiences");
			
			//For Delete links
			CustomReporter.log("Verify whether Total Delete links count match with Total Audiences count");
			
			CustomReporter.log("Total Count for Delete links is " + clientListPage.listDeleteButtonsForEditedAudience.size());
			
			if (!(totalAudiences.size()==clientListPage.listDeleteButtonsForEditedAudience.size()))
				throw new IDIOMException("Total count for Delete links and Audiences does not match ###8846-DeleteLinksTotalCountDoesNotMatch");
			
			CustomReporter.log("Total Count is matching for Delete links and list of Audiences");
			
			//For Duplicate links
			CustomReporter.log("Verify whether Total Duplicate links count match with Total Audiences count");
			
			CustomReporter.log("Total Count for Duplicate links is " + clientListPage.listDuplicateButtonsForEditedAudience.size());
			
			if (!(totalAudiences.size()==clientListPage.listDuplicateButtonsForEditedAudience.size()))
				throw new IDIOMException("Total count for Duplicate links and Audiences does not match ###8846-DuplicateLinksTotalCountDoesNotMatch");
			
			CustomReporter.log("Total Count is matching for Duplicate links and list of Audiences");
			
			//For New Audience button along with its + icon
			CustomReporter.log("Verify whether New Audience Button is present along with its '+' icon");
					
			if (!rm.webElementSync(clientListPage.newAudienceButtonWithPlusIcon, "visibility"))
				throw new IDIOMException("Either New Audience button or its Plus icon or both are not present ###8846-NewAudienceButtonWithPlusIconNotFound");
			
			CustomReporter.log("Either New Audience button or its Plus icon or both are not present");
			
			//For Title Text
			CustomReporter.log("Verify whether Title Text is present");
					
			if (!rm.webElementSync(clientListPage.editAudienceTtitle, "visibility"))
				throw new IDIOMException("Title is missing ###8846-EditTitleNotFound");
			
			CustomReporter.log("Title for Edited Audience is present");
			
			//For Launch Project button
			CustomReporter.log("Verify whether Launch Project  button is present");
			
			if (!rm.webElementSync(MessageFormat.format(clientListPage.strLaunchProjectBtn, strProjectName),"visibiltiybyxpath"))
				throw new IDIOMException("'Launch Project' button is not found on Project overlay ###8846-LaunchProjectButtonNotFound");

			CustomReporter.log("Launch Project  button is present");
			
			//For Project Close button
			CustomReporter.log("Verify whether Project  Close button is present");
			
			if (!rm.webElementSync(MessageFormat.format(clientListPage.strLaunchProjectBtn, strProjectName),"visibiltiybyxpath"))
				throw new IDIOMException("'Project Close' button is not found on Project overlay ###8846-ProjectCloseButtonNotFound");

			CustomReporter.log("Project Close button is present");
			
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
			rm.captureScreenShot("8846", "fail");
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
