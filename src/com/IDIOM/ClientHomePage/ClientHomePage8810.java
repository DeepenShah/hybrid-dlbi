package com.IDIOM.ClientHomePage;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Login_Page;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p>	<b>Test Case Name:</b> ClientHomePage_FirstTimeUserExp_User Has selected Clients. </p>
<p>	<b>Objective:</b> Verify that for users who have clients AND had previously selected a client in the new client homepage, default them to the client they had most previously viewed. </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8810.aspx </p>
<p>	<b>Module:</b> Client Home Page </p>
@author: Shailesh Kava
@since: 15-June-2016
**********************************************************************/
public class ClientHomePage8810 extends BaseClass {
	
	Analyse_Pathing_Page pathingPage;
	String PathingLink;
	String strDetails;
	String fileLocation;
	Actions action;
	String exportdatafilename;
	
	@Test
	public void verifyPreservedClient(){
		
		exportdatafilename = property.getProperty("pathingexportdatafilename"); 
				
		String strMsg = null;
		boolean bStatus = true;
		boolean bProjectCreate = false;
		String selectedClientBeforeLogout;
		String selectedClientAfterRelogin;
		try{
			loginToSelectClient(property.getProperty("SuperAdminUser"),property.getProperty("SuperAdminpassword"));
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			selectedClientBeforeLogout = clientListPage.selectedClient.getText();
			
			CustomReporter.log("Selected client is ["+selectedClientBeforeLogout+"]");
			pageHeader.performAction("logout");
			
			Login_Page ln = new Login_Page(driver);
			ln.func_LoginToIDIOM(property.getProperty("SuperAdminUser"),property.getProperty("SuperAdminpassword"));
			
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			selectedClientAfterRelogin = clientListPage.selectedClient.getText();
			
			if(!selectedClientAfterRelogin.equalsIgnoreCase(selectedClientBeforeLogout))
				throw new IDIOMException("Selected client is not preserved ["+selectedClientBeforeLogout+"] after login again ["+selectedClientAfterRelogin+"]###8810-selectedClientNotPreserved");
			
			CustomReporter.log("Selected client preserved after login ["+selectedClientBeforeLogout+"] after login again ["+selectedClientAfterRelogin+"]");
			
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
			rm.captureScreenShot("8810", "fail");
		}finally{
			try{
				
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the project");
				}
				
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