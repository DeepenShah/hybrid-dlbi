package com.IDIOM.ClientHomePage;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p>	<b>Test Case Name:</b> Manage Projects 2.b.iii : Create a new audience. </p>
<p>	<b>Objective:</b> To verify create new audience functionality. </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8852.aspx </p>
<p>	<b>Module:</b> Client Home Page </p>
@author: Shailesh Kava
@since: 10-June-2016
**********************************************************************/
public class ClientHomePage8852 extends BaseClass {
	
	Analyse_Pathing_Page pathingPage;
	String PathingLink;
	String strDetails;
	String fileLocation;
	Actions action;
	String exportdatafilename;
	
	@Test
	public void clientHomePage_verifyHoveEffectOnCreateNewAudience(){
		
		PathingLink = property.getProperty("pathing");
		exportdatafilename = property.getProperty("pathingexportdatafilename"); 
				
		String strMsg = null;
		String strProjectName = "8852_"+rm.getCurrentDateTime();
		String strAudName = "8852Aud_"+rm.getCurrentDateTime();
		boolean bProjectCreate = false;
		
		pathingPage = new Analyse_Pathing_Page(driver);
		fileLocation = System.getProperty("user.dir");
		action = new Actions(driver);
		
		try{
			loginToSelectClient();
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			//Step 3: Create/Select project and launch the same 
			CustomReporter.log("Creating new project as no project for this client");
			System.out.println("Creating new project as no project for this client");
			clientListPage.createNewProject(strProjectName);
			
			//Edit the same project
			Thread.sleep(2000);
			clientListPage.performActionOnProject("edit", strProjectName);
			Thread.sleep(3000);
			//Clicking on Audience tab
			clientListPage.func_PerformAction("Audience Tab");
			Thread.sleep(3000);
			
			//Hovering on create new audience link
			if(!clientListPage.verifyMouseHoveEffectOnAddAudience()){
				rm.captureScreenShot("8852MissingHoverEffect", "fail");
				CustomReporter.errorLog("Hover effect is missing on +New Audience link, screenshot '8852MissingHoverEffect.png'");
				System.out.println("Hover effect is missing on +New Audience link, screenshot '8852MissingHoverEffect.png'");
				BaseClass.testCaseStatus = false;
			}else{
				System.out.println("Hover effect is present on +New Audience link");
				CustomReporter.log("Hover effect is present on +New Audience link");
			}
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			//Adding new audience in this project
			clientListPage.createNewAudience(strAudName);
			//Launch the project
			clientListPage.func_PerformAction("Launch Project");
			bProjectCreate = true;
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, PathingLink.trim()));
			Thread.sleep(7000);
			
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			pageHeader.performAction("audiencedropdown");
			Thread.sleep(1000);
			List<String> getAudienceListFromHeaderDropDown = new ArrayList<>(); 
			getAudienceListFromHeaderDropDown=pageHeader.returnAudiencesInDropDown();
			System.out.println(getAudienceListFromHeaderDropDown);
			
			for(int i=0; i<getAudienceListFromHeaderDropDown.size(); i++){
				if(getAudienceListFromHeaderDropDown.get(i).trim().equalsIgnoreCase(strAudName)){
					System.out.println("Audience is created successfully");
					CustomReporter.log("Audience is created successfully");
					break;
				}
				
				if(i==getAudienceListFromHeaderDropDown.size()-1){
					if(!getAudienceListFromHeaderDropDown.get(i).trim().equalsIgnoreCase(strAudName)){
						rm.captureScreenShot("8852_failedToCreateAudience", "fail");
						System.out.println("Failed to create audience as it is not appearing drop down, screenshot 'failedToCreateAudience.png'");
						CustomReporter.errorLog("Failed to create audience as it is not appearing drop down, screenshot 'failedToCreateAudience.png'");
						BaseClass.testCaseStatus = false;
					}
				}
			}
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("Some exception is generated, " + strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8852", "fail");
		}finally{
			try{
				
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
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
		//****************Test step ends here************************************************
		
		if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}
	}
}