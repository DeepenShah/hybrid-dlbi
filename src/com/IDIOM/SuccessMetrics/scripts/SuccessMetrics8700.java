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
 * <p> <b>Test Case Name:</b> *1969_AddingSuccessMatrics_Verification </p>
 * <p> <b>Objective:</b> Verify success metrics is getting added  successfully </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8700.aspx </p>
 * <p> <b>Module:</b> Audience Builder - Success Metrics</p>
 * @author Rohan Macwan
 * @since 04 May 2016
 *
 */
public class SuccessMetrics8700  extends BaseClass{

@Test
	
	public void	SuccessMetrics8700(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName=property.getProperty("projectName8700");
		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strProjectDescription=property.getProperty("projectDescription");
			String strSuccessMetrics = property.getProperty("SelectingSuccessMetric8700");
			String strSuccessMetrics_2 = property.getProperty("SelectingSuccessMetric2_8700");
			String strSuccessMetrics_3 = property.getProperty("SelectingSuccessMetric3_8700");
			String strRemoveSuccessMetric8700 = property.getProperty("removeSuccessMetric8700");
			String strExpectedRemovedAndAddedSuccessMetric8700 = property.getProperty("expectedRemovedAndAddedSuccessMetric8700");
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
			
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			
			//Step 2, 3 - Select any existing client from client dropdown. Click on any project/Create a project and launch the same
		
			strProjectName = clientListPage.createNewProject("");
			bProjectCreate=true;
			clientListPage.launchProject(strProjectName);
			
				
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);
			System.out.println("Project dashboard page is open");
			CustomReporter.log("Project dashboard page is open");
			
			//Step 4 - Click on destination link success metrics and verify message that appears in center.
			System.out.println("Open Success Metrics page");
			CustomReporter.log("Open Success Metrics page");
			
			pdp.navigateTo(property.getProperty("successMetrics"));
			
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);
			
			
			//Step 5 - Select some success metrics
			CustomReporter.log("Select few Success Metrics");
			audienceBuilder.selectMetricByName(strSuccessMetrics);
			
			audienceBuilder.selectMetricByName(strSuccessMetrics_2);
			
			audienceBuilder.selectMetricByName(strSuccessMetrics_3);
			

			
			//Step 5 - Remove one success metric
			CustomReporter.log("Remove succees Metic = '" + strExpectedRemovedAndAddedSuccessMetric8700 + "'");
			audienceBuilder.removeSuccessMetricCardByName(strRemoveSuccessMetric8700);
			Thread.sleep(2000);
			List<String> strlistOfSelectedSuccessMetricsNames = audienceBuilder.getNameOfAllMetricCards();
			
			String strActualVal = strlistOfSelectedSuccessMetricsNames.toString().toLowerCase();			
			System.out.println("Remove :" + strActualVal);
			if (strActualVal.contains(strExpectedRemovedAndAddedSuccessMetric8700.trim()))
				throw new IDIOMException("Success Metric has not been removed successfully. ###8700_didNotRemoveSuccessMetric");
			
			CustomReporter.log("Succees Metic = '" + strRemoveSuccessMetric8700 + "' has successfully been removed");
			
			////Step 5 - Add same success metric which we removed before 
			strlistOfSelectedSuccessMetricsNames.clear();
			
			CustomReporter.log("Add succees Metric = '" + strExpectedRemovedAndAddedSuccessMetric8700 + "'");
			audienceBuilder.selectMetricByName(strSuccessMetrics);
			
			
			strlistOfSelectedSuccessMetricsNames=audienceBuilder.getNameOfAllMetricCards();
			
			strActualVal = strlistOfSelectedSuccessMetricsNames.toString().toLowerCase();
			System.out.println("Again " +strActualVal);
			if (!strActualVal.contains(strExpectedRemovedAndAddedSuccessMetric8700.trim()))
				throw new IDIOMException("Success Metric has not been added successfully. ###8700_didNotAddSuccessMetric");
			
			CustomReporter.log("Message has correctly disappeared from Center Area after the selection of Success Metrics");
			
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
			rm.captureScreenShot("8700", "fail");
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
				
				//Step 6 - Click on logout 
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
