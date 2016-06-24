package com.IDIOM.Analyse.Pathing.scripts;

import java.text.MessageFormat;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p><b>Objective</b> Verifying pathing probabilities name and border color if same category found in probability list</p>
<p><b>Test Case Name</b> Pathing_Verify_Navigation_to_Pathing</p>
<p><b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/8022.aspx</p>
<p><b>Module</b> Analise_Pathing</p>
@author Shailesh Kava
@since 15 September 2015
**********************************************************************/

public class Pathing8105 extends BaseClass {
	boolean bProjectCreate = false;
	String strDetails;
	
	@Test
	public void test_Pathing8105() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String pathingLink = property.getProperty("pathing");	
		String strMsg = null;
		String strProjectName="8105_"+rm.getCurrentDateTime();
		
	//****************Test step starts here************************************************	
	try{
			//Step1: Launch Browser
			loginToSelectClient();
			CustomReporter.log("Creating new project as no project for this client");
			System.out.println("Creating new project as no project for this client");
			clientListPage.createNewProject(strProjectName);
			
			//Getting project and client id to delete through REST service
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			clientListPage.launchProject(strProjectName);
			bProjectCreate = true;
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, pathingLink.trim()));
			Thread.sleep(2000);
			
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			CustomReporter.log("Navigating to Pathing page");
			System.out.println("Navigating to Pathing page");
			
			if(browserName.equalsIgnoreCase("ie")){
				System.out.println("clicking through action");
				pdp.navigateToByActionClass(pathingLink.trim());
			}else{
				System.out.println("clicking through link");
				pdp.navigateTo(pathingLink.trim());
			}
			
			Analyse_Pathing_Page pathingPage = new Analyse_Pathing_Page(driver);
			
			if(rm.webElementSync(pageHeader.loadingSpinner, "visibility"))
			     rm.webElementSync("idiomspinningcomplete");
			
			if(!pathingPage.isVisible("pathing_wheel"))
				throw new IDIOMException("Failed to open pathing page###8105-pathingWheelNotAppear");
			
			CustomReporter.log("Pathing page is appear properly");
			
			//Step 8
			CustomReporter.log("Step 8: Click on the different wheel paths");
			pathingPage.func_ClickElement("pathing_selectPathonWheel");
			
			Thread.sleep(4000);
			
			if(!pathingPage.func_pathingRightSideVerifyBGColor_Probabilities("Profile_rightSideVerifySameColorForSameCategory"))
				throw new IDIOMException("Same category should have same BG color###8105-differentBGColorForSelectedSegmentAndProbabilities");
				
			CustomReporter.log("Step 8: Same category has same color");
			System.out.println("Same category has same color");
			
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
			rm.captureScreenShot("8105", "fail");
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
	}}
