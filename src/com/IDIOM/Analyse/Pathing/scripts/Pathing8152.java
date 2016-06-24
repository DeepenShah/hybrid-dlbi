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
<p><b>Objective</b> Verify the pathing wheel pre-post toggle loads the corresponding categories data set leading (pre) to or following a behavior (post).</p>
<p><b>Test Case Name</b> Pathing_Verify_Lock_Unlock_Padlock</p>
<p><b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/8152.aspx</p>
<p><b>Module</b> Analise_Pathing</p>
@author Shailesh Kava
@since 23 September 2015
**********************************************************************/
public class Pathing8152 extends BaseClass {
	
	boolean bProjectCreate = false;
	String strDetails;
	
	@Test
	public void test_Pathing8152() throws Exception{
	//****************Variables declaration and Initialization****************************
		String pathingLink = property.getProperty("pathing");	
		String strMsg = null;
		String strProjectName="8152_"+rm.getCurrentDateTime();
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
			throw new IDIOMException("Failed to open pathing page###8152-pathingWheelNotAppear");
		
		CustomReporter.log("Pathing page is appear properly");
			
			//Step 8: Click on a path.
			CustomReporter.log("Step 8: selecting any path from wheel chart and verifying behaviour");
			pathingPage.func_ClickElement("pathing_selectPathonWheel");
			
			Thread.sleep(4000);
			
			if(pathingPage.func_ElementExist("lockIcon")){
				CustomReporter.log("Lock icon is appearing");
			}else{
				rm.captureScreenShot("8152_missingLockIcon", "fail");
				CustomReporter.errorLog("Lock icon is not appearing");
				BaseClass.testCaseStatus = false;
			}
			
			Thread.sleep(4000);
			
			ArrayList<String> getBorderColorForSelectedPath = pathingPage.func_CaptureList("pathing_getBGColorForSelectedPathonWheelChart");
			ArrayList<String> getBorderColorForProbabilities = pathingPage.func_CaptureList("pathing_getProbabilityValues");
			
			if(getBorderColorForSelectedPath.size() == getBorderColorForProbabilities.size()){
				
				if(rm.compareArray(getBorderColorForSelectedPath, getBorderColorForProbabilities)){
					CustomReporter.log("Selected path and probabilities color are matched");
				}else{
					rm.captureScreenShot("8152_selectedPathAndProbDiffColor", "fail");
					CustomReporter.errorLog("Selected path and probabilities color are not matched");
					BaseClass.testCaseStatus = false;
				}
				
			}else{
				rm.captureScreenShot("8152_selectedPathAndProbHaveDiffCount", "fail");
				CustomReporter.errorLog("Selected path and Probabilities count does not match");
				BaseClass.testCaseStatus = false;
			}
			
			if(pathingPage.func_ElementExist("downArrow")){
				CustomReporter.log("An arrow at the edge of the circle point inwards from he path.");
			}else{
				rm.captureScreenShot("8152_arrowShouldPointInwards", "fail");
				CustomReporter.errorLog("An arrow at the edge of the circle does not point inwards from he path.");
				BaseClass.testCaseStatus = false;
			}
			
			if(pathingPage.func_ElementExist("pre_toggle")){
				CustomReporter.log("The pre-post toggle is set to pre");
			}else{
				rm.captureScreenShot("8152_toggleNotSetToPre", "fail");
				CustomReporter.errorLog("The pre-post toggle is not set to pre");
				BaseClass.testCaseStatus = false;
			}
			
			//Step 9: Click on Padlock . 
			CustomReporter.log("Step 9: Clicking on lock icon and verify path is reset");
			
			pathingPage.func_ClickElement("LockIcon");
			Thread.sleep(3000);
			
			if(!pathingPage.func_ElementExist("percentage_wheelChart") || !pathingPage.func_ElementExist("percentage_probability") 
					|| pathingPage.func_ElementExist("unlockIcon")){
				CustomReporter.log("Appearing whole chart and not appearing % inside the circle");
				
			}else{
				rm.captureScreenShot("8152_failToResetChart", "fail");
				CustomReporter.errorLog("Failed to reset whole chart or % is appearing inside the circle");
				BaseClass.testCaseStatus = false;
			}
			
			//step 10: Double click on a path and verify same color for highlighted segments
			
			Actions action = new Actions(driver);
			
			int i=0;
			while(pathingPage.list_probabilities.size()!=1 || i==10){
				System.out.println("Doublec click ["+i+"]");
				action.doubleClick(pathingPage.doubleClickElement).build().perform();
				i++;
			}
			
			System.out.println("double clicked performed");
			Thread.sleep(4000);
			
			//getting color from right side {probabilities}
			ArrayList<String> expectedColors = pathingPage.func_CaptureList("pathing_getProbabilityValues");
			System.out.println("Expected colors ["+expectedColors+"]");
			if(expectedColors.size() == 1){
				
				String color = expectedColors.get(0);
				
				if(pathingPage.func_expectedColors("selectedpathbydoubleclick", color)){
					CustomReporter.log("Selected segments have same color");
					System.out.println("Selected segments have same color");
				}else{
					rm.captureScreenShot("8152_selectedSagmentHaveDiffColor", "fail");
					System.out.println("Selected segments do not have same color");
					CustomReporter.errorLog("Selected segments do not have same color");
					BaseClass.testCaseStatus = false;
				}
			}else{
				rm.captureScreenShot("8152_failedToPerformDoubleClick", "fail");
				CustomReporter.errorLog("More than one probabilitites are available on double clicking segment");
				BaseClass.testCaseStatus = false;
			}
			
			//Step 11: again clicking on padlock
			
			CustomReporter.log("Step 11: Again clicking on lock icon and verify path is reset");
			
			pathingPage.func_ClickElement("LockIcon");
			Thread.sleep(3000);
			
			if(!pathingPage.func_ElementExist("percentage_wheelChart") || !pathingPage.func_ElementExist("percentage_probability") 
					|| pathingPage.func_ElementExist("unlockIcon")){
				CustomReporter.log("Appearing whole chart and not appearing % inside the circle");
				
			}else{
				rm.captureScreenShot("8152_failToResetChart", "fail");
				CustomReporter.errorLog("Failed to reset whole chart or % is appearing inside the circle");
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
			rm.captureScreenShot("8152", "fail");
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
		