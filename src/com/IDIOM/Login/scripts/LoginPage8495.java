package com.IDIOM.Login.scripts;

import java.text.MessageFormat;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>2298: Forget Password_Valid Email ID_Existing In Idiom System</p>
 *  <p> <b>Objective:</b>Verify that the application is performing as expected while trying with valid email id in forget password</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8495.aspx</p>
 *  <p> <b>Module:</b>Login & Forgot Password Functionality</p>
 *  
 * @author Deepen Shah
 * @since 21 Jun 2016
 *
 */
public class LoginPage8495 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyForgotPasswordWithValidEmail(){		
		
		String strEmail = property.getProperty("validEmail8495");
		String strSuccessMessage = MessageFormat.format(property.getProperty("forgotPasswordSuccessMessage"), strEmail);
		String strSentBtnTxt =  property.getProperty("forgotPasswordBtnTextAfterEmailSent");
		String strSentBtnColor = property.getProperty("forgotPasswordBtnColorAfterEmailSent");
		String strTickMarkColor = property.getProperty("successTickMarkColor");
		
		try{			
			//Step1: Clicked forgot password
			ln.func_PerformAction("Forget Pwd", "");
			
			if(!rm.webElementSync(ln.passwordResetEmailButton, "visibility"))
				throw new IDIOMException("Failed to land on forgot password page.###NotAForgotPasswordPage");
			
			CustomReporter.log("Reach to forgot password page");
			
			rm.webElementSync(ln.passwordResetEmailButton, "clickable");
			
			//Step2: Entered valid email id
			ln.func_PerformAction("Input Email", strEmail);
			CustomReporter.log("Entered valid email id <i>"+strEmail +"</i>" );
			Thread.sleep(2000);
			
			//Just taking pic
			rm.captureScreenShot("8495-FillValue", "pass");
			
			//Step3: Clicked on 'SEND PASSWORD RESET EMAIL' button
			ln.func_PerformAction("Password Reset Email", "");
			CustomReporter.log("Clicked on 'SEND PASSWORD RESET EMAIL  button");
			Thread.sleep(2000);			
			
			if(!ln.isVisible("forgotpasswordsuccessmsg"))
				throw new IDIOMException("Forgot password email sent message is not visible.###MessageIsNotVisible");
			
			CustomReporter.log("Success message is visible");
			
			//Step4: Verifying success message
			String strActualMsg = ln.func_ReturnMsg("forgotpasswordsuccessmsg");
			if(!strActualMsg.equalsIgnoreCase(strSuccessMessage))
				throw new IDIOMException("Failed to verify message. Expected " + 
						strSuccessMessage + " and found " + strActualMsg + ".###FailedToCompareMessage");
			
			CustomReporter.log("Successfully verified the message <i>" + strActualMsg +"</i>");
			
			//Step4: Verifying button text
			strActualMsg = ln.func_ReturnMsg("forgotpasswordsentbtntext");
			if(!strActualMsg.equalsIgnoreCase(strSentBtnTxt))
				throw new IDIOMException("Failed to verify sent button text. Expected " + 
						strSentBtnTxt + " and found " + strActualMsg + ".###FailedToCompareSentBtnText");
			
			CustomReporter.log("Successfully verified the button text <i>" + strActualMsg +"</i>");
			
			//Step4: Verifying button color
			strActualMsg = ln.passwordResetEmailSent.getCssValue("background-color");
			if(!strActualMsg.equalsIgnoreCase(strSentBtnColor))
				throw new IDIOMException("Failed to verify sent button color. Expected " + 
						strSentBtnColor + " and found " + strActualMsg + ".###FailedToVerifySentBtnColor");
			
			CustomReporter.log("Successfully verified the button color <i>" + strActualMsg +"</i>");
			
			if(ln.passwordResetEmailSent.isEnabled())
				throw new IDIOMException("Send button is still enabled. ###SendButtonIsEnabled");
			
			CustomReporter.log("Verified: Button is disabled");			
			
			//Step5: Verify green color check mark
			strActualMsg = ln.greenCheckmark.getCssValue("color");
			if(!strActualMsg.equalsIgnoreCase(strTickMarkColor))
				throw new IDIOMException("Failed to verify tick mark color available beside email id. Expected " + 
						strTickMarkColor + " and found " + strActualMsg + ".###FailedToVerifyTickMarkColor");
			
			CustomReporter.log("Successfully verified the tick mark, available beside email field, color <i>" + strActualMsg +"</i>");
			
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
