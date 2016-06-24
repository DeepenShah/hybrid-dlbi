package com.IDIOM.Login.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.google.common.base.Strings;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;
/********************************************************************
<p><b>Test Case Name</b> Login Page_Verify Login Button is Disabled When Domain is Incorrect and Password Field is Empty.</p>
<p><b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/7753.aspx</p>
<p><b>Module</b> Login Page</p>
<p><b>Updated By, Date</b> Shailesh Kava, 22-June-2016</p>
@author Amrutha Nair
@since 31st JULY 2015
**********************************************************************/
public class LoginPage7753 extends BaseClass {
		
	@Test
	public void test_LoginPage7753() {
		
		try{
			Boolean status=true;
			String emailid = property.getProperty("incorrectDomainemail");
			String password = property.getProperty("password");
			String errorMsg=property.getProperty("InvalidCredMessage");
			CustomReporter.log("Launch Browser and Enter URL");
			Login_Page ln = new Login_Page(driver);	
			
			CustomReporter.log("Enter incorrect domain email id");			
			status = ln.func_IsLoginButtonEnabled("UN",emailid);	
			if (status.booleanValue())
			{
				CustomReporter.errorLog("Test Step Failed: The login button is not in disbled status");	
				BaseClass.testCaseStatus=false;
			}
			else
			{
				CustomReporter.log("The login button is in disbled status");	
			}	
			
			if(status.booleanValue())
				throw new IDIOMException("Login button is enabled on entering email "+emailid+"###7753_invalidDomainExepts");
			
			CustomReporter.log("Invalid domain is not excepted email id is ["+emailid+"]");
			
			ln.func_LoginToIDIOM(emailid, password);
			//throw new IDIOMException("The login button is NOT disabled on entering only email address###7752-loginButtonIsEnabled");
			String errormessage=ln.func_ReturnMsg("Invalid Credential");
			if(Strings.isNullOrEmpty(errormessage))
				throw new IDIOMException("Validation message is not exist on entering invalid email id ###7753_noValidationMessage");
			
			CustomReporter.log("Validation message is appearing on entering invalid email id.");
			
			if(!errormessage.equalsIgnoreCase(errorMsg))
				throw new IDIOMException("Expected validation message is not available on entering invalid email address###7753_inCorrectValidationMessage");
				
			CustomReporter.log("The wrong Email id error message element is existing and expected error message is coming. The error message is:"+errormessage);	
			
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