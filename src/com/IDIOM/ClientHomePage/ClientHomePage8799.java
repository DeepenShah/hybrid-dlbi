package com.IDIOM.ClientHomePage;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>ClientHomePage_FirstTimeUserExp_ User has Clients, But not yet Selected</p>
 *  <p> <b>Objective:</b>Verify that for users who have clients but have not yet selected a client in the new homepage, land them on the Client page with no content except the Select Client dropdown and a little tool tip that tells them to select a client </p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8799.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Shailesh Kava
 * @since 15 Jun 2016
 *
 */
public class ClientHomePage8799 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyMessageIfNoClientSelected(){		
		
		String defaultTextOnSelectClient = property.getProperty("selectClient");
		String strMsg="";
		boolean bStatus = true;
		boolean bProjectCreated = false;	
		try{
			
			ClientList_Page cl = new ClientList_Page(driver);
			//****************Test step starts here************************************************
			
			String expectedMessageWhenNoClientSelected = property.getProperty("messagewhennoclientselected");
			
			Login_Page ln = new Login_Page(driver);
			ln.func_LoginToIDIOM(property.getProperty("8799userhasnoproject_user"), property.getProperty("8799userhasnoproject_pass"));
			
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			Thread.sleep(5000);
			
			System.out.println(cl.messageWhenNoClientSelected.isDisplayed());
			if(!cl.selectClientMsgInDropDown.getText().trim().equalsIgnoreCase(defaultTextOnSelectClient)){
				bStatus = false;
				throw new IDIOMException("Skipped Test Case:Client is already selected for this user###8799-clientIsAlreadySelected");
			}
			
			String messageOnSiteIfNoClientSelected = cl.messageWhenNoClientSelected.getText().trim();
			
			if(!messageOnSiteIfNoClientSelected.equalsIgnoreCase(expectedMessageWhenNoClientSelected)){
				throw new IDIOMException("Unexpected message when client is not selected expected is ["+expectedMessageWhenNoClientSelected
						+"] Actual is ["+messageOnSiteIfNoClientSelected+"]"+"###8799-unExpectedMessage");
			}else{
				CustomReporter.log("Message is matched with expected message expected is ["+expectedMessageWhenNoClientSelected
						+"] Actual is ["+messageOnSiteIfNoClientSelected+"]");
			}
			
		}catch(Exception e){
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8799", "fail");
		}finally{
			try{
				pageHeader.performAction("logout");
				strMsg = "Logged out successfully";
				CustomReporter.log(strMsg);
				System.out.println(getClass().getSimpleName() + ": " + strMsg);
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
		//****************Test step ends here************************************************
		
		if(!BaseClass.testCaseStatus && !bStatus){
			   CustomReporter.errorLog("Test case skipped");
			   throw new SkipException("Skipping this test case as no project older than today found");
			  }else if(!BaseClass.testCaseStatus){
				   CustomReporter.errorLog("Test case failed");
				   Assert.assertTrue(false);
			  }else{
				  CustomReporter.log("Test case passed");
			}
	}
}