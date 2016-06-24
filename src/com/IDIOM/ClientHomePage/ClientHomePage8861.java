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
 * <p> <b>Test Case Name:</b> *Manage Projects 3.b : Duplicate the project and check the name </p>
 * <p> <b>Objective:</b> To verify duplicate functionality for project </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8861.aspx </p>
 * <p> <b>Module:</b> Client Home Page </p>
 * @author Rohan Macwan
 * @since 13 June 2016
 *
 */
public class ClientHomePage8861 extends BaseClass{
	@Test
	public void	verifyProjectDuplication(){
		String strMsg = null;		
		boolean bProjectCreate = false;
		boolean bProjectCreateDuplicate = false;
		boolean bProjectCreateAgainDuplicate = false;
		String strProjectName="Testing 8861";
		String strProjectDesc="Testing 8861 Desc";
		String strDetails ="";
		String strDetailsDuplicate ="";
		String strDetailsAgainDuplicate ="";
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
			clientListPage.func_PerformAction("New Project");
			BaseClass.rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
							
			clientListPage.findAndSaveProjectWindow(true, "");
			clientListPage.fillProject(strProjectName,strProjectDesc);				
			clientListPage.func_PerformAction("Save Project");
			Thread.sleep(2000);
			
			strMsg = "Project "+ strProjectName+" created successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			clientListPage.func_PerformAction("Close Project");
			
			bProjectCreate=true;
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");

			
			rm.webElementSync("pageload");

			
			clientListPage.performActionOnProject("edit", strProjectName);
			
			//ArrayList<String> audiecnesInEditOverlay=clientListPage.func_getListOfAudiencesForEditedProject();
			
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			if(!clientListPage.isVisible("newaudiencebtn"))
				throw new IDIOMException("Audience Tab seems to be not properly loaded and there might be issues present on it###8861-AudienceTabIssue");
			
			strAudienceName= clientListPage.createNewAudience("");
			
			CustomReporter.log("Audience " + strAudienceName + " created successfully");
			
			clientListPage.func_PerformAction("Close Project");
			//############################
			
			//Step 6 - 	For any project click on 'Duplicate'.
			CustomReporter.log("Duplicate the project " + strProjectName);
			
			clientListPage.performActionOnProject("duplicate", strProjectName);
			Thread.sleep(2000);
			String prjNameDesc []= new String [2];
			
			prjNameDesc=clientListPage.getProjectNameAndDescriptionFromOverlay();
			
			CustomReporter.log("Verify Project Name and Project Description in Project Duplicate Window");
			
			if(!prjNameDesc [0].equals(strProjectName))
				throw new IDIOMException("Project Name does not match in Duplicate Project window - Project Name Textbox it###8861-ProjectNameDoesNotMatchOnPrjDuplicateWindow");
			
			CustomReporter.log("Project Name has been correctly set for the Duplicate");
			
			if(!prjNameDesc [1].equals(strProjectDesc))
				throw new IDIOMException("Project Description does not match in Duplicate Project window - Project Name Textbox it###8861-ProjectDescDoesNotMatchOnPrjDuplicateWindow");
			
			CustomReporter.log("Project Description has been correctly set for the Duplicate");
			
			clientListPage.findAndSaveProjectWindow(false, strProjectName, 2);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			if(!clientListPage.isVisible("newaudiencebtn"))
				throw new IDIOMException("Audience Tab seems to be not properly loaded and there might be issues present on it###8861-AudienceTabIssue_1");

			CustomReporter.log("Verify whether Audience which was created in first project is present in Duplicate Project window");
	
			ArrayList<String> audiecnesInEditOverlay=clientListPage.func_getListOfAudiencesForEditedProject();
					
			if(!audiecnesInEditOverlay.contains(strAudienceName))
				throw new IDIOMException("Audience " + strAudienceName + "is not successfully found in Duplicate window ###8861-AudienceNotFoundInDuplicateWindow");

			CustomReporter.log("Audience " + strAudienceName + " is successfully found in Duplicate window");
			
			
			clientListPage.func_PerformAction("Close Project");
			//##############################################################
			
			CustomReporter.log("Edit the duplicated Project");
			clientListPage.performActionOnDuplicateProject("edit", strProjectName, 2);
			
			Thread.sleep(2000);
			
			prjNameDesc=null;
			prjNameDesc=clientListPage.getProjectNameAndDescriptionFromOverlay();
			
			CustomReporter.log("Verify Project Name and Project Description in Duplicated Project Edit window");
			
			if(!prjNameDesc [0].equals(strProjectName))
				throw new IDIOMException("Project Name does not match in Duplicated Project edit window - Project Name Textbox ###8861-ProjectNameDoesNotMatchOnPrjDuplicateWindow_Edit");
			
			CustomReporter.log("Project Name has been correctly set for the Duplicated project edit window");
			
			if(!prjNameDesc [1].equals(strProjectDesc))
				throw new IDIOMException("Project Description does not match for Duplicated Project in edit window - Project Description Textbox ###8861-ProjectDescDoesNotMatchOnPrjDuplicateWindow_Edit");
			
			CustomReporter.log("Project Description has been correctly set for the Duplicated Project in edit window");
			
			clientListPage.findAndSaveProjectWindow(false, strProjectName, 2);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			if(!clientListPage.isVisible("newaudiencebtn"))
				throw new IDIOMException("Audience Tab seems to be not properly loaded and there might be issues present on it###8861-AudienceTabIssue_Edit");

			audiecnesInEditOverlay=null;
			audiecnesInEditOverlay=clientListPage.func_getListOfAudiencesForEditedProject();
			
			CustomReporter.log("Verify whether Audience which was created in first project is present in its Duplicated project Edit window");
			
			if(!audiecnesInEditOverlay.contains(strAudienceName))
				throw new IDIOMException("Audience " + strAudienceName + "is not successfully found in Duplicated Project edit window ###8861-AudienceNotFoundInDuplicateWindow_Edit");

			CustomReporter.log("Audience " + strAudienceName + "is successfully found in Duplicated Project edit window");
			
			bProjectCreateDuplicate=true;
			strDetailsDuplicate=clientListPage.getAudienceProjectClientId(strProjectName, "",2);
			
			clientListPage.func_PerformAction("Close Project");
						
			
			//############################
			
			//Step 6 - 	For any project click on 'Duplicate'.
			CustomReporter.log("Again Duplicate the same project " + strProjectName);
			
			clientListPage.performActionOnProject("duplicate", strProjectName);
			//clientListPage.performActionOnDuplicateProject("duplicate", strProjectName, 2);
			
			Thread.sleep(2000);
			prjNameDesc=null;
			
			prjNameDesc=clientListPage.getProjectNameAndDescriptionFromOverlay();
			
			CustomReporter.log("Verify Project Name and Project Description in Project Duplicate Window");
			
			if(!prjNameDesc [0].equals(strProjectName))
				throw new IDIOMException("Project Name does not match in Duplicate Project window - Project Name Textbox ###8861-ProjectNameDoesNotMatchOnPrjDuplicateWindow_AgainDuplicate");
			
			CustomReporter.log("Project Name has been correctly set for the Duplicate");
			
			if(!prjNameDesc [1].equals(strProjectDesc))
				throw new IDIOMException("Project Description does not match in Duplicate Project window - Project Description Textbox ###8861-ProjectDescDoesNotMatchOnPrjDuplicateWindow_AgainDuplicate");
			
			CustomReporter.log("Project Description has been correctly set for the Duplicate");
			
			clientListPage.findAndSaveProjectWindow(false, strProjectName, 3);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			if(!clientListPage.isVisible("newaudiencebtn"))
				throw new IDIOMException("Audience Tab seems to be not properly loaded and there might be issues present on it###8861-AudienceTabIssue_AgainDuplicate");

			CustomReporter.log("Verify whether Audience which was duplicated for first project is present in Duplicate Project window");
	
			audiecnesInEditOverlay=null;
			audiecnesInEditOverlay=clientListPage.func_getListOfAudiencesForEditedProject();
					
			if(!audiecnesInEditOverlay.contains(strAudienceName))
				throw new IDIOMException("Audience " + strAudienceName + "is not successfully found in Duplicate window ###8861-AudienceNotFoundInDuplicateWindow_AgainDuplicate");

			CustomReporter.log("Audience " + strAudienceName + " is successfully found in Duplicate window");
			
			//Step 6 - For any audience click on 'Delete'. 
			
			clientListPage.func_PerformAction("Close Project");
			//##############################################################
			
			CustomReporter.log("Edit the secondly duplicated Project");
			clientListPage.performActionOnDuplicateProject("edit", strProjectName, 3);
			
			Thread.sleep(2000);
			
			prjNameDesc=null;
			prjNameDesc=clientListPage.getProjectNameAndDescriptionFromOverlay();
			
			CustomReporter.log("Verify Project Name and Project Description in Duplicated Project Edit window");
			
			if(!prjNameDesc [0].equals(strProjectName))
				throw new IDIOMException("Project Name does not match in Duplicated Project edit window - Project Name Textbox ###8861-ProjectNameDoesNotMatchOnPrjDuplicateWindow_Edit_AgainDuplicate");
			
			CustomReporter.log("Project Name has been correctly set for the Duplicated project edit window");
			
			if(!prjNameDesc [1].equals(strProjectDesc))
				throw new IDIOMException("Project Description does not match for Duplicated Project in edit window - Project Name Textbox ###8861-ProjectDescDoesNotMatchOnPrjDuplicateWindow_Edit_AgainDuplicate");
			
			CustomReporter.log("Project Description has been correctly set for the Duplicated Project in edit window");
			
			clientListPage.findAndSaveProjectWindow(false, strProjectName, 3);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			if(!clientListPage.isVisible("newaudiencebtn"))
				throw new IDIOMException("Audience Tab seems to be not properly loaded and there might be issues present on it###8861-AudienceTabIssue_Edit_AgainDuplicate");

			audiecnesInEditOverlay=null;
			audiecnesInEditOverlay=clientListPage.func_getListOfAudiencesForEditedProject();
			
			CustomReporter.log("Verify whether Audience which was created in first project is present in its Duplicated project Edit window");
			
			if(!audiecnesInEditOverlay.contains(strAudienceName))
				throw new IDIOMException("Audience " + strAudienceName + "is not successfully found in Duplicated Project edit window ###8861-AudienceNotFoundInDuplicateWindow_Edit_AgainDuplicated");

			CustomReporter.log("Audience " + strAudienceName + "is successfully found in Duplicated Project edit window");
			
			bProjectCreateAgainDuplicate=true;
			strDetailsAgainDuplicate=clientListPage.getAudienceProjectClientId(strProjectName, "",3);
			
			clientListPage.func_PerformAction("Close Project");
			
			//############################
									
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
			rm.captureScreenShot("8861", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the Project");
				}
				
				//Step for Deleting newly created project (Duplicated)
				if(bProjectCreateDuplicate){
					rm.deleteProjectOrAudience(strDetailsDuplicate, true);
					CustomReporter.log("Deleted the Duplicated Project");
				}
				
				//Step for Deleting newly created project (Again Duplicated)
				if(bProjectCreateAgainDuplicate){
					rm.deleteProjectOrAudience(strDetailsAgainDuplicate, true);
					CustomReporter.log("Deleted the Duplicated Project");
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
