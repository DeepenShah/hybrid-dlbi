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
 * <p> <b>Test Case Name:</b> *Success Metric_2.e.ii_Bottom Save Bar - Verify click on 'Remove all' link in bottom save bar. </p>
 * <p> <b>Objective:</b> To verify behaviour after clicking 'Remove all' </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8569.aspx </p>
 * <p> <b>Module:</b> Audience Builder - Success Metrics</p>
 * @author Rohan Macwan
 * @since 05 May 2016
 *
 */
public class SuccessMetrics8569 extends BaseClass {
@Test
	
	public void	SuccessMetrics8569(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName=property.getProperty("projectName8700");
		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			
			String strSuccessMetrics = property.getProperty("SelectingSuccessMetric8569");
			String strSuccessMetrics_2 = property.getProperty("SelectingSuccessMetric2_8569");
			String strSuccessMetrics_3 = property.getProperty("SelectingSuccessMetric3_8569");
			
			
			String strExpectedSuccessMetric1=property.getProperty("ExpectedSuccessMetric1_8569");
			String strExpectedSuccessMetric2=property.getProperty("ExpectedSuccessMetric2_8569");
			String strExpectedSuccessMetric3=property.getProperty("ExpectedSuccessMetric3_8569");
			
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
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric1 + "' has not been added successfully. ###8569_didNotAddSuccessMetric");

			if (!strActualVal.contains(strExpectedSuccessMetric2.trim()))
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric2 + "' has not been added successfully. ###8569_2_didNotAddSuccessMetric");
			
			if (!strActualVal.contains(strExpectedSuccessMetric3.trim()))
				throw new IDIOMException("Success Metric '" + strExpectedSuccessMetric3 + "' has not been added successfully. ###8569_3_didNotAddSuccessMetric");

			if (!audienceBuilder.isVisible("removealllink", ""))
				throw new IDIOMException("Remove All Link is not present at the right hand side bottom of the page ###8569_4_RemoveAllLinkNotPresent");
			
			if (!(audienceBuilder.getCountForSuccessMetricsOrAudienceDefinition()>0))
				throw new IDIOMException("Total Count for added Metrics is not present at the right hand side bottom of the page ###8569_4_RemoveAllLinkNotPresent");

			CustomReporter.log("Selected Success Metrics appear in centre area. Bottom save bar with Total Count of saved metrics are displayed. Below that 'Remove All' link is available.");
			
			//Step 6 - Click on 'Remove all' 
			
			CustomReporter.log("Remove All Success Metrics");
			
			audienceBuilder.removeAllSuccessMetricsOrAudienceDefinitionQueries();
			
			rm.webElementSync(audienceBuilder.noSuccessMetricText,"visibility");
						
			if (!audienceBuilder.isVisible("nosuccessmetrictext", ""))
				throw new IDIOMException("The default message in center area is not present. There seems to be Success Metrics still present or some other issues are there. ###8569_messageNotDisplayedInCenterArea");
			
			CustomReporter.log("Message has correctly shown in Center Area after the removal of Success Metrics");

						
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
			rm.captureScreenShot("8569", "fail");
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
