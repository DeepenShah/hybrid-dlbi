package com.IDIOM.AudienceDefinition;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/**
 * <p> <b>Test Case Name:</b> *Common Elements - 2.c Population - Verify Population once filters are added </p>
 * <p> <b>Objective:</b> Verify Population once filters are added </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8588.aspx </p>
 * <p> <b>Module:</b> Audience Builder - Success Metrics</p>
 * @author Rohan Macwan
 * @since 16 May 2016
 *
 */

public class AudienceDefinition8588 extends BaseClass {

@Test
	
	public void	AudienceDefinition8588(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName="";
		AudienceBuilderPage abPage=null;
		
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
			
			String strAudienceAttributes1=property.getProperty("commonAudienceAttribute4");
			//String strAudienceAttributes=property.getProperty("commonAudienceAttribute4");
			
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
				throw new IDIOMException("Success Metrics Tab seems to be not available or there may be some issues on the page. ###8588_SuccessMetricsTabNotAvailable");
			
			CustomReporter.log("Success Metrics Tab is available and visible on the page and currently Active");
			
			
			//Step 8 - Verify after success metrics are added, the message that will appear
			rm.webElementSync(audienceBuilder.projectedTab,"visibility");
			rm.webElementSync(audienceBuilder.percentagePopulationValue,"visibility");
			
			int PopulationPercent_1=audienceBuilder.getPopulationPercentage();
			
			audienceBuilder.performAction("actualpopulationtab");
			Thread.sleep(3000);
			rm.webElementSync(audienceBuilder.ActualTab,"visibility");
			
			rm.webElementSync(audienceBuilder.percentagePopulationValue,"visibility");
			
			int PopulationPercent_Actual_Before=audienceBuilder.getPopulationPercentage();
			
			audienceBuilder.performAction("projectedtab");
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
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric1 + "' has not been added successfully. ###8588_didNotAddSuccessMetric");

			if (!strActualVal.contains(strExpectedSuccessMetric2.trim()))
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric2 + "' has not been added successfully. ###8588_2_didNotAddSuccessMetric");
			
			if (!strActualVal.contains(strExpectedSuccessMetric3.trim()))
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric3 + "' has not been added successfully. ###8588_3_didNotAddSuccessMetric");

			CustomReporter.log("Selected Success Metrics appear in centre area.");
						
			//Step9: Click on Audience Definition link from project dashboard
				
			audienceBuilder.performAction("gotoaudiencedefinition");
			
			Thread.sleep(3000);
			
			abPage = new AudienceBuilderPage(driver);
			rm.webElementSync(abPage.allMetricsLabel,"visibility");
			CustomReporter.log("Navigated to audience definition page");
			
			CustomReporter.log("Projected - Population before adding groups/queries - " + PopulationPercent_1);
			CustomReporter.log("Actual - Population before adding groups/queries - " + PopulationPercent_Actual_Before);
			
			//Step 10 - Select few attributes and add them
			abPage.selectAttributeOnAudienceDefinition(strAudienceAttributes1);
			abPage.performAction("addattribute");
			
			Thread.sleep(6000);
			
			//Step 11 - Verify Population once filters are added
			rm.webElementSync(abPage.projectedTab,"visibility");
			rm.webElementSync(abPage.percentagePopulationValue,"visibility");
			
			int PopulationPercent_2=abPage.getPopulationPercentage();
			
			abPage.performAction("actualpopulationtab");
			Thread.sleep(6000);
			
			rm.webElementSync(abPage.ActualTab,"visibility");		
			rm.webElementSync(abPage.percentagePopulationValue,"visibility");
			
			int PopulationPercent_Actual_After=abPage.getPopulationPercentage();
			
			abPage.performAction("projectedtab");
			rm.webElementSync(abPage.projectedTab,"visibility");
			
			CustomReporter.log("Projected - Population after adding groups/queries - " + PopulationPercent_2);
			CustomReporter.log("Actual - Population after adding groups/queries - " + PopulationPercent_Actual_After);
			
			if (!(PopulationPercent_1!=PopulationPercent_2))
				throw new IDIOMException("Projected - Population before adding groups/queries and after adding them matches. ###8588_Population_did_Not_Match_Projected");
			
			CustomReporter.log("Projected - Population before adding groups/queries and after adding them does not match");
			
			
			if (!(PopulationPercent_Actual_Before!=PopulationPercent_Actual_After))
				throw new IDIOMException("Actual - Population before adding groups/queries and after adding matches. ###8588_Population_did_Not_Match_Actual");
			
			CustomReporter.log("Actual - Population before adding groups/queries and after adding them do not match");
			
			
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
			rm.captureScreenShot("8588", "fail");
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
				
				//Step 12 - Click on logout
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
