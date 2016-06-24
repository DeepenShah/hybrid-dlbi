package com.IDIOM.Login.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.ClientList_Page;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p><b>Test Case Name</b> Login Page_Verify Login Functionality</p>
<p><b>Test Case ID</b> http://qa.digitas.com/SpiraTest/523/TestCase/7739.aspx</p>
<p><b>Objective</b> Verifying Login functionality</p> 
<p><b>Module</b> Login Page</p>
<p><b>Updated By: Date</b>Shailesh Kava, 21 June 2016 </p>
@author Abhishek Deshpande
@since 28th JULY 2015
**********************************************************************/
public class LoginPage7739 extends BaseClass {
	
		String strMsg;
		boolean bStatus = true;
		@Test
		public void test_LoginPage7739() throws InterruptedException {
			
			try{
				
				loginToSelectClient();
				 
				ClientList_Page cl = new ClientList_Page(driver);
				
				rm.webElementSync(cl.newProjectBtn, "visible");
				
				if(!cl.isVisible("newproject"))
					throw new IDIOMException("The user has not been logged in to client list page with valid user id and password###7739-failedToLogin");
				
				CustomReporter.log("The user has been logged in successfully. It got navigated to clientlist page");
				
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
				rm.captureScreenShot("7739", "fail");
			}finally{
				try{
					//Logout from application
					pageHeader.performAction("logout");
					strMsg = "Logged out successfully";
					CustomReporter.log(strMsg);
					System.out.println(getClass().getSimpleName() + ": " + strMsg);
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