package com.IDIOM.Analyse.Webnet.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b>Webnet_Verify Audience drop down when switching audience from Another Page</p>
 *  <p> <b>Objective:</b>Verify that the audience which is selected in another  page is selected in Webnet by default when we navigate to it from another page</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/9056.aspx</p>
 *  <p> <b>Module:</b>Webnet</p>
 *  
 * @author Deepen Shah
 * @since 31 May 2016
 *
 */
public class Webnet9056 extends BaseClass {	
	
	String strClassName = getClass().getSimpleName();
	
	@Test
	void verifyChangingAudienceOnAnotherPage(){
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
			
			//Step8: Navigating to any other page.
			HVA_Page hva = new HVA_Page(driver);
			rm.webElementSync(pageHeader.megaMenuIcon, "clickable");
			pageHeader.megaMenuLinksClick(property.getProperty("hva"));
			
			if(!rm.webElementSync(hva.behaviour,"visibility"))
			    throw new IDIOMException("Failed to land on HVA page.###FailedToLandOnHVAPage");
			
			CustomReporter.log("Successfully navigated to HVA page");
			
			
			//Step9: Change audience from drop down.
			rm.webElementSync(pageHeader.audienceDropDownLink, "clickable");
			pageHeader.selectAudienceFromDropDown(strAudienceName);
			rm.webElementSync("idiomspinningcomplete");
			if(!rm.webElementSync(hva.behaviour,"visibility"))
			    throw new IDIOMException("Failed to reload hva page after chaging audience.###FailedToLoadHVAPageAfterChangingAudience");
			
			rm.webElementSync(pageHeader.audienceDropDownLink, "clickable");
			if(!pageHeader.getSelectedAudienceName().equalsIgnoreCase(strAudienceName))
				throw new IDIOMException("Not able to change audience.###FailedToChangeAudience");
			
			CustomReporter.log("Changed to " + strAudienceName+" audience");	
			
			
			//Step10: Go back to webnet
			rm.webElementSync(pageHeader.megaMenuIcon, "clickable");
			pageHeader.megaMenuLinksClick(property.getProperty("webnet"));
			rm.webElementSync("idiomspinningcomplete");
			if(!webnet.isVisible("webnetchart"))
				throw new IDIOMException("Failed to load webnet page after coming back from HVA.###WebnetPageIsNotLoadedUponReturningFromHV");
			
			CustomReporter.log("Returned to Webnet page");
			
			//Step11: Check selected audience
			if(!pageHeader.getSelectedAudienceName().equalsIgnoreCase(strAudienceName))
				throw new IDIOMException("Audience is not same as selected earlier on HVA page. It should be "+
						strAudienceName +".###FailedVerifyAudienceSelectedEarlier");
			
			CustomReporter.log("Successfully verified " + strAudienceName+" audience on webnet after returning back");		
			
		
		}catch(Exception ie){
			exceptionCode(ie);			
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
			rm.captureScreenShot("9056-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot("9056-"+strMsgAndFileName[1], "fail");	
		}		
	}
	
}
