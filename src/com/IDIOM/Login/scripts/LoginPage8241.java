
package com.IDIOM.Login.scripts;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.google.common.base.Strings;
import com.reports.CustomReporter;
//import com.msat.frameworks.data_driven.generic.reusables.GenericComponents;



import common.BaseClass;
import common.IDIOMException;


/** 
 * <p> <b>Test Case Name:</b>415_Login Page_Verify error message when user is deleted</p>
 * <p> <b>Objective:</b>ogin Page_Verify error message when user is deleted.</p>
 * <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8241.aspx/p>
 * <p> <b>Module:</b> Login</p>
 * @author Amrutha Nair
 * @since  4th August 2015
 * @modified Date: 23 June 2016
 *
 */

public class LoginPage8241 extends BaseClass {
		
	@Test
	public void verifyDeletedUserAndDeactivatedUserLogin() throws InterruptedException, IDIOMException {
		
		
		//****************Variables declaration and Initialization****************************	
		
		String emailid = property.getProperty("deletedEmail");
		String password = property.getProperty("deletedPwd"); 
		String errorMsg=property.getProperty("InvalidCredMessage");
		
		String emailidDeactivated = property.getProperty("deactivatedEmail");
		String passwordDeactivated = property.getProperty("deactivatedPwd"); 
		//****************Test step starts here************************************************
		
		try{
		
		//Step 1:Open URL 
		
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		
		//Step 2:Try to login with user which has been deleted
		CustomReporter.log("Enter deleted email and password value");
		ln.func_LoginToIDIOM(emailid, password);
		
		CustomReporter.log("Verify proper error message is displayed");
		String errormessage=ln.func_ReturnMsg("Invalid Credential");
		if(Strings.isNullOrEmpty(errormessage))
		{		
			CustomReporter.errorLog("The wrong Email id error message element is not existing");
			BaseClass.testCaseStatus=false;
		}
		else{
			if(errormessage.contentEquals(errorMsg)){
				CustomReporter.log("The Deleted Email id error message element is existing and expected error message is coming. The error message is:"+errormessage);
			}
			else{
				CustomReporter.errorLog("The Deleted Email id error message element is  existing, but the message appearing is not the expected one. The message currently getting populated is:"+errormessage);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8241_ErrorMessage_DeletedUser", "fail");
			}  
			
		}
		
		//Step3":	Try to login with user which has been deactivated
		//Reading deactivated email and password value from property file
		
				

		CustomReporter.log("Enter deactivated email and password value");
		ln.func_LoginToIDIOM(emailidDeactivated, passwordDeactivated);
				
		CustomReporter.log("Verify proper error message is displayed");
		 errormessage=ln.func_ReturnMsg("Invalid Credential");
		if(Strings.isNullOrEmpty(errormessage))
		{		
			CustomReporter.errorLog("The wrong Email id error message element is not existing");
			BaseClass.testCaseStatus=false;
		}
		else{
			if(errormessage.contentEquals(errorMsg)){
				CustomReporter.log("The deactivated Email id error message element is existing and expected error message is coming. The error message is:"+errormessage);
			}
			else{
				CustomReporter.errorLog("The deactivated Email id error message element is  existing, but the message appearing is not the expected one. The message currently getting populated is:"+errormessage);
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("8241_ErrorMessage_DeactivatedUser", "fail");
			}
			
		}
		}
		
		catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8241", "fail");
		}
		
		
		if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}
	
	}
	
	
	
}
