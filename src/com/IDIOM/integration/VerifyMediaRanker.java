package com.IDIOM.integration;

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
 * <p><b>Test Case Name:</b> Verify Media Ranker – Digital and TV Ranker </p>
 * <p><b>Objective:</b> Verify Media Ranker – Digital and TV Ranker should be accessible without any error</p>
 * <p><b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8993.aspx </p>
 * <p><b>Module:</b> Media Ranker </p>
 * @author Abhishek Deshpande
 * @since 26-April-2016
 * 
 */
public class VerifyMediaRanker extends BaseClass {
	
	@Test
	public void verifyMediaRankerPage(){
		String strMsg = null;
		String strProjectName="";
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strClientName=property.getProperty("clientName");
			strProjectName=property.getProperty("projectNameScenario4");
			String strProjectDescription=property.getProperty("projectDescriptionScenario4");
			
			//****************Test step starts here************************************************
			//Step1: Open URL
			strMsg = "Launch Browser and enter IDIOM url";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ":" + strMsg);
			Login_Page ln = new Login_Page(driver);
			
			//Step2: Login with valid credential
			Thread.sleep(3000);
			ln.func_LoginToIDIOM(strEmailId, strPassword);
			strMsg = "Logged in successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Step3: Select client from drop down
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
			
			//Step4: Click on 'New Project' button
			clientListPage.func_PerformAction("New Project");
			rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Identify the new project overlay in client home page
			clientListPage.findAndSaveProjectWindow(true, "");
			
			//Before filling details, checking 'Audience' tab is exist or not. It should be false.
			if(clientListPage.func_ClientListPageElementExist("audiencetab"))
				throw new IDIOMException("Not able to verify new project window###SmokeScenario4-CreateProjectWindow");
			
			//If audience tab is not found then above validation is passed. User can enter project name and description.
			
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
						
			//Step6: Verify user is able to save project and navigated to audience tab
			if(!(clientListPage.func_ClientListPageElementExist("audiencetab") && clientListPage.func_ClientListPageElementExist("audiencetablabel")))
				throw new IDIOMException("Fails to land on Audience tab. Problem might be in saving project###SmokeScenario4-AudienceTabNotFound");			   
				
			strMsg = "Project " + strProjectName +" is saved successfully and user is navigated to Audience tab";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			bProjectCreate = true;
			Thread.sleep(2000);
			
			//Step7: Launch the project
			clientListPage.func_PerformAction("Launch Project");
			strMsg = "User is able to launch project";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			Thread.sleep(2000);
			
			//Project dash board should be opened. User should see links to navigate to different pages.
			ProjectDashboardPage pd = new ProjectDashboardPage(driver);			
			if(!(pd.isVisible("link",property.getProperty("projectDescription")) && pd.isVisible("link",property.getProperty("successMetrics")) && pd.isVisible("link",property.getProperty("digitalRanker"))))
				throw new IDIOMException("Fails to Identify links in project dashboard. Problem might be in loading project dash board ###SmokeScenario4-FaileCheckDashboard");
			   			    
			strMsg = "Project dashboard page is displayed and verified links to navigate different pages";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//Step8: Click on 'Digital Ranker' link from project dash board
			pd.navigateTo(property.getProperty("digitalRanker"));
			rm.webElementSync("idiomspinningcomplete");
			
			//Step9: Verify that Digital ranker page is opened without any errors
			ArchitectMediaRankerPage mr = new ArchitectMediaRankerPage(driver);
			if(!(mr.func_VerifyElementExist("metricbubbleplot")) && mr.func_VerifyElementExist("mediarankertextinmenu") && mr.func_VerifyElementExist("categorylist"))
				throw new IDIOMException("Fails to land on Digital Ranker page. Problem might be in loading digital ranker page ###SmokeScenario4-FaileCheckDigitalRanker");
			   
			
			strMsg = "Digital ranker page is displayed without any errors";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			String strCurrentURL = driver.getCurrentUrl();
			
			String strChannelName = strCurrentURL.substring(strCurrentURL.lastIndexOf("/")+1);

			if(!strChannelName.equalsIgnoreCase("digital"))
				throw new IDIOMException("Not able to verify Digital channel name in URL ###SmokeScenario4-FailedCheckDigitalInURL");
			
			CustomReporter.log("Verified 'Digital' in the URL");
			
			String strCatName = mr.func_GetValue("categoryheaderpanel0");
			
			if(!strCatName.equalsIgnoreCase("categories"))
				throw new IDIOMException("Failed to verify right side first category name for digital ranker. Found: " + strCatName + " and expected: Categories. ###SmokeScenario4-FailedVerifyCatForDigital");
			
			CustomReporter.log("Verified category name, '"+ strCatName + "', for Digital Ranker");
			
			//Step10: Click on TV Ranker from mega menu
			pageHeader.megaMenuLinksClick(property.getProperty("tvRanker"));
			rm.webElementSync("idiomspinningcomplete");
			rm.webElementSync("pageload");
			
			//Verifying if we landed on appropriate page
			strCurrentURL = driver.getCurrentUrl();
			
			strChannelName = strCurrentURL.substring(strCurrentURL.lastIndexOf("/")+1);

			if(!strChannelName.equalsIgnoreCase("tv"))
				throw new IDIOMException("Not able to verify TV channel name in URL ###SmokeScenario4-FailedCheckTVInURL");
			
			CustomReporter.log("Verified 'TV' in the URL");
			
			if(!(mr.func_VerifyVisibilityOfElement("scatteredplot")))
				throw new IDIOMException("Failed to verify scatteredplot on TV ranker ###SmokeScenario4-NoScatteredPlotForTV");
			
			CustomReporter.log("Verified scattered plot on TV Ranker");
			
			strCatName = mr.func_GetValue("categoryheaderpanel0");
			if(!strCatName.equalsIgnoreCase("dayparts"))
				throw new IDIOMException("Failed to verify right side first category name for tv ranker. Found: " + strCatName + " and expected: Dayparts. ###SmokeScenario4-FailedVerifyCatForTV");
			
			CustomReporter.log("Verified category name, '"+ strCatName + "', for TV Ranker");
			
		} 
		catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Some exception is generated, " + strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("Integration-Scenario4", "fail");
		}
		finally{
			try{
				
				//Step 13: Deleting newly created project
				if(bProjectCreate){
					
					pageHeader.navigateTo("projecthomepage");
					rm.webElementSync(pageHeader.personMenu,"refreshed");
					
					rm.webElementSync(clientListPage.globalProjectWindow, "visibility");
					clientListPage.func_PerformAction("Close Project");
					
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
					Thread.sleep(2000);
					CustomReporter.log("Deleted the project");
				}
				
				
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
	

}
