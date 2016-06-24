package com.IDIOM.Analyse.HVA.scripts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.IDIOM.pages.AudienceBuilderPage;
import com.IDIOM.pages.ClientList_Page;
import com.IDIOM.pages.HVA_Page;
import com.IDIOM.pages.Login_Page;
import com.IDIOM.pages.ProjectDashboardPage;
import com.reports.CustomReporter;
import common.BaseClass;
import common.IDIOMException;

/**
 * <p> <b>Test Case Name:</b> *2611: Verify selected behavior should display by default in HVA </p>
 * <p> <b>Objective:</b> This test case will verify that selected behavior is appearing by default in HVA page </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8902.aspx </p>
 * <p> <b>Module:</b> HVA </p>
 * @author Rohan Macwan
 * @since 19 May 2016
 *
 */
public class HVA8902 extends BaseClass {

@Test
	
	public void	verifyAllBehaviorAvailability(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName="";
		String strDetails ="";
		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			
			String strSuccessMetrics = property.getProperty("CommonSelectingSuccessMetric");
			String strSuccessMetrics_2 = property.getProperty("CommonSelectingSuccessMetric2");
						
			String strExpectedSuccessMetric1=property.getProperty("CommonExpectedSuccessMetric1");
			String strExpectedSuccessMetric2=property.getProperty("CommonExpectedSuccessMetric2");
			
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
			
			//Step 3- Select any client from client drop down
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			
			//Step 4 - Click on new project button
			//Step 5 - Enter valid project name and description and click on save button 
			strProjectName = clientListPage.createNewProject("");
			bProjectCreate=true;
			
			strDetails=clientListPage.getAudienceProjectClientId(strProjectName, "");
			
			//Step 6 - Now click on launch project
			clientListPage.launchProject(strProjectName);
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			//Step 7 - Click on success metrics
			System.out.println("Open Success Metrics page");
			CustomReporter.log("Open Success Metrics page");
			
			pdp.navigateTo(property.getProperty("successMetrics"));
			
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);

			CustomReporter.log("Check whether Success Metrics Tab is Active when Audience Builder Section gets loaded");
					
			if (!((audienceBuilder.isVisible("selectedactivetab", "") && (audienceBuilder.SelectedActiveTab.getText().trim().toLowerCase().equalsIgnoreCase("Success Metrics")))))
				throw new IDIOMException("Success Metrics Tab seems to be not available or there may be some issues on the page. ###8902_SuccessMetricsTabNotAvailable");
			
			CustomReporter.log("Success Metrics Tab is available and visible on the page and currently Active");
			
			
			//Step 8 - select single success metrics 
			CustomReporter.log("Select one Success Metric");
			audienceBuilder.selectMetricByName(strSuccessMetrics);
						
			List<String> strlistOfSelectedSuccessMetricsNames = audienceBuilder.getNameOfAllMetricCards();
			String strActualVal = strlistOfSelectedSuccessMetricsNames.toString().toLowerCase();
			System.out.println("Actual Val - " + strActualVal);
			
