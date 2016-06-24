package com.IDIOM.Login.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>1082_ForgetPassword_No Input_Message</p>
 *  <p> <b>Objective:</b>Verify that a valid message is coming when we are clicking on 'Send Password Reset Mail' button without providing any data</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8493.aspx</p>
 *  <p> <b>Module:</b>Login & Forgot Password Functionality</p>
 *  
 * @author Deepen Shah
 * @since 22 Jun 2016
 *
 */
public class LoginPage8493 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyErrorMessageWithoutEmailIdForForgotPassword(){		
		
		try{			
			//Step1: Clicked forgot password
			ln.func_PerformAction("Forget Pwd", "");
			
			if(!rm.webElementSync(ln.passwordResetEmailButton, "visibility"))
				throw new IDIOMException("Failed to land on forgot password page.###NotAForgotPasswordPage");
			
			CustomReporter.log("Reach to forgot password page");
			
			//Step2: Click 'SEND PASSWORD RESET EMAIL' button
			ln.func_PerformAction("Password Reset Email", "");
			CustomReporter.log("Clicked on 'SEND PASSWORD RESET EMAIL' button without providing email id");
			
			Thread.sleep(3000);
			
			//Step3: Verifying error message
			if(ln.forgotPasswordRedErrorMessageList.size() == 0)
				throw new IDIOMException("No error message found. ###NoErrorMsg");
			
			CustomReporter.log("Successfully verified error message for no input data");
			
		}catch(Exception e){
			exceptionCode(e,strClassName);
		}
		
		if(!BaseClass.testCaseStatus){
			  CustomReporter.errorLog("Test case failed");
			  Assert.assertTrue(false);
		  }else{
			  CustomReporter.log("Test case passed");
		  }	
	}
}
