package com.IDIOM.Login.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>Login Page_Verify User Session Timeout</p>
 *  <p> <b>Objective:</b></p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/7758.aspx</p>
 *  <p> <b>Module:</b>Login & Forgot Password Functionality</p>
 *  
 *  <p> <b>Note: </b> <br/> Currently test case only verify login page after time out. Add code to verify message for 'Session Timeout' when this feature is available. </p>
 *  
 * @author Deepen Shah
 * @since 18 Jun 2016
 *
 */
public class LoginPage7758 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifySessionTimeOut(){		
		String strMsg="";		
		try{			
			int iTimeOut = Integer.parseInt(property.getProperty("sessionTimeout"));
			//****************Test step starts here************************************************
						
			//Step1-3: Login To Selecting Client
			loginToSelectClient();			
			
			//Waiting till session time out + 1min
			Thread.sleep(((iTimeOut+1)*60000));
			
			//Refreshing the page.
			driver.navigate().refresh();
			
			if(!rm.webElementSync(ln.EmailID, "visibility"))
				throw new IDIOMException("Session is not timing out. It should land on login page.###FaileToCheckLoginPageAfterSessionTimeOut");
			
			CustomReporter.log("User is landed on login page after session time out.");
			
			//Add code to verify message when it is available			
			
			
		}catch(Exception e){
			exceptionCode(e,strClassName);
		}finally{
			try{
				//Logout
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(strClassName + ": " + strMsg);
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
		
		if(!BaseClass.testCaseStatus){
			  CustomReporter.errorLog("Test case failed");
			  Assert.assertTrue(false);
		  }else{
			  CustomReporter.log("Test case passed");
		  }	
	}
}
