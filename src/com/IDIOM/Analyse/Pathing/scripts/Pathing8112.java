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
<p><b>Objective</b> Verify the path's subcategories look and feel and that their percentages add to 100% their respective category.</p>
<p><b>Test Case Name</b> 924_Pathing_Verify_Subcategories</p>
<p><b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/8112.aspx</p>
<p><b>Module</b> Analise_Pathing</p>
@author Shailesh Kava
@since 24 September 2015
**********************************************************************/
public class Pathing8112 extends BaseClass {
	boolean bProjectCreate = false;
	String strDetails;
	
	@Test
	public void test_Pathing8112() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String pathingLink = property.getProperty("pathing");	
		String strMsg = null;
		String strProjectName="8112_"+rm.getCurrentDateTime();
		
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
				throw new IDIOMException("Failed to open pathing page###8112-pathingWheelNotAppear");
			
			CustomReporter.log("Pathing page is appear properly");
			
			//Step 7: Click on a path
			CustomReporter.log("Step 7: selecting any path from wheel chart and verifying behaviour");
			pathingPage.func_ClickElement("pathing_selectPathonWheel");
			
			Thread.sleep(4000);
			
			//Verifying sum of % value of sub category
			
			if(!pathingPage.func_verifySumOfSubCategory())
				throw new IDIOMException("Sum of probability for selected wheel segment is not 100% ###8112-pathingPercentageNot100");
			
			CustomReporter.log("Sum of probability for selected wheel segment is 100%");
			Thread.sleep(3000);
			
			CustomReporter.log("Step 8: Click on a category.");
			
			//verifying bg color of category should match with bg color of sub category
			if(!pathingPage.func_compareBGColorCategoryWithSubcategories())
				throw new IDIOMException("BG color for selected segment in pathing wheel and Probability is not matched% ###8112-BGColorNotMatched");
				
			CustomReporter.log("BG color for selected segment in pathing wheel and Probability is matched");
			
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
			rm.captureScreenShot("8112", "fail");
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