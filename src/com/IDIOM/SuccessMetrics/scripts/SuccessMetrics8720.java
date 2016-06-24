package com.IDIOM.SuccessMetrics.scripts;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
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
import common.ReusableMethods;

/**
 * <p> <b>Test Case Name:</b> *2092_Verify Presence of Selected metrics in two browsers at the same time </p>
 * <p> <b>Objective:</b> Verify Presence of Selected metrics in two browsers at the same </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8720.aspx </p>
 * <p> <b>Module:</b> Audience Builder - Success Metrics</p>
 * @author Rohan Macwan
 * @since 10 May 2016
 *
 */

public class SuccessMetrics8720 extends BaseClass {

	@Test
	
	public void	SuccessMetrics8720(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName=property.getProperty("CommonProjectName");
				
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strSuccessMetrics = property.getProperty("CommonSelectingSuccessMetric");
			String strSuccessMetrics_2 = property.getProperty("CommonSelectingSuccessMetric2");
			String strSuccessMetrics_3 = property.getProperty("CommonSelectingSuccessMetric3");
			
			
			String strExpectedSuccessMetric1=property.getProperty("CommonExpectedSuccessMetric1");
			String strExpectedSuccessMetric2=property.getProperty("CommonExpectedSuccessMetric2");
			String strExpectedSuccessMetric3=property.getProperty("CommonExpectedSuccessMetric3");
			
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
			
			
			//Step 5 - Select some success metrics
			CustomReporter.log("Select few Success Metrics");
			audienceBuilder.selectMetricByName(strSuccessMetrics);
			
			audienceBuilder.selectMetricByName(strSuccessMetrics_2);
			
			audienceBuilder.selectMetricByName(strSuccessMetrics_3);
			
			List<String> strlistOfSelectedSuccessMetricsNames = audienceBuilder.getNameOfAllMetricCards();
			String strActualVal = strlistOfSelectedSuccessMetricsNames.toString().toLowerCase();
			System.out.println("Actual Val - " + strActualVal);
			
			if (!strActualVal.contains(strExpectedSuccessMetric1.trim()))
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric1 + "' has not been added successfully. ###8720_didNotAddSuccessMetric");

			if (!strActualVal.contains(strExpectedSuccessMetric2.trim()))
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric2 + "' has not been added successfully. ###8720_2_didNotAddSuccessMetric");
			
			if (!strActualVal.contains(strExpectedSuccessMetric3.trim()))
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric3 + "' has not been added successfully. ###8720_3_didNotAddSuccessMetric");

			CustomReporter.log("Selected Success Metrics appear in centre area.");
			
			//############################# ========== ####################################
			
			//Step 6 - Now open another browser and navigate to same idiom creates in step 4 
			String strAnotherBrowser = "";
			if(browserName.equalsIgnoreCase("ie")){
				strAnotherBrowser = "firefox";
			}else if(browserName.equalsIgnoreCase("firefox")){
				strAnotherBrowser = "ie";
			}else if(browserName.equalsIgnoreCase("chrome")){
				strAnotherBrowser = "ie";
			}
			
			CustomReporter.log("Launch " + strAnotherBrowser + " Browser");
			
			WebDriver newDriver= launchBrowser(strAnotherBrowser); 
			
			ReusableMethods newrm = new ReusableMethods(newDriver);
			ClientList_Page clientListPage1=null;
			clientListPage1 = new ClientList_Page(newDriver);
			ProjectDashboardPage pdp1 = new ProjectDashboardPage(newDriver);
			Login_Page ln1 = new Login_Page(newDriver);
			
			try{
				Thread.sleep(3000);
				ln1.func_LoginToIDIOM(strEmailId, strPassword);		    
			    
				Thread.sleep(5000);
				rm.webElementSync("pageload");
				rm.webElementSync("jqueryload");
				
				clientListPage1.selectClient(strClientName);
				strMsg = "Selected '"+strClientName+"' client successfully.";
				
				clientListPage1.launchProject(strProjectName);
				
				rm.webElementSync(pdp1.projectName, "visibility");
				Thread.sleep(2000);
				System.out.println("Project dashboard page is open");
				CustomReporter.log("Project dashboard page is open");
				
				System.out.println("Open Success Metrics page");
				CustomReporter.log("Open Success Metrics page");
				
				pdp1.navigateTo(property.getProperty("successMetrics"));
				
				AudienceBuilderPage audienceBuilder1 = new AudienceBuilderPage(newDriver);
				
				List<String> strlistOfSelectedSuccessMetricsNames1 = audienceBuilder.getNameOfAllMetricCards();
				String strActualVal1 = strlistOfSelectedSuccessMetricsNames1.toString().toLowerCase();
				System.out.println("Actual Val - " + strActualVal1);
				
				if (!strActualVal1.contains(strExpectedSuccessMetric1.trim()))
					throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric1 + "' has not been appearing. ###8720_4_didNotAddSuccessMetric");
	
				if (!strActualVal1.contains(strExpectedSuccessMetric2.trim()))
					throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric2 + "' has not been appearing. ###8720_5_didNotAddSuccessMetric");
				
				if (!strActualVal1.contains(strExpectedSuccessMetric3.trim()))
					throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric3 + "' has not been appearing. ###8720_6_didNotAddSuccessMetric");
	
				CustomReporter.log("Selected Success Metrics in previous browser aslo appear in centre area. It is functioning properly");
				
			
			}catch(IDIOMException ie){
				BaseClass.testCaseStatus = false;
				String strMsgAndFileName[] = ie.getMessage().split("###");
				System.out.println(getClass().getSimpleName() + ": " + strMsgAndFileName[0]);
				CustomReporter.errorLog("" + strMsgAndFileName[0] + ".");
				newrm.captureScreenShot(strMsgAndFileName[1], "fail");
				
			}catch(Exception e){
				
				BaseClass.testCaseStatus = false;
				e.printStackTrace(System.out);
				CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
				newrm.captureScreenShot("8720_NewBrowser", "fail");
			}
			finally
			{
				newDriver.quit();
			}
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
			rm.captureScreenShot("8720", "fail");
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
				
				//Step 7 - Click on logout 
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
