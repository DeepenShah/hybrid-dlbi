package com.IDIOM.Login.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Forget Password_InValid Email ID</p>
 *  <p> <b>Objective:</b> Verify that the application is performing as expected while trying with invalid email id in forget password</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8498.aspx</p>
 *  <p> <b>Module:</b>Login & Forgot Password Functionality</p>
 *  
 * @author Deepen Shah
 * @since 21 Jun 2016
 *
 */
public class LoginPage8498 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyMessageWithInvalidEmail(){		
		
		String strForgotPasswordTitleMsg = property.getProperty("forgotPasswordTittleMsg");
		String strInvalidEmailIdMsg = property.getProperty("invalidEmailIdMsg");
		
		try{			
			
			ln.func_PerformAction("Forget Pwd", "");
			
			if(!rm.webElementSync(ln.passwordResetEmailButton, "visibility"))
				throw new IDIOMException("Failed to land on forgot password page.###NotAForgotPasswordPage");
			
			CustomReporter.log("Reach to forgot password page");
			
			if(!ln.isVisible("forgotpasswordidiomlogo"))
				throw new IDIOMException("IDIOM logo is not visible on Forgot Password page. ###IDIOMLogoNotVisible");
			
			CustomReporter.log("Verified: IDIOM logo is visible");
			
			if(!ln.isVisible("forgotpasswordtitlemsg"))
				throw new IDIOMException("Forgot Password title message is not visible. ###FP-TitleMsgNotVisible");
			
			CustomReporter.log("Verified: title message is visible");
			
			String strTitleMsg = ln.func_ReturnMsg("forgotpasswordtitlemsg");
			if(!strTitleMsg.equalsIgnoreCase(strForgotPasswordTitleMsg))
				throw new IDIOMException("Failed to verify title message. Expected " +strForgotPasswordTitleMsg + 
						" and found " + strTitleMsg +".###FailedToCompareTitleMessage");
			
			CustomReporter.log("Successfully verified title message <i> " + strTitleMsg + "</i>");
			
			if(!ln.isVisible("forgotpasswordemail"))
				throw new IDIOMException("Forgot Password Email text field not visible. ###FP-EmailTxtFieldNotVisible");
			
			CustomReporter.log("Verified: Email id text field is visible");
			
			if(!ln.isVisible("forgotpasswordsendbtn"))
				throw new IDIOMException("Forgot Password 'SEND PASSWORD RESET EMAIL' button is not visible. ###FP-SendPasswordResetEmailBtnNotVisible");
			
			CustomReporter.log("Verified: 'SEND PASSWORD RESET EMAIL' button is visible");
			
			if(!ln.isVisible("returntoidiomlink"))
				throw new IDIOMException("Forgot Password Return to idiom link is not visible. ###FP-ReturnToIdiomLinkNotVisible");
			
			CustomReporter.log("Verified: Return to idiom link is visible");
			
			ln.func_PerformAction("Input Email", "dfdsf");
			CustomReporter.log("Entered invalid email: dfdsf");
			
			ln.func_PerformAction("Password Reset Email", "");
			CustomReporter.log("Clicked on 'SEND PASSWORD RESET EMAIL' button");
			Thread.sleep(2000);
			
			String strErrorMsg = ln.func_ReturnMsg("rederrormessage");
			if(!strErrorMsg.equalsIgnoreCase(strInvalidEmailIdMsg))
				throw new IDIOMException("Failed to verify error message. Expected " +strInvalidEmailIdMsg + 
						" and found " + strErrorMsg +".###FailedToCompareInvalidEmailIdErrorMsg");
			
			CustomReporter.log("Successfully verified error message <i> " + strErrorMsg + "</i>");
			
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
