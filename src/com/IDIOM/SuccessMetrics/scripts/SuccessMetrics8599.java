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
 * <p> <b>Test Case Name:</b> *Success Matric: Search filtering: Verify search results for success metrics under any category </p>
 * <p> <b>Objective:</b> Verify search results for success metrics under any category </p>
 * <p> <b>Test Case ID:</b> http://qa.digitas.com/SpiraTest/523/TestCase/8599.aspx </p>
 * <p> <b>Module:</b> Audience Builder - Success Metrics</p>
 * @author Rohan Macwan
 * @since 10 May 2016
 *
 */
public class SuccessMetrics8599 extends BaseClass {

@Test
	
	public void	SuccessMetrics8599(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		boolean bProjectCreate = false;
		String strProjectName=property.getProperty("projectName8700");
		
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
												
			String strCommonExpectedSuccessMetricCategory1=property.getProperty("CommonExpectedSuccessMetricCategory1");
			String strCommonSearchCriteria1=property.getProperty("CommonSearchCriteria1");
			
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
			
			//Step 5 - Select any category of success metrics say Transaction
			CustomReporter.log("Select any category of success metrics say Transaction");
			
			audienceBuilder.selectMetricByName(strCommonExpectedSuccessMetricCategory1);
			
			//Step 6 - Click on  search icon to search any success metrics
			CustomReporter.log("Click on  search icon to search any success metric");
			
			audienceBuilder.performAction("searchbutton");
			
			//Step 7 - Type few characters of any word say "Purc" and hold for a while
			
			CustomReporter.log("Type few characters of any word say 'Purc' and hold for a while");
			audienceBuilder.inputSearchValue(strCommonSearchCriteria1);
			
			rm.webElementSync("pageload");
			
			Thread.sleep(4000);
			
			System.out.println("Result = " + audienceBuilder.verifySuccessMetricsSearchfunctionality(strCommonSearchCriteria1));
			
			if (!audienceBuilder.verifySuccessMetricsSearchfunctionality(strCommonSearchCriteria1))
				throw new IDIOMException("Searching does not work properly. Please refer relevant screen shot in Reports folder for more information ###8599_SearchingDoesNotWork");
			
			CustomReporter.log("Searching functions properly. Please refer screen shot 'SearchResult' in Reports folder");
			
			rm.captureScreenShot("SearchResult", "pass");
						
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
			rm.captureScreenShot("8599", "fail");
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
