package com.IDIOM.Analyse.Pathing.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Pathing_Verify Audience drop down when navigating from Another Page </p>
 *  <p> <b>Objective:</b>Verify that the audience which is selected in a page is at the same status when launched from another page</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/9046.aspx</p>
 *  <p> <b>Module:</b>Pathing</p>
 *  
 * @author Deepen Shah
 * @since 01 Jun 2016
 *
 */
public class Pathing9046 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyAudienceAfterNavigatingToAnotherPage(){
		 Analyse_Pathing_Page pathing = null;
			
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
			
			CustomReporter.log("Created two new audiences "+ strAudienceName +" and "+ strAudience2Name+" in project " + strProjectName);
			
			clientListPage.func_PerformAction("Close Project");
			Thread.sleep(2000);			
			
			//Step6: Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(1000);
			
			//Step7: Navigating to Webnet page
			pathing = new Analyse_Pathing_Page(driver);
			pdp.navigateTo(property.getProperty("pathing"));
			
			if(rm.webElementSync(pageHeader.loadingSpinner, "visibility"))
					rm.webElementSync("idiomspinningcomplete");			
			
			if(!pathing.isVisible("pathing_wheel"))
				throw new IDIOMException("Failed to load pathing page.###PathingPageIsNotLoaded");
			
			CustomReporter.log("Navigated to webnet page");
			
			if(!pageHeader.verifySelectedAudienceInDropDown(strAudience2Name))
				throw new IDIOMException("Default audience is not matching. Expected " + strAudience2Name +
						" and found " + pageHeader.getSelectedAudienceName() +".###DefaultAudienceNotMatched");
			
			CustomReporter.log("Successfully verified default audience selected in drop down. " + strAudience2Name);
			
			//Step8: Switch audience
			pageHeader.selectAudienceFromDropDown(strAudienceName);
			if(rm.webElementSync(pageHeader.loadingSpinner, "visibility"))
				rm.webElementSync("idiomspinningcomplete");		
			
			if(!pathing.isVisible("pathing_wheel"))
				throw new IDIOMException("Failed to load pathing page after switching audience.###PathingPageIsNotLoadedAfterSwitchinAudience");
			
			CustomReporter.log("Switched to " + strAudienceName + " audience");
			
			//Step9: Navigating to another page. HVA
			HVA_Page hva = new HVA_Page(driver);
			rm.webElementSync(pageHeader.megaMenuIcon, "clickable");
			pageHeader.megaMenuLinksClick(property.getProperty("hva"));
			
			if(!rm.webElementSync(hva.behaviour,"visibility"))
			    throw new IDIOMException("Failed to land on HVA page.###FailedToLandOnHVAPage");
			
			CustomReporter.log("Successfully navigated to HVA page");
			
			//Step10: Go back to Pathing
			rm.webElementSync(pageHeader.megaMenuIcon, "clickable");
			pageHeader.megaMenuLinksClick(property.getProperty("pathing"));
			if(rm.webElementSync(pageHeader.loadingSpinner, "visibility"))
				rm.webElementSync("idiomspinningcomplete");		
			
			if(!pathing.isVisible("pathing_wheel"))
				throw new IDIOMException("Failed to load pathing page after coming back from HVA.###PathingPageIsNotLoadedUponReturningFromHV");
			
			CustomReporter.log("Returned to Pathing page");
			
			//Step11: Verifying selected audience.
			if(!pageHeader.getSelectedAudienceName().equalsIgnoreCase(strAudienceName))
				throw new IDIOMException("Audience is not same as selected earlier It should be "+
						strAudienceName +".###FailedVerifyAudienceSelectedEarlier");
			
			CustomReporter.log("Successfully verified " + strAudienceName+" audience on pathing after returning back");			
			
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
			rm.captureScreenShot("9046-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("9046-"+strMsgAndFileName[1], "fail");	
		}		
	}
}
