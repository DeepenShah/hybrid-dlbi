package com.IDIOM.Login.scripts;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;
//import com.msat.frameworks.data_driven.generic.reusables.GenericComponents;

import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p><b>Test Case Name</b> Login Page_Verify Login Button is Disabled When Email Field is Empty</p>
<p><b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/7748.aspx</p>
<p><b>Module</b> Login Page</p>
<p><b>Updated By: Date </b>Amrutha Nair | Shailesh Kava, 11th August 2015 | 22 June 2016 </p>
@author Abhishek Deshpande
@since 28th JULY 2015
**********************************************************************/
public class LoginPage7748 extends BaseClass {
	String strMsg;
	boolean bStatus = true;
	
	@Test
	public void test_LoginPage7748() {
		
		try{
			//Reading password value from property file
			String password = property.getProperty("password");
			
			CustomReporter.log("Launch Browser and Enter URL");
			Login_Page ln = new Login_Page(driver);	
			
			CustomReporter.log("Enter only password, email id field should be blank");
			
			if(ln.func_IsLoginButtonEnabled("PWD",password))
				throw new IDIOMException("The login button is NOT disabled on entering only password###7748-loginButtonIsEnabled");
				
			CustomReporter.log("The login button is  disabled on entering only password");
			
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
			rm.captureScreenShot("7748", "fail");
		}
		if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}
	
	}
}