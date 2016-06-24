package com.IDIOM.Login.scripts;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p><b>Test Case Name</b> Login page_Verify error on double click on logout</p>
<p><b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/8144.aspx</p>
<p><b>Module</b> Login page</p>
<p><b>Updated By, Date</b> Shailesh Kava, 22-June-2016</p>
@author Amrutha Nair
@since 4th August 2015
**********************************************************************/
public class LoginPage8144 extends BaseClass {
		
	@Test
	public void test_LoginPage8144() throws InterruptedException {
		try{
			 
			loginToSelectClient();
			Thread.sleep(7000);
			
			Actions action = new Actions(driver);
			action.click(pageHeader.personMenu).build().perform();
			Thread.sleep(1000);
			action.doubleClick(pageHeader.logoutLink).build().perform();
			
			String errormessage=ln.func_ReturnMsg("Unknown Error");
			
			CustomReporter.log("Performing double click action on Logout action");

			
			if (!ln.func_ElementExist("Login Button"))
				throw new IDIOMException("Failed to logout from application on double click###8144_failedToLogOut");
			
			CustomReporter.log("The user got logged out on double click");
			
			if(rm.webElementSync(ln.unknownError, "visibility"))
				throw new IDIOMException("The user got logged out on double click and "+errormessage+" is coming at loging page###8144_errorMessage");
			
			CustomReporter.log("No error message found on logout using double click");
			
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
			rm.captureScreenShot("8144", "fail");
		}
		if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}

		}
	}