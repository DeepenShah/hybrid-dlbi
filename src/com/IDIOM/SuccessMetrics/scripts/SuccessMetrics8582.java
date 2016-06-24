package com.IDIOM.SuccessMetrics.scripts;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.Analyse_Pathing_Page;
import com.IDIOM.pages.Analyse_Profile_Page;
import com.IDIOM.pages.Analyse_Webnet_Page;
import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/**
 * <p> <b>Test Case Name:</b> *Success Metric_3.a.i. Success Metric Card - Verify card design on board. </p>
 * <p> <b>Objective:</b> To verify card design for success metric. </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8582.aspx </p>
 * <p> <b>Module:</b> Audience Builder - Success Metrics</p>
 * @author Rohan Macwan
 * @since 06 May 2016
 *
 */
public class SuccessMetrics8582 extends BaseClass{

	@Test
	
	public void	SuccessMetrics8582(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName=property.getProperty("projectName8700");
		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strSuccessMetrics = property.getProperty("CommonSelectingSuccessMetric");
					
			
			String strExpectedSuccessMetric1=property.getProperty("CommonExpectedSuccessMetric1");
			String strCommonExpectedSuccessMetricCategory1=property.getProperty("CommonExpectedSuccessMetricCategory1");
			String strClientName=property.getProperty("clientName");
			
			//****************Test step starts here************************************************
			
			clientListPage = new ClientList_Page(driver);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			Login_Page ln = new Login_Page(driver);
			
			//Step1: Open IDIOM URL and login with valid credentials
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			//Step 2 Select any existing client from client dropdown
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			
			//Step 3 - Click on any project/Create a project and launch the same
			strProjectName = clientListPage.createNewProject("");
			bProjectCreate=true;
			clientListPage.launchProject(strProjectName);
			
				
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			//Step 4 - Click on destination link success metrics
			System.out.println("Open Success Metrics page");
			CustomReporter.log("Open Success Metrics page");
			
			pdp.navigateTo(property.getProperty("successMetrics"));
			
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);
			
			
			//Step 5 - Select success metric and verify the Card
			CustomReporter.log("Select Success Metric");
			audienceBuilder.selectMetricByName(strSuccessMetrics);
			
					
			List<String> strlistOfSelectedSuccessMetricsNames = audienceBuilder.getNameOfAllMetricCardsWithCategory();;
			String strActualVal = strlistOfSelectedSuccessMetricsNames.toString().toLowerCase();
			System.out.println("Actual Val - " + strActualVal);
			
			if (!strActualVal.contains(strExpectedSuccessMetric1.trim()))
				throw new IDIOMException("Expected Success Metric '" + strExpectedSuccessMetric1.toUpperCase() + "' is not found. ###8582_didFindSuccessMetric");
			
			CustomReporter.log("Selected Success Metric '" + strExpectedSuccessMetric1.toUpperCase() +"' is found in Card area");
			
			if (!strActualVal.contains(strCommonExpectedSuccessMetricCategory1.trim()))
				throw new IDIOMException("Expected Success Metric Category '" + strCommonExpectedSuccessMetricCategory1.toUpperCase() + "' is not found. ###8582_didNotFindSuccessMetricCategory");
			
			CustomReporter.log("Success Metric Category '" + strCommonExpectedSuccessMetricCategory1.toUpperCase() +"' is shown on Card Title");
			
			if (!(audienceBuilder.getBgColorOfSuccessMetricCard_s()))
				throw new IDIOMException("Background color has not be set as Red for Success Metric Card/Cards or there might be different issues on the page. ###8582_didNotFindRedColorInBgOfSuccMetCard_s");
			
			CustomReporter.log("Background color correctly has Red color in selected Success Metric Card/Cards");
			
		}catch(IDIOMException ie){
			BaseClass.testCaseStatus = false;
			String strMsgAndFileName[] = ie.getMessage().split("###");
			System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
			CustomReporter.errorLog("" + strMsgAndFileName[0] + ".");
			rm.captureScreenShot(strMsgAndFileName[1], "fail");
			
		}catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("8582", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					pageHeader.navigateTo("projecthomepage");
					rm.webElementSync(pageHeader.personMenu,"visibility");					
					Thread.sleep(3000);
					clientListPage.func_PerformAction("Close Project");
					clientListPage.performActionOnProject("delete", strProjectName);
					clientListPage.performActionOnProject("yes","");
					Thread.sleep(2000);
					CustomReporter.log("Deleted the project");
				}
				
				//Step 8 - Click on logout 
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
