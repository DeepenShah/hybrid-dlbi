package com.IDIOM.Analyse.Pathing.scripts;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p><b>Objective</b></p> Verify the pathing wheel is locked when a path is clicked on.
<p><b>Test Case Name</b></p> Pathing_Verify_Path_Lock
<p><b>Test Case ID</b></p> http://qa.digitas.com/SpiraTest/523/TestCase/8057.aspx
<p><b>Module</b></p> Analise_Pathing
@author Shailesh Kava
@since 21 September 2015
**********************************************************************/
public class Pathing8057 extends BaseClass {
	boolean bProjectCreate = false;
	String strDetails;
	@Test
	public void test_Pathing8057() throws Exception{
	//****************Variables declaration and Initialization****************************
		String pathingLink = property.getProperty("pathing");	
		String expectedColor_LockIcon = "rgba(255, 15, 83, 1)";
		String strMsg = null;
		String strProjectName="8057_"+rm.getCurrentDateTime();
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
		
		rm.webElementSync(pathingPage.pathingWheel, "visible");
		
		if(!pathingPage.isVisible("pathing_wheel"))
			throw new IDIOMException("Failed to open pathing page###8057-pathingWheelNotAppear");
		
		CustomReporter.log("Pathing page is appear properly");
		
		//Step 8: Hover over the pathing wheel
		CustomReporter.log("Hover on any segment of wheel chart");
		pathingPage.func_onHover("hoverOnSingleSegment");
		
		Thread.sleep(2000);
		int totalSelectedPaths = pathingPage.func_countOfSelectedPaths();
		pathingPage.func_onHover("hoverOnSingleSegment");
		String percentageInWheelChart = pathingPage.func_returnElementValue("percentage_wheelChart");
		
		if(totalSelectedPaths>=1 && percentageInWheelChart!=null){
			CustomReporter.log("Selected path is highlighted and percentage value is appearing in center of wheel chart" +percentageInWheelChart);
		}else{
			rm.captureScreenShot("8057_missingPercentageVal", "fail");
			CustomReporter.errorLog("Selected path is highlighted and % value is appearing in center of wheel chart" +percentageInWheelChart);
			BaseClass.testCaseStatus = false;
		}
		
		Actions action = new Actions(driver);
		action.moveToElement(pathingPage.unlockIcon).build().perform();
		//Step 9: Click on a path.
		CustomReporter.log("Step 9: selecting any path from wheel chart and verifying behaviour");
		
		if(BaseClass.browserName.equalsIgnoreCase("ie"))
			action.click(pathingPage.pathing_selectPathOnWheel).click(pathingPage.pathing_selectPathOnWheel).build().perform();
		else
			action.click(pathingPage.pathing_selectPathOnWheel).build().perform();
			
		//pathingPage.pathing_selectPathOnWheel.click();
		
		Thread.sleep(4000);
		
		if(pathingPage.func_expectedColors("lockIconColor", expectedColor_LockIcon)){
			CustomReporter.log("Color of lock icon is matched");
			System.out.println("Color of lock icon is matched");
		}else{
			rm.captureScreenShot("8057_lockIconColorIsUnexpected", "fail");
			CustomReporter.errorLog("Color of locked icon does not match");
			System.out.println("Color of locked icon does not match");
			BaseClass.testCaseStatus = false;
		}
		
		Thread.sleep(4000);
		
		ArrayList<String> getBorderColorForSelectedPath = pathingPage.func_CaptureList("pathing_getBGColorForSelectedPathonWheelChart");
		ArrayList<String> getBorderColorForProbabilities = pathingPage.func_CaptureList("pathing_getProbabilityValues");
		
		System.out.println(getBorderColorForSelectedPath.size()+"=="+getBorderColorForProbabilities.size());
		
		if(getBorderColorForSelectedPath.size() == getBorderColorForProbabilities.size()){
			
			if(rm.compareArray(getBorderColorForSelectedPath, getBorderColorForProbabilities)){
				CustomReporter.log("Selected path and probabilities color are matched");
				System.out.println("Selected path and probabilities color are matched");
			}else{
				rm.captureScreenShot("8057_probabilityBGColorNotMatchedWithSelectedSegment", "fail");
				System.out.println("Selected path and probabilities color are are matched");
				CustomReporter.errorLog("Selected path and probabilities color are are matched");
				BaseClass.testCaseStatus = false;
			}
			
		}else{
			rm.captureScreenShot("8057_probabilityCountNotMatch", "fail");
			System.out.println("Selected path and Probabilities count does not match");
			CustomReporter.errorLog("Selected path and Probabilities count does not match");
			BaseClass.testCaseStatus = false;
		}
		
		//Step 10: click on lock icon
		CustomReporter.log("Step 10: Clicking on lock icon and verify path is reset");
		
		pathingPage.func_ClickElement("LockIcon");
		Thread.sleep(3000);
		if(pathingPage.func_ElementExist("percentage_wheelChart") || pathingPage.func_ElementExist("percentage_probability")){
			rm.captureScreenShot("8057_unableToResetPath", "fail");
			System.out.println("All paths are not reset of wheel chart on clicking unloack icon");
			CustomReporter.errorLog("All paths are not reset of wheel chart on clicking unloack icon");
			BaseClass.testCaseStatus = false;
		}else{
			System.out.println("All paths are reset of wheel chart on clicking unloack icon");
			CustomReporter.log("All paths are reset of wheel chart on clicking unloack icon");
		}
		
		//Step 11: Click on a path, once more.
		CustomReporter.log("Step 11: selecting any path again from wheel chart and verifying behaviour");
		if(BaseClass.browserName.equalsIgnoreCase("ie"))
			action.click(pathingPage.pathing_selectPathOnWheel).click(pathingPage.pathing_selectPathOnWheel).build().perform();
		else
			action.click(pathingPage.pathing_selectPathOnWheel).perform();
		//pathingPage.func_ClickElement("pathing_selectPathonWheel");
		
		Thread.sleep(3000);
		if(pathingPage.func_expectedColors("lockIconColor", expectedColor_LockIcon)){
			CustomReporter.log("Color of lock icon is matched");
			System.out.println("Color of lock icon is matched");
		}else{
			System.out.println("Color of locked icon does not match");
			rm.captureScreenShot("8057_lockedIconColorNotMatched1", "fail");
			CustomReporter.errorLog("Color of locked icon does not match");
			BaseClass.testCaseStatus = false;
		}
		
		Thread.sleep(3000);
		getBorderColorForSelectedPath = pathingPage.func_CaptureList("pathing_getBGColorForSelectedPathonWheelChart");
		getBorderColorForProbabilities = pathingPage.func_CaptureList("pathing_getProbabilityValues");
		
		if(getBorderColorForSelectedPath.size() == getBorderColorForProbabilities.size()){
			
			if(rm.compareArray(getBorderColorForSelectedPath, getBorderColorForProbabilities)){
				CustomReporter.log("Selected path and probabilities color are matched");
			}else{
				rm.captureScreenShot("8057_selectedProbabilityBGColorNotMatched1", "fail");
				CustomReporter.errorLog("Selected path and probabilities color are are matched");
				BaseClass.testCaseStatus = false;
			}
			
		}else{
			CustomReporter.errorLog("Selected path and Probabilities count does not match");
			BaseClass.testCaseStatus = false;
		}
		
		//Step 12: Click on another path.
		CustomReporter.log("Step 12: selecting another path again from wheel chart and verifying behaviour");
		
		pathingPage.func_ClickElement("selectAnotherPathOnWheel");
		
		if(pathingPage.func_expectedColors("lockIconColor", expectedColor_LockIcon)){
			CustomReporter.log("Color of lock icon is matched");
		}else{
			rm.captureScreenShot("8057_lockBGColorNotMatched2", "fail");
			CustomReporter.errorLog("Color of locked icon does not match");
			BaseClass.testCaseStatus = false;
		}
		
		getBorderColorForSelectedPath = pathingPage.func_CaptureList("pathing_getBGColorForSelectedPathonWheelChart");
		getBorderColorForProbabilities = pathingPage.func_CaptureList("pathing_getProbabilityValues");
		
		if(getBorderColorForSelectedPath.size() == getBorderColorForProbabilities.size()){
			
			if(rm.compareArray(getBorderColorForSelectedPath, getBorderColorForProbabilities)){
				CustomReporter.log("Selected path and probabilities color are matched");
			}else{
				rm.captureScreenShot("8057_probabilityBGColorNotMatched2", "fail");
				CustomReporter.errorLog("Selected path and probabilities color are are matched");
				BaseClass.testCaseStatus = false;
			}
			
		}else{
			rm.captureScreenShot("8057_probabilityCountNotMatched1", "fail");
			CustomReporter.errorLog("Selected path and Probabilities count does not match");
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
		rm.captureScreenShot("8057", "fail");
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
