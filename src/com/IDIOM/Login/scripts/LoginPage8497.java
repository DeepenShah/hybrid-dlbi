package com.IDIOM.Login.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Forget Password_Valid Email ID_Not Existing In Idiom System</p>
 *  <p> <b>Objective:</b>Verify that the application is performing as expected while trying with valid email id which is Not existing in idiom system in forget password section</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8497.aspx</p>
 *  <p> <b>Module:</b>Login & Forgot Password Functionality</p>* 
 *  
 * @author Deepen Shah
 * @since 20 Jun 2016
 *
 */
public class LoginPage8497 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyForgotPasswordWithNonExistingEmail(){	
		
		String strNonExistingEmailId = property.getProperty("login8497");
		String strValidationMsg =  property.getProperty("nonExistingEmailIdMsg");
		
		try{			
			
			ln.func_PerformAction("Forget Pwd", "");
			
			if(!rm.webElementSync(ln.passwordResetEmailButton, "visibility"))
				throw new IDIOMException("Failed to land on forgot password page.###NotAForgotPasswordPage");
			
			CustomReporter.log("Reach to forgot password page");
			
			rm.webElementSync(ln.passwordResetEmailButton, "clickable");
			
			ln.func_PerformAction("Input Email", strNonExistingEmailId);
			CustomReporter.log("Fill the non existing email id " + strNonExistingEmailId);
			
			//Just taking pic
			rm.captureScreenShot("8497-FillValue", "pass");
			
			ln.func_PerformAction("Password Reset Email", "");
			CustomReporter.log("Clicked on 'SEND PASSWORD RESET EMAIL' button");
			
			if(!rm.webElementSync(ln.forgotPasswordRedErrorMessage, "visibility"))
				throw new IDIOMException("No validation message appears. ###NoMessage");
			
			CustomReporter.log("Error message appears. Verifying it now");
			
			String strActualMsg = ln.func_ReturnMsg("rederrormessage");
			
			if(!strActualMsg.equalsIgnoreCase(strValidationMsg))
				throw new IDIOMException("Failed to verify validation message. Expected " + 
			strValidationMsg + " and found " + strActualMsg + ".###FailedToCompareMessage");
			
			CustomReporter.log("Successfully verified the message <i>" + strActualMsg +"</i>");
			
			
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
