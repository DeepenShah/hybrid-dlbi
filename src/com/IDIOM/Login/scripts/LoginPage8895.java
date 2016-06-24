package com.IDIOM.Login.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>2386_Login Page SSO Lion Login Wrong Password</p>
 *  <p> <b>Objective:</b>Verify When correct Lion log id and wrong password are provided</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8895.aspx</p>
 *  <p> <b>Module:</b>Login & Forgot Password Functionality</p>
 *  
 * @author Deepen Shah
 * @since 22 Jun 2016
 *
 */
public class LoginPage8895 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyMessageOnWrongPasswordForLionLogin(){		
	
		String strLionUser = property.getProperty("lionLogin8895");
		String strErrorMsg = property.getProperty("InvalidCredMessage");
		
		try{			
			//Step1: Fill Email and wrong password
			ln.func_LoginToIDIOM(strLionUser, "wrongpass");
			CustomReporter.log("Entered " + strLionUser +" and wrong password.");
			
			Thread.sleep(2000);
			
			//Step2: Checking message
			String strMsg = ln.func_ReturnMsg("Invalid Credential");
			if(!strErrorMsg.equalsIgnoreCase(strMsg))
				throw new IDIOMException("Failed to get message. Expected " + strErrorMsg +
						" and found " + strMsg +".###MsgIsNotValid");
			
			CustomReporter.log("Successfully verified error message. <i>"+strMsg + "</i>");			
			
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
