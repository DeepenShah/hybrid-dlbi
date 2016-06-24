package com.IDIOM.ClientHomePage;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p>	<b>Test Case Name:</b> ClientHomePage_HeaderComponents. </p>
<p>	<b>Objective:</b> Verify header components on Client home page. </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8796.aspx </p>
<p>	<b>Module:</b> Client Home Page </p>
@author: Shailesh Kava
@since: 14-June-2016
**********************************************************************/
public class ClientHomePage8797 extends BaseClass {
	
	Analyse_Pathing_Page pathingPage;
	String PathingLink;
	String strDetails;
	String fileLocation;
	Actions action;
	String exportdatafilename;
	
	@Test
	public void verifyClientHomePageHeaderAndReloadPage(){
		
		PathingLink = property.getProperty("pathing");
		exportdatafilename = property.getProperty("pathingexportdatafilename"); 
				
		String strMsg = null;
		
		boolean bProjectCreate = false;
		
		try{
			loginToSelectClient();
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			CustomReporter.log("User is on Client list page, verifying header information as requested");
			
			if(pageHeader.idiomLogo.isDisplayed() && pageHeader.isVisible("logoutlink")){
				CustomReporter.log("Idiom logo and Person menu is available in header of client home page");
				
				Thread.sleep(1000);
				if(pageHeader.idiomLogo.isDisplayed()){
					System.out.println("Idiom logo is visible and clicking on it");
					CustomReporter.log("Clicking on Idiom Logo");
					pageHeader.idiomLogo.click();
				}
				
				
				
				Thread.sleep(5000);
				rm.webElementSync("pageload");
				rm.webElementSync("jqueryload");
				
				if(!clientListPage.newProjectBtn.isDisplayed())
					throw new IDIOMException("Page is not refreshed on click IDIOM logo###8797-failedToReloadPage");
				
				CustomReporter.log("Page is reloaded on clicking Idiom Logo from header");
			}
			else
				throw new IDIOMException("Either Idiom Logo or person menu icon is missing###8797-idiomLogoOrPersonMenuIconIsMissing");
			
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
			rm.captureScreenShot("8797", "fail");
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
		
		if(!BaseClass.testCaseStatus){
			CustomReporter.errorLog("Test case failed");
			Assert.assertTrue(false);
		}else{
			CustomReporter.log("Test case passed");
		}
	}
}