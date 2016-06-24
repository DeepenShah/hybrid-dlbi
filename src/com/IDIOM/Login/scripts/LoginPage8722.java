package com.IDIOM.Login.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>2129_Forget Password_Clearing of previously conveyed validation message when valid Lion log in email is provided</p>
 *  <p> <b>Objective:</b>Verify clearing of previously conveyed validation message when valid Lion log in email is provided</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8722.aspx</p>
 *  <p> <b>Module:</b>Login & Forgot Password Functionality</p>
 *  
 * @author Deepen Shah
 * @since 21 Jun 2016
 *
 */
public class LoginPage8722 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyClearingValidationMessageAfterProvidingLionLogin(){	
		
		String strNonExistingEmailId = property.getProperty("login8497");
		String strLionEmail =  property.getProperty("lionLogin8722");
		
		try{			
			
			ln.func_PerformAction("Forget Pwd", "");
			
			if(!rm.webElementSync(ln.passwordResetEmailButton, "visibility"))
				throw new IDIOMException("Failed to land on forgot password page.###NotAForgotPasswordPage");
			
			CustomReporter.log("Reach to forgot password page");
			
			ln.func_PerformAction("Input Email", strNonExistingEmailId);
			CustomReporter.log("Fill the non existing email id " + strNonExistingEmailId);
			
			ln.func_PerformAction("Password Reset Email", "");
			CustomReporter.log("Clicked on 'SEND PASSWORD RESET EMAIL' button");
			
			if(!rm.webElementSync(ln.forgotPasswordRedErrorMessage, "visibility"))
				throw new IDIOMException("No validation message appears. ###NoMessage");
			
			CustomReporter.log("Error message appears for wrong email id");
			
			ln.func_PerformAction("Input Email", strLionEmail);
			CustomReporter.log("Entered now Lion email id " + strLionEmail);
			
			ln.func_PerformAction("Password Reset Email", "");
			CustomReporter.log("Clicked on 'SEND PASSWORD RESET EMAIL' button");
			Thread.sleep(3000);
			
			if(ln.forgotPasswordRedErrorMessageList.size() > 1)
				throw new IDIOMException("Message is not cleared after entering valid lion email id. Message count is "+
			ln.forgotPasswordRedErrorMessageList.size() +".###MessageIsNotCleared");
			
			CustomReporter.log("Successfully verified: Message got cleared now");
			
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
