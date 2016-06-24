package com.IDIOM.GlobalHeader;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.HelpCenterPage;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.PageHeader;
import com.IDIOM.pages.ProjectDashboardPage;
import com.IDIOM.pages.UserAdminMyAccount_Page;
import com.IDIOM.pages.UserAdmin_UserPermissions_Page;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p><b>Objective</b> Global Header - Verify options displayed under Account and its click.</p>
<p><b>Test Case Name</b> Global Header - Accounts - Verify options displayed under it and its click</p>
<p><b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/8851.aspx</p>
<p><b>Module</b> Global Header</p>
@author Shailesh Kava
@since 20 June 2016
**********************************************************************/
public class GlobalHeader8851 extends BaseClass {
	boolean bProjectCreate = false;
	String strDetails;
	Login_Page lp;
	
	@Test
	public void verifyLinksInMainMenuAndNavigation() throws Exception{
	//****************Variables declaration and Initialization****************************
		String pathingLink = property.getProperty("pathing");	
		String strMsg = null;
		String strProjectName="8851_"+rm.getCurrentDateTime();
		String[] personMenuLinks = property.getProperty("personmenulinks").split(",");
		List<String> listExpectedLinks = new ArrayList<String>();
		listExpectedLinks = Arrays.asList(personMenuLinks);
		
	//****************Test step starts here************************************************	
	try{
		lp = new Login_Page(driver);
		//Step1: Launch browser
		loginToSelectClient();
		CustomReporter.log("Creating new project as no project for this client");
		System.out.println("Creating new project as no project for this client");
		
		Thread.sleep(2000);
		clientListPage.createNewProject(strProjectName);
		
		pageHeader = new PageHeader(driver);
		
		//Getting project and client id to delete through REST service
		strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
		clientListPage.launchProject(strProjectName);
		bProjectCreate = true;
		
		ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
		rm.webElementSync("visibiltiybyxpath", MessageFormat.format(pdp.strLinkContains, pathingLink.trim()));
		Thread.sleep(2000);
		
		ArrayList<String> linksInPersonMenu = (ArrayList<String>) pageHeader.getLinksFromPersonMenu();
		
		if(linksInPersonMenu.size() == 0)
			throw new IDIOMException(("Person menu has no link ###8851_personMenuHasNoLink"));
		
		System.out.println(linksInPersonMenu+"=="+listExpectedLinks);
		
		
		
		ArrayList<String> actualLinksInPersonMenu = new ArrayList<String>();
		for(String linkName:linksInPersonMenu){
			actualLinksInPersonMenu.add(linkName.toLowerCase());
		}
		ArrayList<String> expectedLinksInPersonMenu = new ArrayList<String>();
		for(String expectedLink:listExpectedLinks){
			expectedLinksInPersonMenu.add(expectedLink.toLowerCase());
		}
		
		System.out.println("lower case values "+actualLinksInPersonMenu+"=="+expectedLinksInPersonMenu);
		
		if(!rm.compareArray(actualLinksInPersonMenu, expectedLinksInPersonMenu))
			throw new IDIOMException(("Person menu not contains expected links "+expectedLinksInPersonMenu+" and actual is "+actualLinksInPersonMenu+" ###8851_personMenuHasNoLink"));
		
		CustomReporter.log("Person menu contains expected links "+expectedLinksInPersonMenu+" and actual is "+actualLinksInPersonMenu);
		
		UserAdmin_UserPermissions_Page userAdmin = new UserAdmin_UserPermissions_Page(driver);
		//Verify in admin access page
		pageHeader.performAction("adminaccess");
		rm.webElementSync("pageload");
		rm.webElementSync("jqueryload");
		rm.webElementSync(userAdmin.createNewUser, "visibility");
		
		if(!userAdmin.isVisible("createuserbutton")){
			rm.captureScreenShot("8851_failedToOpenAcminAccess", "fail");
			CustomReporter.errorLog("Failed to open Admin Access page");
			BaseClass.testCaseStatus = false;
		}else{
			CustomReporter.log("Admin access page is open successfully.");
		}
		
		//Verify in my account page
		pageHeader.performAction("myaccount");
		rm.webElementSync("pageload");
		rm.webElementSync("jqueryload");
		UserAdminMyAccount_Page	myAccountPage = new UserAdminMyAccount_Page(driver);
		rm.webElementSync(myAccountPage.changeResentPassButton, "visibility");
		
		if(!myAccountPage.isVisible("changepassword")){
			rm.captureScreenShot("8851_failedToOpenmyaccount", "fail");
			CustomReporter.errorLog("Failed to open myaccount page");
			BaseClass.testCaseStatus = false;
		}else{
			CustomReporter.log("My Account page is open successfully.");
		}
		
		//Verify in Help Page FAQ tab
		pageHeader.performAction("helpcenter");
		rm.webElementSync("pageload");
		rm.webElementSync("jqueryload");
		HelpCenterPage helpPage = new HelpCenterPage(driver);
		
		rm.webElementSync(helpPage.faqTab, "visibility");
		
		if(!helpPage.isVisible("faqtab")){
			rm.captureScreenShot("8851_failedToOpenFAQTab", "fail");
			CustomReporter.errorLog("Failed to open FAQ Tab");
			BaseClass.testCaseStatus = false;
		}else{
			CustomReporter.log("FAQ tab is open successfully.");
		}
		
		//Verify in Help Page Definition tab
		helpPage.definitionTab.click();
		rm.webElementSync("pageload");
		rm.webElementSync("jqueryload");
		rm.webElementSync(helpPage.faqTab, "visibility");
		if(!helpPage.isVisible("faqtab")){
			rm.captureScreenShot("8851_failedToOpenDefinitionTab", "fail");
			CustomReporter.errorLog("Failed to open Definition Tab");
			BaseClass.testCaseStatus = false;
		}else{
			CustomReporter.log("Definition page is open successfully.");
		}
		
		//Logout from application
		pageHeader.performAction("logout");
		rm.webElementSync(lp.forgetPwd, "visibility");
		
		if(!lp.forgetPwd.isDisplayed()){
			rm.captureScreenShot("8851_failedToNavigateToLogout", "fail");
			CustomReporter.errorLog("Failed to navigate to logout");
		}else{
			CustomReporter.log("User is logged out successfully.");
		}
		
		lp.forgetPwd.click();
		rm.webElementSync(lp.returnToIdiomLink, "visibility");
		
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
		rm.captureScreenShot("8913", "fail");
	}finally{
		try{
			lp.returnToIdiomLink.click();
			Thread.sleep(2000);
			lp.func_LoginToIDIOM(property.getProperty("SuperAdminUser"),property.getProperty("SuperAdminpassword"));
			
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
