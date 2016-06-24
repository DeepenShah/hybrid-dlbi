package com.IDIOM.Login.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.util.Strings;

import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p><b>Test Case Name</b> 533_Login Page_Verify_No_Login_with extra Space</p> 
<p><b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/8195.aspx</p>
<p><b>Objective</b> This test case verifies the user is not able to get logged in with space before or after the user name</p>
<p><b>Module</b> Login page</p>
<p><b>Updated By, Date</b> Shailesh Kava, 22-June-2016</p>
@author Amrutha Nair
@since 31st JULY 2015
**********************************************************************/
public class LoginPage8195 extends BaseClass {
		
	@Test
	public void test_LoginPage8195() throws InterruptedException {
		
		//****************Variables declaration and Initialization****************************	
		
		String emailid = property.getProperty("email");
		String password = property.getProperty("password");
		String errorMsg=property.getProperty("InvalidCredMessage");
				
		//****************Test step starts here************************************************
				
		try{

		
		//Step 1: Access url
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		
		//Step 2: Input username with space at beginning and correct password
		CustomReporter.log("Enter domain email id with space at beginning and password");
		emailid = "     "+emailid;
		ln.func_LoginToIDIOM(emailid, password);
		
		
		
		String errormessage=ln.func_ReturnMsg("Invalid Credential");
		
		if(Strings.isNullOrEmpty(errormessage))
		{		
			//Step 1: Access url
			CustomReporter.log("Launch Browser and Enter URL");
		}			
		
		
		//Step 3: Input username with space at end And correct password
		Thread.sleep(3000);
		CustomReporter.log("Enter domain email id with space at end and password");
		emailid = property.getProperty("email");
		emailid=emailid+"      ";
        ln.func_LoginToIDIOM(emailid, password);
		
        errormessage=ln.func_ReturnMsg("Invalid Credential");
		if(Strings.isNullOrEmpty(errormessage))
		{		
			//Step 2: Input username with space at beginning and correct password
			CustomReporter.log("Enter domain email id with space at beginning and password");
			emailid = "     "+emailid;
			ln.func_LoginToIDIOM(emailid, password);
			
			if(!rm.webElementSync(ln.Invalid_CredError, "visibility"))
				throw new IDIOMException("Validation message is not appearing on entering email and password with blank space###8195_missingValidationMessage");

			
			CustomReporter.log("Error message is appearing while entering email and password with blank space.");
			
			if(!errormessage.contentEquals(errorMsg))
				throw new IDIOMException("The wrong Email id error message element is  existing, but the message appearing is not the expected one. The message currently getting populated is:"+errormessage+"###8195_mismatchValidationMessage");
			
			CustomReporter.log("Expected validation message is appearing ["+errormessage+"]");
			
			//Step 3: Input username with space at end And correct password
			Thread.sleep(3000);
			CustomReporter.log("Enter domain email id with space at end and password");
			emailid = property.getProperty("email");
			emailid=emailid+"      ";
	        ln.func_LoginToIDIOM(emailid, password);
			
	        errormessage=ln.func_ReturnMsg("Invalid Credential");
	        
	        if(!rm.webElementSync(ln.Invalid_CredError, "visibility"))
				throw new IDIOMException("Validation message is not appearing on entering email and password with blank space###8195_missingValidationMessage");
			
			CustomReporter.log("Error message is appearing while entering email and password with blank space.");
			
			if(!errormessage.contentEquals(errorMsg))
				throw new IDIOMException("The wrong Email id error message element is  existing, but the message appearing is not the expected one. The message currently getting populated is:"+errormessage+"###8195_mismatchValidationMessage");
			
			CustomReporter.log("Expected validation message is appearing ["+errormessage+"]");
		} 
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
			rm.captureScreenShot("8195", "fail");
		}
		if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}

		}
	}
