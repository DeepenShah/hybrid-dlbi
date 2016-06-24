package com.IDIOM.architect.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** 
 * <p> <b>Test Case Name:</b> 1081_Media_Ranker_Verify_Click_Of_"X"_Icon_In_Right_Navigation  </p>
 * <p> <b>Objective:</b> Verify the click on "X" icon in Right Navigation Panel </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8411.aspx </p>
 * <p> <b>Module:</b> Media Ranker</p>
 * @author Deepen Shah
 * @since 25 May 2016
 *
 */
public class MediaRanker8411 extends BaseClass {	
	
	@Test
	void verifyClosedCategoryAreaAfterClickingX(){
		String strProjectName="";
		String strMsg="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreated = false;
		String strDeletionDetails="";			
		
		try{
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");			
			String strClientName=property.getProperty("clientName");
			String strMediaRankerPages = property.getProperty("mediaRankerItems");			
			//****************Test step starts here************************************************
			
			//Step1: Launch url
			strMsg = "Launched Browser and Enter URL";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			Login_Page ln = new Login_Page(driver);
			
			//Step2: Login with valid credential
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    strMsg = "Logged in successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Step3: Select client
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
			
			
			//Step4&5: Select/Create project
			if(clientListPage.totalProject()>0){
				strProjectName = clientListPage.getProjectNameById(1);
			}else{
				strProjectName = clientListPage.createNewProject("");
				strDeletionDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
				bProjectCreated = true;
			}			
			
			//Step6: Launch project
			clientListPage.launchProject(strProjectName);
			CustomReporter.log("Launch project " + strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(1000);
			
			//Step7,8,9&10: Navigate to Media Ranker Page
			String[] strChannels = strMediaRankerPages.split(",");
			ArchitectMediaRankerPage mediaRanker = new ArchitectMediaRankerPage(driver);
			
			for(String strChannel:strChannels){
				pageHeader.megaMenuLinksClick(strChannel);											
				if(!mediaRanker.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");			
				
				try{
					//Getting bubble count before
					int iBubbleCountBefore = mediaRanker.func_GetCount("circlesinplot");
					
					//Step11: Click on 'X' to hide navigation
					mediaRanker.func_ClickOnElement("closecatbutton");
					Thread.sleep(2000);
					
					//Verify area is hidden.
					if(!rm.webElementSync(mediaRanker.category, "visibilitynowait"))
						throw new IDIOMException("Category area, navigation area, is still visible.###Digital-NavigationIsVisible");
					
					CustomReporter.log("Successfully verified hidden are for navigation for " + strChannel);
					
					//Getting bubble count after
					int iBubbleCountAfter = mediaRanker.func_GetCount("circlesinplot");
					
					if(iBubbleCountBefore != iBubbleCountAfter)
						throw new IDIOMException("Scattered plot bubble count is not matching. Expected "+
					iBubbleCountBefore + " and found " + iBubbleCountAfter +".###Digital-CountIsNotMatching");
					
					//Opening it back
					mediaRanker.func_ClickOnElement("closecatbutton");
					Thread.sleep(2000);
					
				}catch(IDIOMException e){
					exceptionCode(e);
				}catch(Exception e){
					BaseClass.testCaseStatus = false;
					e.printStackTrace(System.out);
					CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
					rm.captureScreenShot("8328-Exception", "fail");
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
			rm.captureScreenShot("8328-Exception", "fail");
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
			rm.captureScreenShot("8328-Exception", "fail");
		}else{
			rm.captureScreenShot("8328-"+strMsgAndFileName[1], "fail");	
		}		
	}	
}