			if (!strActualVal.contains(strExpectedSuccessMetric1.trim()))
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric1 + "' has not been added successfully. ###8902_didNotAddSuccessMetric");

			CustomReporter.log("Selected Success Metrics appear in centre area.");

			List<String> strSelectedMetricCards = audienceBuilder.getNameOfAllMetricCards();
			
			CustomReporter.log("strSelectedMetricCards - " + strSelectedMetricCards);
			
			List<String> strExpData = new ArrayList();
			
			for(String strCat : strSelectedMetricCards){				
				strExpData.add(strCat);
			}

			//Step 9 - Navigate to HVA page
			CustomReporter.log("Navigate to HVA page");
			pageHeader.megaMenuLinksClick(property.getProperty("hva"));;
			
			HVA_Page hva = new HVA_Page(driver);
			
			hva.isVisible("slowdown");
			
			if(!rm.webElementSync(hva.behaviour,"visibility"))
				throw new IDIOMException("Behavior dropdown is not available or there might be other issues on the page. ###8902_BehaviorsDropdownNotVisible");
			
			//hva.behaviour.click();
								
			String strSelectedBehavior = hva.func_getText("Behaviour");
			
			CustomReporter.log("Now try to find each Success Metric in behavior dropdown in HVA page");
			
			boolean bStatus=false;
			for (String key : strExpData) 
			{
				CustomReporter.log("Current behavior of Success Metrics - '" + key.trim() + "'" );
				if(strSelectedBehavior.equals(key.trim()))
				{
					CustomReporter.log("Behavior - '" + key.trim() + "' in Success Metrics is by default selected in Behavior dropdown on HVA page" );
					bStatus=true;
				}
			}
			
			if (bStatus==false)
			{
				throw new IDIOMException("Behavior dropdown is not defaulted to the expected behavior. ###8902_DefaultValueInBehaviorDropdown");
			}
			
			//##########################################################################
			
			//Step 10 - Navigate to success metrics and  Remove all success metrics
			CustomReporter.log("Navigate to Success Metrics page");
			pageHeader.megaMenuLinksClick("Success Metrics");
			
			rm.webElementSync(audienceBuilder.removeAllLink,"visibility");
			
			CustomReporter.log("Remove all Success Metrics");
			audienceBuilder.removeAllSuccessMetricsOrAudienceDefinitionQueries();
			
			audienceBuilder.allMetricsLabel.click();
			List<String> strFirstLevelCat = audienceBuilder.getCategoryNamesAtAnyLevel();
			
			CustomReporter.log("strFirstLevelCat - " + strFirstLevelCat);
			
			List<String> strAllSuccessMetrics = new ArrayList();
			
			for(String strCat : strFirstLevelCat){				
				audienceBuilder.selectMetricByName(strCat);				
				strAllSuccessMetrics.addAll(audienceBuilder.getCategoryNamesAtAnyLevel());
			}
			
			//Step 11 - Navigate to HVA page
			pageHeader.megaMenuLinksClick(property.getProperty("hva"));;
			CustomReporter.log("Navigate to HVA page and check whether any success metric appears in hva drop down which is valid");
			
			if(!rm.webElementSync(hva.behaviour,"visibility"))
				throw new IDIOMException("Behavior dropdown is not available or there might be other issues on the page. ###8902_BehaviorsDropdownNotVisible");
			
			strSelectedBehavior = hva.func_getText("Behaviour");
			
			if (!strAllSuccessMetrics.contains(strSelectedBehavior.trim()))
				throw new IDIOMException("No default value is noticed in behavior dropdown on HVA Behavior dropdown or there might be other issues on the page. ###8902_NoDefaultValue");
			
			CustomReporter.log("Valid Success Metric -'" + strSelectedBehavior + "' appears as selected");
			
			//##########################################################################
			
			//Step 12 - Navigate to success metrics and select 2 metrics
			CustomReporter.log("Go to Success Metrics page");
			pageHeader.megaMenuLinksClick("Success Metrics");
			
			CustomReporter.log("Select two Success Metrics");
			audienceBuilder.selectMetricByName(strSuccessMetrics);
			
			audienceBuilder.selectMetricByName(strSuccessMetrics_2);
			
			rm.webElementSync("listsize", "2", audienceBuilder.successMetricCardsName);
			
			strlistOfSelectedSuccessMetricsNames = audienceBuilder.getNameOfAllMetricCards();
			strActualVal = strlistOfSelectedSuccessMetricsNames.toString().toLowerCase();
			System.out.println("Actual Val - " + strActualVal);
			
			if (!strActualVal.contains(strExpectedSuccessMetric1.trim()))
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric1 + "' has not been added successfully. ###8902_didNotAddSuccessMetric");

			if (!strActualVal.contains(strExpectedSuccessMetric2.trim()))
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric2 + "' has not been added successfully. ###8902_2_didNotAddSuccessMetric");
			
			CustomReporter.log("Selected Success Metrics appear in centre area.");
			
			strSelectedMetricCards = audienceBuilder.getNameOfAllMetricCards();
			
			CustomReporter.log("strSelectedMetricCards - " + strSelectedMetricCards);
			
			strExpData = new ArrayList();
			
			for(String strCat : strSelectedMetricCards){				
				strExpData.add(strCat);
			}

			//Step 13 - Now go to HVA page and check the same behavior
			pageHeader.megaMenuLinksClick(property.getProperty("hva"));;
			
			if(!rm.webElementSync(hva.behaviour,"visibility"))
				throw new IDIOMException("Behavior dropdown is not available or there might be other issues on the page. ###8902_BehaviorsDropdownNotVisible");
			
			
			//hva.behaviour.click();
								
			strSelectedBehavior = hva.func_getText("Behaviour");
			
			CustomReporter.log("Navigate to HVA page and check whether any one the Metrics comes as selected which are added in Success Metrics");
			
			bStatus=false;
			for (String key : strExpData) 
			{
				CustomReporter.log("Current behavior of Success Metrics - '" + key.trim() + "'" );
				if(strSelectedBehavior.equals(key.trim()))
				{
					CustomReporter.log("Behavior - '" + key.trim() + "' in Success Metrics is by default selected in Behavior dropdown on HVA page" );
					bStatus=true;
				}
			}
			
			if (bStatus==false)
			{
				throw new IDIOMException("Behavior dropdown is not defaulted to the any of the Success Metrics which have been selected on Success Metrics page. ###8902_DefaultValueInBehaviorDropdownTwo");
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
			rm.captureScreenShot("8902", "fail");
		}finally{
			try{
				
				//Step for Deleting newly created project
				if(bProjectCreate){
					rm.deleteProjectOrAudience(strDetails, true);
					CustomReporter.log("Deleted the Project");
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
