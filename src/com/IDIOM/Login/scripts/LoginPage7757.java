package com.IDIOM.Login.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;
/********************************************************************
<p><b>Test Case Name</b> Login Page_Verify Login Functionality When Email and Password Fields are Empty</p>
<p><b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/7757.aspx</p>
<p><b>Module</b> Login page</p>
<p><b>Updated By, Date</b> Shailesh Kava, 22-June-2016</p>
@author  Amrutha Nair
@since 31st JULY 2015
**********************************************************************/
public class LoginPage7757 extends BaseClass {
		
	@Test
	public void test_LoginPage7757() {
		
		try{
			
			CustomReporter.log("Launch Browser and Enter URL");
			Login_Page ln = new Login_Page(driver);	
			
			CustomReporter.log("Donot enter domain email id and password");			
			if (ln.func_IsLoginButtonEnabled("NoValue","Novalue"))
				throw new IDIOMException("Login button should disable on blank email and password###7757_loginButtonIsEnable");
			
			CustomReporter.log("Login button is disable");
			
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
			rm.captureScreenShot("7752", "fail");
		}
		if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}

		}
	}