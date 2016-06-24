package com.IDIOM.Login.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Login_Page;
import com.google.common.base.Strings;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p><b>Test Case Name</b> Login Page_Verifying Error Message on Entering Incorrect Password.</p>
<p><b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/7745.aspx</p>
<p><b>Module</b> Login Page</p>
<p><b>Updated By: Date</b>Shailesh Kava, 21 June 2016 </p>
@author Abhishek Deshpande
@since 28th JULY 2015
**********************************************************************/
public class LoginPage7745 extends BaseClass {
	String strMsg;
	boolean bStatus = true;
	
	@Test
	public void test_LoginPage7745() {
	try{	
		//Reading email and password value from property file
		String emailid = property.getProperty("SuperAdminUser");
		String password = property.getProperty("SuperAdminpassword")+"123";
		String errorMsg=property.getProperty("InvalidCredMessage");
		
		CustomReporter.log("Launch Browser and Enter URL");
		Login_Page ln = new Login_Page(driver);	
		
		CustomReporter.log("Enter domain email id and incorrect password");
		ln.func_LoginToIDIOM(emailid, password);
		String errormessage=ln.func_ReturnMsg("Invalid Credential");

		if(Strings.isNullOrEmpty(errormessage))
			throw new IDIOMException("The wrong password error message element is not existing###7745-messageIsNotAppearing");
		
		CustomReporter.log("Error message is appearing");
		System.out.println(errormessage+"==="+errorMsg);
		if(!errormessage.trim().equalsIgnoreCase(errorMsg.trim()))
			throw new IDIOMException("The wrong password error message element is  existing, but the message appearing is not the expected one. The message currently getting populated is:"+errormessage+"###7745-inCorrectMessageIsAppearing");
		
		CustomReporter.log("Proper validation message is appearing");
		
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
		rm.captureScreenShot("7745", "fail");
	}
	if(!BaseClass.testCaseStatus){
		CustomReporter.errorLog("Test case failed");
		Assert.assertTrue(false);
	}else{
		CustomReporter.log("Test case passed");
	}

}
}
		