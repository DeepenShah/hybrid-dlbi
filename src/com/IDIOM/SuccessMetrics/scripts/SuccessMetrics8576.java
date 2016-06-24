package com.IDIOM.SuccessMetrics.scripts;

import java.util.List;

import org.openqa.selenium.Point;
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
<p>	<b>Test Case Name:<b> Common Elements - 2.b Population - Verify the Population Panel of what all it consists of.
<p>	<b>Objective:<b>  Verify the Population Panel of what all it consists of.
<p>	<b>Test Case ID:<b> http://qa.digitas.com/SpiraTest/523/TestCase/8576.aspx
<p>	<b>Module:<b> Success Metrics
@author:<b> Abhishek Deshpande
@since:<b> 17-May-2016
**********************************************************************/

public class SuccessMetrics8576 extends BaseClass {
	
	@Test
	public void verifyPopulationPanel(){
		String strMsg = null;
		ClientList_Page clientListPage=null;
		String strProjectName="Test Case 8576";
		boolean bProjectCreate = false;
		try{
			//****************Variables declaration and Initialization****************************	
			String strEmailId = property.getProperty("SuperAdminUser");
			String strPassword = property.getProperty("SuperAdminpassword");
			String strClientName=property.getProperty("clientName");
			String strProjectDescription=property.getProperty("projectDescription");			
			
			//****************Test step starts here************************************************
			
			clientListPage = new ClientList_Page(driver);
			ProjectDashboardPage pdp = new ProjectDashboardPage(driver);
			Login_Page ln = new Login_Page(driver);
			AudienceBuilderPage successMetricPage = new AudienceBuilderPage(driver);
			
			//Step 1: Open IDIOM URL and login with valid credentials
			Thread.sleep(3000);
	        ln.func_LoginToIDIOM(strEmailId, strPassword);
	        
	        Thread.sleep(5000);
			rm.webElementSync("pageload");
			rm.webElementSync("jqueryload");
			
			clientListPage.selectClient(strClientName);
			strMsg = "Selected '"+strClientName+"' client successfully.";
			CustomReporter.log(strMsg);			
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			//count total number of projects
			int totalProjects = clientListPage.totalProject();
			System.out.println("Total projects: "+totalProjects);
			
			System.out.println("Create new project");
			CustomReporter.log("Create new project");	
			clientListPage.func_PerformAction("New Project");
			
			
			rm.webElementSync(clientListPage.newProjectWindow, "visibility");
			strMsg = "Create New Project Window appeared successfully";
			CustomReporter.log(strMsg);
			System.out.println(getClass().getSimpleName() + ": " + strMsg);
			
			clientListPage.findAndSaveProjectWindow(true, "");
			
			clientListPage.fillProject(strProjectName,strProjectDescription);				
			clientListPage.func_PerformAction("Save Project");
			Thread.sleep(2000);
			clientListPage.func_PerformAction("Launch Project");
			bProjectCreate=true;
			
			rm.webElementSync(pdp.projectName, "visibility");
			Thread.sleep(2000);			
			CustomReporter.log("Project dashboard page is open");			
			
			//Step 4: Click on destination link success metrics
			CustomReporter.log("Navigate to success metrics page");
			pdp.navigateTo("Success Metrics");
			AudienceBuilderPage audienceBuilder = new AudienceBuilderPage(driver);
			
			CustomReporter.log("Verify success metrics page is open");
			if(!audienceBuilder.isVisible("successmetricstexttitle", ""))
				throw new IDIOMException("Success metrics page is not visible###testcase8576-pageIsNotVisible");
			
			//Verify success metric page.
			successMetricPage.isVisible("nosuccessmetrictext","");
			CustomReporter.log("Successfully landed on Success Metric page");		
			
			//Step 5: Navigate to Audience definition page			
			successMetricPage.performAction("gotoaudiencedefinition");
			rm.webElementSync(audienceBuilder.allMetricsLabel,"verifytext", "ALL ATTRIBUTES");
			CustomReporter.log("Navigated to Audience Definition tab");	
			
			//Step 6: Verify population panel
			if(! audienceBuilder.verifyPopulationGraph() && audienceBuilder.verifyPopulationPercentage())
				throw new IDIOMException("Issue with Audience definition population panel###testcase8576-populationpanel");
			CustomReporter.log("Verified population panel");
			
			//Step 7: Go back to Success Metrics page
			audienceBuilder.performAction("gotosuccessmetric");
			CustomReporter.log("Navigate to Success Metrics page");
			
			//Verify default value of population
			if(!(audienceBuilder.getPopulationPercentage()==100))
				throw new IDIOMException("Audience definition default population value is not 100%###testcase8576-populationpercentage");
			CustomReporter.log("Verified default population value in audience defnition tab" + audienceBuilder.getPopulationPercentage() );
			
				
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
			rm.captureScreenShot("AnalysePagesIssue", "fail");
		}finally{
			try{
				
				//Deleting newly created project
				if(bProjectCreate){
					pageHeader.navigateTo("projecthomepage");
					rm.webElementSync(pageHeader.personMenu,"visibility");
					
					clientListPage.func_PerformAction("Close Project");
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
		
	