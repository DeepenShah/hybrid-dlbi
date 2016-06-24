package com.IDIOM.GlobalHeader;

import java.text.MessageFormat;
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
<p><b>Objective</b> Verify that in header project link should not available on few pages.</p>
<p><b>Test Case Name</b> 2377: Global header: verify that project link in header should not be available in FAQ, Help, Admin Access,My Account Page, forgot password page and change password page</p>
<p><b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/8913.aspx</p>
<p><b>Module</b> Global Header</p>
@author Shailesh Kava
@since 17 June 2016
**********************************************************************/
public class GlobalHeader8913 extends BaseClass {
	boolean bProjectCreate = false;
	String strDetails;
	Login_Page lp;
	@Test
	public void verifyClientLogoInVisibility() throws Exception{
	//****************Variables declaration and Initialization****************************
		String pathingLink = property.getProperty("pathing");	
		String strMsg = null;
		String strProjectName="8907_"+rm.getCurrentDateTime();
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
		
		UserAdmin_UserPermissions_Page userAdmin = new UserAdmin_UserPermissions_Page(driver);
		//Verify in admin access page
		pageHeader.performAction("adminaccess");
		rm.webElementSync("pageload");
		rm.webElementSync("jqueryload");
		rm.webElementSync(userAdmin.createNewUser, "visibility");
		
		if(!userAdmin.isVisible("createuserbutton")){
			rm.captureScreenShot("8913_failedToOpenAcminAccess", "fail");
			CustomReporter.errorLog("Failed to open Admin Access page");
			BaseClass.testCaseStatus = false;
		}else{
			CustomReporter.log("Admin access page is open successfully.");
			
			if(!pageHeader.isVisible("client_logo")){
				CustomReporter.log("Client logo is not visible in Admin Access page");
			}else{
				rm.captureScreenShot("8913_adminAccessClientLogoVisible", "fail");
				CustomReporter.errorLog("Client logo is visible inAdmin Access page");
				BaseClass.testCaseStatus = false;
			}
		}
		
		//Verify in my account page
		pageHeader.performAction("myaccount");
		rm.webElementSync("pageload");
		rm.webElementSync("jqueryload");
		UserAdminMyAccount_Page	myAccountPage = new UserAdminMyAccount_Page(driver);
		rm.webElementSync(myAccountPage.changeResentPassButton, "visibility");
		
		if(!myAccountPage.isVisible("changepassword")){
			rm.captureScreenShot("8913_failedToOpenmyaccount", "fail");
			CustomReporter.errorLog("Failed to open myaccount page");
			BaseClass.testCaseStatus = false;
		}else{
			CustomReporter.log("My Account page is open successfully.");
			
			if(!pageHeader.isVisible("client_logo")){
				CustomReporter.log("Client logo is not visible in My Account page");
			}else{
				rm.captureScreenShot("8913_myAccountClientLogoVisible", "fail");
				CustomReporter.errorLog("Client logo is visible in My Account page");
				BaseClass.testCaseStatus = false;
			}
		}
		
		//Verify in Help Page FAQ tab
		pageHeader.performAction("helpcenter");
		rm.webElementSync("pageload");
		rm.webElementSync("jqueryload");
		HelpCenterPage helpPage = new HelpCenterPage(driver);
		
		rm.webElementSync(helpPage.faqTab, "visibility");
		
		if(!helpPage.isVisible("faqtab")){
			rm.captureScreenShot("8913_failedToOpenFAQTab", "fail");
			CustomReporter.errorLog("Failed to open FAQ Tab");
			BaseClass.testCaseStatus = false;
		}else{
			CustomReporter.log("FAQ tab is open successfully.");
			
			if(!pageHeader.isVisible("client_logo")){
				CustomReporter.log("Client logo is not visible in FAQ tab of help center");
			}else{
				rm.captureScreenShot("8913_FAQClientLogoVisible", "fail");
				CustomReporter.errorLog("Client logo is visible in FAQ tab of help center");
				BaseClass.testCaseStatus = false;
			}
		}
		
		//Verify in Help Page Definition tab
		helpPage.definitionTab.click();
		rm.webElementSync("pageload");
		rm.webElementSync("jqueryload");
		rm.webElementSync(helpPage.faqTab, "visibility");
		if(!helpPage.isVisible("faqtab")){
			rm.captureScreenShot("8913_failedToOpenDefinitionTab", "fail");
			CustomReporter.errorLog("Failed to open Definition Tab");
			BaseClass.testCaseStatus = false;
		}else{
			CustomReporter.log("Definition page is open successfully.");
			
			if(!pageHeader.isVisible("client_logo")){
				CustomReporter.log("Client logo is not visible in Definition tab of help center");
			}else{
				rm.captureScreenShot("8913_DefinitionClientLogoVisible", "fail");
				CustomReporter.errorLog("Client logo is visible in Definition tab of help center");
				BaseClass.testCaseStatus = false;
			}
		}
		
		//Logout from application
		pageHeader.performAction("logout");
		rm.webElementSync(lp.forgetPwd, "visibility");
		
		lp.forgetPwd.click();
		rm.webElementSync(lp.returnToIdiomLink, "visibility");
		
		if(!pageHeader.isVisible("client_logo")){
			CustomReporter.log("Client logo is not visible in Forgot password page");
		}else{
			rm.captureScreenShot("8913_forgotPassClientLogoVisible", "fail");
			CustomReporter.errorLog("Client logo is visible in Forgot password page");
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
