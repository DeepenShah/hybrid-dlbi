/********************************************************************
Test Case Name: *HVA_1.A.i_Verify Navigation to High Value Actions page
Test Case ID:http://qa.digitas.com/SpiraTest/523/TestCase/7947.aspx
Objective:Verify the correct navigation path to the HVA page. 
Module: Analyse>HVA
Created By: Amrutha Nair
Date: 24 September 2015
**********************************************************************/

package com.IDIOM.Analyse.HVA.scripts;


import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.HVA_Page;

import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;

import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;

import common.BaseClass;
import common.IDIOMException;

public class HVA7947 extends BaseClass {
		
	@Test
	public void test_HVA7947() throws Exception{
	//****************Variables declaration and Initialization****************************
			
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName="";
		String strDetails ="";
		AudienceBuilderPage abPage=null;
	
		
		try
		{
			//****************Test step starts here************************************************
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			
			String strSuccessMetrics = property.getProperty("CommonSelectingSuccessMetric");
			String strSuccessMetrics_2 = property.getProperty("CommonSelectingSuccessMetric2");
			String strSuccessMetrics_3 = property.getProperty("CommonSelectingSuccessMetric3");
			
			String strExpectedSuccessMetric1=property.getProperty("CommonExpectedSuccessMetric1");
			String strExpectedSuccessMetric2=property.getProperty("CommonExpectedSuccessMetric2");
			String strExpectedSuccessMetric3=property.getProperty("CommonExpectedSuccessMetric3");
			
			String strAudienceAttributes1=property.getProperty("commonAudienceAttribute4");

			String strClientName=property.getProperty("clientName");
			
			//****************Test step starts here************************************************
			
			clientListPage = new ClientList_Page(driver);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			Login_Page ln = new Login_Page(driver);
			
			//Step1: Launch browser and enter IDIOM Url
			//Step2: Enter valid username and password Click on 'login to idiom' button
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);		    
		    
			Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			//Step 3 In client home page, select a client from dropdown
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			
			//Step 4 - Click on new project button
			//Step 5 - Enter valid name and description for project and click on Save button
			strProjectName = clientListPage.createNewProject("");
			bProjectCreate=true;
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			//step 6 - Click on launch project
		
			clientListPage.launchProject(strProjectName);
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			//Step 7 - Click on destination link success metrics
			System.out.println("Open Success Metrics page");
			CustomReporter.log("Open Success Metrics page");
			
			pdp.navigateTo(property.getProperty("successMetrics"));
			
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);

			CustomReporter.log("Check whether Success Metrics Tab is Active when Audience Builder Section gets loaded");
					
			if (!((audienceBuilder.isVisible("selectedactivetab", "") && (audienceBuilder.SelectedActiveTab.getText().trim().toLowerCase().equalsIgnoreCase("Success Metrics")))))
				throw new IDIOMException("Success Metrics Tab seems to be not available or there may be some issues on the page. ###7947_SuccessMetricsTabNotAvailable");
			
			CustomReporter.log("Success Metrics Tab is available and visible on the page and currently Active");
			
			//Step 8 - Verify after success metrics are added
			rm.webElementSync(audienceBuilder.projectedTab,"visibility");
			rm.webElementSync(audienceBuilder.percentagePopulationValue,"visibility");
			
			CustomReporter.log("Select few Success Metrics");
			audienceBuilder.selectMetricByName(strSuccessMetrics);
			
			audienceBuilder.selectMetricByName(strSuccessMetrics_2);
			
			audienceBuilder.selectMetricByName(strSuccessMetrics_3);
			

			List<String> strlistOfSelectedSuccessMetricsNames = audienceBuilder.getNameOfAllMetricCards();
			String strActualVal = strlistOfSelectedSuccessMetricsNames.toString().toLowerCase();
			System.out.println("Actual Val - " + strActualVal);
			
			if (!strActualVal.contains(strExpectedSuccessMetric1.trim()))
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric1 + "' has not been added successfully. ###7947_didNotAddSuccessMetric");

			if (!strActualVal.contains(strExpectedSuccessMetric2.trim()))
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric2 + "' has not been added successfully. ###7947_2_didNotAddSuccessMetric");
			
			if (!strActualVal.contains(strExpectedSuccessMetric3.trim()))
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric3 + "' has not been added successfully. ###7947_3_didNotAddSuccessMetric");

			CustomReporter.log("Selected Success Metrics appear in centre area.");
						
			//Step9: Click on Audience Definition link from project dashboard
			CustomReporter.log("Go to Audience Definition page");
			audienceBuilder.performAction("gotoaudiencedefinition");
			
			Thread.sleep(3000);
			
			abPage = new AudienceBuilderPage(driver);
			rm.webElementSync(abPage.allMetricsLabel,"visibility");
			CustomReporter.log("Navigated to audience definition page");
							
			//Step 10 - Select few attributes and add them
			CustomReporter.log("Select an Attribute");
			abPage.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			abPage.performAction("addattribute");
			
			Thread.sleep(6000);
			
			rm.webElementSync(abPage.projectedTab,"visibility");
			rm.webElementSync(abPage.percentagePopulationValue,"visibility");
			
			//Step 11 - Navigate to HVA page
			CustomReporter.log("Navigate to HVA page");
			pageHeader.megaMenuLinksClick(property.getProperty("hva"));
			
			
			HVA_Page ah = new HVA_Page(driver);
			
			ah.isVisible("slowdown");
			
			if(!rm.webElementSync(ah.behaviour,"visibility"))
				throw new IDIOMException("Behavior dropdown is not available or there might be other issues on the page. ###7947_BehaviorsDropdownNotVisible");

			CustomReporter.log("HVA page has successfully loaded");
			
			if(ah.func_ElementExist("Barchart")){
				CustomReporter.log("The High Value Actions page with a horizontal bars chart is getting displayed. ");
			}
			else{
				CustomReporter.errorLog("The bar chart is not getting displayed in HVA page");
				BaseClass.testCaseStatus=false;
				rm.captureScreenShot("BarChartNotGettingDisplayed_7947", "fail");
			}	
		}
		catch(Exception e){
			
			BaseClass.testCaseStatus = false;
			e.printStackTrace(System.out);
			CustomReporter.errorLog("Some exception is generated, " + e.getMessage() + ". Please check the log for more details");
			rm.captureScreenShot("7947", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the Project");
				}
				
				//Step 20 - Click on logout
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
	    	
			
		
		
		