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
<p><b>Objective</b></p>  Verify the pathing wheel pre-post toggle loads the corresponding categories data set 
			leading (pre) to or following a behavior (post).
<p><b>Test Case Name</b></p> Pathing_Verify_Pre_Post_Toggle
<p><b>Test Case ID</b></p> http://qa.digitas.com/SpiraTest/523/TestCase/8059.aspx
<p><b>Module</b></p> Analise_Pathing
@author Shailesh Kava
@since 23 September 2015
**********************************************************************/
public class Pathing8059 extends BaseClass {
	boolean bProjectCreate = false;
	String strDetails;	
	@Test
	public void test_Pathing8059() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String pathingLink = property.getProperty("pathing");	
		String strMsg = null;
		String strProjectName="8059_"+rm.getCurrentDateTime();
		
	//****************Test step starts here************************************************	
	try{	
		//Step1: Launch browser
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
			throw new IDIOMException("Failed to open pathing page###8057-pathingWheelNotAppear");
		
		CustomReporter.log("Pathing page is appear properly");
		
		Analyse_Pathing_Page an = new Analyse_Pathing_Page(driver);
		
		CustomReporter.log("Step 8: selecting any path from wheel chart and verifying behaviour");
		Thread.sleep(2000);
		an.pathing_selectPathOnWheel.click();
		
		Thread.sleep(4000);
		
		if(an.func_ElementExist("lockIcon")){
			CustomReporter.log("Lock icon is appearing");
		}else{
			CustomReporter.errorLog("Lock icon is not appearing");
			BaseClass.testCaseStatus = false;
		}
		
		Thread.sleep(4000);
		
		if(!an.func_pathingRightSideVerifyBGColor_Probabilities("Profile_rightSideVerifySameColorForSameCategory" 
			)){
			CustomReporter.errorLog("The right side categories list does not match the current path.");
			BaseClass.testCaseStatus=false;
		}else{
			CustomReporter.log("The right side categories list match the current path.");
		}
		
		if(an.func_ElementExist("downArrow")){
			CustomReporter.log("An arrow at the edge of the circle point inwards from he path.");
		}else{
			CustomReporter.errorLog("An arrow at the edge of the circle does not point inwards from he path.");
			BaseClass.testCaseStatus = false;
		}
		
		if(an.func_returnElementValue("prePost_toggleVal").contentEquals("Pre")){
			CustomReporter.log("The pre-post toggle is set to Pre");
		}else{
			CustomReporter.errorLog("The pre-post toggle is not set to Pre");
			BaseClass.testCaseStatus = false;
		}
		
		//get number of segments for pre path
		Integer pre_NumberofSegment = an.func_countOfSelectedPaths();
		
		//Step 9: Click on pre-post toggle 
		CustomReporter.log("Step 9: Click on the pre-post toggle.");
		
		an.func_ClickElement("prePost_toggle");
		Thread.sleep(5000);
		
		if(an.func_returnElementValue("prePost_toggleVal").contentEquals("Post")){
			CustomReporter.log("The pre-post toggle is set to Post");
		}else{
			rm.captureScreenShot("8059_preNotSetToPost", "fail");
			CustomReporter.errorLog("The pre-post toggle is not set to Post");
			BaseClass.testCaseStatus = false;
		}
		
		if(an.func_ElementExist("upArrow")){
			CustomReporter.log("The path arrow point outwards now");
		}else{
			rm.captureScreenShot("8059_pathArrowNotMoveOutWords", "fail");
			CustomReporter.errorLog("The path arrow does not point outwards");
			BaseClass.testCaseStatus = false;
		}
		
		//get number of segments for post path
		Integer post_NumberofSegment = an.func_countOfSelectedPaths();
		
		
		if(post_NumberofSegment.equals(pre_NumberofSegment)){
			rm.captureScreenShot("8059_prePosthaveSameSagments", "fail");
			CustomReporter.errorLog("Pre-Post actions wheel charts not having different segments/paths");
			BaseClass.testCaseStatus = false;
		}else{
			CustomReporter.log("Pre-Post actions wheel charts having different segments/paths");
		}
		
		//step 10: Click on the pre-post toggle once more.
		CustomReporter.log("step 10: Click on the pre-post toggle once more.");
		Thread.sleep(5000);
		an.func_ClickElement("prePost_toggle");
		Thread.sleep(5000);
		
		if(an.func_returnElementValue("prePost_toggleVal").contentEquals("Pre")){
			CustomReporter.log("The pre-post toggle is set to Pre");
		}else{
			rm.captureScreenShot("8059_postNotSetToPre", "fail");
			CustomReporter.errorLog("The pre-post toggle is not set to Pre");
			BaseClass.testCaseStatus = false;
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
		rm.captureScreenShot("8059", "fail");
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