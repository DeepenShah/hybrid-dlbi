package com.IDIOM.Analyse.Webnet.scripts;

import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b> Webnet_VerifyDefaultAudienceSelectedIn DropDown  </p>
 *  <p> <b>Objective:</b> verify the default audience selected in audience drop down and verify that all the audiences present in create project/edit project overlay is present in the dropdown </p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/9053.aspx</p>
 *  <p> <b>Module:</b>Webnet</p>
 *  
 * @author Deepen Shah
 * @since 31 May 2016
 *
 */
public class Webnet9053 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyDefaultAudienceSelectedInDropdown(){
		 Analyse_Webnet_Page webnet = null;
			
		String strProjectName="";
		String strAudienceName="";
		String strAudience2Name="";
		String strMsg="";
		String strDeletionDetails="";
		
		boolean bProjectCreated = false;	
		
		try{
			
			//****************Test step starts here************************************************
			
			//Login To Selecting Client
			loginToSelectClient();			
			
			//Step3&4: Select/Create project			
			strProjectName = clientListPage.createNewProject("");
			strDeletionDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			bProjectCreated = true;
			
			
			//Step5: Create new audience
			
			clientListPage.performActionOnProject("edit", strProjectName);
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			strAudienceName = clientListPage.createNewAudience("");
			strAudience2Name = clientListPage.createNewAudience("");
			
			ArrayList<String> audiecnesInEditOverlay=clientListPage.func_getListOfAudiencesForEditedProject();
			CustomReporter.log("Total audiences, " + audiecnesInEditOverlay + " in project " + strProjectName);
			
			clientListPage.func_PerformAction("Close Project");
			Thread.sleep(2000);			
			
			//Step6: Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(1000);
			
			//Step7: Navigating to Webnet page
			webnet = new Analyse_Webnet_Page(driver);
			pdp.navigateTo(property.getProperty("webnet"));
			
			rm.webElementSync("idiomspinningcomplete");
			if(!webnet.isVisible("webnetchart"))
				throw new IDIOMException("Failed to load webnet page.###WebnetPageIsNotLoaded");
			
			CustomReporter.log("Navigated to webnet page");
			
			if(!pageHeader.verifySelectedAudienceInDropDown(strAudience2Name))
				throw new IDIOMException("Default audience is not matching. Expected " + strAudience2Name +
						" and found " + pageHeader.getSelectedAudienceName() +".###DefaultAudienceNotMatched");
			
			CustomReporter.log("Successfully verified default audience selected in drop down. " + strAudience2Name);
			
			
			pageHeader.audienceDropDownLink.click();
			CustomReporter.log("Click on audience drop down and verify the audiences present");
			ArrayList<String> audiecnesInWebnet=pageHeader.returnAudiencesInDropDown();

			if(!audiecnesInEditOverlay.equals(audiecnesInWebnet))
				throw new IDIOMException("Audience list in drop down is not matching with Project window. In project, " +
			audiecnesInEditOverlay + " and in drop down " + audiecnesInWebnet +".###FailedToMatchAudiences");
			
			CustomReporter.log("Successfully verified audiences with dropdown and total created in project." + audiecnesInWebnet);
			
		}catch(Exception e){
			exceptionCode(e);
		}finally{
			try{
				
				//Deleting newly created project or Audience			
				if(bProjectCreated){				
					rm.deleteProjectOrAudience(strDeletionDetails, true);
					CustomReporter.log("Deleted the project");					
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
		
	public void exceptionCode(Exception ie){
		BaseClass.testCaseStatus = false;
		String strMsgAndFileName[] = ie.getMessage().split("###");
		System.out.println(strClassName + ": " + strMsgAndFileName[0]);
		ie.printStackTrace(System.out);
		
		if(strMsgAndFileName.length==1){
			CustomReporter.errorLog("Failure: "+ ie.getMessage());
			rm.captureScreenShot("9053-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("9053-"+strMsgAndFileName[1], "fail");	
		}		
	}
}
