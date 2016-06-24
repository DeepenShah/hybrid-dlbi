package com.IDIOM.Analyse.Pathing.scripts;

import java.text.MessageFormat;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/******************************************************************************
<p><b>Assumption</b> All the behaviors have different count of segments in wheel chart</p>
<p><b>Objective</b> Verify the path dataset's updated when a behavior filter's selected.</p>  
<p><b>Test Case Name</b> *Pathing_Verify_Behaviors_filters</p>
<p><b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/8103.aspx</p>
<p><b>Module</b> Analise_Pathing</p>
@author Shailesh Kava
@since 21 September 2015
*******************************************************************************/
public class Pathing8103 extends BaseClass {
	boolean bProjectCreate = false;
	String strDetails;
	@Test
	public void test_Pathing8103() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String pathingLink = property.getProperty("pathing");	
		String strMsg = null;
		String strProjectName="8103_"+rm.getCurrentDateTime();
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
			throw new IDIOMException("Failed to open pathing page###8103-pathingWheelNotAppear");
		
		CustomReporter.log("Pathing page is appear properly");
		
		Analyse_Pathing_Page an = new Analyse_Pathing_Page(driver);
		
		//Step 8 - 9: Click on the behavior filters drop-down list and select a filer.
		if(!an.compareImageOfPathingWheelOfAvailableBehaviour())
			throw new IDIOMException("Same chart appearing for different behaviors###8103-sameChartAppearingForDifferentBehaviors");
		
		CustomReporter.log("Different behaviors having unique charts");
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
		rm.captureScreenShot("8103", "fail");
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