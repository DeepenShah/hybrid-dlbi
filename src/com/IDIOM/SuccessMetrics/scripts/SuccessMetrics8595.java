package com.IDIOM.SuccessMetrics.scripts;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/********************************************************************
<p>	<b>Test Case Name:</b> Common Elements - 3.iii Saving - Veriify when User clicks the ">" button in the Bottom Save Bar </p>
<p>	<b>Objective:</b> Verify when User clicks the ">" button in the Bottom Save Bar </p>
<p>	<b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8595.aspx </p>
<p>	<b>Module:</b> Audience Builder </p>
@author: Shailesh Kava
@since: 16-May-2016
**********************************************************************/
public class SuccessMetrics8595 extends BaseClass {
	
	AudienceBuilderPage audienceBuilder = null;
	
	@Test
	public void verifySearchResultForSpecialChars(){
		String strMsg = null;
		String strProjectName="";
		boolean bProjectCreate = false;
		audienceBuilder = new AudienceBuilderPage(driver);
		
		try{
			//****************Variables declaration and Initialization****************************	
			String selMetric = property.getProperty("SelectingSuccessMetrics8685").trim();
			//****************Test step starts here************************************************
			
			loginToSelectClient();
			
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			//Step 4,5,6: Create/Select project and launch the same 
			
			CustomReporter.log("Creating new project as no project for this client");
			System.out.println("Creating new project as no project for this client");
			
			strProjectName = clientListPage.createNewProject("");
			System.out.println("Clicking on project name: "+strProjectName);
			CustomReporter.log("Clicking on project name: "+strProjectName);
			bProjectCreate = true;

			Thread.sleep(3000);
			clientListPage.launchProject(strProjectName);
			
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			//Step 7: Click on Success Metrics to go to Audience Builder Section 
			CustomReporter.log("Navigate to success metrics page");
			System.out.println("Navigate to success metrics page");
			
			pdp.navigateTo(property.getProperty("successMetrics"));
			
			if(!audienceBuilder.isVisible("successmetricstexttitle", ""))
				throw new IDIOMException("Success metrics page is not visible###SuccessMetrics8595-successMetricsNotOpen");
			
			//Step 8: Verify when User clicks the ">" button in the Bottom Save Bar 
			CustomReporter.log("Success metrics page is open, selecting metrics ["+selMetric+"]");
			
			audienceBuilder.selectMetricByName(selMetric);
			Thread.sleep(2000);
			
			if(audienceBuilder.getCountOfSuccessMetricsAddedInCenter() <= 0)
				throw new IDIOMException("Failed to select success metrics###SuccessMetrics8595-failedToAddSuccessMetrics");
			
			//getting values of selected cards before moving to Audience definition
			ArrayList<String> selectedCards = new ArrayList<>();
			selectedCards = (ArrayList<String>) audienceBuilder.getNameOfAllMetricCardsWithCategory();
			
			rm.captureScreenShot("metricsBeforeNavigate", "pass");
			
			//Clicking on ">" to navigate Audience Definition tab
			audienceBuilder.clickSuccessMetricsBottomSaveBarArrow();
			
			if(!rm.webElementSync(audienceBuilder.addNewGroupLink, "visibility"))
				throw new IDIOMException("Failed to open audience definition tab###SuccessMetrics8595-failedToOpenAudienceDef.");
			
			CustomReporter.log("Audience definition tab is open, navigate to success metrics tab");
			
			audienceBuilder.performAction("gotosuccessmetric");
			
			if(!audienceBuilder.isVisible("successmetricstexttitle", ""))
				throw new IDIOMException("Success metrics page is not visible###SuccessMetrics8595-successMetricsNotOpen");
			
			//getting values of selected cards before moving to Audience definition
			ArrayList<String> selectedCardsAfter = new ArrayList<>();
			selectedCardsAfter = (ArrayList<String>) audienceBuilder.getNameOfAllMetricCardsWithCategory();
			
			if(!selectedCards.containsAll(selectedCardsAfter))
				throw new IDIOMException("Selected metrics are not preserved###SuccessMetrics8595-metricsAreNotPreserved");
			
			CustomReporter.log("Selected metrics are preserved");
			rm.captureScreenShot("metricsAfterNavigate", "pass");
			
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
			rm.captureScreenShot("8695", "fail");
		}finally{
			try{
				
				if(bProjectCreate){
					pageHeader.navigateTo("projecthomepage");
					rm.webElementSync(pageHeader.personMenu,"visibility");					
					Thread.sleep(4000);
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
					Thread.sleep(2000);
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