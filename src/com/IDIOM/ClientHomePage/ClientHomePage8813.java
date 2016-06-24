package com.IDIOM.ClientHomePage;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

/** <p> <b>Test Case Name:</b>2384: Manage Projects : Verify message when client has no project.</p>
 *  <p> <b>Objective:</b>If client has no project inside it then on Manage Projects page appropriate message should be displayed.</p>
 *  <p> <b>Test Case ID:</b>http://qa.digitas.com/SpiraTest/523/TestCase/8813.aspx</p>
 *  <p> <b>Module:</b>Client Home Page</p>
 *  
 * @author Deepen Shah
 * @since 02 Jun 2016
 *
 */
public class ClientHomePage8813 extends BaseClass{
	String strClassName = getClass().getSimpleName();
	
	@Test
	public void verifyNoProjectMessage(){		
		String strMsg="";
		try{
			
			String strEmail = property.getProperty("8800userhasnoproject_user");
			String strPassword = property.getProperty("8800userhasnoproject_pass");
			String strNoProjMsg = property.getProperty("noProjectMessage");
			//****************Test step starts here************************************************
			
			System.out.println("Before Client " + System.currentTimeMillis());
			//Login To Selecting Client
			loginToSelectClient(strEmail,strPassword);			
			
			System.out.println("After Client " + System.currentTimeMillis());
			
			//Delete project if available
			boolean bRefreshReq = clientListPage.projectList.size()==0?false:true;
			for(WebElement project:clientListPage.projectList)
				util.deleteProjectOrAudience(clientListPage.getAudienceProjectClientId(project.getText().trim(), ""), true);
				
			//Refresh the page
			if(bRefreshReq){
				driver.navigate().refresh();			
				Thread.sleep(5000);
				rm.webElementSync("pageload");
				rm.webElementSync("jqueryload");
			}
			
			System.out.println("After for " + System.currentTimeMillis());
			
			//Get the message			
			String strNoProjMsgFromApp = clientListPage.noProjectText.getText().trim();
			
			if(!strNoProjMsg.equalsIgnoreCase(strNoProjMsgFromApp))
				throw new IDIOMException("Not able to verify no project message. Expected " +
			strNoProjMsg + " and found " + strNoProjMsgFromApp +".###FailedToVerifyMessage");
			
			CustomReporter.log("Successfully verified no project message on application. - "+strNoProjMsgFromApp);
			
			
			
		}catch(Exception e){
			exceptionCode(e);
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
		
	public void exceptionCode(Exception ie){
		BaseClass.testCaseStatus = false;
		String strMsgAndFileName[] = ie.getMessage().split("###");
		System.out.println(strClassName + ": " + strMsgAndFileName[0]);
		ie.printStackTrace(System.out);
		
		String strTestCaseId = strClassName.replaceAll("\\D+","");		
		if(strMsgAndFileName.length==1){
			CustomReporter.errorLog("Failure: "+ ie.getMessage());
			rm.captureScreenShot(strTestCaseId+"-Exception", "fail");
		}else{
			CustomReporter.errorLog("Failure: "+ strMsgAndFileName[0]);
			rm.captureScreenShot(strTestCaseId+"-"+strMsgAndFileName[1], "fail");	
		}		
	}	
}
