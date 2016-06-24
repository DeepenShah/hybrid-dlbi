package com.IDIOM.architect.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b> Media Ranker_Verify Audience drop down when navigating from Another Page[After switching audience from both the pages]  </p>
 * <p> <b>Objective:</b> Verify that proper auidence is selected in audience drop down after switching audiences from both the pages </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/9067.aspx </p>
 * <p> <b>Module:</b> Media Ranker</p>
 * @author Deepen Shah
 * @since 24 May 2016
 *
 */
public class MediaRanker9067 extends BaseClass {	
	
	@Test
	void verifySelectedAudienceAfterChangingInTwoDifferentPages(){
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;
		String strDeletionDetails="";
		
		String strAudienceName="";				
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String[] strChannels = property.getProperty("mediaRankerItems").split(",");			
			String strDefaultAudienceName = property.getProperty("defaultaudience");
			//****************Test step starts here************************************************
			
			//Launch url
			strMsg = "Launched Browser and Enter URL";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			Login_Page ln = new Login_Page(driver);
			
			//Step1: Login with valid credential
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    strMsg = "Logged in successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Step2: Select client
			clientListPage = new ClientList_Page(driver);
			
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");		
			
			
			//Step3&4: Create New Project		
			strProjectName = clientListPage.createNewProject("");
			strDeletionDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			bProjectCreated = true;
					
			//Step5&6: Create new audience			
			clientListPage.performActionOnProject("edit", strProjectName);
			clientListPage.findAndSaveProjectWindow(false, strProjectName);
			
			clientListPage.func_PerformAction("Audience Tab");
			
			strAudienceName = clientListPage.createNewAudience("");
			clientListPage.func_PerformAction("Close Project");
			Thread.sleep(2000);
							
			
			//Step7: Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(1000);
			
			//Step8: Navigate to Media Ranker Page			
			ArchitectMediaRankerPage mediaRanker = new ArchitectMediaRankerPage(driver);
			HVA_Page hva = new HVA_Page(driver);
						
			for(int i=0;i<strChannels.length;i++){
				pageHeader.megaMenuLinksClick(strChannels[i]);
				rm.webElementSync("idiomspinningcomplete");
				if(!mediaRanker.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannels[i] +" page");				
								
				try{
					
					//Step9: Change audience from drop down.
					rm.webElementSync(pageHeader.audienceDropDownLink, "clickable");
					pageHeader.selectAudienceFromDropDown(strDefaultAudienceName);
					rm.webElementSync("idiomspinningcomplete");
					if(!mediaRanker.func_VerifyVisibilityOfElement("slowtvranker"))
						   throw new IDIOMException("Page is not loading after changing dropdown.###PageIsNotLoadedAfterChangingAudience");
					
					rm.webElementSync(pageHeader.audienceDropDownLink, "clickable");
					if(!pageHeader.getSelectedAudienceName().equalsIgnoreCase(strDefaultAudienceName))
						throw new IDIOMException("Not able to change audience.###FailedToChangeAudience");
					
					CustomReporter.log("Changed to " + strDefaultAudienceName+" audience on  " + strChannels[i]);
					
					//Step10: Navigating to any other page.
					rm.webElementSync(pageHeader.megaMenuIcon, "clickable");
					pageHeader.megaMenuLinksClick(property.getProperty("hva"));
					
					if(!rm.webElementSync(hva.behaviour,"visibility"))
					    throw new IDIOMException("Failed to land on HVA page.###FailedToLandOnHVAPage");
					
					CustomReporter.log("Successfully navigated to HVA page");
					
					//Step11: Switching audience again
					rm.webElementSync(pageHeader.audienceDropDownLink, "clickable");
					pageHeader.selectAudienceFromDropDown(strAudienceName);
					rm.webElementSync("idiomspinningcomplete");
					if(!rm.webElementSync(hva.behaviour,"visibility"))
					    throw new IDIOMException("Failed reload HVA page after changing audience.###FailedReloadHVAPage");
					
					CustomReporter.log("HVA page is loaded after changing audience.");
					
					//Verifying audience
					rm.webElementSync(pageHeader.audienceDropDownLink, "clickable");
					if(!pageHeader.getSelectedAudienceName().equalsIgnoreCase(strAudienceName))
						throw new IDIOMException("Not able to change audience on HVA page.###FailedToChangeAudienceOnHVA");
					
					CustomReporter.log("Verified " + strAudienceName+" audience on HVA page");
					
					//Step12: Go back to channel page
					rm.webElementSync(pageHeader.megaMenuIcon, "clickable");
					pageHeader.megaMenuLinksClick(strChannels[i]);
					rm.webElementSync("idiomspinningcomplete");
					if(!mediaRanker.func_VerifyVisibilityOfElement("slowtvranker"))
						   throw new IDIOMException("Failed to land on " +strChannels[i] +"page.###FailedToLandOn"+strChannels[i]);
					
					CustomReporter.log("Navigated back to " + strChannels[i]);
					
					//Step13: Check audience again. It should remain same as step8.
					if(!pageHeader.getSelectedAudienceName().equalsIgnoreCase(strAudienceName))
						throw new IDIOMException("Audience is not same as selected earlier It should be "+
								strAudienceName +".###FailedVerifyAudienceSelectedEarlier");
					
					CustomReporter.log("Successfully verified " + strAudienceName+" audience on  " + strChannels[i]);
					
					
				}catch(IDIOMException e){
					exceptionCode(e);
				}catch(Exception e){
					BaseClass.testCaseStatus = false;
					e.printStackTrace(System.out);
					CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
					rm.captureScreenShot("9060-Exception", "fail");
				}finally{				
					rm.webElementSync(pageHeader.clientLogo, "clickable");					
				}
					
			}			
			
		
		}catch(IDIOMException ie){
			exceptionCode(ie);			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("9060-Exception", "fail");
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
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
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
	
	
	public void exceptionCode(IDIOMException ie){
		BaseClass.testCaseStatus = false;
		String strMsgAndFileName[] = ie.getMessage().split("###");
		System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
		CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
		
		if(strMsgAndFileName.length==1){
			rm.captureScreenShot("9060-Exception", "fail");
		}else{
			rm.captureScreenShot("9060-"+strMsgAndFileName[1], "fail");	
		}		
	}	
	
}
