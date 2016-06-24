package com.IDIOM.architect.scripts;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
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
 * <p> <b>Test Case Name:</b> 1065_Media_Ranker_Verify_When_Both_ShowAudience_And_Right_Panel_Are_Open  </p>
 * <p> <b>Objective:</b> Verify when both Show Audience slide bar and Right panel are open </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8406.aspx </p>
 * <p> <b>Module:</b> Media Ranker</p>
 * @author Deepen Shah
 * @since 25 May 2016
 *
 */
public class MediaRanker8406 extends BaseClass {	
	
	@Test
	void verifyCategoryAndShowAudienceIsNotOverlapping(){
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
			
			//Step7,8,9,10&11: Navigate to Media Ranker Page
			String[] strChannels = strMediaRankerPages.split(",");
			ArchitectMediaRankerPage mediaRanker = new ArchitectMediaRankerPage(driver);
			
			for(String strChannel:strChannels){
				pageHeader.megaMenuLinksClick(strChannel);
				rm.webElementSync("idiomspinningcomplete");
				if(!mediaRanker.func_VerifyVisibilityOfElement("slowtvranker"))
					   throw new IDIOMException("Failed to open channel###MissingScatterPlot");
				
				CustomReporter.log("Navigated to "+ strChannel +" page");			
				
				try{
					
					//Step12: Click to show 'Show Audience'
					rm.webElementSync(mediaRanker.showAudienceButton, "clickable");
					mediaRanker.func_ClickOnElement("showaudience");
					Thread.sleep(1000);
					
					Point p1 = mediaRanker.category.getLocation();
					Dimension rcD = mediaRanker.category.getSize();
					
					System.out.println("Category x:" + p1.x + " y:" + p1.y);
					System.out.println("Category hieght:" + rcD.height + " width:" + rcD.width);
					
					Point p2 = new Point(p1.x+rcD.width,p1.y+rcD.height);					
					
					Point p3 = mediaRanker.showAudiencePane.getLocation();
					rcD = mediaRanker.showAudiencePane.getSize();
					
					System.out.println("Show Audience x:" + p3.x + " y:" + p3.y);
					System.out.println("Show Audience hieght:" + rcD.height + " width:" + rcD.width);
					
					Point p4 = new Point(p3.x+rcD.width,p3.y+rcD.height);					
					
					//Checking by math
					if(!(p2.y < p3.y || p1.y > p4.y || p2.x < p3.x || p1.x > p4.x))
						throw new IDIOMException("Category (Dataset) panel is overlapping to showaudience panel.###PanelsAreOverlapping");
					
					CustomReporter.log("By math, Category and Show Audience panels are not overlapping for " + strChannel);					
					
					//Checking if element is not overlapping
					mediaRanker.func_ClickCategoryByNumber(1);
					
					CustomReporter.log("By HTML, Category and Show Audience panels are not overlapping for " + strChannel);				
					
					
				}catch(IDIOMException e){
					exceptionCode(e);
				}catch(Exception e){
					BaseClass.testCaseStatus = false;
					e.printStackTrace(System.out);
					CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
					rm.captureScreenShot("8406-Exception", "fail");
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
			rm.captureScreenShot("8406-Exception", "fail");
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
			rm.captureScreenShot("8406-Exception", "fail");
		}else{
			rm.captureScreenShot("8406-"+strMsgAndFileName[1], "fail");	
		}		
	}	
}
