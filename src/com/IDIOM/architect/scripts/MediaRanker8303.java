package com.IDIOM.architect.scripts;

import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.ArchitectMediaRankerPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b> Media Ranker_ 4.2 a,b,c_Verify X icon behaviour and actions in Right Side Navigation  </p>
 * <p> <b>Objective:</b> Verify X icon behavior and actions in Right Side Navigation of Media Ranker page </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8303.aspx </p>
 * <p> <b>Module:</b> Media Ranker</p>
 * 
 * @author Deepen Shah
 *
 */
public class MediaRanker8303 extends BaseClass{
		
	@Test
	public void verifyScatterPlotBehaviourAfterClickingX(){
		ArchitectMediaRankerPage arMediaRankerPage=null;
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
			arMediaRankerPage = new ArchitectMediaRankerPage(driver);
			
			for(String strChannel:strChannels){
				pageHeader.megaMenuLinksClick(strChannel);
				rm.webElementSync("idiomspinningcomplete");
				if(!arMediaRankerPage.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");
				try{
			
					//Beore Clicking 'X' button getting width of scattered plot
					Dimension beforeDim = arMediaRankerPage.func_GetSizeOfElement("scatteredplot");
					System.out.println("Width before clicking 'X' : " + beforeDim.width);
								
					//Step8: Click on 'X' sign
					rm.webElementSync(arMediaRankerPage.closeCategoryBtn,"clickable");
					arMediaRankerPage.func_ClickOnElement("closecatbutton");
					strMsg = "Clicked on 'X'";
					CustomReporter.log(strMsg);
					System.out.println(getClass().getSimpleName() + ": " + strMsg);
					
					Thread.sleep(2000);
					
					//Step9: Verify menu is hidden or scattered plot become bigger.
					Dimension afterDim = arMediaRankerPage.func_GetSizeOfElement("scatteredplot");
					System.out.println("Width after clicking 'X' : " + afterDim.width);
					
					if(beforeDim.width >= afterDim.width)
						throw new IDIOMException("No change in scatter plot width after closing category panel. Earlier " +
					beforeDim.width +" and now " + afterDim.width + ".###CloseAction-NoChangeInwidth");
					
					CustomReporter.log("Successfully verified width increase after closing category panel");						
					
					//Step11: Clicking '+' icon
					arMediaRankerPage.func_ClickOnElement("closecatbutton");
					strMsg = "Clicked on '+' to open category panel again";
					CustomReporter.log(strMsg);
					System.out.println(getClass().getSimpleName() + ": " + strMsg);
					
					rm.webElementSync(arMediaRankerPage.category, "visibility");
					Thread.sleep(1000);
					
					//Verifying panel should get open.
					Dimension afterOpeningDim = arMediaRankerPage.func_GetSizeOfElement("scatteredplot");
					System.out.println("Width after clicking '+' : " + afterOpeningDim.width);
					
					//Matching width
					if(beforeDim.width != afterOpeningDim.width)
						throw new IDIOMException("Width is not matching with previously open area. Expected " + 
					beforeDim.width +" and found " + afterOpeningDim.width +".###Open-WidthIsNotMatching");
					
					CustomReporter.log("Successfully verified width after opening category");				
				}catch(IDIOMException ie){
					exceptionCode(ie);
				}
				catch(Exception e){
					BaseClass.testCaseStatus = false;
					e.printStackTrace(System.out);
					CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
					rm.captureScreenShot("8303-Exception", "fail");
				}
			}
			
		}catch(IDIOMException ie){
			exceptionCode(ie);
		}catch(Exception e){
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8303-Exception", "fail");
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
			
			}catch(Exception e){
				e.printStackTrace(System.out);
				CustomReporter.errorLog("Some exception is occured, " + e.getMessage() + ", while deleting newly created project '"+strProjectName +"'. Please check the log for more details");
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
			rm.captureScreenShot("8303-Exception", "fail");
		}else{
			rm.captureScreenShot("8303-"+strMsgAndFileName[1], "fail");	
		}		
	}

}
